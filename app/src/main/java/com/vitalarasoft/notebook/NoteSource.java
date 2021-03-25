package com.vitalarasoft.notebook;

import java.util.List;

public interface NoteSource {
    List<Note> getCardData();

    Note getItemAt(int idx);

    int getItemsCount();
}
