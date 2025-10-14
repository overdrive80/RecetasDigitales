package org.overdrive.recetasdigitales.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import org.overdrive.recetasdigitales.relaciones.RecetaCompleta;
import org.overdrive.recetasdigitales.relaciones.RecetaIngredientes;
import org.overdrive.recetasdigitales.relaciones.RecetaPasos;

import java.util.List;

// En tu RecetaDAO
@Dao
public interface RecetaDAO {

    // Recuperar receta con sus ingredientes
    @Transaction
    @Query("SELECT * FROM recetas WHERE idReceta = :id")
    LiveData<RecetaIngredientes> getRecetaConIngredientes(int id);

    // Recuperar receta con sus pasos
    @Transaction
    @Query("SELECT * FROM recetas WHERE idReceta = :id")
    LiveData<RecetaPasos> getRecetaConPasos(int id);

    // Recuperar receta completa: receta + ingredientes + pasos
    @Transaction
    @Query("SELECT * FROM recetas WHERE idReceta = :id")
    LiveData<RecetaCompleta> getRecetaCompleta(int id);
}
