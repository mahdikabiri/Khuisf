package com.example.khuisf.teachers.attendancer;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.khuisf.R;
import com.example.khuisf.entitys.StudentAttendancer;
import com.example.khuisf.entitys.Urls;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class AttendancerActivity extends AppCompatActivity {
    ArrayList<StudentAttendancer> studentItems, studentItems1;
    String charac, courseTitle;
    TextView tvDatePicker, tvCourseName;
    FloatingActionButton fab;
    JSONObject myObj = new JSONObject();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private PersianDatePickerDialog picker;
    SpeedDialView speedDialView;
    LinearLayoutManager layout;
    WaveSwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendancer);
        charac = getIntent().getStringExtra("CHARACTRISTIC");
        courseTitle = getIntent().getStringExtra("coursename");
        init();
        studentItems = new ArrayList<>();
        studentItems1 = new ArrayList<>();
        adapter = new AttendancerAdapter(this, studentItems, myObj);
        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        layout = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.setAdapter(adapter);
        tvCourseName.setText(courseTitle);

        //get date auto matic
        PersianCalendar persianCalendar = new PersianCalendar();
        String date1 = persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay();
        tvDatePicker.setText(date1);
        fab.setOnClickListener(v -> {
            String date = (String) tvDatePicker.getText();
            if (!date.equals("تاریخ را انتخاب کنید")) {
                gatReadyValues(v);
            } else {
                Toast.makeText(AttendancerActivity.this, "تاریخ را مشخص کنید", Toast.LENGTH_SHORT).show();
            }
        });
        getStudnets(0);

        initSwipeRefreashLayout();
       /* recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


///this is good func
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    Toast.makeText(AttendancerActivity.this, "Last", Toast.LENGTH_LONG).show();
                    moveAnimation();
                }
            }
        });*/

    }


    public void moveAnimation() {
        fab.animate().translationY(-300).translationX(400).setDuration(500);
    }

    public void moveAnimationright() {
        fab.animate().translationX(+300).setDuration(500);
    }



    private void initSwipeRefreashLayout() {
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.BLUE);
        swipeRefreshLayout.setWaveColor(Color.rgb(240, 240, 240));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Toast.makeText(this, R.string.updating, Toast.LENGTH_SHORT).show();
            update();
        });

    }


    private void update() {
        studentItems.clear();
        getStudnets(0);
        recyclerView.scheduleLayoutAnimation();
    }

    private void init() {
        recyclerView = findViewById(R.id.recycler_attendance1);
        tvCourseName = findViewById(R.id.attendancer_activity_tv_coursenaem);
        tvDatePicker = findViewById(R.id.attendancer_activity_btn_select);
        fab = findViewById(R.id.attendancer_activity_fab);
        swipeRefreshLayout = findViewById(R.id.get_student_attendance_for_teacher_swipe_refresh);
        //set items to tools
        speedDialView = findViewById(R.id.speedDial);
        speedDialView.inflate(R.menu.menu_tools_attendancer);
        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                // tools items selected
                switch (actionItem.getId()) {
                    case R.id.attendancer_item_tools_tickall:
                        //tick item selected
                        setAllTick();
                        break;
                    case R.id.attendancer_item_tools_cancel:
                        //cancel item selected
                        setAllCancel();
                        break;
                    case R.id.attendancer_item_tools_null:
                        //cancel item selected
                        setAllNull();
                        break;
                }
                return false;
            }
        });
    }

    private void setAllCancel() {
        studentItems.clear();
        getStudnets(0);
    }

    private void setAllNull() {
        studentItems.clear();
        getStudnets(2);
    }
    private void setAllTick() {
        /*for (int i = 0; i <= adapter.getItemCount() - 1; i++) {
            View view = recyclerView.getChildAt(i);
            // TextView tvName = view.findViewById(R.id.attendancer_tv_name);
            IconSwitch iconSwitch = view.findViewById(R.id.attendance_item_icon_switch);
            iconSwitch.setChecked(IconSwitch.Checked.RIGHT);
        }*/
        studentItems.clear();
        getStudnets(1);
    }

    private void gatReadyValues(View v) {
        for (int i = 0; i <= adapter.getItemCount()-1; i++) {
            String name = studentItems.get(i).getName();
            int status = studentItems.get(i).getStatus();
            String code = studentItems.get(i).getCode();
            sendDate(tvDatePicker.getText().toString(), v, code, charac, String.valueOf(status));
        }
    }


    private String generateState(String state) {
        if (state.equals("RIGHT")) {
            //hozoor darad
            return "1";
        } else if (state.equals("LEFT")) {
            //hozoor nadared
            return "0";
        }
        return "-1";
    }

    private void getStudnets(int status) {
        AndroidNetworking.initialize(this);
        AndroidNetworking.post(Urls.host + Urls.getStudent)
                .addBodyParameter("char", charac)
                .setTag("getCourse")
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("ddf", response.toString());
                try {
                    //this loop repeating to count of course list
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        String cName = object.getString("name");
                        String cCode = object.getString("codestudent");
                        String cPic = object.getString("pic");
                        // add items from db and save to arraylist
                        studentItems.add(new StudentAttendancer(cName, cCode, status,cPic));
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.toString() + "", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.d("salam", anError.toString());
            }
        });
        if (swipeRefreshLayout.isRefreshing()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 1000);
        }
    }

    private void sendDate(String date, View view, String studentCode, String charac, String state) {
        AndroidNetworking.post(Urls.host + Urls.insertAttendancer)
                .addBodyParameter("date", date)
                .addBodyParameter("studentcode", studentCode)
                .addBodyParameter("charac", charac)
                .addBodyParameter("status", state)
                .setTag("LOGIN")
                .build().
                getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("1")) {
                            Snackbar.make(view, R.string.insert_success, Snackbar.LENGTH_INDEFINITE).
                                    setAction("Action", null).show();
                            fab.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Snackbar.make(view, R.string.insert_fail, Snackbar.LENGTH_LONG).
                                setAction("Action", null).show();
                    }
                });
    }

    public void openCalender(View view) {
        showCalendar(view);
    }

    public void showCalendar(View v) {
        //Typeface typeface = Typeface.createFromAsset(getAssets(), "vazir.ttf");
        PersianCalendar initDate = new PersianCalendar();
        //initDate.setPersianDate(1370, 3, 13);
        picker = new PersianDatePickerDialog(this)
                .setPositiveButtonString("انتخاب")
                .setNegativeButton("بستن")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setMinYear(1300)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setInitDate(initDate)
                .setActionTextColor(Color.GRAY)

                //.setTypeFace(typeface)
                /*.setBackgroundColor(Color.BLACK)
                .setTitleColor(Color.WHITE)
                .setActionTextColor(Color.WHITE)*/
                //.setPickerBackgroundDrawable(R.drawable.darkmode_bg)
                .setTitleType(PersianDatePickerDialog.DAY_MONTH_YEAR)
                .setCancelable(false)
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        String finalDate = persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay();
                        tvDatePicker.setText(finalDate);
                    }

                    @Override
                    public void onDismissed() {
                    }
                });


        picker.show();
    }


}
