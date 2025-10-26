package org.overdrive.recetasdigitales.model;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.overdrive.recetasdigitales.Constantes;
import org.overdrive.recetasdigitales.model.dao.IngredienteDAO;
import org.overdrive.recetasdigitales.model.dao.PasoDAO;
import org.overdrive.recetasdigitales.model.dao.RecetaDAO;
import org.overdrive.recetasdigitales.model.entidades.Ingrediente;
import org.overdrive.recetasdigitales.model.entidades.Paso;
import org.overdrive.recetasdigitales.model.entidades.Receta;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Base de datos. Debe cumplir los siguientes requisitos:
 * 1. Anotación @Database
 * 2. Es una clase abstracta y extiende a RoomDatabase
 * 3. Metodo abstracto por cada DAO
 */
@Database(
        entities = {Receta.class, Ingrediente.class, Paso.class},
        version = 1,
        exportSchema = false)
public abstract class Recetario extends RoomDatabase {
    private static final String TAG = "RecetarioBBDD";

    // Exponer DAO
    public abstract RecetaDAO recetaDAO();

    public abstract IngredienteDAO ingredienteDAO();

    public abstract PasoDAO pasoDAO();

    //Patrón Singleton con multithilo
    private static volatile Recetario INSTANCIA;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService servicioExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Si hacemos al metodo synchronized: solo crea 1 instancia de la BD
    public static Recetario getInstance(final Context context) {

        if (INSTANCIA == null) {
            synchronized (Recetario.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = crearInstancia(context);

                    inicializar();
                }
            }
        }

        return INSTANCIA;
    }

    /**
     * Fuerza la creación de la base de datos.
     * La base de datos no se crea hasta que la primera operación de acceso.
     */
    private static void inicializar() {
        //Fuerza la creación de la base de datos
        servicioExecutor.execute(() -> {
            INSTANCIA.getOpenHelper().getWritableDatabase();
        });
    }

    // Metodo para crear la instancia
    private static Recetario crearInstancia(Context context) {
        Log.d(TAG, "Creando instancia de la base de datos");
        Recetario db = Room.databaseBuilder(context, Recetario.class, Constantes.BASEDATOS)
                .allowMainThreadQueries()
                .addCallback(crearCallback(context))
                //.fallbackToDestructiveMigrationOnDowngrade(true)
                .build();

        return db;
    }

    private static RoomDatabase.Callback crearCallback(Context appContext) {
        return new RoomDatabase.Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Log.d(TAG, "Base de datos creada, poblando datos...");

                servicioExecutor.execute(() -> {
                    poblarBaseDatos(INSTANCIA, appContext);
                });
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
                Log.d(TAG, "Base de datos abierta");
            }
        };
    }


    private static void poblarBaseDatos(Recetario database, Context applicationContext) {
        if (database == null) return;

        try {
            /** Primero entidades fuertes. Recetas **/
            String uriImg1 = "android.resource://" +
                    applicationContext.getPackageName() + "/drawable/tortilla_patatas";

            Receta r1 = new Receta("Tortilla de patatas",
                    "Clásica receta española a base de patata y huevo.", uriImg1, 45);
            Receta r2 = new Receta("Receta 2", "Descripción 2", "Sin imagen", 75);

            // Insertar recetas
            long rowId1 = database.recetaDAO().insertarReceta(r1);
            long rowId2 = database.recetaDAO().insertarReceta(r2);

            /** Segundo entidades débiles. Ingredientes **/
            List<Ingrediente> ingredientes = Arrays.asList(
                    new Ingrediente("Huevos", 6.0, "ud/s", rowId1),
                    new Ingrediente("Patatas", 3.0, "ud/s", rowId1),
                    new Ingrediente("Aceite", 50.0, "ml", rowId1),
                    new Ingrediente("Sal", 3.0, "g", rowId1),
                    new Ingrediente("R2_Ingrediente 1", 50.0, "ml", rowId2),
                    new Ingrediente("R2_Ingrediente 2", 2, "cucharadas", rowId2)
            );

            // Insertar ingredientes
            database.ingredienteDAO().insertarIngredientes(ingredientes);

            /** Pasos **/
            List<Paso> pasos = Arrays.asList(
                    //Receta 1
                    new Paso(1, "Batir los huevos en un bol de crista y sazonar.", rowId1),
                    new Paso(2, "Freir las patatas en una sartén con abundante aceite. La temperatura del aceite debe estar media-baja" +
                            "para evitar crear corteza dura en la patata y no pueda absorber el huevo batido.", rowId1),
                    new Paso(3, "Incorporar la patata al bol con el huevo y remover hasta crear una mezcla homogénea", rowId1),
                    new Paso(4, "En caso de que la mezcla sea muy densa, echamos dos yemas batidas para dar cremosidad.", rowId1),
                    new Paso(5, "En un sartén con aceite caliente, incorporar la mezcla de huevo y patatas. " +
                            "Cuando veamos en los bordes como el huevo a cuajado, la damos la vuelta ayudandonos de un util que abarque" +
                            "el diametro de la sarten. La volvemos a incorporar con cuidado por el lado que no esta cuajada.", rowId1),

                    new Paso(1, "Mezclar ingredientes", rowId2)
            );

            // Insertar pasos
            database.pasoDAO().insertarPasos(pasos);

//            //Ejecutar como una unica transacción a nivel de POO
//            database.runInTransaction(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            });

        } catch (Exception e) {
            Log.e(TAG, "Error al poblar BD: " + e.getMessage());
        }
    }

    public static void anularInstancia() {
        INSTANCIA = null;
    }
}
