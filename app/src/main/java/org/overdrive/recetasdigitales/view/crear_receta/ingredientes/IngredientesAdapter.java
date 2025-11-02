package org.overdrive.recetasdigitales.view.crear_receta.ingredientes;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.overdrive.recetasdigitales.databinding.RecyclerIngredientesItemBinding;
import org.overdrive.recetasdigitales.model.entidades.Ingrediente;
import org.overdrive.recetasdigitales.model.entidades.Receta;

import java.util.List;

public class IngredientesAdapter extends RecyclerView.Adapter<IngredientesViewHolder> {
    private List<Ingrediente> ingredientes;
    private OnClickIngredienteListener listener;
    private static final String TAG = "IngredientesAdapter";


    public IngredientesAdapter(List<Ingrediente> ingredientes, OnClickIngredienteListener listener) {
        this.ingredientes = ingredientes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public IngredientesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //El LayoutInflater nos permite convertir el archivo XML del layout
        // en objetos View que Android puede mostrar
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        //El metodo estático inflate() del binding nos permite construir la vista del ítem a partir del XML.
        RecyclerIngredientesItemBinding binding = RecyclerIngredientesItemBinding.inflate(inflater, parent, false);

        return new IngredientesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientesViewHolder holder, int position) {
        // Aqui pasamos cada objeto al ViewHolder
        Ingrediente ingrediente = ingredientes.get(position);
        holder.bind(ingrediente);

    }

    @Override
    public int getItemCount() {
        return ingredientes == null ? 0 : ingredientes.size();
    }

    public void actualizarDatos(List<Ingrediente> nuevosIngredientes) {
        this.ingredientes = nuevosIngredientes;
        notifyDataSetChanged();
    }


    public interface OnClickIngredienteListener {
        void onClickIngrediente(Ingrediente ingrediente);
    }
}

