package org.overdrive.recetasdigitales.view.lista_recetas;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.overdrive.recetasdigitales.databinding.RecyclerRecetasItemBinding;
import org.overdrive.recetasdigitales.model.entidades.Receta;

/**
 * Esta clase representa un contenedor que almacena las referencias a las vistas del ITEM
 * y facilita la asignación de los datos a dichas vistas.
 * <p>
 * Responsabilidad: vincular datos a vistas
 */
public class RecetasViewHolder extends RecyclerView.ViewHolder {

    private final RecyclerRecetasItemBinding binding;

    public RecetasViewHolder(@NonNull RecyclerRecetasItemBinding binding, RecetasAdapter.OnClickItemListener listener) {
        super(binding.getRoot());
        this.binding = binding;

        // Listener único, creado una sola vez
        binding.cardReceta.setOnClickListener(v -> {
            int position = getBindingAdapterPosition();

            if (listener == null || position == RecyclerView.NO_POSITION) {
                return;
            }

            listener.onClickReceta(position);
        });
    }

    /**
     * Vincula la receta a las vistas y gestiona el click.
     */
    public void bind(Receta receta, RecetasAdapter.OnClickItemListener listener) {
        binding.tvTituloReceta.setText(receta.getTitulo());
        binding.tvDescripcion.setText(receta.getDescripcion());
    }
}