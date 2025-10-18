package org.overdrive.recetasdigitales.model.relaciones;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.overdrive.recetasdigitales.model.entidades.Paso;
import org.overdrive.recetasdigitales.model.entidades.Receta;

import java.util.List;

public class RecetaPasos {

    @Embedded
    public Receta receta; // Es la entidad padre

    @Relation(
            parentColumn = "idReceta",
            entityColumn = "idReceta_fk" // clave foranea de la entidad hija
    )

    // Los N valores de la entidad hija
    public List<Paso> pasos;
}

