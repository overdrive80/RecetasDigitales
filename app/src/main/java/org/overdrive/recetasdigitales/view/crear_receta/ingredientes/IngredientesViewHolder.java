package org.overdrive.recetasdigitales.view.crear_receta.ingredientes;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.overdrive.recetasdigitales.databinding.RecyclerIngredientesItemBinding;
import org.overdrive.recetasdigitales.model.entidades.Ingrediente;

public class IngredientesViewHolder extends RecyclerView.ViewHolder {
    private RecyclerIngredientesItemBinding binding;

    public IngredientesViewHolder(@NonNull RecyclerIngredientesItemBinding binding,
                                  IngredientesAdapter.ViewHolderCallback callback) {
        super(binding.getRoot());
        this.binding = binding;

        configurarListeners(callback);
    }

    // Aqui se vinculan los datos con las vistas
    public void bind(Ingrediente ingrediente) {
        Double cantidad = ingrediente.getCantidad();
        String unidad = ingrediente.getUnidad();

        if (cantidad != null) {
            binding.tvCantidadItem.setVisibility(View.VISIBLE);

            String cantidadFormateada = (cantidad % 1 == 0)
                    ? String.valueOf(cantidad.intValue())
                    : String.valueOf(cantidad);

            binding.tvCantidadItem.setText(cantidadFormateada);
        } else {
            binding.tvCantidadItem.setVisibility(View.GONE);
        }

        binding.tvUnidadItem.setText(unidad);
        binding.tvNombreIngredienteItem.setText(ingrediente.getNombre());
    }

    private void configurarListeners(IngredientesAdapter.ViewHolderCallback callback) {
        // Listener del item completo
        itemView.setOnClickListener(v -> {
            int posicion = getBindingAdapterPosition();
            if (posicion != RecyclerView.NO_POSITION) {
                callback.onClicItem(posicion);
            }
        });

        // Listener del botón borrar
        binding.ibBorrarIngredienteItem.setOnClickListener(v -> {
            int posicion = getBindingAdapterPosition();
            if (posicion != RecyclerView.NO_POSITION) {
                callback.onBorrarItem(posicion);
            }
        });
    }
}
