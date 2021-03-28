package com.vitalarasoft.notebook;

import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class NoteDataSourceImpl implements NoteSource {
    private final LinkedList<Note> mData = new LinkedList<>();

    public NoteDataSourceImpl(Resources resources) {
        String[] noteNames = resources.getStringArray(R.array.note_name);
        String[] noteDates = resources.getStringArray(R.array.note_date);
        String[] noteDescription = resources.getStringArray(R.array.note_description);

        for (int i = 0; i < noteNames.length; i++) {
            mData.add(new Note(noteNames[i], noteDates[i], noteDescription[i]));
        }

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
}
