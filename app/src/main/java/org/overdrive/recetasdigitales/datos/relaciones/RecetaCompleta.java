package org.overdrive.recetasdigitales.datos.relaciones;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.overdrive.recetasdigitales.datos.entidades.Ingrediente;
import org.overdrive.recetasdigitales.datos.entidades.Paso;
import org.overdrive.recetasdigitales.datos.entidades.Receta;

import java.util.List;

public class RecetaCompleta {

    @Embedded
    public Receta receta;

    @Relation(
            parentColumn = "idReceta",
            entityColumn = "idReceta_fk"
    )
    public List<Ingrediente> ingredientes;

    @Relation(
            parentColumn = "idReceta",
            entityColumn = "idReceta_fk"
    )
    public List<Paso> pasos;
}
