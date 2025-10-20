package org.overdrive.recetasdigitales.view.listarecetas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import org.overdrive.recetasdigitales.databinding.ActivityRecetasBinding;
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

        //Para testear la creación de la base de datos antes de implementar Patron Repositorio
        //Recetario basedatos = Recetario.getInstance(getApplicationContext());
        this.viewModel = new ViewModelProvider(this)
                .get(RecetarioViewModel.class);

        // Creamos la instancia del adapter, es unica.
        adapter = new RecetasAdapter(new ArrayList<>(), this);
        binding.rvRecetas.setAdapter(adapter);

        // Observamos cambios en la lista de recetas y actualizamos el adaptador
        viewModel.getTodasRecetas().observe(this, recetas -> {
            // Crear un nuevo objeto adapter en cada actualización que emite Room
            // tiene efectos indeseados, como perder posición del scroll del Recycler.
            adapter.actualizarDatos(recetas);
        });
    }
}