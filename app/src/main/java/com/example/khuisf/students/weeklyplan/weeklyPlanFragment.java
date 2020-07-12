package com.example.khuisf.students.weeklyplan;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.khuisf.R;
import com.example.khuisf.database.AppDatabase;
import com.example.khuisf.entitys.course.Course;
import com.example.khuisf.entitys.coursesec.CourseEntity;
import com.example.khuisf.entitys.coursesec.CourseEntityDao;
import com.example.khuisf.tools.MyTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

import static android.content.Context.MODE_PRIVATE;

public class weeklyPlanFragment extends Fragment {
    ArrayList<Course> courseItems;
    ArrayList<CourseEntity> courseEntityItems;
    List<CourseEntity> courseEntityItemList;
    WaveSwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private CourseEntityDao courseDao;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_getcourse, container, false);
        recyclerView = view.findViewById(R.id.recycler_getcourse);
        AndroidNetworking.initialize(getContext());
        courseItems = new ArrayList<>();
        //courseEntityItems = new ArrayList<>();
        //adapter = new CourseAdapter(getContext(), (ArrayList<CourseEntity>) courseEntityItems);
        AppDatabase appDatabase = AppDatabase.getInstance(getContext());
        courseDao = appDatabase.courseEntityDao();
        courseEntityItemList = courseDao.getAll();
        adapter = new CourseAdapterFromDb(getContext(), courseEntityItemList);
        recyclerView.setAdapter(adapter);
        initSwipeRefreashLayout(view);

        //courseEntityItems= (ArrayList<CourseEntity>) courseDao.getAll();
        getCoursesWeb();
        if (adapter.getItemCount() == 0) {
            ImageView bgBackColor = view.findViewById(R.id.get_course_img_bg);
            bgBackColor.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "درسی یافت نشد ", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    private void initSwipeRefreashLayout(View view) {
        swipeRefreshLayout = view.findViewById(R.id.get_course_swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        swipeRefreshLayout.setWaveColor(Color.rgb(57, 73, 171));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Toast.makeText(getContext(), R.string.updating, Toast.LENGTH_SHORT).show();
            update();
        });
    }

    private void getCoursesWeb() {
        if (MyTools.isNetworkConnected(getContext())
        ) {
            AndroidNetworking.initialize(getActivity());
            AndroidNetworking.post(getString(R.string.host) + getString(R.string.getCourses))
                    .addBodyParameter("code", getNameFromSharedRefs())
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
                            //courseItems.add(new Course(cId, cName, cDay, cTime, cChar));
                            //   courseEntityItems.add(new CourseEntity(cChar, cName, null, cDay, cTime, null, null, null, 1, 2));
                            CourseEntity myCourseWeb = new CourseEntity(cChar, cName, null, cDay, cTime, null, null, null, 1, 2);
                            saveIfNotExisitInDB(myCourseWeb);
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
        } else {
            Toast.makeText(getActivity(), "مشکل شبکه", Toast.LENGTH_SHORT).show();

        }
        if (swipeRefreshLayout.isRefreshing()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 1000);
        }


    }

    private void saveIfNotExisitInDB(CourseEntity courseEntity) {
        CourseEntity courseafter = courseDao.getOne(courseEntity.getCharacteristic());
        if (courseafter == null) {
            //onject is not exist in db
            // Log.d("test", courseafter.getCharacteristic());
            courseDao.save(courseEntity);
        } else {
            Log.d("test", courseafter.getCharacteristic());
        }
    }

    private void update() {
        courseItems.clear();
        courseEntityItemList = courseDao.getAll();
        recyclerView.scheduleLayoutAnimation();
        getCoursesWeb();
    }

    private String getNameFromSharedRefs() {
        SharedPreferences preferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        return preferences.getString("code", "");
    }

    private void readyResponse(String result) {
        String[] elements = result.split(",");
        List<String> fix = Arrays.asList(elements);
        ArrayList<String> listofStrings = new ArrayList<>(fix);
        Toast.makeText(getActivity(), "courses:" + listofStrings, Toast.LENGTH_SHORT).show();

    }
}