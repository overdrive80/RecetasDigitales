package org.overdrive.recetasdigitales.relaciones;


import androidx.room.Embedded;
import androidx.room.Relation;

import org.overdrive.recetasdigitales.entidades.Ingrediente;
import org.overdrive.recetasdigitales.entidades.Receta;

import java.util.List;

public class RecetaIngredientes {

    @Embedded
    public Receta receta; // Es la entidad padre

    @Relation(
            parentColumn = "idReceta",
            entityColumn = "idReceta_fk" // clave foranea de la entidad hija
    )

    // Los N valores de la entidad hija
    public List<Ingrediente> ingredientes;
}
