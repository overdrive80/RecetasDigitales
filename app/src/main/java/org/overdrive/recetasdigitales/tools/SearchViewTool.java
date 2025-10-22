package org.overdrive.recetasdigitales.tools;

import android.content.Context;

import androidx.appcompat.widget.SearchView;

import org.overdrive.recetasdigitales.viewmodel.RecetarioViewModel;

public class SearchViewTool {

    public static void configurar(SearchView searchView, Context context, RecetarioViewModel viewModel) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.setFiltroBusqueda(query.trim());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.setFiltroBusqueda(newText.trim());
                return true;
            }
        });
    }
}
