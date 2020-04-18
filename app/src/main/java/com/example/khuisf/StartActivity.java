package com.example.khuisf;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {
    ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ivLogo = findViewById(R.id.iv_logo);

        AlphaAnimation animation1 = new AlphaAnimation(0f, 1.0f);
        animation1.setDuration(2000);
        ivLogo.startAnimation(animation1);

        android.os.Handler handler = new Handler();
        Runnable r = () -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        };
        handler.postDelayed(r, 2500);

    }
}
