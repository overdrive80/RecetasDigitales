package org.overdrive.recetasdigitales.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.overdrive.recetasdigitales.model.RecetarioRepositorio;

public class RecetarioViewModel extends AndroidViewModel {
    private final RecetarioRepositorio repo;


    public RecetarioViewModel(@NonNull Application application) {
        super(application);

        this.repo = new RecetarioRepositorio(application);

    }
}
