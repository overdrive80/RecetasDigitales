package org.overdrive.recetasdigitales.view.listarecetas;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import org.overdrive.recetasdigitales.databinding.ActivityRecetasBinding;
import org.overdrive.recetasdigitales.model.entidades.Receta;
import org.overdrive.recetasdigitales.viewmodel.RecetarioViewModel;

import java.util.ArrayList;

public class RecetasActivity extends AppCompatActivity {
    private RecetarioViewModel viewModel;
    private ActivityRecetasBinding binding;
    private RecetasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecetasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        inicializarViewModel();
        configurarRecyclerView();
        configurarSearchView();
        configurarObservadores();
        configurarFab();

    }



    private void configurarSearchView() {
        binding.svRecetas.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.setFiltroBusqueda(query.trim());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.setFiltroBusqueda(newText.trim());
                return true;
            }
        });
    }

    private void configurarObservadores() {
        // Observamos cambios en la lista de recetas
        viewModel.getTodasRecetas().observe(this, recetas -> {
            // Evitamos crear un nuevo adapter por cada cambio en Room
            adapter.actualizarDatos(recetas);
        });

        // Observamos los cambios al filtrar recetas
        viewModel.recetasFiltradas.observe(this, recetas -> {
            adapter.actualizarDatos(recetas);
        });
    }

    private void configurarRecyclerView() {
        adapter = new RecetasAdapter(new ArrayList<>(), getOnClickRecetaListener());
        binding.rvRecetas.setAdapter(adapter);
    }

    private void inicializarViewModel() {
        //Para testear la creación de la base de datos antes de implementar Patron Repositorio
        //Recetario basedatos = Recetario.getInstance(getApplicationContext());
        this.viewModel = new ViewModelProvider(this)
                .get(RecetarioViewModel.class);
    }

    // Implementación del listener al hacer clic sobre un item del recyclerview
    private RecetasAdapter.OnClickItemListener getOnClickRecetaListener() {
        return new RecetasAdapter.OnClickItemListener() {
            @Override
            public void onClickReceta(Receta receta) {
                viewModel.setRecetaSeleccionada(receta);

                // Android recomienda no crear constructores con parametros en Fragments
                RecetasBottomSheet bottomSheet = new RecetasBottomSheet();
                bottomSheet.setOnClickOpcionListener(getOnClickOpcionListener());
                bottomSheet.show(getSupportFragmentManager(), "RecetasBottomSheet");
            }
        };
    }

    // Implementación del listener sobre las opciones del bottomSheet
    private RecetasBottomSheet.OnClickOpcionListener getOnClickOpcionListener() {
        return new RecetasBottomSheet.OnClickOpcionListener() {
            @Override
            public void onVerReceta(Receta receta) {
                //Abrir activity para ver receta
                Toast.makeText(RecetasActivity.this, "Ver receta " + receta.getTitulo(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onModificarReceta(Receta receta) {
                //Abrir activity para modificar receta

                Toast.makeText(RecetasActivity.this, "Modificar receta " + receta.getTitulo(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onEliminarReceta(Receta receta) {
                //Eliminar receta
                String titulo = receta.getTitulo();

                viewModel.borrarReceta(receta);

                Snackbar.make(binding.getRoot(), titulo + " eliminada.", Snackbar.LENGTH_LONG).show();
            }
        };
    }

    private void configurarFab() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(binding.fab)
                        .setAction("Action", null).show();
            }
        });
    }
}