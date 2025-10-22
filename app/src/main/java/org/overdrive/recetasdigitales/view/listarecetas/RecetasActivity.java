package org.overdrive.recetasdigitales.view.listarecetas;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.overdrive.recetasdigitales.databinding.ActivityRecetasBinding;
import org.overdrive.recetasdigitales.model.entidades.Receta;
import org.overdrive.recetasdigitales.tools.SearchViewTool;
import org.overdrive.recetasdigitales.viewmodel.RecetarioViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecetasActivity extends AppCompatActivity {
    private RecetarioViewModel viewModel;
    private ActivityRecetasBinding binding;
    private RecetasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecetasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Para testear la creación de la base de datos antes de implementar Patron Repositorio
        //Recetario basedatos = Recetario.getInstance(getApplicationContext());
        this.viewModel = new ViewModelProvider(this)
                .get(RecetarioViewModel.class);

        // Creamos la instancia del adapter, es unica.
        adapter = new RecetasAdapter(new ArrayList<>(), getOnClickRecetaListener());
        binding.rvRecetas.setAdapter(adapter);

        // Observamos cambios en la lista de recetas y actualizamos el adaptador
        viewModel.getTodasRecetas().observe(this, new Observer<List<Receta>>() {
            @Override
            public void onChanged(List<Receta> recetas) {
                // Evitamos crear un nuevo adapter por cada cambio en Room
                adapter.actualizarDatos(recetas);
            }
        });

        // *** SEARCHVIEW *** //
        SearchViewTool.configurar(binding.svRecetas, this, viewModel);

        // Observamos los cambios al filtrar recetas
        viewModel.recetasFiltradas.observe(this, recetas -> {
            adapter.actualizarDatos(recetas);
        });
    }

    // Implementación del listener al hacer clic sobre un item del recyclerview
    private RecetasAdapter.OnClickItemListener getOnClickRecetaListener() {
        return new RecetasAdapter.OnClickItemListener() {
            @Override
            public void onClickReceta(Receta receta) {
                // Crea el BottomSheet y configura el listener ANTES de mostrarlo
                RecetasBottomSheet bottomSheet = new RecetasBottomSheet();

                // Configura el listener inmediatamente después de crear la instancia
                bottomSheet.setOnClickOpcionListener(getOnClickOpcionListener());

                // Ahora muestra el BottomSheet
                bottomSheet.show(getSupportFragmentManager(), "RecetasBottomSheet");

            }
        };
    }

    // Implementación del listener sobre las opciones del bottomSheet
    private RecetasBottomSheet.OnClickOpcionListener getOnClickOpcionListener() {
        return new RecetasBottomSheet.OnClickOpcionListener() {
            @Override
            public void onVerReceta() {
                //Abrir activity para ver receta
                Toast.makeText(RecetasActivity.this, "Ver receta", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onModificarReceta() {
                //Abrir activity para modificar receta
                Toast.makeText(RecetasActivity.this, "Modificar receta", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onEliminarReceta() {
                //Eliminar receta
                Toast.makeText(RecetasActivity.this, "Eliminar receta", Toast.LENGTH_SHORT).show();
            }
        };
    }
}