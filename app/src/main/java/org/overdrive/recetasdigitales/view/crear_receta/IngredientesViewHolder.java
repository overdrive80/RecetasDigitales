package org.overdrive.recetasdigitales.view.crear_receta;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.overdrive.recetasdigitales.databinding.RecyclerIngredientesItemBinding;
import org.overdrive.recetasdigitales.model.entidades.Ingrediente;

public class IngredientesViewHolder extends RecyclerView.ViewHolder {
    private RecyclerIngredientesItemBinding binding;

    public IngredientesViewHolder(@NonNull RecyclerIngredientesItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

    }

    // Aqui se vinculan los datos con las vistas
    public void bind(Ingrediente ingrediente) {

    }
}
