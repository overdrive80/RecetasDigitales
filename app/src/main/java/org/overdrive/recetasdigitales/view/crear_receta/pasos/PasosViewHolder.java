package org.overdrive.recetasdigitales.view.crear_receta.pasos;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.overdrive.recetasdigitales.databinding.RecyclerIngredientesItemBinding;
import org.overdrive.recetasdigitales.databinding.RecyclerPasosItemBinding;
import org.overdrive.recetasdigitales.model.entidades.Ingrediente;
import org.overdrive.recetasdigitales.model.entidades.Paso;
import org.overdrive.recetasdigitales.view.crear_receta.ingredientes.IngredientesAdapter;

public class PasosViewHolder extends RecyclerView.ViewHolder  {
    private RecyclerPasosItemBinding binding;

   public PasosViewHolder(@NonNull RecyclerPasosItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

    }

    public void bind(Paso paso) {
        binding.tvDescripcionPasoItem.setText(paso.getDescripcion());
        //El número del paso es el indice del adapter.
        //Nos interesará recuperarlo para persistirlo cuando se guarde la receta
    }
}
