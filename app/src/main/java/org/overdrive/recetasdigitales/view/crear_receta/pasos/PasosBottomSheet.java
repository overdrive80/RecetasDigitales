package org.overdrive.recetasdigitales.view.crear_receta.pasos;

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
import org.overdrive.recetasdigitales.model.entidades.Paso;
import org.overdrive.recetasdigitales.viewmodel.CrearRecetaViewModel;

public class PasosBottomSheet extends BottomSheetDialogFragment{
    public static final String TAG = "PasosBottomSheet";
    private BottomsheetNuevoIngredienteBinding binding;
    private CrearRecetaViewModel viewModel;
    private boolean esEdicion = false;
    private Paso pasoEditado;


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
        pasoEditado = null;

        configurarListeners();
        configurarObservadores();


    }

    private void configurarObservadores() {
        viewModel.getPasoSeleccionado().observe(getViewLifecycleOwner(), paso -> {
            pasoEditado = paso;

            if (paso != null) {


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

//                //Solo se valida un campo, el nombre del ingrediente
//                if (binding.etNombre.getText().toString().isEmpty()) {
//                    binding.etNombre.setError("Este campo es obligatorio");
//                    return;
//                }

                Toast.makeText(requireContext(), "Paso agregado", Toast.LENGTH_SHORT).show();

                if (esEdicion) {
                    actualizarPaso();
                } else {
                    agregarPaso();
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

    private void actualizarPaso() {

        viewModel.actualizarPaso(pasoEditado);
    }

    private void agregarPaso() {
        Paso paso = new Paso();

        viewModel.setPaso(paso);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

