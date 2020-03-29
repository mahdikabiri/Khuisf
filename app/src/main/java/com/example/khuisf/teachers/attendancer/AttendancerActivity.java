package com.example.khuisf.teachers.attendancer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.khuisf.R;
import com.example.khuisf.entitys.Student;
import com.example.khuisf.entitys.Urls;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class AttendancerActivity extends AppCompatActivity {
    ArrayList<Student> studentItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    String charac, courseTitle;
    private PersianDatePickerDialog picker;
    TextView tvDatePicker,tvCourseName;
    FloatingActionButton fab;
    JSONObject myObj = new JSONObject();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendancer);
        charac = getIntent().getStringExtra("CHARACTRISTIC");
        courseTitle = getIntent().getStringExtra("coursename");
        init();

        AndroidNetworking.initialize(this);
        studentItems = new ArrayList<>();
        adapter = new AttendancerAdapter(this, studentItems, myObj);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        tvCourseName.setText(courseTitle);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = (String) tvDatePicker.getText();
                if (!date.equals("تاریخ را انتخاب کنید")) {
                    gatReadyValues(v);
                } else {
                    Toast.makeText(AttendancerActivity.this, "تاریخ را مشخص کنید", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getStudnets();


    }

    private void init() {
        recyclerView = findViewById(R.id.recycler_attendance1);
        tvCourseName=findViewById(R.id.attendancer_activity_tv_coursenaem);
        tvDatePicker = findViewById(R.id.attendancer_activity_btn_select);
        fab = findViewById(R.id.attendancer_activity_fab);
    }

    private void gatReadyValues(View v) {

        for (int i = 0; i <= adapter.getItemCount() - 1; i++) {
            View view = recyclerView.getChildAt(i);
            // TextView tvName = view.findViewById(R.id.attendancer_tv_name);
            TextView tvCode = view.findViewById(R.id.attendancer_tv_code);
            RadioGroup rgState = view.findViewById(R.id.attendancer_rg);
            //String name = tvName.getText().toString();
            String code = tvCode.getText().toString();
            String status = generateStateFromId(rgState.getCheckedRadioButtonId());
            //Toast.makeText(this, status+"", Toast.LENGTH_SHORT).show();
            sendDate(tvDatePicker.getText().toString(), v, code, charac, status);
        }


        // Log.d("mylog1","code="+code +"   name="+name+"   /"+status);
    }

    private String generateStateFromId(int id) {
        if (id == 2131296543) {
            //hozoor darad
            return "1";
        } else if (id == 2131296542) {
            //hozoor nadared
            return "0";
        }
        return "-1";
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


    private void getStudnets() {
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
                        // add items from db and save to arraylist
                        studentItems.add(new Student(cName, cCode));
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
    }


    public void openCalender(View view) {
        showCalendar(view);
    }

    public void showCalendar(View v) {
        //Typeface typeface = Typeface.createFromAsset(getAssets(), "Shabnam-Light-FD.ttf");

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
