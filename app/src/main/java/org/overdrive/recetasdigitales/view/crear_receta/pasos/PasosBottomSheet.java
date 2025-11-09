package org.overdrive.recetasdigitales.view.crear_receta.pasos;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.overdrive.recetasdigitales.R;
import org.overdrive.recetasdigitales.databinding.BottomsheetNuevoPasoBinding;
import org.overdrive.recetasdigitales.model.entidades.Paso;
import org.overdrive.recetasdigitales.viewmodel.CrearRecetaViewModel;

import java.util.List;

public class PasosBottomSheet extends BottomSheetDialogFragment {
    public static final String TAG = "PasosBottomSheet";
    private BottomsheetNuevoPasoBinding binding;
    private CrearRecetaViewModel viewModel;
    private boolean esEdicion = false;
    private Paso pasoEditando;

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
        binding = BottomsheetNuevoPasoBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        esEdicion = false;
        pasoEditando = null;

        configurarListeners();
        configurarObservadores();
    }

    private void configurarObservadores() {
        // El observador recibe siempre su último valor almacenado en cuando se suscribe al LiveData
        viewModel.getPosicionPasoEditando().observe(getViewLifecycleOwner(), posicion -> {
            List<Paso> pasos = viewModel.getPasos().getValue();

            // No hacemos nada si no es válido. Si es -1 entonces no es edicion.
            if (posicion == null || posicion < 0 || pasos == null || posicion >= pasos.size()) {
                binding.tvTituloPaso.setText("Nuevo paso");
                esEdicion = false;
                return;
            }

            pasoEditando = viewModel.getPasos().getValue().get(posicion);

            binding.etDescripcionPaso.setText(pasoEditando.getDescripcion());
            binding.tvTituloPaso.setText(
                    requireContext().getString(R.string.num_paso_item,
                            String.valueOf(pasoEditando.getOrden()))
            );

            esEdicion = true;

        });
    }

    private void inicializarViewModel() {
        this.viewModel = new ViewModelProvider(requireActivity())
                .get(CrearRecetaViewModel.class);
    }

    private void configurarListeners() {

        binding.btnAceptarPaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Solo se valida la descripcion del paso
                if (binding.etDescripcionPaso.getText().toString().isEmpty()) {
                    binding.etDescripcionPaso.setError("Este campo es obligatorio");
                    return;
                }

                //Toast.makeText(requireContext(), "Paso agregado", Toast.LENGTH_SHORT).show();

                if (esEdicion) {
                    actualizarPaso();
                } else {
                    agregarPaso();
                }

                dismiss();
            }
        });

        binding.btnCancelarPaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(requireContext(), "Operacion cancelada", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }

    private void actualizarPaso() {

        pasoEditando.setDescripcion(binding.etDescripcionPaso.getText().toString());
        //El numero de paso se actualizaría mediante drag&drop
        viewModel.actualizarPaso(pasoEditando);
    }

    private void agregarPaso() {
        List<Paso> pasos = viewModel.getPasos().getValue();

        Paso paso = new Paso();
        paso.setDescripcion(binding.etDescripcionPaso.getText().toString());

        if (pasos != null) {
            paso.setOrden(pasos.size() + 1);

        }

        viewModel.setPaso(paso);
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
        viewModel.setPosicionPasoEditando(-1);
        esEdicion = false;
    }
}

