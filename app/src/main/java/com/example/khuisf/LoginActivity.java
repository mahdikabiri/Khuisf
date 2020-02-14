package com.example.khuisf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edtUsername, edtPassword;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activiry);
        init();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        btnLogin.setOnClickListener((View v) -> {

            login(edtUsername.getText().toString().trim(), edtPassword.getText().toString().trim());
        });
    }

    private void login(final String username, String password) {
        AndroidNetworking.post(Urls.host + Urls.login)
                .addBodyParameter("username", username)
                .addBodyParameter("password", password)
                .setTag("LOGIN")
                .build().
                /*getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                Log.d("answ", response.toString());

            }

            @Override
            public void onError(ANError anError) {

            }
        });*/
    getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("answ", response.toString());
                try {
                    String getedUsername = response.getString("username");
                    String getedName = response.getString("name");
                    int getedAccess = response.getInt("access");

                    if (getedUsername.toLowerCase().equals(username.toLowerCase())) {
                        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
                        preferences.edit().putString("username", getedUsername).apply();
                        preferences.edit().putString("name", getedName).apply();
                        preferences.edit().putInt("access", getedAccess).apply();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                        Toast.makeText(LoginActivity.this, "شما وارد شدید", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "نام کاربری یا رمز عبور اشتباه است", Toast.LENGTH_SHORT).show();
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
        edtPassword = findViewById(R.id.login_edt_password);
        spinner = findViewById(R.id.spinner);


    }

    public void opentest(View view) {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));

    }
}
