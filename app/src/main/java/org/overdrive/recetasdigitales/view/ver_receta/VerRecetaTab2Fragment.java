package org.overdrive.recetasdigitales.view.ver_receta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.overdrive.recetasdigitales.databinding.FragmentVerRecetaTab2Binding;

public class VerRecetaTab2Fragment extends Fragment {

    private FragmentVerRecetaTab2Binding binding;

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    public VerRecetaTab2Fragment() {
        // Se requiere un constructor público vacío.
    }

    /**
     * Utilice este metodo de fábrica para crear una nueva instancia de
     * este fragmento utilizando los parámetros proporcionados.
     */
    public static VerRecetaFragment newInstance(String param1, String param2) {
        VerRecetaFragment fragment = new VerRecetaFragment();

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
        // Infla el diseño de este fragmento.
        binding = FragmentVerRecetaTab2Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}