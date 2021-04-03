package com.vitalarasoft.notebook;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoteListFragment extends Fragment {
    private static final String ARG_FRUIT_IDX = "FruitListFragment.arg_fruit_idx";
    private int mCurrentImageIdx = -1;
    private int mLastSelectedPosition = -1;
    private NoteSource mNoteSource; /*mCardDataSource*/
    private ViewHolderAdapter mViewHolderAdapter;
    private RecyclerView mRecyclerView;

    private NoteSource.NoteDataSourceListener mListener = new NoteSource.NoteDataSourceListener() {
        @Override
        public void onItemAdded(int idx) {
            if (mViewHolderAdapter != null) {
                mViewHolderAdapter.notifyItemInserted(idx);
            }
        }

        @Override
        public void onItemRemoved(int idx) {
            if (mViewHolderAdapter != null) {
                mViewHolderAdapter.notifyItemRemoved(idx);
            }
        }

        @Override
        public void onItemUpdated(int idx) {
            if (mViewHolderAdapter != null) {
                mViewHolderAdapter.notifyItemChanged(idx);
            }
        }

        @Override
        public void onDataSetChanged() {
            if (mViewHolderAdapter != null) {
                mViewHolderAdapter.notifyDataSetChanged();
            }
        }
    };


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

        setHasOptionsMenu(true);
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_note_list, container, false);
        mRecyclerView.setHasFixedSize(true);

        DividerItemDecoration decorator = new DividerItemDecoration(requireActivity(),
                LinearLayoutManager.VERTICAL);
        decorator.setDrawable(getResources().getDrawable(R.drawable.decaration));
        mRecyclerView.addItemDecoration(decorator);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mNoteSource = NoteDataSourceFirebaseImpl.getInstance();
        mViewHolderAdapter = new ViewHolderAdapter(this, mNoteSource);
        mNoteSource.addNoteDataSourceListener(mListener);
        mViewHolderAdapter.setOnClickListener((v, position) -> {
            setLastSelectedPosition(position);
            if (getResources().getConfiguration().orientation ==
                    Configuration.ORIENTATION_PORTRAIT) {
                Log.e("!!! position NoteIndex", String.valueOf(mLastSelectedPosition));
                goToSeparateActivity(mLastSelectedPosition);
            } else {
                showToTheRight(mLastSelectedPosition);
            }
        });

        mRecyclerView.setAdapter(mViewHolderAdapter);
        //Log.e("note Names", String.valueOf(noteNames.length));
        return mRecyclerView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mNoteSource.removeNoteDataSourceListener(mListener);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentImageIdx = savedInstanceState.getInt(NoteViewFragment.ARG_NOTE_INDEX, -1);
            if (mCurrentImageIdx != -1 &&
                    getResources().getConfiguration().orientation ==
                            Configuration.ORIENTATION_LANDSCAPE) {
                showToTheRight(mCurrentImageIdx);
            }
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.note_list_menu, menu);
        Log.e("1111", "onCreateView: 1111111112");
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.e("1111", "onCreateView: 1111111113");
        if (item.getItemId() == R.id.note_list_menu_add) {
            mNoteSource.add(new Note("Edit me!!!", "Edit me!!!", "Edit me!!!"));
            int position = mNoteSource.getItemsCount() - 1;
            mViewHolderAdapter.notifyItemInserted(position);

            ((RecyclerView) getView()).scrollToPosition(position);

            //mRecyclerView.scrollToPosition(position);
        } else if (item.getItemId() == R.id.note_list_menu_clear) {
            mNoteSource.clear();
            mViewHolderAdapter.notifyDataSetChanged();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu,
                                    @NonNull View v,
                                    @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.note_list_item_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.note_item_menu_edit) {
            if (mLastSelectedPosition != -1) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.note_list_container,
                        NoteEditorFragment.newInstance(mLastSelectedPosition));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        } else if (item.getItemId() == R.id.note_item_menu_delete) {
            if (mLastSelectedPosition != -1) {
                Log.e("remove", "remove: 2030405060");
                FragmentManager fragmentManager = getFragmentManager();
                if (fragmentManager != null) {
                    CustomBottomSheetDialogFragment mSheetDialog =
                            CustomBottomSheetDialogFragment.newInstance();
                    mSheetDialog.setOnDialogListener(dialogListener);
                    mSheetDialog.show(fragmentManager, "CustomBottomSheetDialogFragment");
                }
                //                mNoteSource.remove(mLastSelectedPosition);
                //                mViewHolderAdapter.notifyItemRemoved(mLastSelectedPosition);

            }

        } else {
            return super.onContextItemSelected(item);
        }

        return true;
    }

    private OnDialogListener dialogListener = new OnDialogListener() {
        @Override
        public void onDialogOk() {
            Log.e("remove", "remove: 100 - 2030405060");
            mNoteSource.remove(mLastSelectedPosition);
            mViewHolderAdapter.notifyItemRemoved(mLastSelectedPosition);
        }

        @Override
        public void onDialogCancel() {
            Log.e("remove", "remove: 200 - 2030405060");

        }
    };


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NoteViewFragment.ARG_NOTE_INDEX, mCurrentImageIdx);
    }

    private void goToSeparateActivity(int idx) {
//        Intent intent = new Intent(getActivity(), NoteEditActivity.class);
//        intent.putExtra(NoteEditActivity.KEY_NOTE_INDEX, idx);
//        startActivity(intent);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.note_list_container,
                NoteViewFragment.newInstance(idx));
        //NoteEditorFragment.newInstance(mLastSelectedPosition));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void showToTheRight(int idx) {
        Log.e("idx", String.valueOf(idx));
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.note_edit_container, NoteViewFragment.newInstance(idx));
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    /* package */ void setLastSelectedPosition(int lastSelectedPosition) {

        mLastSelectedPosition = lastSelectedPosition;
        Log.e("mLastSelectedPosition=", String.valueOf(lastSelectedPosition));
    }

    public interface OnClickListener {
        void onItemClick(View v, int position);
    }

}