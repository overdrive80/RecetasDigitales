package org.overdrive.recetasdigitales.datos;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.overdrive.recetasdigitales.Constantes;
import org.overdrive.recetasdigitales.datos.dao.IngredienteDAO;
import org.overdrive.recetasdigitales.datos.dao.PasoDAO;
import org.overdrive.recetasdigitales.datos.dao.RecetaDAO;
import org.overdrive.recetasdigitales.datos.entidades.Ingrediente;
import org.overdrive.recetasdigitales.datos.entidades.Paso;
import org.overdrive.recetasdigitales.datos.entidades.Receta;

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
        version = 0,
        exportSchema = false)
public abstract class Recetario extends RoomDatabase {
    private static final String TAG = "RecetarioBBDD";

    // Exponer DAO
    public abstract RecetaDAO recetaDAO();

    public abstract IngredienteDAO ingredienteDAO();

    public abstract PasoDAO pasoDAO();

    // Singleton

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
        return Room.databaseBuilder(context, Recetario.class, Constantes.BASEDATOS)
                .allowMainThreadQueries()
                .addCallback(accionesCicloVida)
                .build();
    }

    // Interceptar el ciclo de vida BBDD mediante callback
    static RoomDatabase.Callback accionesCicloVida = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d(TAG, "Base de datos creada, poblando datos...");

            // Poblar la base de datos en un hilo worker
            // para evitar bloquear el hilo principal
            servicioExecutor.execute(() -> {
                poblarBaseDatos(Recetario.INSTANCIA);
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.d(TAG, "Base de datos abierta");
        }
    };

    private static void poblarBaseDatos(Recetario database) {
        if (database == null) return;

        try {
            /** Primero entidades fuertes. Recetas **/
            Receta r1 = new Receta("Receta 1", "Descripción 1", "Sin imagen", 0);
            Receta r2 = new Receta("Receta 2", "Descripción 2", "Sin imagen", 0);

            // Insertar recetas
            long rowId1 = database.recetaDAO().insertarReceta(r1);
            long rowId2 = database.recetaDAO().insertarReceta(r2);

            /** Segundo entidades débiles. Ingredientes **/
            List<Ingrediente> ingredientes = Arrays.asList(
                    new Ingrediente("R1_Ingrediente 1", 100.0, "g", rowId1),
                    new Ingrediente("R1_Ingrediente 2", 1.0, "ud", rowId1),
                    new Ingrediente("R2_Ingrediente 1", 50.0, "ml", rowId2),
                    new Ingrediente("R2_Ingrediente 2", 2, "cucharadas", rowId2)
            );

            // Insertar ingredientes
            database.ingredienteDAO().insertarIngredientes(ingredientes);

            /** Pasos **/
            List<Paso> pasos = Arrays.asList(
                    new Paso(1, "Mezclar ingredientes", rowId1),
                    new Paso(2, "Hornear 30 minutos", rowId1),
                    new Paso(1, "Batir los huevos", rowId2)
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
