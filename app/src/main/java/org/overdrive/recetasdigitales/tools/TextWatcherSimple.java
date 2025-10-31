package org.overdrive.recetasdigitales.tools;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class TextWatcherSimple implements TextWatcher {

    // Para eludir la obligación de implementar metodos de la interfaz TextWatcher
    // Solo obligamos a implementar este metodo
    @Override
    public abstract void afterTextChanged(android.text.Editable s);

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}
