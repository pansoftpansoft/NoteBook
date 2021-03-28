package com.vitalarasoft.notebook;

import java.util.Date;


public class Note {
    public final String NoteName;
    public final String NoteData;
    public final String NoteDescription;

    public Note(String NoteName, String NoteData, String NoteDescription) {
        this.NoteName = NoteName;
        this.NoteData = NoteData;
        this.NoteDescription = NoteDescription;
    }
}
