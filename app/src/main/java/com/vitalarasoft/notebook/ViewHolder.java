package com.vitalarasoft.notebook;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.atomic.AtomicInteger;

public class ViewHolder extends RecyclerView.ViewHolder {
    private static final AtomicInteger COUNTER = new AtomicInteger();
    public final int id;
    public final TextView textName;
    public final TextView textDate;
    private int mLastSelectedPosition = -1;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        id = COUNTER.incrementAndGet();
        textName = itemView.findViewById(R.id.list_item_text);
        textDate = itemView.findViewById(R.id.list_item_date);
    }

    public void populate(NoteListFragment fragment, Note note) {
        textName.setText(note.mNoteName);
        textDate.setText(note.mNoteDate);
        itemView.setOnClickListener((v) -> {
            int i = getLayoutPosition();
            Log.e("getLayoutPosition = ", String.valueOf(i));
            fragment.setLastSelectedPosition(getLayoutPosition());
            //return false;
        });

        itemView.setOnLongClickListener((v) -> {
            fragment.setLastSelectedPosition(getLayoutPosition());
            return false;
        });


        fragment.registerForContextMenu(itemView);
    }

    public void clear(Fragment fragment){
        itemView.setOnLongClickListener(null);
        fragment.unregisterForContextMenu(itemView);
    }
}
