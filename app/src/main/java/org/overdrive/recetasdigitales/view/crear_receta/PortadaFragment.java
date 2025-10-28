package org.overdrive.recetasdigitales.view.crear_receta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.overdrive.recetasdigitales.R;
import org.overdrive.recetasdigitales.databinding.FragmentPortadaRecetaBinding;
import org.overdrive.recetasdigitales.databinding.FragmentVerRecetaTab1Binding;

public class PortadaFragment extends Fragment {

    private FragmentPortadaRecetaBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;

    public PortadaFragment() {
        // Required empty public constructor
    }


    public static PortadaFragment newInstance(String param1, String param2) {
        PortadaFragment fragment = new PortadaFragment();
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
        binding = FragmentPortadaRecetaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}