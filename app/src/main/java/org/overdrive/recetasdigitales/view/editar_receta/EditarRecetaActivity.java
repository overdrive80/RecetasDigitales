package org.overdrive.recetasdigitales.view.editar_receta;

import static org.overdrive.recetasdigitales.Constantes.RECETA_ID;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.overdrive.recetasdigitales.view.crear_receta.CrearRecetaActivity;

public class EditarRecetaActivity extends CrearRecetaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long idReceta = getIntent().getLongExtra(RECETA_ID, -1);

        if (idReceta == -1) {
            finish();
            return;
        }

        viewModel.cargarRecetaParaEditar(idReceta, this);
    }

    @Override
    protected void configurarToolbar() {
        super.configurarToolbar();
        setTitle("Editar receta");
    }
}
