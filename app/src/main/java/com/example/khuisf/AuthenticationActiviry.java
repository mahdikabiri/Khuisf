package com.example.khuisf;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.raycoarana.codeinputview.CodeInputView;
import com.raycoarana.codeinputview.OnCodeCompleteListener;

public class AuthenticationActiviry extends AppCompatActivity {
    CodeInputView codeInput;
    TextView tvSendAgain;
    String getedAuthCode;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        init();
        Intent intent = getIntent();
        getedAuthCode = intent.getStringExtra("authCode");

        codeInput.addOnCompleteListener(new OnCodeCompleteListener() {
            @Override
            public void onCompleted(String code) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Make the input enable again so the user can change it
                        if (getedAuthCode.equals(codeInput.getCode())) {
                            Toast.makeText(AuthenticationActiviry.this, "در حال انتقال به صفخه تغییر پسورد", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(AuthenticationActiviry.this, ChangePassActivity.class));
                        } else {
                            codeInput.setEditable(true);
                            //Show error
                            codeInput.setError("کد شما اشتباه است");
                        }
                    }
                }, 1000);
            }
        });
        setClickableText();
    }

    private void init() {
        codeInput = findViewById(R.id.authentication_code_input);
        tvSendAgain = findViewById(R.id.authentication_tv_sendaagain);
    }

    private void setClickableText() {
        String fotgetText = getString(R.string.if_not_recive_sms);
        SpannableString ss = new SpannableString(fotgetText);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                //  startActivity(new Intent(LoginActivity.this, FortgetPassActivity.class));
                Toast.makeText(AuthenticationActiviry.this, "send again message", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.mybluecolor));
            }
        };
        ss.setSpan(clickableSpan, 27, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvSendAgain.setText(ss);
        tvSendAgain.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
