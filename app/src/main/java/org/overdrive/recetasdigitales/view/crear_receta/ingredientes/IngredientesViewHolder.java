package org.overdrive.recetasdigitales.view.crear_receta.ingredientes;

import android.view.View;

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
        Double cantidad = ingrediente.getCantidad();
        String unidad = ingrediente.getUnidad();

        if (cantidad != null){
            binding.tvCantidad.setVisibility(View.VISIBLE);

            String cantidadFormateada = (cantidad % 1 == 0)
                    ? String.valueOf(cantidad.intValue())
                    : String.valueOf(cantidad);

            binding.tvCantidad.setText(cantidadFormateada);
        } else {
            binding.tvCantidad.setVisibility(View.GONE);
        }

        binding.tvUnidad.setText(unidad);

        binding.tvNombreIngrediente.setText(ingrediente.getNombre());
    }
}
