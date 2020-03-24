package com.example.khuisf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ir.mirrajabi.persiancalendar.PersianCalendarView;
import ir.mirrajabi.persiancalendar.core.PersianCalendarHandler;
import ir.mirrajabi.persiancalendar.core.interfaces.OnDayClickedListener;
import ir.mirrajabi.persiancalendar.core.models.PersianDate;

public class CalenderActivity extends AppCompatActivity {
    PersianCalendarView persianCalendarView;
    PersianCalendarHandler calendarHandler;
    PersianDate today;
    TextView tvTodayy,tvMouth,tvYear;
    Button btnComeToday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        persianCalendarView = findViewById(R.id.persian_calendar);
        calendarHandler = persianCalendarView.getCalendar();
        today = calendarHandler.getToday();
        tvTodayy = findViewById(R.id.activiry_calender_tv_day);
        tvYear = findViewById(R.id.activiry_calender_tv_year);
        tvMouth = findViewById(R.id.activiry_calender_tv_mouth);
        btnComeToday=findViewById(R.id.activiry_calender_btn_today);

        getToday(today);


        btnComeToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getToday(today);
                persianCalendarView.update();
            }
        });


        persianCalendarView.setOnDayClickedListener(new OnDayClickedListener() {
            @Override
            public void onClick(PersianDate persianDate) {
                // Toast.makeText(MainActivity.this, "sss" + today.getYear() + today.getMonth() + today.getDayOfMonth(), Toast.LENGTH_SHORT).show();
                tvMouth.setText(calendarHandler.getMonthName(persianDate));
                tvYear.setText(persianDate.getYear()+"");
                tvTodayy.setText(persianDate.getDayOfMonth()+"");
            }
        });

    }



    private void getToday(PersianDate today) {
        tvTodayy.setText(today.getDayOfMonth()+"");
        tvMouth.setText(calendarHandler.getMonthName(today)+"");
        tvYear.setText(today.getYear()+"");
    }
}
