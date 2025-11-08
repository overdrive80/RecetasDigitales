package org.overdrive.recetasdigitales.view.ver_receta;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import org.overdrive.recetasdigitales.R;
import org.overdrive.recetasdigitales.databinding.FragmentVerRecetaTab1Binding;
import org.overdrive.recetasdigitales.model.entidades.Receta;
import org.overdrive.recetasdigitales.model.relaciones.RecetaCompleta;
import org.overdrive.recetasdigitales.tools.GestorTiempo;
import org.overdrive.recetasdigitales.viewmodel.VerRecetaViewModel;

public class VerRecetaTab1Fragment extends Fragment {

    private FragmentVerRecetaTab1Binding binding;
    private VerRecetaViewModel viewModel;
    private static final String ARG_PARAM1 = "param1";
    private Receta receta;

    private String mParam1;

    public VerRecetaTab1Fragment() {
        // Se requiere un constructor público vacío.
    }

    /**
     * Utilizar este metodo de fábrica para crear una nueva instancia de
     * este fragmento utilizando los parámetros proporcionados.
     */
    public static VerRecetaTab1Fragment newInstance(String param1, String param2) {
        VerRecetaTab1Fragment fragment = new VerRecetaTab1Fragment();

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
        binding = FragmentVerRecetaTab1Binding.inflate(inflater, container, false);
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
                    receta = recetaCompleta.receta;
                    actualizarUI(receta);
                }
            }
        });

    }

    private void actualizarUI(Receta receta) {
        binding.tvTituloTab1.setText(receta.getTitulo());
        binding.tvDescripcionTab1.setText(receta.getDescripcion());

        //En base al long configuramos la salida mostrada
        formatearTiempo(receta);

        //Gestion de la imagen
        cargarImagen(receta);
    }

    private void formatearTiempo(Receta receta) {
        GestorTiempo t = new GestorTiempo(receta.getTiempo());

        if (!t.hayTiempo()) {
            binding.layoutTiempoTab1.setVisibility(View.GONE);
        } else {
            binding.layoutTiempoTab1.setVisibility(View.VISIBLE);

            binding.tvHorasTab1.setVisibility(t.getHoras() > 0 ? View.VISIBLE : View.GONE);
            binding.tvMinutosTab1.setVisibility(t.getMinutos() > 0 ? View.VISIBLE : View.GONE);

            binding.tvHorasTab1.setText(t.getHoras() + " h");
            binding.tvMinutosTab1.setText(t.getMinutos() + " min");
        }
    }


    private void configurarViewModel() {

        // Obtenemos instancia del viewModel compartido
        viewModel = new ViewModelProvider(requireActivity()).get(VerRecetaViewModel.class);
    }

    private void cargarImagen(Receta receta) {
        ShapeableImageView ivImagen = binding.ivImagenTab1;

        if (receta.getImagenUri() == null || receta.getImagenUri().isEmpty() ||
                receta.getImagenUri().equals("Sin imagen")) {

            ColorStateList transparentColor = ColorStateList.valueOf(Color.TRANSPARENT);
            ivImagen.setStrokeColor(transparentColor);
            ivImagen.setImageResource(R.drawable.outline_broken_image_24);
        } else {
            int colorAzulOriginal = ContextCompat.getColor(requireContext(), R.color.blue_800);
            binding.ivImagenTab1.setStrokeColor(ColorStateList.valueOf(colorAzulOriginal));

            Glide.with(this)
                    .load(receta.getImagenUri())
                    //.placeholder(R.drawable.outline_broken_image_24)
                    .error(R.drawable.outline_broken_image_24)
                    .dontAnimate()
                    .into(ivImagen);
        }
    }

}