package org.overdrive.recetasdigitales.view.crear_receta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import org.overdrive.recetasdigitales.R;
import org.overdrive.recetasdigitales.databinding.FragmentIngredientesBinding;


public class IngredientesFragment extends Fragment {
    private FragmentIngredientesBinding binding;

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
        binding = FragmentIngredientesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configurarListeners();
    }

    private void configurarListeners() {
        binding.btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_ingredientes_a_pasos);
            }
        });
    }
}