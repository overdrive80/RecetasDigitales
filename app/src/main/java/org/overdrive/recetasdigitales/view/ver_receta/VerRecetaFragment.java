package org.overdrive.recetasdigitales.view.ver_receta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.overdrive.recetasdigitales.R;
import org.overdrive.recetasdigitales.databinding.FragmentVerRecetaBinding;

public class VerRecetaFragment extends Fragment {

    private FragmentVerRecetaBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentVerRecetaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                    default:
                        return new VerRecetaTab1Fragment();
                    case 1:
                        return new VerRecetaTab2Fragment();
                    case 2:
                        return new VerRecetaTab3Fragment();
                }
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        });

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                    default:
                        tab.setText(R.string.portada);
                        break;
                    case 1:
                        tab.setText(R.string.ingredientes);
                        break;
                    case 2:
                        tab.setText(R.string.pasos);
                        break;
                }
            }
        }).attach();
    }
}