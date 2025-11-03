package org.overdrive.recetasdigitales.view.crear_receta.pasos;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.overdrive.recetasdigitales.databinding.RecyclerIngredientesItemBinding;
import org.overdrive.recetasdigitales.databinding.RecyclerPasosItemBinding;
import org.overdrive.recetasdigitales.model.entidades.Ingrediente;
import org.overdrive.recetasdigitales.model.entidades.Paso;
import org.overdrive.recetasdigitales.view.crear_receta.ingredientes.IngredientesAdapter;
import org.overdrive.recetasdigitales.view.crear_receta.ingredientes.IngredientesViewHolder;

import java.util.List;

public class PasosAdapter extends RecyclerView.Adapter<PasosViewHolder> {
    private List<Paso> pasos;

    public PasosAdapter(List<Paso> pasos) {
        this.pasos = pasos;
        //this.listener = listener;
    }

    @NonNull
    @Override
    public PasosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //El LayoutInflater nos permite convertir el archivo XML del layout
        // en objetos View que Android puede mostrar
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        //El metodo estático inflate() del binding nos permite construir la vista del ítem a partir del XML.
        RecyclerPasosItemBinding binding = RecyclerPasosItemBinding.inflate(inflater, parent, false);

        return new PasosViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PasosViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
