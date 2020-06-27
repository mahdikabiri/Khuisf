package com.example.khuisf.entitys.test;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TestDao {

    @Insert
    void save(Test test);

    @Update
    void update(Test test);

    @Delete
    void delete (Test test);

    @Query("SELECT * FROM Test")
    List<Test> getAll();
}
