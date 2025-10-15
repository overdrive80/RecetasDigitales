package org.overdrive.recetasdigitales.datos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import org.overdrive.recetasdigitales.datos.entidades.Paso;
import org.overdrive.recetasdigitales.datos.relaciones.RecetaPasos;

import java.util.List;

public interface PasoDAO {

    @Insert
    void insertarPasos(List<Paso> pasos);

    // Recuperar pasos de una receta
    @Transaction
    @Query("SELECT * FROM recetas WHERE idReceta = :id")
    LiveData<RecetaPasos> getRecetaConPasos(int id);
}
