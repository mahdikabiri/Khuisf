package com.example.khuisf.teachers.messages;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.khuisf.R;
import com.example.khuisf.entitys.Urls;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class FinalSendMessageActivity extends AppCompatActivity {
    ListView listViewName, listViewCode;
    EditText text;
    Button btnSendMessgae;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        ArrayList<String> names = getIntent().getStringArrayListExtra("names");
        ArrayList<String> codes = getIntent().getStringArrayListExtra("codes");
        String flagRole = getIntent().getStringExtra("flagRole");
        listViewName.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names));
        listViewCode.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, codes));

        btnSendMessgae.setOnClickListener(v -> {
            sendMessage(v, text.getText().toString());
            //for a delay to inserting data we neet to time
            Handler handler = new Handler();
            Runnable r = () -> {
                for (int i = 0; i < codes.size(); i++) {
                    sendStudentData(v, codes.get(i), text.getText().toString(), flagRole);
                }
                btnSendMessgae.setVisibility(View.GONE);
            };
            handler.postDelayed(r, 500);

        });
    }


    private void sendStudentData(View view, String code, String text, String flagRole) {
        AndroidNetworking.post(Urls.host + Urls.insertMessageForStudent)
                .addBodyParameter("student_code", code)
                .addBodyParameter("message_text", text)
                .addBodyParameter("flag_role", flagRole)
                .build().
                getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(FinalSendMessageActivity.this, response + "", Toast.LENGTH_SHORT).show();
                        Log.d("salamali", response);
                        if (response.equals("1")) {
                            Snackbar.make(view, R.string.insert_success, Snackbar.LENGTH_LONG).
                                    setAction("ثبت شد", null).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Snackbar.make(view, R.string.insert_fail, Snackbar.LENGTH_LONG).
                                setAction("Action", null).show();
                    }
                });
    }


    private void sendMessage(View view, String text) {
        AndroidNetworking.post(Urls.host + Urls.insertMessage)
                .addBodyParameter("message_text", text)
                .addBodyParameter("sender_message", getNameFromSharedRefs())
                .build().
                getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("1")) {
                            Snackbar.make(view, R.string.insert_success, Snackbar.LENGTH_INDEFINITE).
                                    setAction("Action", null).show();
                            //  fab.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Snackbar.make(view, R.string.insert_fail, Snackbar.LENGTH_LONG).
                                setAction("Action", null).show();
                    }
                });

    }

    private String getNameFromSharedRefs() {
        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        return preferences.getString("name", "");
    }

    private void init() {
        setContentView(R.layout.activity_final_send_message);
        listViewName = findViewById(R.id.activity_final_send_message_listview_name);
        listViewCode = findViewById(R.id.activity_final_send_message_listview_code);
        btnSendMessgae = findViewById(R.id.activity_final_send_message_btn_send);
        text = findViewById(R.id.activity_final_send_message_edittext);
    }
}
