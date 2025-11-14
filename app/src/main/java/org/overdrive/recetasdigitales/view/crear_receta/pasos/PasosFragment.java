package org.overdrive.recetasdigitales.view.crear_receta.pasos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;

import org.overdrive.recetasdigitales.R;
import org.overdrive.recetasdigitales.databinding.FragmentPasosBinding;
import org.overdrive.recetasdigitales.viewmodel.CrearRecetaViewModel;

import java.util.ArrayList;

public class PasosFragment extends Fragment {
    private FragmentPasosBinding binding;
    private NavController navController;
    private CrearRecetaViewModel viewModel;
    private PasosAdapter adapter;
    private PasosItemTouchHelper touchCallback;
    private ItemTouchHelper touchHelper;

    public PasosFragment() {
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
        // Inflate the layout for this fragment
        binding = FragmentPasosBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view); // View: es el elemento raiz del layout del fragment
        configurarMenuProvider();
        configurarRecyclerView();
        configurarObservers();
        configurarTouchHelper();
        configurarFab();
    }

    private void configurarTouchHelper() {
        touchCallback = new PasosItemTouchHelper(new PasosItemTouchHelper.OrdenarPasos() {
            @Override
            public void mover(int fromPos, int toPos) {
                // Realizamos cambios en la lista del ViewModel, lo que notifica observer
                // y actualiza el adapter
                viewModel.moverPaso(fromPos, toPos);
            }
        });

        touchHelper = new ItemTouchHelper(touchCallback);
        touchHelper.attachToRecyclerView(binding.rvPasos);
    }


    private void configurarObservers() {

        viewModel.getPasos().observe(getViewLifecycleOwner(), pasos -> {
            if (pasos != null) {
                adapter.actualizarDatos(pasos);
            }
        });

        //  Observa el resultado de guardar la receta para finalizar el fragment
        viewModel.getRecetaGuardada().observe(getViewLifecycleOwner(), guardada -> {
            if (Boolean.TRUE.equals(guardada)) {
                Toast.makeText(getContext(), R.string.receta_guardada, Toast.LENGTH_SHORT).show();
                requireActivity().finish();
            }
        });
    }

    private void configurarRecyclerView() {
        adapter = new PasosAdapter(new ArrayList<>(), new PasosAdapter.OnClickPasoListener() {
            @Override
            public void onClickPaso(int position) {

                viewModel.setPosicionPasoEditando(position);
                //El bottomSheet observara este elemento y lo mostrara
                PasosBottomSheet bottomSheet = new PasosBottomSheet();
                bottomSheet.show(PasosFragment.this.getParentFragmentManager(), PasosBottomSheet.TAG);

            }

            @Override
            public void onEliminarPaso(int posicion) {
                // Si estamos en la portada mostrar dialogo
                new AlertDialog.Builder(requireContext())
                        .setTitle(R.string.eliminar)
                        .setMessage(R.string.esta_seguro_de_borrar_el_paso)
                        .setNegativeButton(R.string.no, null)
                        .setPositiveButton(R.string.si, (dialog, which) -> viewModel.eliminarPaso(posicion))
                        .show();
            }
        });
        binding.rvPasos.setAdapter(adapter);

    }

    private void inicializarViewModel() {
        this.viewModel = new ViewModelProvider(requireActivity())
                .get(CrearRecetaViewModel.class);
    }

    private void configurarMenuProvider() {
        requireActivity().addMenuProvider(new MenuProvider() {

            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_guardar, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                // Item: guardar
                if (menuItem.getItemId() == R.id.action_guardar) {
                    guardarReceta();
                    return true; // Le dice que ha consumido el evento y no lo propage
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED); // Esta parte hace que se reutilize el menu
    }

    private void guardarReceta() {
        viewModel.guardarRecetaCompleta();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void configurarFab() {
        binding.fabPasos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PasosBottomSheet bottomSheet = new PasosBottomSheet();
                bottomSheet.show(getParentFragmentManager(), PasosBottomSheet.TAG);
            }
        });
    }
}