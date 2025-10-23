package org.overdrive.recetasdigitales.view.listarecetas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.overdrive.recetasdigitales.R;
import org.overdrive.recetasdigitales.databinding.RecetasBottomSheetBinding;
import org.overdrive.recetasdigitales.model.entidades.Receta;
import org.overdrive.recetasdigitales.viewmodel.RecetarioViewModel;

public class RecetasBottomSheet extends BottomSheetDialogFragment {
    private RecetasBottomSheetBinding binding;
    private OnClickOpcionListener listener;
    private RecetarioViewModel viewModel;
    private Receta recetaSeleccionada;


    //La documentación de Android prohíbe constructores con parámetros en fragments
    //Al recrear el fragmento se rompe el ciclo de vida del fragment

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.ThemeBottomSheet); //Establecemos el tema del boton sheet
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Obtenemos la vista del layout del bottomSheet mediante el inflater del metodo
        binding = RecetasBottomSheetBinding.inflate(inflater, container, false);

        //Obtenemos la vista raiz del layout para el BottomSheet
        View view = binding.getRoot();

        // Obtenemos instancia del viewModel compartido
        viewModel = new ViewModelProvider(requireActivity()).get(RecetarioViewModel.class);
        recetaSeleccionada = viewModel.getRecetaSeleccionada().getValue();

        // Esta opcion abre una nueva actividad para mostrar la receta
        binding.opcionVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Notificamos a los oyentes del evento
                if (listener != null) listener.onVerReceta(recetaSeleccionada);

                //Cerramos la hoja inferior
                dismiss();
            }
        });

        // Esta opcion abre la actividad que permite editar la receta.
        // Debe pasar un id de la receta para recuperar los datos
        binding.opcionModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Notificamos a los oyentes del evento
                if (listener != null) listener.onModificarReceta(recetaSeleccionada);
                dismiss();
            }
        });

        // Esta acción elimina la receta. Lo ideal es un callback para la actividad
        // sea la responsable de eliminar la receta a través del ViewModel
        binding.opcionEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Notificamos a los oyentes del evento
                if (listener != null) listener.onEliminarReceta(recetaSeleccionada);
                dismiss();
            }
        });


        return view;
    }

    public void setOnClickOpcionListener(OnClickOpcionListener listener) {
        this.listener = listener;
    }

    public interface OnClickOpcionListener {
        void onVerReceta(Receta receta);

        void onModificarReceta(Receta receta);

        void onEliminarReceta(Receta Receta);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
