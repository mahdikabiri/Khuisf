package com.example.khuisf.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.khuisf.entitys.coursesec.CourseEntity;
import com.example.khuisf.entitys.coursesec.CourseEntityDao;
import com.example.khuisf.entitys.note.Note;
import com.example.khuisf.entitys.note.NoteDao;

@Database(entities = {Note.class,CourseEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;

    public abstract NoteDao noteDao();
    public abstract CourseEntityDao courseEntityDao();

    public static AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "db_khuisf").allowMainThreadQueries().build();
        }
        return appDatabase;
    }
}
