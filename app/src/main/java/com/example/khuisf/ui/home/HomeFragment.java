package com.example.khuisf.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.khuisf.ExampleAdapter;
import com.example.khuisf.ExapleItem;
import com.example.khuisf.R;
import com.example.khuisf.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<ExapleItem> courseItems=new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        getNameFromSharedRefs();
        getCourses();
        Toast.makeText(getActivity(), "sss", Toast.LENGTH_SHORT).show();
        createRecycler(root);
        recyclerView.setAdapter(adapter);


/*
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });*/


       /* homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });*/
        return root;
    }

    public void test(){
        Toast.makeText(getActivity(), "ssff", Toast.LENGTH_SHORT).show();
    }

    private void createRecycler(View root) {

        recyclerView=root.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getActivity());
        adapter=new ExampleAdapter(courseItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

    private void getCourses() {
        AndroidNetworking.initialize(getActivity());
        AndroidNetworking.post(Urls.host+Urls.getCourses)
                .addBodyParameter("username",getNameFromSharedRefs())
                .setTag("getCourses")
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    //this loop repeating to count of course list
                    for (int i=0;i<response.length();i++){
                        JSONObject object=response.getJSONObject(i);
                        String cName=object.getString("course_name");
                        String cDay=object.getString("day");
                        String cTime=object.getString("time");
                        // add items from db and save to arraylist
                        courseItems.add(new ExapleItem(cName,cDay,cTime));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(getActivity(), "ایراد در دریافت برنامه هقتگی", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private String getNameFromSharedRefs() {
        SharedPreferences preferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        return preferences.getString("username","");
    }

    private void readyResponse(String result) {
        String[] elements=result.split(",");

        List<String> fix= Arrays.asList(elements);

        ArrayList<String> listofStrings=new ArrayList<>(fix);

        Toast.makeText(getActivity(), "courses:"+listofStrings, Toast.LENGTH_SHORT).show();

    }
}