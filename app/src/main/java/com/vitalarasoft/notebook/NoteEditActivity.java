package com.vitalarasoft.notebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

public class NoteEditActivity extends AppCompatActivity {
    public static final String KEY_NOTE_INDEX = "NoteActivity.note_index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        Log.e("Stop", "1");
        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE) {
            Log.e("Stop", "2");
            finish();
            return;
        }
        Log.e("Stop", "3");
        if (savedInstanceState == null) {

            int noteIndex = getIntent().getIntExtra(KEY_NOTE_INDEX, -1);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.note_edit_container, NoteViewFragment.newInstance(noteIndex));
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();
        }
    }
}