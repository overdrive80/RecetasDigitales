package org.overdrive.recetasdigitales.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import org.overdrive.recetasdigitales.R;
import org.overdrive.recetasdigitales.viewmodel.RecetarioViewModel;

public class MainActivity extends AppCompatActivity {
    private RecetarioViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Para testear la creación de la base de datos antes de implementar Patron Repositorio
        //Recetario basedatos = Recetario.getInstance(getApplicationContext());

        this.viewModel = new ViewModelProvider(this)
                .get(RecetarioViewModel.class);

    }
}