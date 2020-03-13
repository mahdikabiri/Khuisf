package com.example.khuisf.ui.slideshow;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.khuisf.Course;
import com.example.khuisf.CourseAdapterForTeach;
import com.example.khuisf.CourseAdapterForTeachInsertScore;
import com.example.khuisf.R;
import com.example.khuisf.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class InsertScoreFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    ArrayList<Course> courseItems;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_insertscore, container, false);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.fragment_insert_score_recycler);
        courseItems = new ArrayList<>();
        adapter = new CourseAdapterForTeachInsertScore(getContext(), courseItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);
        //geting teachers code from shared preferences
        SharedPreferences preferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        String code=preferences.getString("code","");
        getCourses(code);
    }


    private void getCourses(String teacherCode) {
        AndroidNetworking.initialize(getActivity());
        AndroidNetworking.post(Urls.host + Urls.getCourseTeacher)
                .addBodyParameter("code", teacherCode)
                .setTag("getCourses")
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("sss",response.toString());
                try {
                    //this loop repeating to count of course list
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        String cName = object.getString("name");
                        String cDay = object.getString("day");
                        String cTime = object.getString("time");
                        String cChar = object.getString("charac");
                        // add items from db and save to arraylist
                        courseItems.add(new Course(cName, cDay, cTime,cChar));
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
                Log.d("sss",anError.toString());

            }
        });
    }
}