package org.overdrive.recetasdigitales.view.crear_receta.pasos;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.overdrive.recetasdigitales.R;
import org.overdrive.recetasdigitales.databinding.RecyclerPasosItemBinding;
import org.overdrive.recetasdigitales.model.entidades.Paso;

public class PasosViewHolder extends RecyclerView.ViewHolder {
    private RecyclerPasosItemBinding binding;
    private PasosAdapter.OnClickPasoListener listener;

    public PasosViewHolder(@NonNull RecyclerPasosItemBinding binding, PasosAdapter.OnClickPasoListener listener) {
        super(binding.getRoot());

        this.listener = listener;
        this.binding = binding;

        configurarListeners();

    }

    public void bind(Paso paso) {
        binding.tvDescripcionPasoItem.setText(paso.getDescripcion());

        //Evitamos guardar contexto para no tener memoryleaks
        int numero = getBindingAdapterPosition() + 1;

        String titulo = itemView.getContext()
                .getString(R.string.num_paso_item,
                        String.valueOf(numero)
                );
        binding.tvNumPasoItem.setText(titulo);

    }

    private void configurarListeners() {
        // Listener del item completo
        itemView.setOnClickListener(v -> {
            int posicion = getBindingAdapterPosition();
            if (posicion != RecyclerView.NO_POSITION) {
                listener.onClickPaso(posicion);
            }
        });

        // Listener del botón borrar
        binding.ibBorrarPasoItem.setOnClickListener(v -> {
            int posicion = getBindingAdapterPosition();
            if (posicion != RecyclerView.NO_POSITION) {
                listener.onEliminarPaso(posicion);
            }
        });
    }
}
