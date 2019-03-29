package com.miketucker.notes.async;

import android.os.AsyncTask;
import android.util.Log;

import com.miketucker.notes.models.Note;
import com.miketucker.notes.persistence.NoteDao;

// Delete

public class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {

    private static final String TAG = "DeleteAsyncTask";

    private NoteDao noteDao;

    public DeleteAsyncTask(NoteDao dao) {
        noteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        Log.d(TAG, "doInBackground: thread: " + Thread.currentThread().getName());
        noteDao.delete(notes);
        return null;
    }
}
