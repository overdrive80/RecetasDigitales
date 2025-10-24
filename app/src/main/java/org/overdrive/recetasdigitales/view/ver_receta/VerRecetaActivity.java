package org.overdrive.recetasdigitales.view.ver_receta;

import static org.overdrive.recetasdigitales.Constantes.RECETA_ID;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import org.overdrive.recetasdigitales.databinding.ActivityVerRecetaBinding;
import org.overdrive.recetasdigitales.viewmodel.VerRecetaViewModel;

public class VerRecetaActivity extends AppCompatActivity {
    private VerRecetaViewModel viewModel;
    private ActivityVerRecetaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVerRecetaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        inicializarViewModel();
        recuperarReceta();
        configurarToolbar();
        habilitarBotonRetroceso();
    }

    private void recuperarReceta() {
        Intent intent = getIntent();

        if (intent == null) return; //Si no tiene receta, es complicado que ocurra pero por si.

        long idReceta = intent.getLongExtra(RECETA_ID, 0);

        //Pasamos el idReceta al viewModel. La idea es que si se reconstruye sepa que receta mostrar
        viewModel.init(idReceta);

    }

    private void inicializarViewModel() {
        this.viewModel = new ViewModelProvider(this)
                .get(VerRecetaViewModel.class);
    }

    private void configurarToolbar() {
        setSupportActionBar(binding.appBarLayout.toolbar);
        setTitle("Ver receta");
    }

    private void habilitarBotonRetroceso() {
        // Habilitar botón de retroceso
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Manejar el clic en el botón de retroceso de la toolbar
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}