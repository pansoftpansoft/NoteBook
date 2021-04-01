package com.vitalarasoft.notebook;

import android.util.Log;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;

public abstract class BaseNoteDataSource implements NoteSource {
    private HashSet<NoteDataSourceListener> mListeners = new HashSet<>();
    protected final LinkedList<Note> mData = new LinkedList<>();

    public void addNoteDataSourceListener(NoteDataSourceListener listener) {
        mListeners.add(listener);
    }

    public void removeNoteDataSourceListener(NoteDataSourceListener listener) {
        mListeners.remove(listener);
    }

    public void updateNoteDataSourceListener(NoteDataSourceListener listener) {

    }


    @Override
    public List<Note> getCardData() {
        return Collections.unmodifiableList(mData);
    }

    @Override
    public Note getItemAt(int idx) {
        return mData.get(idx);
    }

    @Override
    public int getItemsCount() {
        return mData.size();
    }


    @Override
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public void add(@NonNull Note data) {
        mData.add(data);
        int idx = mData.size() - 1;
        for (NoteDataSourceListener listener : mListeners) {
            listener.onItemAdded(idx);
        }
    }

    @Override
    public void remove(int position) {
        mData.remove(position);
        for (NoteDataSourceListener listener : mListeners) {
            listener.onItemRemoved(position);
        }
    }

    @Override
    public void update(@NonNull Note data) {
        Log.e("TAG update", "update: " + data.mNoteName) ;
        String id = data.getId();
        if (id != null) {
            int idx = 0;
            for (Note noteData : mData) {
                Log.e("TAG update",   id + " = " + noteData.getId()) ;
                if (id.equals(noteData.getId())) {
                    noteData.setNoteName(data.getNoteName());
                    noteData.setNoteDate(data.getNoteDate());
                    noteData.setNoteDescription(data.getNoteName());
                    notifyUpdated(idx);
                    return;
                }
            }
            idx++;
        }
        mData.add(data);
    }

    protected final void notifyUpdated(int idx) {
        for (NoteDataSourceListener listener : mListeners) {
            listener.onItemUpdated(idx);
        }
    }

    protected final void notifyDataSetChanged() {
        for (NoteDataSourceListener listener : mListeners) {
            listener.onDataSetChanged();
        }
    }
}