package com.miketucker.notes.persistence;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.miketucker.notes.async.DeleteAsyncTask;
import com.miketucker.notes.async.InsertAsyncTask;
import com.miketucker.notes.async.UpdateAsyncTask;
import com.miketucker.notes.models.Note;

import java.util.List;

public class NoteRepository {

    private NoteDatabase mNoteDatabase;

    public NoteRepository(Context context) {
        mNoteDatabase = NoteDatabase.getInstance(context);
    }

    // New Note

    public void insertNoteTask(Note note){
        new InsertAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    // Update Note

    public void updateNote(Note note) {
        new UpdateAsyncTask(mNoteDatabase.getNoteDao()).execute(note);

    }

    // Retrieve Notes Data

    public LiveData<List<Note>> retrieveNotesTask(){

        return mNoteDatabase.getNoteDao().getNotes();
    }

    // Delete Note

    public void deleteNote(Note note){
        new DeleteAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }
}
