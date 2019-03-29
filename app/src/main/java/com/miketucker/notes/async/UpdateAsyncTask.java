package com.miketucker.notes.async;

import android.os.AsyncTask;
import android.util.Log;

import com.miketucker.notes.models.Note;
import com.miketucker.notes.persistence.NoteDao;

public class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {

    // Update

    private static final String TAG = "UpdateAsyncTask";

    private NoteDao noteDao;

    public UpdateAsyncTask(NoteDao dao) {
        noteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        Log.d(TAG, "doInBackground: thread: " + Thread.currentThread().getName());
        noteDao.update(notes);
        return null;
    }
}
