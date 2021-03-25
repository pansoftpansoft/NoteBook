package com.vitalarasoft.notebook;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


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
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_note_list, container, false);

        DividerItemDecoration decorator = new DividerItemDecoration(requireActivity(),
                LinearLayoutManager.VERTICAL);

        decorator.setDrawable(getResources().getDrawable(R.drawable.decaration));
        recyclerView.addItemDecoration(decorator);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        ViewHolderAdapter viewHolderAdapter = new ViewHolderAdapter(inflater,
                new NoteDataSourceImpl(getResources()));
        viewHolderAdapter.setOnClickListener((v, position) -> {
            if (getResources().getConfiguration().orientation ==
                    Configuration.ORIENTATION_PORTRAIT) {
                goToSeparateActivity(position);
            } else {
                showToTheRight(position);
            }
        });

        recyclerView.setAdapter(viewHolderAdapter);
        //Log.e("note Names", String.valueOf(noteNames.length));
        return recyclerView;
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
        Log.e("idx", String.valueOf(idx));
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.note_edit_container, NoteEditFragment.newInstance(idx));
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private static final AtomicInteger COUNTER = new AtomicInteger();
        public final int id;
        public final TextView textName;
        public final TextView textDate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = COUNTER.incrementAndGet();
            textName = itemView.findViewById(R.id.list_item_text);
            textDate = itemView.findViewById(R.id.list_item_date);
        }

        public void populate(Note note) {
            textName.setText(note.NoteName);
            textDate.setText(note.NoteData);
        }
    }

    private interface OnClickListener {
        void onItemClick(View v, int position);
    }

    private static class ViewHolderAdapter extends RecyclerView.Adapter<ViewHolder> {
        private final LayoutInflater mInflater;
        private final NoteSource mNoteSource;

        private OnClickListener mOnClickListener;

        public ViewHolderAdapter(LayoutInflater inflater, NoteSource noteSource) {
            mNoteSource = noteSource;
            //Log.e("note mValues", String.valueOf(mValues.length));
            mInflater = inflater;
        }

        public void setOnClickListener(OnClickListener onClickListener) {
            mOnClickListener = onClickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = mInflater.inflate(R.layout.list_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(v);

            Log.e(NoteListFragment.class.getCanonicalName(),
                    String.format(Locale.getDefault(), "created holder id = %d", viewHolder.id));

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Note note = mNoteSource.getItemAt(position);
            holder.populate(note);
            holder.itemView.setOnClickListener((v) -> {
                if (mOnClickListener != null) {
                    mOnClickListener.onItemClick(v, position);
                }
            });
            Log.e(NoteListFragment.class.getCanonicalName(),
                    String.format(Locale.getDefault(), "used holder id = %d", holder.id));
        }

        @Override
        public int getItemCount() {
            return mNoteSource.getItemsCount();
        }
    }
}