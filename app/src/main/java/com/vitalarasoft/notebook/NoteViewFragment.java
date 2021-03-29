package com.vitalarasoft.notebook;

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
 * Use the {@link NoteViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteViewFragment extends Fragment {

    public static final String ARG_NOTE_INDEX = "Note.note_index";

    private int mNoteIndex = -1;

    public NoteViewFragment() {
        // Required empty public constructor
    }

    public static NoteViewFragment newInstance(int NoteIndex) {
        Log.e("NoteIndex", String.valueOf(NoteIndex));
        NoteViewFragment fragment = new NoteViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NOTE_INDEX, NoteIndex);
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
        return inflater.inflate(R.layout.fragment_note_view, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("mNoteIndex", String.valueOf(mNoteIndex));
        if (mNoteIndex != -1) {

            final NoteSource noteSource = NoteDataSourceImpl.getInstance(getResources());

            Note note = noteSource.getItemAt(mNoteIndex);

            String[] noteNames = getResources().getStringArray(R.array.note_name);
            String[] noteDates = getResources().getStringArray(R.array.note_date);
            String[] noteDescription = getResources().getStringArray(R.array.note_description);

            TextView textViewNoteName = view.findViewById(R.id.list_item_view_note_name);
            TextView textNoteDate = view.findViewById(R.id.list_item_view_note_date);
            TextView textDescription = view.findViewById(R.id.list_item_view_note_description);
            Log.e(" 2222 mNoteIndex", String.valueOf(mNoteIndex));
            Log.e(" 2222 mNoteIndex", String.valueOf(noteNames.length));
            textViewNoteName.setText(note.mNoteName);
            textNoteDate.setText(note.mNoteDate);
            textDescription.setText(note.mNoteDescription);
        }
    }
}