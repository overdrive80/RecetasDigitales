package org.overdrive.recetasdigitales.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.overdrive.recetasdigitales.model.RecetarioRepositorio;
import org.overdrive.recetasdigitales.model.entidades.Ingrediente;
import org.overdrive.recetasdigitales.model.entidades.Paso;
import org.overdrive.recetasdigitales.model.entidades.Receta;
import org.overdrive.recetasdigitales.model.relaciones.RecetaCompleta;

import java.util.List;

public class CrearRecetaViewModel extends AndroidViewModel {

    private final RecetarioRepositorio repo;
    private LiveData<RecetaCompleta> recetaCompleta;
    private MutableLiveData<Receta> receta = new MutableLiveData<>();
    private MutableLiveData<List<Ingrediente>> ingredientes = new MutableLiveData<>();
    private MutableLiveData<List<Paso>> pasos = new MutableLiveData<>();

    public CrearRecetaViewModel(@NonNull Application application) {
        super(application);

        repo = new RecetarioRepositorio(application);
    }

    public void setNuevoIngrediente(Ingrediente ingrediente){
        ingredientes.getValue().add(ingrediente);
    }
}
