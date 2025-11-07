package org.overdrive.recetasdigitales.view.crear_receta;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.overdrive.recetasdigitales.R;
import org.overdrive.recetasdigitales.databinding.ActivityCrearRecetaBinding;
import org.overdrive.recetasdigitales.viewmodel.CrearRecetaViewModel;

public class CrearRecetaActivity extends AppCompatActivity {
    private ActivityCrearRecetaBinding binding;
    protected CrearRecetaViewModel viewModel;
    protected NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCrearRecetaBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        inicializarViewModel();
        configurarToolbar();
        configurarNavegacion();
        vincularToolbarConNavegacion();

    }

    private void vincularToolbarConNavegacion() {
        // Al no definir destino raíz, el fragmento inicial muestra flecha up
        // ya que existe un destino anterior, la activity que la llamó.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder().build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    private void configurarNavegacion() {

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(binding.navHostFragmentCrear.getId());

        navController = navHostFragment.getNavController();
    }

    private void inicializarViewModel() {
        this.viewModel = new ViewModelProvider(this)
                .get(CrearRecetaViewModel.class);
    }

    protected void configurarToolbar() {
        setSupportActionBar(binding.appBarLayout.toolbar);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavDestination destination = navController.getCurrentDestination();

        if (destination != null && destination.getId() == R.id.portadaFragment) {
            // Si estamos en la portada mostrar dialogo
            new AlertDialog.Builder(this)
                    .setTitle("Cancelar creación de receta")
                    .setMessage("¿Deseas salir del asistente?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Si", (dialog, which) -> finish())
                    .show();

            return true; //Evitamos que se siga propagando el evento
        }

        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}