package com.vitalarasoft.notebook;

import androidx.annotation.NonNull;

import java.util.List;

public interface NoteSource {
    interface NoteDataSourceListener {
        void onItemAdded(int idx);

        void onItemRemoved(int idx);

        void onItemUpdated(int idx);

        void onDataSetChanged();

    }

    void addNoteDataSourceListener(NoteDataSourceListener listener);
    void updateNoteDataSourceListener(NoteDataSourceListener listener);
    void removeNoteDataSourceListener(NoteDataSourceListener listener);

    List<Note> getCardData();

    Note getItemAt(int idx);

    int getItemsCount();
    void add(@NonNull Note data);
    void updateNote(int position, Note data);
    void remove(int position);
    void clear();
}
