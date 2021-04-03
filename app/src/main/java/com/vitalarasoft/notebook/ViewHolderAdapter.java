package com.vitalarasoft.notebook;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

class ViewHolderAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final NoteListFragment mFragment;
    private final LayoutInflater mInflater;
    private final NoteSource mNoteSource;

    private NoteListFragment.OnClickListener mOnClickListener;

    public ViewHolderAdapter(NoteListFragment fragment,
                             NoteSource noteSource) {
        mNoteSource = noteSource;
        //Log.e("note mValues", String.valueOf(mValues.length));
        mFragment = fragment;
        mInflater = fragment.getLayoutInflater();
    }

    public void setOnClickListener(NoteListFragment.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        Log.e(NoteListFragment.class.getCanonicalName(),
                String.format(Locale.getDefault(), "created holder id = %d", viewHolder.id));
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = mNoteSource.getItemAt(position);
        holder.populate(mFragment, note);
        holder.itemView.setOnClickListener((v) -> {
            if (mOnClickListener != null) {
                mOnClickListener.onItemClick(v, position);
            }
        });
        Log.e(NoteListFragment.class.getCanonicalName(),
                String.format(Locale.getDefault(), "used holder id = %d", holder.id));
    }

    @Override
    public int getItemCount() {
        return mNoteSource.getItemsCount();
    }
}
