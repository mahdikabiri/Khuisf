package com.example.khuisf;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.khuisf.tools.SessionManagerIntroSlider;

public class StartActivity extends AppCompatActivity {
    ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ivLogo = findViewById(R.id.iv_logo);

        AlphaAnimation animation1 = new AlphaAnimation(0f, 0.7f);
        animation1.setDuration(1000);
        ivLogo.startAnimation(animation1);
        SessionManagerIntroSlider manager = new SessionManagerIntroSlider(this);
        if (!manager.isLogedInforIntro()) {
            finish();
            startActivity(new Intent(this, IntroSlider.class));
        } else {

            android.os.Handler handler = new Handler();
            Runnable r = () -> {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            };
            handler.postDelayed(r, 1500);
        }

    }
}
