package org.overdrive.recetasdigitales.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.overdrive.recetasdigitales.model.RecetarioRepositorio;
import org.overdrive.recetasdigitales.model.entidades.Receta;

import java.util.List;

public class RecetarioViewModel extends AndroidViewModel {
    private final RecetarioRepositorio repo;


    public RecetarioViewModel(@NonNull Application application) {
        super(application);

        this.repo = new RecetarioRepositorio(application);

    }

    public LiveData<List<Receta>> getTodasRecetas() {
        return this.repo.getTodasRecetas();
    }
}
