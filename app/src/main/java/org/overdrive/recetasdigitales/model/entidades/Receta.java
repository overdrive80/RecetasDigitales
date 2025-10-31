package org.overdrive.recetasdigitales.model.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.overdrive.recetasdigitales.Constantes;

/**
 * Entidad: Recetas
 * Definimos la entidad que se corresponde con una tabla en SQLite/Room
 *
 * @author Israel Lucas Torrijos
 * @version 1.0
 * @since 14/10/2025
 */
@Entity(tableName = Constantes.TABLA_RECETAS)
public class Receta {

    @PrimaryKey(autoGenerate = true)
    private long idReceta;

    @ColumnInfo(defaultValue = "Sin título")
    private String titulo;

    private String descripcion;

    @ColumnInfo(name = "imagen")
    private String imagenUri;

    private long tiempo;

    //Constructores
    @Ignore
    public Receta() {
    }

    public Receta(String titulo, String descripcion, String imagenUri, long tiempo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagenUri = imagenUri;
        this.tiempo = tiempo;
    }


    //Getter-Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenUri() {
        return imagenUri;
    }

    public void setImagenUri(String imagenUri) {
        this.imagenUri = imagenUri;
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }

    public long getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(long idReceta) {
        this.idReceta = idReceta;
    }
}
