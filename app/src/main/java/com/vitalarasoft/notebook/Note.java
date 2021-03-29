package com.vitalarasoft.notebook;


public class Note {
    public String mNoteName;
    public String mNoteDate;
    public String mNoteDescription;

    public Note(String NoteName, String NoteDate, String NoteDescription) {
        mNoteName = NoteName;
        mNoteDate = NoteDate;
        mNoteDescription = NoteDescription;
    }


    public String getNoteName() {
        return mNoteName;
    }

    public void setNoteName(String NoteName) {
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
