package org.overdrive.recetasdigitales.view.crear_receta.ingredientes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.overdrive.recetasdigitales.R;
import org.overdrive.recetasdigitales.databinding.BottomsheetNuevoIngredienteBinding;
import org.overdrive.recetasdigitales.model.entidades.Ingrediente;
import org.overdrive.recetasdigitales.viewmodel.CrearRecetaViewModel;

import java.util.List;

public class IngredientesBottomSheet extends BottomSheetDialogFragment {
    public static final String TAG = "IngredientesBottomSheet";
    private BottomsheetNuevoIngredienteBinding binding;
    private CrearRecetaViewModel viewModel;
    private boolean esEdicion = false;
    private Ingrediente ingredienteEditando;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.ThemeBottomSheet); //Establecemos el tema del boton sheet
        setCancelable(false);
        inicializarViewModel();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Obtenemos la referencia al Dialog
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();

        if (dialog != null) {
            // Encontramos el contenedor principal del BottomSheet (es un FrameLayout interno de Android)
            FrameLayout bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);

            if (bottomSheet != null) {
                // Obtenemos el comportamientoasociado
                BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);

                // Hacemos que ocupe toda la altura disponible inmediatamente
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                // Evitamos que se colapse
                behavior.setSkipCollapsed(true);

                //Impedir que se pueda arrastrar con el dedo
                behavior.setDraggable(false);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Obtenemos la vista del layout del bottomSheet mediante el inflater del metodo
        binding = BottomsheetNuevoIngredienteBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        esEdicion = false;
        ingredienteEditando = null;

        configurarListeners();
        configurarObservadores();
        configurarAutocompletadoUnidades();
    }

    private void configurarObservadores() {
        // El observador recibe siempre su último valor almacenado en cuando se suscribe al LiveData
        viewModel.getPosicionIngredienteEditando().observe(getViewLifecycleOwner(), posicion -> {
            List<Ingrediente> ingredientes = viewModel.getIngredientes().getValue();

            // No hacemos nada si no es válido. Si es -1 entonces no es edicion.
            if (posicion == null || posicion < 0 || ingredientes == null || posicion >= ingredientes.size()) {
                esEdicion = false;
                return;
            }

            ingredienteEditando = viewModel.getIngredientes().getValue().get(posicion);

            binding.etNombreIngrediente.setText(ingredienteEditando.getNombre());
            binding.etUnidadIngrediente.setText(ingredienteEditando.getUnidad());

            if (ingredienteEditando.getCantidad() != null) {
                binding.etCantidadIngrediente.setText(String.valueOf(ingredienteEditando.getCantidad()));
            } else {
                binding.etCantidadIngrediente.setText("");
            }

            esEdicion = true;

        });
    }

    private void inicializarViewModel() {
        this.viewModel = new ViewModelProvider(requireActivity())
                .get(CrearRecetaViewModel.class);
    }

    private void configurarListeners() {

        binding.btnAceptarIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Solo se valida un campo, el nombre del ingrediente
                if (binding.etNombreIngrediente.getText().toString().isEmpty()) {
                    binding.etNombreIngrediente.setError(getString(R.string.este_campo_es_obligatorio));
                    return;
                }

                //Toast.makeText(requireContext(), "Ingrediente agregado", Toast.LENGTH_SHORT).show();

                if (esEdicion) {
                    actualizarIngrediente();
                } else {
                    agregarIngrediente();
                }

                dismiss();
            }
        });

        binding.btnCancelarIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(requireContext(), "Operacion cancelada", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }

    private void actualizarIngrediente() {

        ingredienteEditando.setNombre(binding.etNombreIngrediente.getText().toString());
        ingredienteEditando.setUnidad(binding.etUnidadIngrediente.getText().toString());

        if (!binding.etCantidadIngrediente.getText().toString().isEmpty()) {
            ingredienteEditando.setCantidad(Double.parseDouble(binding.etCantidadIngrediente.getText().toString()));
        } else {
            ingredienteEditando.setCantidad(null);
        }


        viewModel.actualizarIngrediente(ingredienteEditando);
    }

    private void agregarIngrediente() {
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre(binding.etNombreIngrediente.getText().toString());
        ingrediente.setUnidad(binding.etUnidadIngrediente.getText().toString());

        if (!binding.etCantidadIngrediente.getText().toString().isEmpty()) {
            ingrediente.setCantidad(Double.parseDouble(binding.etCantidadIngrediente.getText().toString()));
        } else {
            ingrediente.setCantidad(null);
        }

        viewModel.setIngrediente(ingrediente);
    }

    private void configurarAutocompletadoUnidades() {
        String[] sugerencias = getResources().getStringArray(R.array.unidades);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, sugerencias);
        binding.etUnidadIngrediente.setAdapter(adapter);
        binding.etUnidadIngrediente.setThreshold(1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        // Resetear estado de edición
        viewModel.setPosicionIngredienteEditando(-1);
        esEdicion = false;
    }

}
