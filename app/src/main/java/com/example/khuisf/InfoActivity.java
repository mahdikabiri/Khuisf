package com.example.khuisf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity {
    public static int ROLE = 0;
    Spinner spinner;
    TextView tvCode, tvName;
    Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        spinner = findViewById(R.id.spinner2);
        btnReturn = findViewById(R.id.info_btn_return);
        tvCode = findViewById(R.id.info_tv_num);
        tvName = findViewById(R.id.info_tv_name2);
        createSpinner(preferences, spinner);
        tvName.setText(preferences.getString("name", ""));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String textFormSpinner = spinner.getSelectedItem().toString();
                //get role code form spinner and send with username to server
                getInfo(String.valueOf(getSelectedItem(textFormSpinner)), preferences.getString("username", ""));
                ROLE = getSelectedItem(textFormSpinner);
                preferences.edit().putInt("role", getSelectedItem(textFormSpinner)).apply();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InfoActivity.this, MainActivity.class));
            }
        });
    }


    private int getSelectedItem(String textFormSpinner) {
        int roleForSend = 0;
        switch (textFormSpinner) {
            case "دانشجو":
                roleForSend = 1;
                break;
            case "استاد":
                roleForSend = 2;
                break;
            case "کارمند":
                roleForSend = 3;
                break;
            case "مدیر":
                roleForSend = 4;
                break;
        }
        return roleForSend;

    }


    private void createSpinner(SharedPreferences preferences, Spinner s) {
        int role = preferences.getInt("role", 0);
        if (role > 4) {
            Toast.makeText(this, "شما چند نقش دارید لطفا یکی را انتخاب کنید", Toast.LENGTH_SHORT).show();
        }
        final List<String> list = new ArrayList<String>();

        switch (role) {
            case 1:
                list.add("دانشجو");
                break;
            case 2:
                list.add("استاد");
                break;
            case 3:
                list.add("کارمند");
                break;
            case 4:
                list.add("مدیر");
                break;
            case 5:
                list.add("استاد");
                list.add("دانشجو");
                break;
            case 6:
                list.add("کارمند");
                list.add("دانشجو");
                break;


        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        s.setAdapter(dataAdapter);
    }

    private void getInfo(String role, String username) {

        AndroidNetworking.post(getString(R.string.host) + getString(R.string.getInfo))
                .addBodyParameter("username", username)
                .addBodyParameter("role", role)
                .setTag("LOGIN")
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);

                String getedCode = null;
                try {
                    getedCode = response.getString("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvCode.setText(getedCode);
                preferences.edit().putString("code", getedCode).apply();
            }

            @Override
            public void onError(ANError anError) {
                Log.d("sdsd", anError.toString());
            }
        });
    }


}
