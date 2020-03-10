package com.example.khuisf.ui.send;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.example.khuisf.Course;
import com.example.khuisf.CourseAdapter;
import com.example.khuisf.R;
import com.example.khuisf.Score;
import com.example.khuisf.ScoreAdapter;
import com.example.khuisf.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class GetScoreFragment extends Fragment {
    ArrayList<Score> courseItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_getscore, container, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        root.setLayoutParams(layoutParams);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.frag_getscore_recycler);
        AndroidNetworking.initialize(getContext());
        courseItems = new ArrayList<>();
        adapter = new ScoreAdapter(getContext(), courseItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        getScore();

    }

    private void getScore() {
        AndroidNetworking.initialize(getActivity());
        AndroidNetworking.post(Urls.host + Urls.getScore)
                .addBodyParameter("code", getCodeFromSharedPrefs())
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("sss",response.toString());
                try {
                    //this loop repeating to count of course list
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        String score = object.getString("score");
                        String cName = object.getString("course_name");
                        String unitAmali = object.getString("unit_practical");
                        String unitTeori = object.getString("unit_theoretical");
                        //if score not inserted
                        if (score.equals("null")){
                            score="خالی";
                        }
                        // add items from db and save to arraylist
                        courseItems.add(new Score(cName, unitTeori, unitAmali,score));
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
                Log.d("sss",anError.toString());

            }
        });
    }

    private String getCodeFromSharedPrefs() {
        SharedPreferences preferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        return preferences.getString("code","");
    }
}