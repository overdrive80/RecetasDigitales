package org.overdrive.recetasdigitales.view.crear_receta;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import org.overdrive.recetasdigitales.R;
import org.overdrive.recetasdigitales.databinding.FragmentPortadaBinding;
import org.overdrive.recetasdigitales.model.entidades.Receta;
import org.overdrive.recetasdigitales.tools.GestorTiempo;
import org.overdrive.recetasdigitales.tools.TextWatcherSimple;
import org.overdrive.recetasdigitales.viewmodel.CrearRecetaViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class PortadaFragment extends Fragment {

    private FragmentPortadaBinding binding;
    private NavController navController;
    private CrearRecetaViewModel viewModel;
    private int horas, minutos;
    private final int horaMax = 23, horaMin = 0;
    private final int minutoMax = 59, minutoMin = 0;
    // Launcher para obtener la imagen
    private ActivityResultLauncher<String> lanzadorImagen = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {

                    if (uri != null) {
                        // Guardar URI en ViewModel
                        viewModel.setImagenUri(uri);
                        //La imagen se almacena cuando se guarde la receta
                    }
                }
            }
    );

    public PortadaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializarViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPortadaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view); // View: es el elemento raiz del layout del fragment

        configurarMenuProvider();
        configurarTextWatchers();
        configurarListeners();
        configurarObservers();

    }

    private void configurarListeners() {
        binding.ivImagenReceta.setOnClickListener(v -> {
            lanzadorImagen.launch("image/*");
        });
    }

    private void configurarObservers() {

        viewModel.getReceta().observe(getViewLifecycleOwner(), receta -> {
            binding.etNombreReceta.setText(receta.getTitulo());
            binding.etDescripcionReceta.setText(receta.getDescripcion());
            int horas = GestorTiempo.getHoras(receta.getTiempo());
            int minutos = GestorTiempo.getMinutos(receta.getTiempo());
            binding.etHoras.setText(String.valueOf(horas));
            binding.etMinutos.setText(String.valueOf(minutos));

            this.horas = horas;
            this.minutos = minutos;
        });

        // Observar cambios en la URI de la imagen
        viewModel.getImagenUri().observe(getViewLifecycleOwner(), uri -> {
            if (uri != null) {
                Glide.with(requireContext())
                        .load(uri)
                        .into(binding.ivImagenReceta);
            }
        });
    }

    private void configurarTextWatchers() {

        binding.etHoras.addTextChangedListener(new TextWatcherSimple() {
            @Override
            public void afterTextChanged(android.text.Editable s) {
                horas = StringToInt(s.toString(), 0);

                if (!validarTiempo(horas, horaMin, horaMax)) {
                    binding.etHoras.setError("Valor entre " + horaMin + " y " + horaMax);
                    binding.etHoras.requestFocus();
                } else {
                    binding.etHoras.setError(null);
                }
            }
        });

        binding.etMinutos.addTextChangedListener(new TextWatcherSimple() {

            @Override
            public void afterTextChanged(android.text.Editable s) {
                minutos = StringToInt(s.toString(), 0);

                if (!validarTiempo(minutos, minutoMin, minutoMax)) {
                    binding.etMinutos.setError("Valor entre " + minutoMin + " y " + minutoMax);
                    binding.etMinutos.requestFocus();
                } else {
                    binding.etMinutos.setError(null);
                }

            }
        });
    }

    private boolean validarTiempo(int tiempo, int min, int max) {
        return tiempo >= min && tiempo <= max;
    }

    private int StringToInt(String tiempo, int valorDefecto) {
        int numero;

        try {
            numero = Integer.parseInt(tiempo);
        } catch (NumberFormatException e) {
            return valorDefecto;
        }

        return numero;
    }

    private void inicializarViewModel() {
        this.viewModel = new ViewModelProvider(getActivity())
                .get(CrearRecetaViewModel.class);
    }

    private void configurarMenuProvider() {
        requireActivity().addMenuProvider(new MenuProvider() {

            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_siguiente, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                // Item: siguiente
                if (menuItem.getItemId() == R.id.action_siguiente) {

                    // Validar nombre obligatorio
                    if (binding.etNombreReceta.getText().toString().trim().isEmpty()) {
                        binding.etNombreReceta.setError("El nombre de la receta es obligatorio");
                        binding.etNombreReceta.requestFocus();
                        return true;
                    }

                    if (contenidoCorrecto()) {
                        setRecetaViewModel();
                        navController.navigate(R.id.action_portada_a_ingredientes);
                        return true; // Le dice que ha consumido el evento y no lo propage
                    }
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED); // Esta parte hace que se reutilize el menu
    }

    private boolean contenidoCorrecto() {
        return binding.etNombreReceta.getError() == null &&
                binding.etHoras.getError() == null &&
                binding.etMinutos.getError() == null;
    }

    private void setRecetaViewModel() {
        Receta receta = new Receta();
        receta.setTitulo(binding.etNombreReceta.getText().toString());
        receta.setDescripcion(binding.etDescripcionReceta.getText().toString());
        //La imagen se establecerá en el ViewModel
        receta.setTiempo(GestorTiempo.getTiempoMinutos(horas, minutos));

        viewModel.setReceta(receta);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

