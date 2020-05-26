package com.example.khuisf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.khuisf.recoverpass.FortgetPassActivity;
import com.example.khuisf.tools.ButtonDesign;
import com.example.khuisf.tools.SessionManager;
import com.google.android.material.textfield.TextInputLayout;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.cheshmak.android.sdk.core.Cheshmak;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edtUsername, edtPassword;
    TextView tvforgetPass;
    TextInputLayout inputLayoutUsername, inputLayoutPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activiry);
        init();
        SessionManager manager = new SessionManager(this);
        if (manager.isLogedIn()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }


        ButtonDesign.setDesign(btnLogin, this);

        inputLayoutUsername = findViewById(R.id.text_input_username);
        inputLayoutPass = findViewById(R.id.text_input_pass);

        tvforgetPass = findViewById(R.id.login_tv_forgetpass);

        setClickableText();

        btnLogin.setOnClickListener((View v) -> {
                    if (validInput(inputLayoutUsername) && validInput(inputLayoutPass)) {
                        login(edtUsername.getText().toString().trim(), edtPassword.getText().toString().trim(), manager);
                    }
                }
        );
    }

    private void setClickableText() {
        String fotgetText = "رمز عبور خود را فراموش کرده ام";

        SpannableString ss = new SpannableString(fotgetText);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(LoginActivity.this, FortgetPassActivity.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.mybluecolor));
            }
        };
        ss.setSpan(clickableSpan, 16, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvforgetPass.setText(ss);
        tvforgetPass.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void login(final String username, String password, final SessionManager manager) {
        AndroidNetworking.post(getString(R.string.host) + getString(R.string.login_url))
                .addBodyParameter("username", username)
                .addBodyParameter("password", password)
                .build().
                getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String getedUsername = response.getString("username");
                            String getedName = response.getString("name");
                            int getedAccess = response.getInt("role");

                            if (getedUsername.toLowerCase().equals(username.toLowerCase())) {
                                //
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                MDToast mdToast = MDToast.makeText(getApplicationContext(), getString(R.string.your_entered), MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                                mdToast.show();
                                SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
                                preferences.edit().putString("username", getedUsername).apply();
                                preferences.edit().putString("name", getedName).apply();
                                preferences.edit().putInt("role", getedAccess).apply();
                                sendCheshmakIdToServer(getedUsername);

                                if (getedAccess == 1) {
                                    Cheshmak.sendTag("student");
                                } else if (getedAccess == 2) {
                                    Cheshmak.sendTag("teacher");
                                }

                                finish();
                            }

                        } catch (Exception e) {
                            MDToast mdToast = MDToast.makeText(getApplicationContext(), getString(R.string.wrong_user_pass), MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                            mdToast.show();
                            inputLayoutPass.setError("");
                            inputLayoutUsername.setError("");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.toString().contains("is_blocked")) {
                            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                    .setTitleText("ورود ممکن نیست!")
                                    .setContentText("حساب مورد نظر از طرف مدیریت مسدود شده است")
                                    .setCustomImage(R.drawable.ic_banned)
                                    .setConfirmButton("ارتباط با پشتیبانی", sweetAlertDialog -> {

                                    })
                                    .setCancelButton("بستن", sweetAlertDialog -> sweetAlertDialog.dismissWithAnimation())
                                    .show();

                        } else {
                            MDToast mdToast = MDToast.makeText(getApplicationContext(), getString(R.string.network_err), MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                            mdToast.show();
                        }
                    }
                });


    }


    private void sendCheshmakIdToServer(String username) {
        String cheshmakID = Cheshmak.getCheshmakID(this);
        AndroidNetworking.post(getString(R.string.host) + getString(R.string.insertCheshmakId))
                .addBodyParameter("username", username)
                .addBodyParameter("cheshmak_id", cheshmakID)
                .build();
    }

    private void init() {
        btnLogin = findViewById(R.id.login_btn_login);
        edtUsername = findViewById(R.id.login_edt_username);
        edtPassword = findViewById(R.id.login_edt_pass);
    }

    private boolean validInput(TextInputLayout tvUsername) {
        String userName = tvUsername.getEditText().getText().toString().trim();
        if (userName.isEmpty()) {
            tvUsername.setError(" ورودی نمی تواند خالی باشد");
            return false;
        } else if (userName.length() > 15) {
            tvUsername.setError("ورودی ها طولانی است");
            return false;
        } else {
            tvUsername.setError(null);
            return true;
        }
    }
}
