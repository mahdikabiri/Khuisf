package com.example.khuisf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.glomadrian.codeinputlib.CodeInput;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class FotgetPassActivity extends AppCompatActivity {
Button  btnSend;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotget_pass);
        //codeInputViewPhone=findViewById(R.id.forget_pass_codeinput);
        btnSend=findViewById(R.id.forget_pass_btn_send_number);



        PushDownAnim.setPushDownAnimTo(btnSend)
                .setScale(PushDownAnim.MODE_SCALE, 0.89f);


        //this is for low versions andorid can not show ripple
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            btnSend.setBackground(getResources().getDrawable(R.drawable.custom_tv_blue_bg));
        } else {
            btnSend.setBackground(getResources().getDrawable(R.drawable.ripple_foe_loginbtn));
        }



        /*btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeInputViewPhone.animate();

                codeInputViewPhone.getCode();
            }
        });*/
    }
}
