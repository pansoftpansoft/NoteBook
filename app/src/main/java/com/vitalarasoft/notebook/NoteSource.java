package com.vitalarasoft.notebook;

import androidx.annotation.NonNull;

import java.util.List;

public interface NoteSource {
    List<Note> getCardData();

    Note getItemAt(int idx);

    int getItemsCount();

    void clear();
    void add(@NonNull Note data);
    void remove(int position);
}
