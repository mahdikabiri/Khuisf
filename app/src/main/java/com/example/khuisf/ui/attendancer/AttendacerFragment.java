package com.example.khuisf.ui.attendancer;

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
import com.example.khuisf.Urls;
import com.example.khuisf.attendancerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AttendacerFragment extends Fragment {
    ArrayList<Course> studentItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    public AttendacerFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_attendance, container, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.recycler_attendance);
        AndroidNetworking.initialize(getContext());
        studentItems = new ArrayList<>();
        adapter = new attendancerAdapter(getContext(), studentItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        getStudnets();
    }

    private void getStudnets() {
        AndroidNetworking.initialize(getActivity());
        AndroidNetworking.post(Urls.host+ Urls.getStudent)
                .addBodyParameter("characteristic",getCharFromSharedPrefs())
                .setTag("getCourse")
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    //this loop repeating to count of course list
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        String cName = object.getString("name");
                        // add items from db and save to arraylist
                        studentItems.add(new Course(cName));
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), e.toString() + "", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }            }

            @Override
            public void onError(ANError anError) {
                Log.d("salam",anError.toString());

            }
        });
    }

    private String getCharFromSharedPrefs() {
        return "2222";
    }

}