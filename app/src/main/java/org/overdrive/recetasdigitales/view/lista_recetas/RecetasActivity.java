package org.overdrive.recetasdigitales.view.lista_recetas;

import static org.overdrive.recetasdigitales.Constantes.RECETA_ID;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.overdrive.recetasdigitales.databinding.ActivityRecetasBinding;
import org.overdrive.recetasdigitales.model.entidades.Receta;
import org.overdrive.recetasdigitales.view.crear_receta.CrearRecetaActivity;
import org.overdrive.recetasdigitales.view.editar_receta.EditarRecetaActivity;
import org.overdrive.recetasdigitales.view.ver_receta.VerRecetaActivity;
import org.overdrive.recetasdigitales.viewmodel.RecetasViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecetasActivity extends AppCompatActivity {
    private RecetasViewModel viewModel;
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
                .get(RecetasViewModel.class);
    }

    // Implementación del listener al hacer clic sobre un item del recyclerview
    private RecetasAdapter.OnClickItemListener getOnClickRecetaListener() {
        return new RecetasAdapter.OnClickItemListener() {
            @Override
            public void onClickReceta(int posicion) {
                // Obtenemos la lista con los datos filtrados
                List<Receta> listaActual = viewModel.recetasFiltradas.getValue();

                // Verificamos que no sea nula
                if (listaActual != null && posicion != RecyclerView.NO_POSITION) {

                    Receta receta = listaActual.get(posicion);
                    viewModel.setRecetaSeleccionada(receta);

                    // Android recomienda no crear constructores con parametros en Fragments
                    RecetasBottomSheet bottomSheet = new RecetasBottomSheet();
                    bottomSheet.setOnClickOpcionListener(getOnClickOpcionListener());
                    bottomSheet.show(getSupportFragmentManager(), RecetasBottomSheet.TAG);
                }
            }
        };
    }

    // Implementación del listener sobre las opciones del bottomSheet
    private RecetasBottomSheet.OnClickOpcionListener getOnClickOpcionListener() {
        return new RecetasBottomSheet.OnClickOpcionListener() {
            @Override
            public void onVerReceta(Receta receta) {
                //Abrir activity para ver receta pasandole el id
                Intent intent = new Intent(RecetasActivity.this, VerRecetaActivity.class);
                intent.putExtra(RECETA_ID, receta.getIdReceta());
                startActivity(intent);
            }

            @Override
            public void onModificarReceta(Receta receta) {
                //Abrir activity para modificar receta
                Intent intent = new Intent(RecetasActivity.this, EditarRecetaActivity.class);
                intent.putExtra(RECETA_ID, receta.getIdReceta());
                startActivity(intent);

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
        binding.fabCrearReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abrir activity para crear receta
                Intent intent = new Intent(RecetasActivity.this, CrearRecetaActivity.class);
                startActivity(intent);
            }
        });
    }
}