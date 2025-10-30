package org.overdrive.recetasdigitales.view.lista_recetas;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.overdrive.recetasdigitales.databinding.RecyclerRecetasItemBinding;
import org.overdrive.recetasdigitales.model.entidades.Receta;

import java.util.List;

/**
 * Esta clase recibe los datos y los pasa al ViewHolder
 */
public class RecetasAdapter extends RecyclerView.Adapter<RecetasViewHolder> {
    private List<Receta> recetas;
    private static final String TAG = "RecetasAdapter";
    private OnClickItemListener listener;


    public RecetasAdapter(List<Receta> recetas, OnClickItemListener listener) {
        this.recetas = recetas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecetasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "parent: " + parent);

        //El LayoutInflater nos permite convertir el archivo XML del layout
        // en objetos View que Android puede mostrar
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        //El metodo estático inflate() del binding nos permite construir la vista del ítem a partir del XML.
        RecyclerRecetasItemBinding binding = RecyclerRecetasItemBinding.inflate(inflater, parent, false);

        return new RecetasViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecetasViewHolder holder, int position) {
        // Aqui pasamos cada objeto al ViewHolder
        Receta receta = recetas.get(position);
        holder.bind(receta, listener);

    }

    @Override
    public int getItemCount() {
        return recetas == null ? 0 : recetas.size();
    }

    /**
     * Actualizar datos del adapter y nofiticar cambios al recyclerView.
     * <p>
     * Permite optimizar el uso del recyclerview.
     *
     * @param nuevasRecetas Lista de nuevas recetas
     */
    public void actualizarDatos(List<Receta> nuevasRecetas) {
        this.recetas = nuevasRecetas;
        notifyDataSetChanged();
    }

    public interface OnClickItemListener {
        void onClickReceta(Receta receta);
    }
}
