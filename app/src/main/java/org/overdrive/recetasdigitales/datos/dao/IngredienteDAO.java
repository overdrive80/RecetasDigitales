package org.overdrive.recetasdigitales.datos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import org.overdrive.recetasdigitales.datos.entidades.Ingrediente;
import org.overdrive.recetasdigitales.datos.relaciones.RecetaIngredientes;

import java.util.List;

@Dao
public interface IngredienteDAO {

    @Insert
    void insertarIngredientes(List<Ingrediente> ingredientes);

    // Recuperar ingredientes de una receta
    @Transaction
    @Query("SELECT * FROM recetas WHERE idReceta = :id")
    LiveData<RecetaIngredientes> getRecetaConIngredientes(int id);
}
