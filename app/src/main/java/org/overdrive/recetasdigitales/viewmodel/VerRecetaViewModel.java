package org.overdrive.recetasdigitales.viewmodel;

import static org.overdrive.recetasdigitales.Constantes.RECETA_ID;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

import org.overdrive.recetasdigitales.model.RecetarioRepositorio;
import org.overdrive.recetasdigitales.model.relaciones.RecetaCompleta;

public class VerRecetaViewModel extends AndroidViewModel {

    private RecetarioRepositorio repo;
    private LiveData<RecetaCompleta> recetaCompleta;
    private SavedStateHandle savedStateHandle;

    // Evidentemente cuando se reconstruye se ejecuta su constructor
    public VerRecetaViewModel(@NonNull Application application, @NonNull SavedStateHandle savedStateHandle) {
        super(application);
        this.savedStateHandle = savedStateHandle;

        repo = new RecetarioRepositorio(application);

        //Al reconstruir el fragment se recupera el id de la receta y la receta completa.
        if (savedStateHandle.contains(RECETA_ID)) {
            long idReceta = savedStateHandle.get(RECETA_ID);
            recetaCompleta = repo.getRecetaCompleta(idReceta);
        }
    }

    public VerRecetaViewModel(Application application) {
        super(application);
        repo = new RecetarioRepositorio(application);
    }

    public void cargarRecetaCompleta(long recetaId) {
        recetaCompleta = repo.getRecetaCompleta(recetaId);

    }

    public LiveData<RecetaCompleta> getRecetaCompleta() {
        return recetaCompleta;
    }

    public void init(long recetaId) {
        // Sólo cargar si aún no se ha cargado (evita recarga innecesaria)
        if (recetaCompleta != null) {
            return;
        }

        //Guardamos el estado del fragment con el idReceta seleccionada
        savedStateHandle.set(RECETA_ID, recetaId);
        recetaCompleta = repo.getRecetaCompleta(recetaId);
    }
}
