package com.vitalarasoft.notebook;

import android.content.res.Resources;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;

public class NoteDataSourceFirebaseImpl extends BaseNoteDataSource {
    private static final String TAG = "NoteDataSourceFirebase";
    private volatile static NoteDataSourceFirebaseImpl sInstance;
    private final static String COLLECTION_NOTE = "com.vitalarasoft.CollectionNote";

    private final FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private final CollectionReference mCollection = mStore.collection(COLLECTION_NOTE);

    public static NoteDataSourceFirebaseImpl getInstance() {
        NoteDataSourceFirebaseImpl instance = sInstance;
        if (instance == null) {
            synchronized (NoteDataSourceFirebaseImpl.class) {
                instance = new NoteDataSourceFirebaseImpl();
                sInstance = instance;
            }
        }
        return instance;
    }

    private NoteDataSourceFirebaseImpl() {
        mCollection.orderBy("date"
                ,Query.Direction.DESCENDING).get()
                .addOnCompleteListener(this::onFetchComplete)
                .addOnFailureListener(this::onFetchFailed);
    }


    private void onFetchComplete(Task<QuerySnapshot> task) {
        Log.e("LOAD : ", String.valueOf(task.getResult().size()));
        if (task.isSuccessful()) {
            LinkedList<NoteDataFromFirestore> data = new LinkedList<>();
            for (QueryDocumentSnapshot document : task.getResult()) {
                data.add(new NoteDataFromFirestore(document.getId(), document.getData()));

                //data.clear();
                notifyDataSetChanged();
            }
            Log.e("data", String.valueOf(data.size()));
            mData.clear();
            mData.addAll(data);
        }
    }

    private void onFetchFailed(Exception e) {
        Log.e(TAG, "Fetch failed");
    }



    @Override
    public void add(@NonNull Note data) {
        final NoteDataFromFirestore noteData;
        if (data instanceof NoteDataFromFirestore) {
            noteData = (NoteDataFromFirestore) data;
        } else {
            noteData = new NoteDataFromFirestore(data);
        }

        mData.add(noteData);
        mCollection.add(noteData.getFields()).addOnSuccessListener(documentReference -> {
            noteData.setId(documentReference.getId());
        });
    }

    public void update100(@NonNull Note data) {
        String id = data.getId();
        mCollection.document(id).set(NoteDataFromFirestore.getFields());
    }

    @Override
    public void remove(int position) {
        String id = mData.get(position).getId();
        mCollection.document(id).delete();
        super.remove(position);
    }

    @Override
    public void clear() {
        for (Note noteData : mData) {
            mCollection.document(noteData.getId()).delete();
        }
        super.clear();
    }


}
