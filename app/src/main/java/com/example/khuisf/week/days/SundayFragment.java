package com.example.khuisf.week.days;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.khuisf.R;
import com.example.khuisf.database.AppDatabase;
import com.example.khuisf.entitys.course.Course;
import com.example.khuisf.entitys.note.Note;
import com.example.khuisf.entitys.note.NoteDao;
import com.example.khuisf.notes.NoteAdapter;
import com.example.khuisf.students.weeklyplan.CourseAdapter;
import com.example.khuisf.tools.GetDataFromSH;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SundayFragment extends Fragment implements NoteAdapter.NoteCallback{
    ArrayList<Course> courseItems;
    private RecyclerView recyclerView,recyclerViewNote;
    private RecyclerView.Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sonday, container, false);
        recyclerView = view.findViewById(R.id.fragment_sunday_recycler);
        recyclerViewNote = view.findViewById(R.id.fragment_sunday_recycler_note);
        AndroidNetworking.initialize(getContext());
        courseItems = new ArrayList<>();
        adapter = new CourseAdapter(getContext(), courseItems);
        recyclerView.setAdapter(adapter);
        getCourses();
        intiNoteRecycler();
        return view;
    }

    private void intiNoteRecycler() {
        NoteDao noteDao;
        List<Note> notes;
        AppDatabase appDatabase = AppDatabase.getInstance(getContext());
        noteDao = appDatabase.noteDao();
        notes = noteDao.getAllSunday();
        NoteAdapter noteAdapter;
        noteAdapter = new NoteAdapter(this,true,getContext());
        recyclerViewNote.setAdapter(noteAdapter);
        noteAdapter.addNotes(notes);
    }

    private void getCourses() {
        AndroidNetworking.initialize(getActivity());
        AndroidNetworking.post(getString(R.string.host) + getString(R.string.getCoursesday))
                .addBodyParameter("code", GetDataFromSH.getCodeFromSharedPrefs(getContext()))
                .addBodyParameter("day", getString(R.string.sunday))
                .setTag("getCourses")
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    //this loop repeating to count of course list
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        int cId = object.getInt("id");
                        String cName = object.getString("name");
                        String cDay = object.getString("day");
                        String cTime = object.getString("time");
                        String cChar = object.getString("charac");
                        // add items from db and save to arraylist
                        courseItems.add(new Course(cId, cName, cDay, cTime, cChar));
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), e.toString() + "", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(getActivity(), "ایراد در دریافت برنامه هقتگی", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDeleteClick(Note note, int position) {

    }

    @Override
    public void onEditClick(Note note, int position) {
    }

    @Override
    public void onOpenNote(Note note, int postion) {

    }
}