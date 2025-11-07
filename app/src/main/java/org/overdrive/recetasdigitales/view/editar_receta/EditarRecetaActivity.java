package org.overdrive.recetasdigitales.view.editar_receta;

import static org.overdrive.recetasdigitales.Constantes.RECETA_ID;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavDestination;

import org.overdrive.recetasdigitales.R;
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

    @Override
    public boolean onSupportNavigateUp() {
        NavDestination destination = navController.getCurrentDestination();

        if (destination != null && destination.getId() == R.id.portadaFragment) {
            // Si estamos en la portada mostrar dialogo
            new AlertDialog.Builder(this)
                    .setTitle("Cancelar modificación de receta")
                    .setMessage("¿Deseas salir del asistente?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Si", (dialog, which) -> finish())
                    .show();

            return true; //Evitamos que se siga propagando el evento
        }

        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
