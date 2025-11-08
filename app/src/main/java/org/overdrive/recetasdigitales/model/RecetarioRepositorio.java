package org.overdrive.recetasdigitales.model;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.overdrive.recetasdigitales.model.dao.IngredienteDAO;
import org.overdrive.recetasdigitales.model.dao.PasoDAO;
import org.overdrive.recetasdigitales.model.dao.RecetaDAO;
import org.overdrive.recetasdigitales.model.entidades.Ingrediente;
import org.overdrive.recetasdigitales.model.entidades.Paso;
import org.overdrive.recetasdigitales.model.entidades.Receta;
import org.overdrive.recetasdigitales.model.relaciones.RecetaCompleta;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecetarioRepositorio {
    private static final String TAG = RecetarioRepositorio.class.getSimpleName();

    //Guardar instancia de la base de datos
    private final Recetario mdb;
    private final IngredienteDAO mIngredienteDAO;
    private final RecetaDAO mRecetaDAO;
    private final PasoDAO mPasoDAO;

    private final Context appContext;

    public RecetarioRepositorio(Context contexto) {
        this.appContext = contexto;
        this.mdb = Recetario.getInstance(contexto);

        // Invocamos la implementación interna de los DAO por Room
        this.mRecetaDAO = mdb.recetaDAO();
        this.mIngredienteDAO = mdb.ingredienteDAO();
        this.mPasoDAO = mdb.pasoDAO();

    }

    public LiveData<List<Receta>> getTodasRecetas() {
        return mRecetaDAO.getTodasRecetas();
    }

    public LiveData<List<Receta>> buscarPortitulo(String texto) {
        return mRecetaDAO.buscarPortitulo(texto);
    }

    public void borrarReceta(Receta receta) {
        Recetario.servicioExecutor.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        eliminarImagen(receta.getImagenUri());
                        mRecetaDAO.borrarReceta(receta);
                    }
                }
        );
    }

    public LiveData<RecetaCompleta> getRecetaCompleta(long recetaId) {
        return mRecetaDAO.getRecetaCompleta(recetaId);
    }

    public long insertarReceta(Receta receta) {
        return mRecetaDAO.insertarReceta(receta);
    }

    private void persistirRecetaCompleta(Receta receta,
                                         List<Ingrediente> ingredientes,
                                         List<Paso> pasos,
                                         Uri imagenUriTemporal) {

        String rutaFinalImagen = copiarImagenAlmacenamientoInterno(imagenUriTemporal);
        receta.setImagenUri(rutaFinalImagen);

        long recetaId = mRecetaDAO.insertarReceta(receta);
        receta.setIdReceta(recetaId);

        for (Ingrediente ing : ingredientes) ing.setIdReceta(recetaId);
        mIngredienteDAO.insertarIngredientes(ingredientes);

        for (int i = 0; i < pasos.size(); i++) {
            Paso paso = pasos.get(i);
            paso.setIdReceta(recetaId);
            paso.setOrden(i + 1);
        }
        mPasoDAO.insertarPasos(pasos);
    }

    public void insertarRecetaCompleta(Receta receta,
                                       List<Ingrediente> ingredientes,
                                       List<Paso> pasos,
                                       Uri imagenUriTemporal,
                                       Runnable onFinish) {
        Recetario.servicioExecutor.execute(() -> {
            mdb.runInTransaction(() -> persistirRecetaCompleta(receta, ingredientes, pasos, imagenUriTemporal));

            if (onFinish != null) {
                new Handler(Looper.getMainLooper()).post(onFinish);
            }
        });
    }

    public void reemplazarRecetaCompleta(long idOriginal,
                                         Receta receta,
                                         List<Ingrediente> ingredientesNuevos,
                                         List<Paso> pasosNuevos,
                                         Uri imagenUriTemporal,
                                         Runnable onFinish) {
        Recetario.servicioExecutor.execute(() -> {
            mdb.runInTransaction(() -> {

                //1. Sacamos receta original solo para conocer su URI real
                Receta recetaOriginal = mRecetaDAO.getReceta(idOriginal);
                String imagenOriginal = recetaOriginal.getImagenUri();

                //2. Borrar la imagen solo si el usuario realmente eligió una nueva
                if (imagenUriTemporal != null && imagenOriginal != null) {
                    eliminarImagen(imagenOriginal);
                }

                //3. Borramos la receta completa
                mRecetaDAO.borrarRecetaPorId(idOriginal);

                //4. Insertamos la nueva con su imagen correspondiente
                persistirRecetaCompleta(receta, ingredientesNuevos, pasosNuevos, imagenUriTemporal);

            });

            if (onFinish != null) {
                new Handler(Looper.getMainLooper()).post(onFinish);
            }
        });
    }

    //Gestion de imagenes
    private String copiarImagenAlmacenamientoInterno(Uri uriTemp) {
        if (uriTemp == null) return null;

        try {
            // Crear nombre único
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                    .format(new Date());
            String nombreArchivo = "RECETA_" + timeStamp + ".jpg";

            // Directorio privado de la app. Si no existe se crea.
            File directorio = new File(appContext.getFilesDir(), "imagenes_recetas");
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            //Creamos el archivo a persistir
            File archivoDestino = new File(directorio, nombreArchivo);

            // Copiar archivo con buffer. Usamos try-with-resources para cerrar archivos al finalizar
            try (InputStream in = appContext.getContentResolver().openInputStream(uriTemp);
                 OutputStream out = new FileOutputStream(archivoDestino)) {
                byte[] buffer = new byte[1024];
                int length;

                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            }

            // Devolvemos la uri
            String ruta = archivoDestino.getAbsolutePath();
            return "file://" + ruta;

        } catch (Exception e) {
            Log.e(TAG, "Error copiando imagen: " + e.getMessage());
            return null;
        }
    }

    // Metodo para eliminar imagenes de recetas modificadas
    public void eliminarImagen(String imagenPath) {
        if (imagenPath == null) return;

        // Eliminamos prefijo file:// para que File encuentre el archivo
        if (imagenPath.startsWith("file://")) {
            imagenPath = imagenPath.substring("file://".length());
        }

        File imagenFile = new File(imagenPath);
        if (imagenFile.exists()) {
            imagenFile.delete();
        }
    }
}
