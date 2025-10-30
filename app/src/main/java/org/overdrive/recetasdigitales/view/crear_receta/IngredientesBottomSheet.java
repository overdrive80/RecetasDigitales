package org.overdrive.recetasdigitales.view.crear_receta;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import org.overdrive.recetasdigitales.R;
import org.overdrive.recetasdigitales.databinding.BottomsheetNuevoIngredienteBinding;
import org.overdrive.recetasdigitales.databinding.BottomsheetVerRecetasBinding;
import org.overdrive.recetasdigitales.model.entidades.Receta;
import org.overdrive.recetasdigitales.viewmodel.CrearRecetaViewModel;
import org.overdrive.recetasdigitales.viewmodel.RecetasViewModel;

import java.util.List;

public class IngredientesBottomSheet extends BottomSheetDialogFragment {
    public static final String TAG = "IngredientesBottomSheet";
    private BottomsheetNuevoIngredienteBinding binding;
    private CrearRecetaViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.ThemeBottomSheet); //Establecemos el tema del boton sheet
        setCancelable(false);

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

        configurarListeners();
        configurarAutocompletadoUnidades();


    }

    private void configurarListeners() {

        binding.btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Solo se valida un campo, el nombre del ingrediente
                if (binding.etNombre.getText().length() == 0) {
                    binding.etNombre.setError("Este campo es obligatorio");
                    return;
                }

                Toast.makeText(requireContext(), "Ingrediente agregado", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        binding.btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireContext(), "Operacion cancelada", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
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
