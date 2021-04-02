package com.vitalarasoft.notebook;

import java.nio.channels.FileLockInterruptionException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NoteDataFromFirestore extends Note {
    public final static String FIELD_ID = "id";
    public final static String FIELD_NOTE_NAME = "name";
    public final static String FIELD_NOTE_DATE = "date";
    public final static String FIELD_NOTE_DESCRIPTION = "description";

    public NoteDataFromFirestore(String NoteName, String NoteDate, String NoteDescription) {
        super(NoteName, NoteDate, NoteDescription);
    }

    public NoteDataFromFirestore(String id, String NoteName, String NoteDate, String NoteDescription) {
        this(NoteName, NoteDate, NoteDescription);
        setId(id);
    }

    public NoteDataFromFirestore(String id, Map<String, Object> fields) {
        this(id, (String) fields.get(FIELD_NOTE_NAME),
                (String) fields.get(FIELD_NOTE_DATE),
                (String) fields.get(FIELD_NOTE_DESCRIPTION));
    }

    public NoteDataFromFirestore(Note data) {
        this(data.getId(), data.getNoteName(), data.getNoteDate(), data.getNoteDescription());
    }

    public final Map<String, Object> getFields() {
        HashMap<String, Object> fields = new HashMap<>();
        fields.put(FIELD_NOTE_NAME, getNoteName());
        fields.put(FIELD_NOTE_DATE, getNoteDate());
        fields.put(FIELD_NOTE_DESCRIPTION, getNoteDescription());
        return Collections.unmodifiableMap(fields);
    }
}
