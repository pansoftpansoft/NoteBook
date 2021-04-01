package com.vitalarasoft.notebook;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import androidx.annotation.NonNull;

public class Note {
    @Nullable
    public String mId;
    @NonNull
    public static String mNoteName;
    public static String mNoteDate;
    public static String mNoteDescription;

    public Note(@NonNull String NoteName, String NoteDate, String NoteDescription) {
        assert NoteName != null;
        mNoteName = NoteName;
        mNoteDate = NoteDate;
        mNoteDescription = NoteDescription;
    }
    @Nullable
    public String getId() {
        return mId;
    }

    public void setId(@Nullable String Id) {
        mId = Id;
    }

    @NonNull
    public static String getNoteName() {
        return mNoteName;
    }

    public void setNoteName(@NonNull String NoteName) {
        mNoteName = NoteName;
    }

    public static String getNoteDate() {
        return mNoteDate;
    }

    public void setNoteDate(String NoteDate) {
        mNoteDate = NoteDate;
    }

    public static String getNoteDescription() {
        return mNoteDescription;
    }

    public void setNoteDescription(String NoteDescription) {
        mNoteDescription = NoteDescription;
    }


}
