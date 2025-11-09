package org.overdrive.recetasdigitales.view.lista_recetas;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.overdrive.recetasdigitales.databinding.RecyclerRecetasItemBinding;
import org.overdrive.recetasdigitales.model.entidades.Receta;

import java.util.List;

/**
 * Adaptador encargado de proporcionar los datos al RecyclerView.
 * Recibe la lista actual y notifica el click mediante un listener.
 */
public class RecetasAdapter extends RecyclerView.Adapter<RecetasViewHolder> {

    private List<Receta> recetas;
    private final OnClickItemListener listener;

    public RecetasAdapter(List<Receta> recetas, OnClickItemListener listener) {
        this.recetas = recetas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecetasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerRecetasItemBinding binding =
                RecyclerRecetasItemBinding.inflate(inflater, parent, false);

        return new RecetasViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecetasViewHolder holder, int position) {
        Receta receta = recetas.get(position);
        holder.bind(receta, listener);
    }

    @Override
    public int getItemCount() {
        return recetas != null ? recetas.size() : 0;
    }

    /**
     * Actualiza los datos del adaptador y refresca la interfaz.
     */
    public void actualizarDatos(List<Receta> nuevasRecetas) {
        this.recetas = nuevasRecetas;
        notifyDataSetChanged();
    }

    /**
     * Listener usado para propagar el evento de click al Fragment/Activity.
     */
    public interface OnClickItemListener {
        void onClickReceta(int posicion);
    }
}
