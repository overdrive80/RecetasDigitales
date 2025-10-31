package org.overdrive.recetasdigitales.model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import org.overdrive.recetasdigitales.model.dao.IngredienteDAO;
import org.overdrive.recetasdigitales.model.dao.PasoDAO;
import org.overdrive.recetasdigitales.model.dao.RecetaDAO;
import org.overdrive.recetasdigitales.model.entidades.Receta;
import org.overdrive.recetasdigitales.model.relaciones.RecetaCompleta;

import java.util.List;

public class RecetarioRepositorio {
    private static final String TAG = RecetarioRepositorio.class.getSimpleName();

    //Guardar instancia de la base de datos
    private final Recetario mdb;
    private final IngredienteDAO mIngredienteDAO;
    private final RecetaDAO mRecetaDAO;
    private final PasoDAO mPasoDAO;

    public RecetarioRepositorio(Context contexto) {
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
                () -> mRecetaDAO.borrarReceta(receta)
        );
    }

    public LiveData<RecetaCompleta> getRecetaCompleta(long recetaId) {
        return mRecetaDAO.getRecetaCompleta(recetaId);
    }

    public long insertarReceta(Receta receta) {
        return mRecetaDAO.insertarReceta(receta);
    }
}
