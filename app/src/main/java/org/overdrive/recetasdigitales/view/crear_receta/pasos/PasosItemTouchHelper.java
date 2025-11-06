package org.overdrive.recetasdigitales.view.crear_receta.pasos;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class PasosItemTouchHelper extends ItemTouchHelper.Callback {

    public interface OrdenarPasos {
        void mover(int fromPos, int toPos);
    }

    private final OrdenarPasos listener;
    private int posicionInicial = RecyclerView.NO_POSITION;
    private int posicionFinal = RecyclerView.NO_POSITION;

    public PasosItemTouchHelper(OrdenarPasos listener) {
        this.listener = listener;
    }

    // Activa el movimiento con pulsacion larga
    @Override public boolean isLongPressDragEnabled() { return true; }
    // Desactiva el desplazamiento lateral
    @Override public boolean isItemViewSwipeEnabled() { return false; }

    @Override
    public int getMovementFlags(@NonNull RecyclerView rv, @NonNull RecyclerView.ViewHolder vh) {
        //Solo permitimos el arrastre vertical
        return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView rv,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {

        int from = viewHolder.getBindingAdapterPosition();
        int to = target.getBindingAdapterPosition();

        // Condicion de salida si no tiene posiciones validas
        if (from == RecyclerView.NO_POSITION || to == RecyclerView.NO_POSITION) return false;

        // Movemos los items durante el drag SIN actualizar el viewmodel
        rv.getAdapter().notifyItemMoved(from, to);

        // Posicion inicial donde se inicio el movimiento. Solo la establecemos una vez
        if (posicionInicial == RecyclerView.NO_POSITION) posicionInicial = from;

        // Actualiza la última posición alcanzada
        posicionFinal = to;

        return true;
    }

    @Override public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) { }

    @Override
    public void clearView(@NonNull RecyclerView rv, @NonNull RecyclerView.ViewHolder vh) {
        super.clearView(rv, vh);

        vh.itemView.setActivated(false); // Restablece estado al soltar

        // Si las posiciones son validas
        if (posicionInicial != RecyclerView.NO_POSITION && posicionFinal != RecyclerView.NO_POSITION
                && posicionInicial != posicionFinal) {

            // Comunicamos al viewmodel que debe efectuar los cambios en la lista
            listener.mover(posicionInicial, posicionFinal);
        }

        // Reinicializamos las posiciones para el siguiente movimiento.
        posicionInicial = RecyclerView.NO_POSITION;

    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);

        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG && viewHolder != null) {
            viewHolder.itemView.setActivated(true); // Aplica color de arrastre definido en el selector
        }
    }

}
