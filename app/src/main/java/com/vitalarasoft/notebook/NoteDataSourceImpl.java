package com.vitalarasoft.notebook;

import android.content.res.Resources;
import android.content.res.TypedArray;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class NoteDataSourceImpl implements NoteSource {
    //private static final Object LOCK = new Object();
    private volatile static NoteDataSourceImpl sInstance;

    private final LinkedList<Note> mData = new LinkedList<>();

    public static NoteDataSourceImpl getInstance(Resources resources) {
        NoteDataSourceImpl instance = sInstance;
        if (instance == null) {
            synchronized (NoteDataSourceImpl.class) {
                instance = new NoteDataSourceImpl(resources);
                sInstance=instance;
            }
        }
        return instance;
    }

    private NoteDataSourceImpl(Resources resources) {
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


    @Override
    public void clear() {
        mData.clear();
    }

    @Override
    public void add(@NonNull Note data) {
        mData.add(data);
    }

    @Override
    public void remove(int position) {
        mData.remove(position);
    }
}
