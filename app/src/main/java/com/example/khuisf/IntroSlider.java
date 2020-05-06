package com.example.khuisf;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.khuisf.tools.SessionManager;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;


public class IntroSlider extends AppIntro2 {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntroFragment.newInstance("برنامه هفتگی", "نمایش برنامه هفتگی و اطلاعات دروس", R.drawable.graphic_calender_whites, ContextCompat.getColor(getApplicationContext(), R.color.mybluecolor)));
        addSlide(AppIntroFragment.newInstance("حضور وغیاب", "ثبت حضور وغیاب توسط اساتید و مشاهده توسط دانشجویان", R.drawable.graphic_attendancer, ContextCompat.getColor(getApplicationContext(), R.color.mybluecolor)));
        addSlide(AppIntroFragment.newInstance("ارتباطات", "ارتباط میان دانشجویان و اساتید", R.drawable.grapic_messaging_whiteg, ContextCompat.getColor(getApplicationContext(), R.color.mybluecolor)));
        addSlide(AppIntroFragment.newInstance("انتخاب واحد", "انتخاب درس و لیست دروس ارائه شده", R.drawable.graphic_select_courses, ContextCompat.getColor(getApplicationContext(), R.color.mybluecolor)));
        addSlide(AppIntroFragment.newInstance("خذف و اضافه", "حذف دروس ", R.drawable.graphic_delete_whiteg, ContextCompat.getColor(getApplicationContext(), R.color.mybluecolor)));
        addSlide(AppIntroFragment.newInstance("اعلانات", "اطلاع رسانی هدفمند", R.drawable.graphic_notify, ContextCompat.getColor(getApplicationContext(), R.color.colordark)));
        //setImageSkipButton(getDrawable(R.drawable.ic_skip));
        setColorTransitionsEnabled(true);
        setVibrateIntensity(30);
        setProgressIndicator();
        showStatusBar(false);
        setBackButtonVisibilityWithDone(false);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        openApp();
    }

    private void openApp() {
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.setLogedInforIntro(true);
        finish();
        startActivity(new Intent(this, StartActivity.class));
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        openApp();
    }
}
