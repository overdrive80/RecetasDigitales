package org.overdrive.recetasdigitales.view.ver_receta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.overdrive.recetasdigitales.R;
import org.overdrive.recetasdigitales.databinding.FilaVerPasoBinding;
import org.overdrive.recetasdigitales.databinding.FragmentVerRecetaTab3Binding;
import org.overdrive.recetasdigitales.model.entidades.Paso;
import org.overdrive.recetasdigitales.model.relaciones.RecetaCompleta;
import org.overdrive.recetasdigitales.viewmodel.VerRecetaViewModel;

import java.util.List;

public class VerRecetaTab3Fragment extends Fragment {

    private FragmentVerRecetaTab3Binding binding;
    private VerRecetaViewModel viewModel;
    private List<Paso> pasos;

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    public VerRecetaTab3Fragment() {
        // Se requiere un constructor público vacío.
    }

    /**
     * Utilice este metodo de fábrica para crear una nueva instancia de
     * este fragmento utilizando los parámetros proporcionados.
     */
    public static VerRecetaTab3Fragment newInstance(String param1, String param2) {
        VerRecetaTab3Fragment fragment = new VerRecetaTab3Fragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVerRecetaTab3Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configurarViewModel();
        configurarObservadores();
    }

    private void configurarObservadores() {

        viewModel.getRecetaCompleta().observe(getViewLifecycleOwner(), new Observer<RecetaCompleta>() {
            @Override
            public void onChanged(RecetaCompleta recetaCompleta) {
                if (recetaCompleta != null) {
                    pasos = recetaCompleta.pasos;
                    actualizarUI(pasos);
                }
            }
        });

    }

    private void actualizarUI(List<Paso> pasos) {
        limpiarLista(); // Limpia contenedor

        LayoutInflater inflater = LayoutInflater.from(requireContext());

        for (Paso paso : pasos) {

            // Inflar fila
            FilaVerPasoBinding filaBinding =
                    FilaVerPasoBinding.inflate(inflater, binding.contenedorPasos, false);

            // Establecer datos
            String pasoNum = getString(R.string.paso_num, String.valueOf(paso.getOrden()));
            filaBinding.tvPasoNumItem.setText(pasoNum);
            filaBinding.tvPasoDescripcionItem.setText(paso.getDescripcion());

            // Añadir al LinearLayout
            binding.contenedorPasos.addView(filaBinding.getRoot());
        }
    }

    private void limpiarLista() {
        binding.contenedorPasos.removeAllViews();
    }

    private void configurarViewModel() {

        // Obtenemos instancia del viewModel compartido
        viewModel = new ViewModelProvider(requireActivity()).get(VerRecetaViewModel.class);
    }
}