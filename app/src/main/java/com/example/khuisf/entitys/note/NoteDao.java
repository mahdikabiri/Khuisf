package com.example.khuisf.entitys.note;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface NoteDao {

    @Insert
    void save(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete (Note note);

    @Query("SELECT * FROM tbl_notes order by id DESC")
    List<Note> getAll();

    @Query("SELECT * FROM tbl_notes WHERE day=0 order by id DESC ")
    List<Note> getAllSaturday();

    @Query("SELECT * FROM tbl_notes WHERE day=1 order by id DESC ")
    List<Note> getAllSunday();

    @Query("SELECT * FROM tbl_notes WHERE day=2 order by id DESC ")
    List<Note> getAllMonday();

    @Query("SELECT * FROM tbl_notes WHERE day=3 order by id DESC ")
    List<Note> getAllTuesday();

    @Query("SELECT * FROM tbl_notes WHERE day=4 order by id DESC ")
    List<Note> getAllWednesday();

    @Query("SELECT * FROM tbl_notes WHERE day=5 order by id DESC ")
    List<Note> getAllThursday();

    @Query("SELECT * FROM tbl_notes WHERE day=6 order by id DESC ")
    List<Note> getAllFriday();

    @Query("DELETE FROM tbl_notes")
    void deleteAllNotes();



    @Query("SELECT * FROM tbl_notes WHERE title LIKE :keyword")
    List<Note> searchByTitle(String keyword);
}
