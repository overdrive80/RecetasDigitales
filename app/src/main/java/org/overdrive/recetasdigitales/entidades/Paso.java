package org.overdrive.recetasdigitales.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.overdrive.recetasdigitales.Constantes;

/**
 * Entidad: Pasos
 * Definimos la entidad que se corresponde con una tabla en SQLite/Room
 *
 * @author Israel Lucas Torrijos
 * @version 1.0
 * @since 14/10/2025
 */

@Entity(tableName = Constantes.TABLA_PASOS,
        foreignKeys = {
                @ForeignKey(
                        entity = Receta.class,
                        parentColumns = "idReceta",
                        childColumns = "idReceta_fk",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        })
public class Paso {

    @PrimaryKey(autoGenerate = true)
    private int idPaso;

    private int orden;
    private String descripcion;

    @ColumnInfo(name = "idReceta_fk")
    private int idReceta;

    //Constructor
    public Paso(int orden, String descripcion, int idReceta) {
        this.orden = orden;
        this.descripcion = descripcion;
        this.idReceta = idReceta;
    }

    //Getters y Setters
    public int getIdPaso() {
        return idPaso;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }
}
