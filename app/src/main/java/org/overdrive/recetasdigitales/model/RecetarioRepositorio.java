package org.overdrive.recetasdigitales.model;

import android.content.Context;

import org.overdrive.recetasdigitales.model.dao.IngredienteDAO;
import org.overdrive.recetasdigitales.model.dao.PasoDAO;
import org.overdrive.recetasdigitales.model.dao.RecetaDAO;

public class RecetarioRepositorio {
    private static final String TAG = RecetarioRepositorio.class.getSimpleName();

    //Guardar instancia de la base de datos
    private final Recetario mdb;
    private final IngredienteDAO mIngredienteDAO;
    private final RecetaDAO mRecetaDAO;
    private final PasoDAO mPasoDAO;

    public RecetarioRepositorio(Context contexto) {
        this.mdb = Recetario.getInstance(contexto);

        // Invocamos la implementación interna de los DAO por Room
        this.mRecetaDAO = mdb.recetaDAO();
        this.mIngredienteDAO = mdb.ingredienteDAO();
        this.mPasoDAO = mdb.pasoDAO();

    }
}
