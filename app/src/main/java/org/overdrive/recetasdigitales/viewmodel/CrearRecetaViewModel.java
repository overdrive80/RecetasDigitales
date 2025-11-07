package org.overdrive.recetasdigitales.viewmodel;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.overdrive.recetasdigitales.model.RecetarioRepositorio;
import org.overdrive.recetasdigitales.model.entidades.Ingrediente;
import org.overdrive.recetasdigitales.model.entidades.Paso;
import org.overdrive.recetasdigitales.model.entidades.Receta;
import org.overdrive.recetasdigitales.model.relaciones.RecetaCompleta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrearRecetaViewModel extends AndroidViewModel {

    private final RecetarioRepositorio repo;
    private final MutableLiveData<Receta> receta = new MutableLiveData<>();
    private final MutableLiveData<List<Ingrediente>> ingredientes = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<Paso>> pasos = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Uri> imagenUriTemporal = new MutableLiveData<>();

    private final MutableLiveData<Integer> posicionIngredienteEditando = new MutableLiveData<>(-1);
    private final MutableLiveData<Integer> posicionPasoEditando = new MutableLiveData<>(-1);

    public CrearRecetaViewModel(@NonNull Application application) {
        super(application);
        repo = new RecetarioRepositorio(application);
    }

    // RECETA
    public void setReceta(Receta receta) { this.receta.setValue(receta); }
    public MutableLiveData<Receta> getReceta() { return receta; }

    public void setImagenUri(Uri uri) { imagenUriTemporal.setValue(uri); }
    public MutableLiveData<Uri> getImagenUri() { return imagenUriTemporal; }

    // INGREDIENTES
    public MutableLiveData<List<Ingrediente>> getIngredientes() { return ingredientes; }

    public void setIngrediente(Ingrediente ingrediente) {
        //Creamos siempre una lista nueva. Eso asegura que el liveData notifique cambios.
        List<Ingrediente> lista = new ArrayList<>(ingredientes.getValue());
        lista.add(ingrediente);
        ingredientes.setValue(lista);
    }

    public void actualizarIngrediente(Ingrediente ingredienteEditado) {
        Integer pos = posicionIngredienteEditando.getValue();
        if (pos != null && pos >= 0) {
            List<Ingrediente> lista = new ArrayList<>(ingredientes.getValue());
            lista.set(pos, ingredienteEditado);
            ingredientes.setValue(lista);
        }
    }

    public void eliminarIngrediente(int posicion) {
        List<Ingrediente> listaActual = ingredientes.getValue();
        if (listaActual == null || listaActual.isEmpty()) return;
        if (posicion < 0 || posicion >= listaActual.size()) return;

        List<Ingrediente> nueva = new ArrayList<>(listaActual);
        nueva.remove(posicion);
        ingredientes.setValue(nueva);
    }

    public void setPosicionIngredienteEditando(int posicion) { posicionIngredienteEditando.setValue(posicion); }
    public MutableLiveData<Integer> getPosicionIngredienteEditando() { return posicionIngredienteEditando; }

    // PASOS
    public MutableLiveData<List<Paso>> getPasos() { return pasos; }

    public void setPaso(Paso paso) {
        List<Paso> lista = new ArrayList<>(pasos.getValue());
        lista.add(paso);
        pasos.setValue(lista);
    }

    public void actualizarPaso(Paso pasoEditado) {
        Integer pos = posicionPasoEditando.getValue();
        if (pos != null && pos >= 0) {
            List<Paso> lista = new ArrayList<>(pasos.getValue());
            lista.set(pos, pasoEditado);
            pasos.setValue(lista);
        }
    }

    public void eliminarPaso(int posicion) {
        List<Paso> listaActual = pasos.getValue();
        if (listaActual == null || listaActual.isEmpty()) return;
        if (posicion < 0 || posicion >= listaActual.size()) return;

        List<Paso> nueva = new ArrayList<>(listaActual);
        nueva.remove(posicion);
        pasos.setValue(nueva);
    }

    public void setPosicionPasoEditando(int posicion) { posicionPasoEditando.setValue(posicion); }
    public MutableLiveData<Integer> getPosicionPasoEditando() { return posicionPasoEditando; }

    public void moverPaso(int from, int to) {
        List<Paso> lista = new ArrayList<>(pasos.getValue());
        Paso movido = lista.remove(from);
        lista.add(to, movido);
        pasos.setValue(lista);
    }

    // Gestion de persistencia //
    private final MutableLiveData<Boolean> recetaGuardada = new MutableLiveData<>();
    public LiveData<Boolean> getRecetaGuardada() { return recetaGuardada; }

    public void guardarRecetaCompleta() {
        Receta r = receta.getValue();
        List<Ingrediente> ing = ingredientes.getValue();
        List<Paso> ps = pasos.getValue();
        Uri uri = imagenUriTemporal.getValue();

        repo.insertarRecetaCompleta(r, ing, ps, uri, () -> {
            // Esto se ejecuta cuando el repo finaliza en background
            recetaGuardada.postValue(true);
        });
    }

    // Cuando se modifica una receta
    public void cargarRecetaParaEditar(long recetaId, @NonNull LifecycleOwner owner) {

        repo.getRecetaCompleta(recetaId).observe(owner, recetaCompleta -> {
            if (recetaCompleta == null) return;

            // Muy importante: crear nuevos objetos para que LiveData emita
            receta.setValue(recetaCompleta.receta);

            ingredientes.setValue(new ArrayList<>(recetaCompleta.ingredientes));
            pasos.setValue(new ArrayList<>(recetaCompleta.pasos));

            if (recetaCompleta.receta.getImagenUri() != null) {
                imagenUriTemporal.setValue(Uri.parse(recetaCompleta.receta.getImagenUri()));
            }
        });
    }



}

