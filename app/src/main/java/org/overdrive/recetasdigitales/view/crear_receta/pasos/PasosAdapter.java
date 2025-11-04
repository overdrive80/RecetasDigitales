package org.overdrive.recetasdigitales.view.crear_receta.pasos;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.overdrive.recetasdigitales.databinding.RecyclerPasosItemBinding;
import org.overdrive.recetasdigitales.model.entidades.Paso;

import java.util.List;

public class PasosAdapter extends RecyclerView.Adapter<PasosViewHolder> {
    private List<Paso> pasos;
    private PasosAdapter.OnClickPasoListener listener;

    public PasosAdapter(List<Paso> pasos, PasosAdapter.OnClickPasoListener listener) {
        this.pasos = pasos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PasosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //El LayoutInflater nos permite convertir el archivo XML del layout
        // en objetos View que Android puede mostrar
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        //El metodo estático inflate() del binding nos permite construir la vista del ítem a partir del XML.
        RecyclerPasosItemBinding binding = RecyclerPasosItemBinding.inflate(inflater, parent, false);

        return new PasosViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PasosViewHolder holder, int position) {
        holder.bind(pasos.get(position));

    }

    @Override
    public int getItemCount() {
        return pasos == null ? 0 : pasos.size();
    }

    public void actualizarDatos(List<Paso> nuevosPasos) {
        this.pasos = nuevosPasos;
        notifyDataSetChanged();
    }

    public interface OnClickPasoListener {
        void onClickPaso(int posicion);

        void onEliminarPaso(int posicion);
    }


}
