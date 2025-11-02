package org.overdrive.recetasdigitales.model.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
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
    private Long idIngrediente;

    @ColumnInfo
    private String nombre;

    @ColumnInfo(defaultValue = "0")
    private Double cantidad;

    @ColumnInfo
    private String unidad;

    @NonNull
    @ColumnInfo(name = "idReceta_fk", index = true)
    private Long idReceta;

    //Constructor
    @Ignore
    public Ingrediente() {
    }

    public Ingrediente(String nombre, Double cantidad, String unidad, Long idReceta) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.idReceta = idReceta;
    }

    //Getter-Setter
    public Long getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Long idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Long getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Long idReceta) {
        this.idReceta = idReceta;
    }
}
