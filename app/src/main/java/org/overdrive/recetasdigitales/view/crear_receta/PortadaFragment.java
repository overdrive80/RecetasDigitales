package org.overdrive.recetasdigitales.view.crear_receta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import org.overdrive.recetasdigitales.R;
import org.overdrive.recetasdigitales.databinding.FragmentPortadaRecetaBinding;

public class PortadaFragment extends Fragment {

    private FragmentPortadaRecetaBinding binding;
    private NavController navController;

    public PortadaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPortadaRecetaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view); // View: es el elemento raiz del layout del fragment
        configurarMenuProvider();

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
                    navController.navigate(R.id.action_portada_a_ingredientes);
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
}

