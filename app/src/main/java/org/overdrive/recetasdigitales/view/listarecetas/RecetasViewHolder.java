package org.overdrive.recetasdigitales.view.listarecetas;

import android.view.View;

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
    private RecyclerRecetasItemBinding binding;

    public RecetasViewHolder(@NonNull RecyclerRecetasItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

    }

    // Aqui se vinculan los datos con las vistas
    public void bind(Receta receta, RecetasAdapter.OnClickItemListener listener) {
        binding.tvTituloReceta.setText(receta.getTitulo());
        binding.tvDescripcion.setText(receta.getDescripcion());

        // Listener
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Notificamos al listener
                listener.onClickReceta(receta);
            }
        });
    }
}
