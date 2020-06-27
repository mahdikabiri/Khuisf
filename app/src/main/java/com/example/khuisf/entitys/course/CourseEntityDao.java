package com.example.khuisf.entitys.course;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;


@Dao
public interface CourseEntityDao {
    @Insert
    void save(CourseEntity courseEntity);

    @Update
    void update(CourseEntity courseEntity);

    @Delete
    void delete(CourseEntity courseEntity);

//    void getOne(int id);


}
