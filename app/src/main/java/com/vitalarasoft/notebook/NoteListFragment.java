package com.vitalarasoft.notebook;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 * Use the {@link NoteListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteListFragment extends androidx.fragment.app.Fragment {

    private int mCurrentImageIdx = -1;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    public NoteListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_note_list, container, false);
        String[] noteNames = getResources().getStringArray(R.array.note_name);
        String[] noteDates = getResources().getStringArray(R.array.note_date);
        //String[] noteDescription = getResources().getStringArray(R.array.note_description);
        int index = 0;
        for (int i = 0; i < noteNames.length; i++) {
            TextView tv = new TextView(getContext());
            tv.setText(noteNames[i] + "\n" + "Дата : " + noteDates[i].toString());
            tv.setTextSize(30);
            final int idx = index;
            tv.setOnClickListener((v) -> {
                setCurrentImageIdx(idx);
                if (getResources().getConfiguration().orientation ==
                        Configuration.ORIENTATION_PORTRAIT) {
                    goToSeparateActivity(idx);
                } else {
                    showToTheRight(idx);
                }
            });
            view.addView(tv);
            index++;
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentImageIdx = savedInstanceState.getInt(NoteEditFragment.ARG_NOTE_INDEX, -1);
            if (mCurrentImageIdx != -1 &&
                    getResources().getConfiguration().orientation ==
                            Configuration.ORIENTATION_LANDSCAPE) {
                showToTheRight(mCurrentImageIdx);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NoteEditFragment.ARG_NOTE_INDEX, mCurrentImageIdx);
    }

    private void setCurrentImageIdx(int idx) {
        mCurrentImageIdx = idx;
    }

    private void goToSeparateActivity(int idx) {
        Intent intent = new Intent(getActivity(), NoteEditActivity.class);
        intent.putExtra(NoteEditActivity.KEY_NOTE_INDEX, idx);
        startActivity(intent);
    }

    private void showToTheRight(int idx) {
        Log.e("idx" , String.valueOf(idx));
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.note_edit_container, NoteEditFragment.newInstance(idx));
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }
}