package org.overdrive.recetasdigitales.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.overdrive.recetasdigitales.model.RecetarioRepositorio;
import org.overdrive.recetasdigitales.model.relaciones.RecetaCompleta;

public class VerRecetaViewModel extends AndroidViewModel {
    private RecetarioRepositorio repo;
    private LiveData<RecetaCompleta> recetaCompleta;

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
}
