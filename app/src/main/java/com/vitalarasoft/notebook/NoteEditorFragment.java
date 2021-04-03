package com.vitalarasoft.notebook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteEditorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteEditorFragment extends Fragment {

    private static final String ARG_ITEM_IDX = "NoteEditorFragment.item_idx";
    private NoteSource mNoteSource; /*mCardDataSource*/
    private int mCurrentItemIdx = -1;

    public NoteEditorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param CurrentItemIdx Parameter 1.
     * @return A new instance of fragment NoteEditorFragment.
     */
    public static NoteEditorFragment newInstance(int CurrentItemIdx) {
        Log.e("CurrentItemIdx", String.valueOf(CurrentItemIdx));
        NoteEditorFragment fragment = new NoteEditorFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ITEM_IDX, CurrentItemIdx);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCurrentItemIdx = getArguments().getInt(ARG_ITEM_IDX, -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note_editor, container, false);
        final NoteSource noteSource = NoteDataSourceFirebaseImpl.getInstance();

        Note note = noteSource.getItemAt(mCurrentItemIdx);

        final TextInputEditText editTextNoteName = view.findViewById(R.id.list_item_note_name);
        final TextInputEditText editTextNoteDate = view.findViewById(R.id.list_item_note_date);
        final TextInputEditText editTextNoteDescription = view.findViewById(R.id.list_item_note_description);
        final MaterialButton btnSave = view.findViewById(R.id.btn_save);

        Log.e("note.mNoteName", "onCreateView: " + note.mNoteName);

        editTextNoteName.setText(note.mNoteName);
        editTextNoteDate.setText(note.mNoteDate);
        editTextNoteDescription.setText(note.mNoteDescription);

        btnSave.setOnClickListener((v) -> {
            note.setNoteName(editTextNoteName.getText().toString());
            note.setNoteDate(editTextNoteDate.getText().toString());
            note.setNoteDescription(editTextNoteDescription.getText().toString());
            noteSource.updateNote(mCurrentItemIdx, note);
            getFragmentManager().popBackStack();
        });
        return view;
    }
}