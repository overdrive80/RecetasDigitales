package org.overdrive.recetasdigitales.view.crear_receta.ingredientes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import org.overdrive.recetasdigitales.R;
import org.overdrive.recetasdigitales.databinding.FragmentIngredientesBinding;
import org.overdrive.recetasdigitales.viewmodel.CrearRecetaViewModel;

import java.util.ArrayList;


public class IngredientesFragment extends Fragment {
    private FragmentIngredientesBinding binding;
    private NavController navController;
    private CrearRecetaViewModel viewModel;
    private IngredientesAdapter adapter;

    public IngredientesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializarViewModel();
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

        navController = Navigation.findNavController(view);
        configurarMenuProvider();
        configurarRecyclerView();
        configurarObservers();
        configurarFab();
    }

    private void configurarObservers() {

        viewModel.getIngredientes().observe(getViewLifecycleOwner(), ingredientes -> {
            if (ingredientes != null) {
                adapter.actualizarDatos(ingredientes);
            }
        });
    }

    private void configurarRecyclerView() {
        adapter = new IngredientesAdapter(new ArrayList<>(), new IngredientesAdapter.OnClickIngredienteListener() {
            @Override
            public void onClickIngrediente(int position) {

                viewModel.setPosicionIngredienteEditando(position);
                //El bottomSheet observara este elemento y lo mostrara
                IngredientesBottomSheet bottomSheet = new IngredientesBottomSheet();
                bottomSheet.show(IngredientesFragment.this.getParentFragmentManager(), IngredientesBottomSheet.TAG);

            }

            @Override
            public void onEliminarIngrediente(int posicion) {
                // Si estamos en la portada mostrar dialogo
                new AlertDialog.Builder(requireContext())
                        .setTitle("Eliminar")
                        .setMessage("¿Está seguro de borrar el ingrediente?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Si", (dialog, which) -> viewModel.eliminarIngrediente(posicion))
                        .show();
            }
        });
        binding.rvIngredientes.setAdapter(adapter);

    }

    private void inicializarViewModel() {
        this.viewModel = new ViewModelProvider(requireActivity())
                .get(CrearRecetaViewModel.class);
    }

    private void configurarMenuProvider() {
        requireActivity().addMenuProvider(new MenuProvider() {

            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_siguiente, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                // Item: siguiente
                if (menuItem.getItemId() == R.id.action_siguiente) {
                    navController.navigate(R.id.action_ingredientes_a_pasos);
                    return true; // Le dice que ha consumido el evento y no lo propage
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED); // Esta parte hace que se reutilize el menu
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void configurarFab() {
        binding.fabIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IngredientesBottomSheet bottomSheet = new IngredientesBottomSheet();
                bottomSheet.show(getParentFragmentManager(), IngredientesBottomSheet.TAG);
            }
        });
    }
}