package org.overdrive.recetasdigitales.view.crear_receta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.overdrive.recetasdigitales.R;


public class IngredientesFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    public IngredientesFragment() {
        // Required empty public constructor
    }

    public static IngredientesFragment newInstance(String param1, String param2) {
        IngredientesFragment fragment = new IngredientesFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredientes, container, false);
    }
}