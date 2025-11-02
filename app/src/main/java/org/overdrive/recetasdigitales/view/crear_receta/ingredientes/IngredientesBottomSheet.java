package org.overdrive.recetasdigitales.view.crear_receta.ingredientes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.overdrive.recetasdigitales.R;
import org.overdrive.recetasdigitales.databinding.BottomsheetNuevoIngredienteBinding;
import org.overdrive.recetasdigitales.model.entidades.Ingrediente;
import org.overdrive.recetasdigitales.viewmodel.CrearRecetaViewModel;

public class IngredientesBottomSheet extends BottomSheetDialogFragment {
    public static final String TAG = "IngredientesBottomSheet";
    private BottomsheetNuevoIngredienteBinding binding;
    private CrearRecetaViewModel viewModel;
    private boolean esEdicion = false;
    private Ingrediente ingredienteEditado;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.ThemeBottomSheet); //Establecemos el tema del boton sheet
        setCancelable(false);

        inicializarViewModel();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Obtenemos la vista del layout del bottomSheet mediante el inflater del metodo
        binding = BottomsheetNuevoIngredienteBinding.inflate(inflater, container, false);

        //Obtenemos la vista raiz del layout para el BottomSheet
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        esEdicion = false;
        ingredienteEditado = null;

        configurarListeners();
        configurarObservadores();
        configurarAutocompletadoUnidades();


    }

    private void configurarObservadores() {
        viewModel.getIngredienteSeleccionado().observe(getViewLifecycleOwner(), ingrediente -> {
            ingredienteEditado = ingrediente;

            if (ingrediente != null) {
                binding.etNombre.setText(ingrediente.getNombre());
                binding.etUnidad.setText(ingrediente.getUnidad());

                if (ingrediente.getCantidad() != null) {
                    binding.etCantidad.setText(String.valueOf(ingrediente.getCantidad()));
                } else {
                    binding.etCantidad.setText("");
                }

                esEdicion = true;
            }
        });
    }

    private void inicializarViewModel() {
        this.viewModel = new ViewModelProvider(getActivity())
                .get(CrearRecetaViewModel.class);
    }

    private void configurarListeners() {

        binding.btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Solo se valida un campo, el nombre del ingrediente
                if (binding.etNombre.getText().toString().isEmpty()) {
                    binding.etNombre.setError("Este campo es obligatorio");
                    return;
                }

                Toast.makeText(requireContext(), "Ingrediente agregado", Toast.LENGTH_SHORT).show();

                if (esEdicion) {
                    actualizarIngrediente();
                } else {
                    agregarIngrediente();
                }

                dismiss();
            }
        });

        binding.btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(requireContext(), "Operacion cancelada", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }

    private void actualizarIngrediente() {

        ingredienteEditado.setNombre(binding.etNombre.getText().toString());
        ingredienteEditado.setUnidad(binding.etUnidad.getText().toString());

        if (!binding.etCantidad.getText().toString().isEmpty()) {
            ingredienteEditado.setCantidad(Double.parseDouble(binding.etCantidad.getText().toString()));
        } else {
            ingredienteEditado.setCantidad(null);
        }


        viewModel.actualizarIngrediente(ingredienteEditado);
    }

    private void agregarIngrediente() {
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre(binding.etNombre.getText().toString());
        ingrediente.setUnidad(binding.etUnidad.getText().toString());

        if (!binding.etCantidad.getText().toString().isEmpty()) {
            ingrediente.setCantidad(Double.parseDouble(binding.etCantidad.getText().toString()));
        } else {
            ingrediente.setCantidad(null);
        }

        viewModel.setIngrediente(ingrediente);
    }

    private void configurarAutocompletadoUnidades() {
        String[] sugerencias = getResources().getStringArray(R.array.unidades);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, sugerencias);
        binding.etUnidad.setAdapter(adapter);
        binding.etUnidad.setThreshold(1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
