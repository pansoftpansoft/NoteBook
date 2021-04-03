package com.vitalarasoft.notebook;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import androidx.annotation.NonNull;

public class Note {
    @Nullable
    public String mId;
    @NonNull
    public String mNoteName;
    public String mNoteDate;
    public String mNoteDescription;

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
    public String getNoteName() {
        return mNoteName;
    }

    public void setNoteName(@NonNull String NoteName) {
        mNoteName = NoteName;
    }

    public String getNoteDate() {
        return mNoteDate;
    }

    public void setNoteDate(String NoteDate) {
        mNoteDate = NoteDate;
    }

    public String getNoteDescription() {
        return mNoteDescription;
    }

    public void setNoteDescription(String NoteDescription) {
        mNoteDescription = NoteDescription;
    }


}
