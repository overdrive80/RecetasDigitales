package org.overdrive.recetasdigitales.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import org.overdrive.recetasdigitales.model.RecetarioRepositorio;
import org.overdrive.recetasdigitales.model.entidades.Receta;

import java.util.List;

import kotlin.jvm.functions.Function1;

/**
 * ViewModel para Recetario
 * <p>
 * Sobrevive a recreaciones por eso hay que persistir los datos para que no se pierdan
 */
public class RecetasViewModel extends AndroidViewModel {
    private final RecetarioRepositorio repo;
    private final MutableLiveData<String> filtroBusqueda = new MutableLiveData<>("");
    public LiveData<List<Receta>> recetasFiltradas;
    private MutableLiveData<Receta> recetaSeleccionada = new MutableLiveData<>();

    public RecetasViewModel(@NonNull Application application) {
        super(application);

        repo = new RecetarioRepositorio(application);

        // Transforma un LiveData de entrada por otro adaptado al criterio de busqueda
        recetasFiltradas = Transformations.switchMap(filtroBusqueda, new Function1<String, LiveData<List<Receta>>>() {
                    @Override
                    public LiveData<List<Receta>> invoke(String texto) {
                        return repo.buscarPortitulo(texto);
                    }
                }
        );

    }

    public LiveData<List<Receta>> getTodasRecetas() {
        return repo.getTodasRecetas();
    }


    public void setFiltroBusqueda(String texto) {
        filtroBusqueda.setValue(texto);
    }

    // Asignar receta seleccionada
    public void setRecetaSeleccionada(Receta receta) {
        recetaSeleccionada.setValue(receta);
    }

    public LiveData<Receta> getRecetaSeleccionada() {
        return recetaSeleccionada;
    }

    public void borrarReceta(Receta receta) {
        repo.borrarReceta(receta);
    }
}
