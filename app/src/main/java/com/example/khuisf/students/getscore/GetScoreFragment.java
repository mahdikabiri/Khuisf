package com.example.khuisf.students.getscore;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.khuisf.R;
import com.example.khuisf.entitys.Score;
import com.example.khuisf.tools.GetDataFromSH;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

import static android.content.Context.MODE_PRIVATE;

public class GetScoreFragment extends Fragment {
    ArrayList<Score> courseItems;
    private RecyclerView recyclerView;
    private WaveSwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView.Adapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_getscore, container, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        recyclerView = view.findViewById(R.id.frag_getscore_recycler);
        AndroidNetworking.initialize(getContext());
        courseItems = new ArrayList<>();
        adapter = new ScoreAdapter(getContext(), courseItems);
        recyclerView.setAdapter(adapter);
        initSwipeRefreashLayout(view);
        getScore();
        view.setLayoutParams(layoutParams);
        return view;
    }



    private void initSwipeRefreashLayout(View view) {
        swipeRefreshLayout = view.findViewById(R.id.get_score_swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        swipeRefreshLayout.setWaveColor(Color.rgb(57, 73, 171));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Toast.makeText(getContext(), R.string.updating, Toast.LENGTH_SHORT).show();
            update();
        });

    }


    private void update() {
        courseItems.clear();
        recyclerView.scheduleLayoutAnimation();
        getScore();
    }

    private void getScore() {
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
    }
/*
    private String getCodeFromSharedPrefs() {
        SharedPreferences preferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        return preferences.getString("code", "");
    }*/
}