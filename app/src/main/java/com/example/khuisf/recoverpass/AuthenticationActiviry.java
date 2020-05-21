package com.example.khuisf.recoverpass;

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

import com.example.khuisf.R;
import com.raycoarana.codeinputview.CodeInputView;
import com.raycoarana.codeinputview.OnCodeCompleteListener;
import com.valdesekamdem.library.mdtoast.MDToast;

import ir.samanjafari.easycountdowntimer.CountDownInterface;
import ir.samanjafari.easycountdowntimer.EasyCountDownTextview;

public class AuthenticationActiviry extends AppCompatActivity {
    CodeInputView codeInput;
    TextView tvSendAgain,tvInsertCode;
    String getedAuthCode;
    EasyCountDownTextview countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        init();
        Intent intent = getIntent();
        getedAuthCode = intent.getStringExtra("authCode");

        countDownTimer.setOnTick(new CountDownInterface() {
            @Override
            public void onTick(long time) {
            }

            @Override
            public void onFinish() {
                Toast.makeText(AuthenticationActiviry.this, "finish", Toast.LENGTH_SHORT).show();
                codeInput.setEditable(false);
                codeInput.setError("زمان شما تمام شده");
            }
        });
        codeInput.addOnCompleteListener(new OnCodeCompleteListener() {
            @Override
            public void onCompleted(String code) {
                //Make the input enable again so the user can change it
                if (getedAuthCode.equals(codeInput.getCode())) {
                    MDToast mdToast = MDToast.makeText(getApplicationContext(), getString(R.string.moving_to_change_pass_ac), MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                    mdToast.show();
                    finish();
                    startActivity(new Intent(AuthenticationActiviry.this, ChangePassActivity.class));
                } else {
                    //Show error
                    codeInput.setError(R.string.wrong_code);
                    codeInput.setEditable(true);

                }
            }
        });
        setClickableText();
    }

    private void init() {
        codeInput = findViewById(R.id.authentication_code_input1);
        tvSendAgain = findViewById(R.id.authentication_tv_sendaagain);
        countDownTimer = findViewById(R.id.authentication_timer);
    }

    private void setClickableText() {
        String fotgetText = getString(R.string.if_not_recive_sms);
        SpannableString ss = new SpannableString(fotgetText);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(AuthenticationActiviry.this, FortgetPassActivity.class));
                finish();
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
