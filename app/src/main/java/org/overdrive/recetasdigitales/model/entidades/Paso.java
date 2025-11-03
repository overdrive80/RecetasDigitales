package org.overdrive.recetasdigitales.model.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
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
    private long idPaso;

    private int orden;
    private String descripcion;

    @ColumnInfo(name = "idReceta_fk", index = true)
    private long idReceta;

    //Constructor
    @Ignore
    public Paso() {    }

    public Paso(int orden, String descripcion, long idReceta) {
        this.orden = orden;
        this.descripcion = descripcion;
        this.idReceta = idReceta;
    }

    //Getters y Setters
    public long getIdPaso() {
        return idPaso;
    }

    public void setIdPaso(long idPaso) {
        this.idPaso = idPaso;
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

    public long getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(long idReceta) {
        this.idReceta = idReceta;
    }
}
