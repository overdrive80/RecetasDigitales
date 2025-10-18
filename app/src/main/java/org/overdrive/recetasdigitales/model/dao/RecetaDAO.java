package org.overdrive.recetasdigitales.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import org.overdrive.recetasdigitales.model.entidades.Receta;
import org.overdrive.recetasdigitales.model.relaciones.RecetaCompleta;

import java.util.List;

// En tu RecetaDAO
@Dao
public interface RecetaDAO {

    // Recuperar receta completa por id
    @Transaction
    @Query("SELECT * FROM recetas WHERE idReceta = :id")
    LiveData<RecetaCompleta> getRecetaCompleta(int id);

    // Recuperar todas recetas completas
    @Transaction
    @Query("SELECT * FROM recetas")
    LiveData<List<RecetaCompleta>> getTodasRecetasCompletas();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertarReceta(Receta receta);
}
