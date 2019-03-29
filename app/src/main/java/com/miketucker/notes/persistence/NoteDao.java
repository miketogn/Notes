package com.miketucker.notes.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.miketucker.notes.models.Note;

import java.util.List;

// DAO

@Dao
public interface NoteDao {

    @Insert
    long[] insertNotes(Note... notes);

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();

    @Query("SELECT * FROM notes WHERE id = :title")
    List<Note> getNoteWithCustomeQuery(String title);

    @Delete
    int delete(Note... notes);

    @Update
    int update(Note... notes);
}
