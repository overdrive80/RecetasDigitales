package org.overdrive.recetasdigitales.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import org.overdrive.recetasdigitales.model.entidades.Paso;
import org.overdrive.recetasdigitales.model.relaciones.RecetaPasos;

import java.util.List;

@Dao
public interface PasoDAO {

    @Insert
    void insertarPasos(List<Paso> pasos);

    // Recuperar pasos de una receta
    @Transaction
    @Query("SELECT * FROM recetas WHERE idReceta = :id")
    LiveData<RecetaPasos> getRecetaConPasos(long id);

    @Insert
    void insertarPaso(Paso paso);
}
