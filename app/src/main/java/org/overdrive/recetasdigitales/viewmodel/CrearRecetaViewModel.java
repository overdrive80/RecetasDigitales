package org.overdrive.recetasdigitales.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.overdrive.recetasdigitales.model.RecetarioRepositorio;

public class CrearRecetaViewModel extends AndroidViewModel {

    private final RecetarioRepositorio repo;

    public CrearRecetaViewModel(@NonNull Application application) {
        super(application);

        repo = new RecetarioRepositorio(application);
    }
}
