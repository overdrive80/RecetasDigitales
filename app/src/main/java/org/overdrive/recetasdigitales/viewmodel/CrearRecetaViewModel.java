package org.overdrive.recetasdigitales.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.overdrive.recetasdigitales.model.RecetarioRepositorio;
import org.overdrive.recetasdigitales.model.entidades.Ingrediente;
import org.overdrive.recetasdigitales.model.entidades.Paso;
import org.overdrive.recetasdigitales.model.entidades.Receta;
import org.overdrive.recetasdigitales.model.relaciones.RecetaCompleta;

import java.util.ArrayList;
import java.util.List;

public class CrearRecetaViewModel extends AndroidViewModel {

    private final RecetarioRepositorio repo;
    private MutableLiveData<Receta> receta = new MutableLiveData<>();
    private MutableLiveData<List<Ingrediente>> ingredientes = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<List<Paso>> pasos = new MutableLiveData<>(new ArrayList<>());

    public CrearRecetaViewModel(@NonNull Application application) {
        super(application);

        repo = new RecetarioRepositorio(application);
    }

    public void agregarIngrediente(Ingrediente ingrediente) {
        ingredientes.getValue().add(ingrediente);
    }

    public void setReceta(Receta receta) {
        this.receta.setValue(receta);
    }

    public MutableLiveData<Receta> getReceta() {
        return receta;
    }


}
