package com.example.khuisf.entitys.coursesec;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface CourseEntityDao {
    @Insert
    void save(CourseEntity courseEntity);

    @Update
    void update(CourseEntity courseEntity);

    @Delete
    void delete(CourseEntity courseEntity);

    @Query("DELETe  FROM tbl_courses")
    void deleteAll();

    @Query("SELECT * FROM tbl_courses WHERE characteristic LIKE :charac")
    CourseEntity getOne(String charac);


    @Query("SELECT * FROM tbl_courses order by id DESC")
    List<CourseEntity> getAll();

//    void getOne(int id);


}
