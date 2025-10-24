package org.overdrive.recetasdigitales.view.ver_receta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.overdrive.recetasdigitales.databinding.FragmentVerRecetaTab3Binding;

public class VerRecetaTab3Fragment extends Fragment {

    private FragmentVerRecetaTab3Binding binding;

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
}