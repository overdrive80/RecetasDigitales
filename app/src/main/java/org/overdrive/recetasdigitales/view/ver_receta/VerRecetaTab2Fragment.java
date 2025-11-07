package org.overdrive.recetasdigitales.view.ver_receta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.overdrive.recetasdigitales.databinding.FilaVerIngredienteBinding;
import org.overdrive.recetasdigitales.databinding.FragmentVerRecetaTab2Binding;
import org.overdrive.recetasdigitales.model.entidades.Ingrediente;
import org.overdrive.recetasdigitales.model.relaciones.RecetaCompleta;
import org.overdrive.recetasdigitales.viewmodel.VerRecetaViewModel;

import java.util.List;

public class VerRecetaTab2Fragment extends Fragment {

    private FragmentVerRecetaTab2Binding binding;
    private VerRecetaViewModel viewModel;
    private List<Ingrediente> ingredientes;
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    public VerRecetaTab2Fragment() {
        // Se requiere un constructor público vacío.
    }

    /**
     * Utilice este metodo de fábrica para crear una nueva instancia de
     * este fragmento utilizando los parámetros proporcionados.
     */
    public static VerRecetaTab2Fragment newInstance(String param1, String param2) {
        VerRecetaTab2Fragment fragment = new VerRecetaTab2Fragment();

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
        binding = FragmentVerRecetaTab2Binding.inflate(inflater, container, false);
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
                    ingredientes = recetaCompleta.ingredientes;
                    actualizarUI(ingredientes);
                }
            }
        });

    }

    private void actualizarUI(List<Ingrediente> ingredientes) {
        limpiarLista();
        LayoutInflater inflater = LayoutInflater.from(requireContext());

        for (Ingrediente ingrediente : ingredientes) {
            // Inflar la fila desde XML
            FilaVerIngredienteBinding filaBinding = FilaVerIngredienteBinding.inflate(inflater, binding.tablaIngredientes, false);

            // Configurar los textos y agregarlos
            Double cantidad = ingrediente.getCantidad();
            String unidad = ingrediente.getUnidad();
            String nombre = ingrediente.getNombre();

            if (cantidad != null) {
                String cantidadFormateada = (cantidad % 1 == 0)
                        ? String.valueOf(cantidad.intValue())
                        : String.valueOf(cantidad);

                filaBinding.tvCantidadItemTabla.setText(String.format("%s %s", cantidadFormateada, unidad));
            } else {
                filaBinding.tvCantidadItemTabla.setText(unidad);
            }

            filaBinding.tvIngredienteItemTabla.setText(nombre);


            // Añadir la fila a la tabla
            binding.tablaIngredientes.addView(filaBinding.getRoot());
        }
    }

    private void limpiarLista() {
        TableLayout tabla = binding.tablaIngredientes;

        // Eliminar todas las filas menos la cabecera
        while (tabla.getChildCount() > 1) {
            tabla.removeViewAt(1);
        }
    }

    private void configurarViewModel() {

        // Obtenemos instancia del viewModel compartido
        viewModel = new ViewModelProvider(requireActivity()).get(VerRecetaViewModel.class);
    }
}