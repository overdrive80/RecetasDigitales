package org.overdrive.recetasdigitales.datos.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.overdrive.recetasdigitales.Constantes;

/**
 * Entidad: Ingredientes
 * Definimos la entidad que se corresponde con una tabla en SQLite/Room
 *
 * @author Israel Lucas Torrijos
 * @version 1.0
 * @since 14/10/2025
 */

@Entity(tableName = Constantes.TABLA_INGREDIENTES,
        foreignKeys = {
                @ForeignKey(
                        entity = Receta.class,
                        parentColumns = "idReceta",
                        childColumns = "idReceta_fk",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        })
public class Ingrediente {

    @PrimaryKey(autoGenerate = true)
    private long idIngrediente;

    @ColumnInfo
    private String nombre;

    @ColumnInfo(defaultValue = "0")
    private double cantidad;

    @ColumnInfo
    private String unidad;

    @NonNull
    @ColumnInfo(name = "idReceta_fk", index = true)
    private long idReceta;

    //Constructor
    public Ingrediente(String nombre, double cantidad, String unidad, long idReceta) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.idReceta = idReceta;
    }

    //Getter-Setter
    public long getIdIngrediente() {
        return idIngrediente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public long getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(long idReceta) {
        this.idReceta = idReceta;
    }
}
