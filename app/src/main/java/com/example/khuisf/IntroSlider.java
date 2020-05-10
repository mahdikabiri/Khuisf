package com.example.khuisf;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.khuisf.tools.SessionManager;
import com.example.khuisf.tools.SessionManagerIntroSlider;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;


public class IntroSlider extends AppIntro2 {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntroFragment.newInstance(getString(R.string.weekly_plan), getString(R.string.weekly_plan_des), R.drawable.graphic_calender_whites, ContextCompat.getColor(getApplicationContext(), R.color.mybluecolor)));
        addSlide(AppIntroFragment.newInstance(getString(R.string.attendancer), getString(R.string.attendancer_des), R.drawable.graphic_attendancer, ContextCompat.getColor(getApplicationContext(), R.color.mybluecolor)));
        addSlide(AppIntroFragment.newInstance(getString(R.string.connections), getString(R.string.connections_des), R.drawable.grapic_messaging_whiteg, ContextCompat.getColor(getApplicationContext(), R.color.mybluecolor)));
        addSlide(AppIntroFragment.newInstance(getString(R.string.select_score), getString(R.string.select_courses_des), R.drawable.graphic_select_courses, ContextCompat.getColor(getApplicationContext(), R.color.mybluecolor)));
        addSlide(AppIntroFragment.newInstance(getString(R.string.delete_course), getString(R.string.deletecourse_des), R.drawable.graphic_delete_whiteg, ContextCompat.getColor(getApplicationContext(), R.color.mybluecolor)));
        addSlide(AppIntroFragment.newInstance(getString(R.string.noifications), getString(R.string.notify_des), R.drawable.graphic_notify, ContextCompat.getColor(getApplicationContext(), R.color.colordark)));
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
        SessionManagerIntroSlider sessionManager = new SessionManagerIntroSlider(this);
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
