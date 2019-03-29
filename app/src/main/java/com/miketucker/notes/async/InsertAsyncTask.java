package com.miketucker.notes.async;

import android.os.AsyncTask;

import com.miketucker.notes.models.Note;
import com.miketucker.notes.persistence.NoteDao;

// insert

public class InsertAsyncTask extends AsyncTask<Note, Void, Void> {

    private NoteDao noteDao;

    public InsertAsyncTask(NoteDao dao) {
        noteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        noteDao.insertNotes(notes);
        return null;
    }
}
