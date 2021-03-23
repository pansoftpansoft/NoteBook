package com.vitalarasoft.notebook;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteEditFragment extends Fragment {

    public static final String ARG_NOTE_INDEX = "Note.note_index";

    private int mNoteIndex = -1;

    public NoteEditFragment() {
        // Required empty public constructor
    }

    public static NoteEditFragment newInstance(int param1) {
        Log.e("param1", String.valueOf(param1));
        NoteEditFragment fragment = new NoteEditFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NOTE_INDEX, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNoteIndex = getArguments().getInt(ARG_NOTE_INDEX, -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("onCreateView", String.valueOf(mNoteIndex));
        return inflater.inflate(R.layout.fragment_note_edit, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("mNoteIndex", String.valueOf(mNoteIndex));
        if (mNoteIndex != -1) {

            String[] noteNames = getResources().getStringArray(R.array.note_name);
            String[] noteDates = getResources().getStringArray(R.array.note_date);
            String[] noteDescription = getResources().getStringArray(R.array.note_description);

            TextView textViewNoteName = view.findViewById(R.id.TextViewNoteName);
            EditText editTextNoteDate = view.findViewById(R.id.editTextNoteDate);
            EditText editTextDescription = view.findViewById(R.id.editTextNoteDescription);

            textViewNoteName.setText(noteNames[mNoteIndex]);
            editTextNoteDate.setText(noteDates[mNoteIndex]);
            editTextDescription.setText(noteDescription[mNoteIndex]);
        }
    }
}