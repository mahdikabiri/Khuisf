package com.example.khuisf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.khuisf.recoverpass.FortgetPassActivity;
import com.example.khuisf.tools.SessionManager;
import com.google.android.material.textfield.TextInputLayout;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.json.JSONObject;

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


        /*TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }else {
        Log.d("sss",telephonyManager.getDeviceId());
        }
*/
        PushDownAnim.setPushDownAnimTo(btnLogin)
                .setScale(PushDownAnim.MODE_SCALE, 0.89f);



       /* ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
*/

        inputLayoutUsername = findViewById(R.id.text_input_username);
        inputLayoutPass = findViewById(R.id.text_input_pass);

        tvforgetPass = findViewById(R.id.login_tv_forgetpass);

        setClickableText();


        //this is for low versions andorid can not show ripple
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            btnLogin.setBackground(getResources().getDrawable(R.drawable.custom_tv_blue_bg));
        } else {
            btnLogin.setBackground(getResources().getDrawable(R.drawable.ripple_foe_loginbtn));
        }


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
                .setTag("LOGIN")
                .build().
                getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("answ", response.toString());
                        try {
                            String getedUsername = response.getString("username");
                            String getedName = response.getString("name");
                            int getedAccess = response.getInt("role");

                            if (getedUsername.toLowerCase().equals(username.toLowerCase())) {
                                //
                                SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
                                preferences.edit().putString("username", getedUsername).apply();
                                preferences.edit().putString("name", getedName).apply();
                                preferences.edit().putInt("role", getedAccess).apply();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                                Toast.makeText(LoginActivity.this, "شما وارد شدید", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(LoginActivity.this, "نام کاربری یا رمز عبور اشتباه است", Toast.LENGTH_SHORT).show();
                            inputLayoutPass.setError("");
                            inputLayoutUsername.setError("");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(LoginActivity.this, "connection fail", Toast.LENGTH_SHORT).show();
                        Log.d("answ", anError.toString());

                    }
                });
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
