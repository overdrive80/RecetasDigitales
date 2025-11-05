package org.overdrive.recetasdigitales.view.crear_receta.pasos;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PasosItemTouchHelper extends ItemTouchHelper.Callback {

    private final OrdenarPasos listener;
    private int fromPos, toPos;

    public PasosItemTouchHelper(OrdenarPasos listener) {
        this.listener = listener;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {

        int from = viewHolder.getBindingAdapterPosition();
        int to = target.getBindingAdapterPosition();

        recyclerView.getAdapter().notifyItemMoved(from, to); // SOLO UI
        this.fromPos = from;
        this.toPos = to;

        return true;
    }


    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // No usamos swipe
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        listener.mover(fromPos, toPos); // Guardamos orden real aquí
    }



    public interface OrdenarPasos {
        void mover(int fromPos, int toPos);
    }

}
