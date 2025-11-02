package org.overdrive.recetasdigitales.viewmodel;

import android.app.Application;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.overdrive.recetasdigitales.model.RecetarioRepositorio;
import org.overdrive.recetasdigitales.model.entidades.Ingrediente;
import org.overdrive.recetasdigitales.model.entidades.Paso;
import org.overdrive.recetasdigitales.model.entidades.Receta;

import java.util.ArrayList;
import java.util.List;

public class CrearRecetaViewModel extends AndroidViewModel {

    private final RecetarioRepositorio repo;
    private MutableLiveData<Receta> receta = new MutableLiveData<>();
    private MutableLiveData<List<Ingrediente>> ingredientes = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<List<Paso>> pasos = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<Uri> imagenUriTemporal = new MutableLiveData<>();

    //Editando elemento recyclerview de Ingredientes
    private MutableLiveData<Ingrediente> ingredienteSeleccionado = new MutableLiveData<>();
    private MutableLiveData<Integer> posicionIngredienteEditando = new MutableLiveData<>(-1);
    private final Context appContext;

    public CrearRecetaViewModel(@NonNull Application application) {
        super(application);
        appContext = application;
        repo = new RecetarioRepositorio(application);
    }


    public void setReceta(Receta receta) {
        this.receta.setValue(receta);
    }

    public MutableLiveData<Receta> getReceta() {
        return receta;
    }


    public void setImagenUri(Uri uri) {
        imagenUriTemporal.setValue(uri);
    }

    public MutableLiveData<Uri> getImagenUri() {
        return imagenUriTemporal;

    }

    /**
     * Lista de ingredientes
     **/
    public MutableLiveData<List<Ingrediente>> getIngredientes() {
        return ingredientes;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        List<Ingrediente> listaActual = ingredientes.getValue();

        listaActual.add(ingrediente);

        // Para realizar la notificacion se debe pasar la lista al MutableLiveData
        ingredientes.setValue(listaActual);
    }

    public void setIngredientes(MutableLiveData<List<Ingrediente>> ingredientes) {
        this.ingredientes = ingredientes;
    }

    /**
     * Seleccion de ingrediente en el Recycler
     **/
    public void setIngredienteSeleccionado(Ingrediente ingrediente) {
        ingredienteSeleccionado.setValue(ingrediente);
    }

    public MutableLiveData<Ingrediente> getIngredienteSeleccionado() {
        return ingredienteSeleccionado;
    }


    public void actualizarIngrediente(Ingrediente ingredienteEditado) {
        Integer pos = posicionIngredienteEditando.getValue();

        if (pos != null && pos >= 0) {
            List<Ingrediente> listaActual = new ArrayList<>(ingredientes.getValue());
            listaActual.set(pos, ingredienteEditado);
            ingredientes.setValue(listaActual);
        }
    }

    public void setPosicionIngredienteEditando(int pos) {
        posicionIngredienteEditando.setValue(pos);
    }

    public MutableLiveData<Integer> getPosicionIngredienteEditando() {
        return posicionIngredienteEditando;
    }

    public void eliminarIngrediente(int posicion) {
        List<Ingrediente> listaActual = ingredientes.getValue();

        if (listaActual != null && posicion >= 0 && posicion < listaActual.size()) {
            // Creamos una nueva lista para que LiveData detecte el cambio y actualice la UI
            List<Ingrediente> listaModificada = new ArrayList<>(listaActual);
            listaModificada.remove(posicion);

            ingredientes.setValue(listaModificada);
        }
    }
}
