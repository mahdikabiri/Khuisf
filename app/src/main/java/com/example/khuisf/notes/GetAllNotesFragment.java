package com.example.khuisf.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khuisf.R;
import com.example.khuisf.database.AppDatabase;
import com.example.khuisf.entitys.note.Note;
import com.example.khuisf.entitys.note.NoteDao;
import com.ferfalk.simplesearchview.SimpleSearchView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class GetAllNotesFragment extends Fragment implements NoteAdapter.NoteCallback , Filterable {
    private RecyclerView recyclerView;
    private WaveSwipeRefreshLayout swipeRefreshLayout;
    private NoteAdapter noteAdapter;

    private boolean isInEditMode = false;
    private NoteDao noteDao;
    List<Note> notes;
    int position;
    View view;
    LinearLayout layout;

    private SimpleSearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_allnotes, container, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        AppDatabase appDatabase = AppDatabase.getInstance(getContext());

        searchView = view.findViewById(R.id.searchView);
        //    sss=view.findViewById(R.id.sssssssss);

        noteDao = appDatabase.noteDao();
        notes = noteDao.getAll();
        recyclerView = view.findViewById(R.id.recycler_allnotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        noteAdapter = new NoteAdapter(this, false, getContext(),notes);
        recyclerView.setAdapter(noteAdapter);
        noteAdapter.addNotes(notes);
        layout = view.findViewById(R.id.linier_layout_allnotes);
        FloatingActionButton fabAdddNote = view.findViewById(R.id.fragment_allnotes_btn_add_note);
        fabAdddNote.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditNoteActivity.class);
            startActivityForResult(intent, 1);
        });
        setHasOptionsMenu(true);
        initSearchBar();
        return view;
    }

    private void initSearchBar() {
        searchView.setOnQueryTextListener(new SimpleSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(), query + "", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /*notes = noteDao.searchByTitle(newText);
                noteAdapter.addNotes(notes);
                noteAdapter.notifyAll();*/
                noteAdapter.getFilter().filter(newText);
                return false;
            }

            @Override
            public boolean onQueryTextCleared() {
                return false;
            }
        });
    }

    @Override
    public void onStart() {
        showBack();
        super.onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Note note = data.getParcelableExtra("note");
            if (requestCode == 1) {
                //  String title = data.getStringExtra("title");
                //  String description = data.getStringExtra("description");
                noteDao.save(note);
                noteAdapter.addNote(note);
                Toast.makeText(getContext(), "یادداشت ذخیره شد", Toast.LENGTH_SHORT).show();
            } else if (requestCode == 2) {
                noteDao.update(note);
                noteAdapter.updateNote(note, position);
                Toast.makeText(getContext(), "یادداشت ویرایش شد", Toast.LENGTH_SHORT).show();
            }

            if (!notes.isEmpty()) {
                layout.setVisibility(View.INVISIBLE);
            }

        } catch (Exception e) {

        }
    }

    @Override
    public void onDeleteClick(Note note, int position) {
        /*noteDao.delete(note);
        noteAdapter.delteNote(position);
        showBack();*/
    }

    private void showBack() {
        if (notes.isEmpty()) {
            layout.setVisibility(View.VISIBLE);
        } else {
            layout.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.note_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onEditClick(Note note, int position) {
        this.position = position;
        Intent intent = new Intent(getActivity(), EditNoteActivity.class);
        intent.putExtra("note", note);
        //  intent.putExtra("position",position);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onOpenNote(Note note, int postion) {
        Intent intent = new Intent(getActivity(), ShowNoteActivity.class);
        intent.putExtra("note", note);
        startActivity(intent);
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    /*
    private void initSwipeRefreashLayout(View view) {
        swipeRefreshLayout = view.findViewById(R.id.get_score_swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        swipeRefreshLayout.setWaveColor(Color.rgb(57, 73, 171));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Toast.makeText(getContext(), R.string.updating, Toast.LENGTH_SHORT).show();
            update();
        });
    }*/


  /*  private void update() {
        courseItems.clear();
        recyclerView.scheduleLayoutAnimation();
        getScore();
    }*/

    /*private void getScore() {
        AndroidNetworking.initialize(getActivity());
        AndroidNetworking.post(getString(R.string.host) + getString(R.string.getScore))
                .addBodyParameter("code", GetDataFromSH.getCodeFromSharedPrefs(getContext()))
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    //this loop repeating to count of course list
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        String score = object.getString("score");
                        String cName = object.getString("course_name");
                        String unitAmali = object.getString("unit_practical");
                        String unitTeori = object.getString("unit_theoretical");
                        //if score not inserted
                        if (score.equals("null")) {
                            score = "خالی";
                        }
                        // add items from db and save to arraylist
                        courseItems.add(new Score(cName, unitTeori, unitAmali, score));
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), e.toString() + "", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(getActivity(), "اراد در دریافت نمرات", Toast.LENGTH_SHORT).show();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }*/
/*
    private String getCodeFromSharedPrefs() {
        SharedPreferences preferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        return preferences.getString("code", "");
    }*/
}