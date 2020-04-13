package com.example.khuisf.teachers.messages.bycourses;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.khuisf.R;
import com.example.khuisf.entitys.Student;
import com.example.khuisf.entitys.Urls;
import com.example.khuisf.teachers.messages.FinalSendMessageActivity;
import com.example.khuisf.teachers.messages.all.StudentAdapterForTeachMessage;

import net.igenius.customcheckbox.CustomCheckBox;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SendMessageToStudentByCourseActivity extends AppCompatActivity {
    String courseName, characteristic;
    TextView tvCourseName, tvStuCount;
    ArrayList<Student> studentItems;
    private RecyclerView.Adapter adapter;
    ImageView ivSelecAll;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message_to_student_by_course);
        init();
        tvCourseName.setText(courseName);

        recyclerView = findViewById(R.id.activity_send_message_to_student_by_course_tv_stu_count_recyclerview);
        studentItems = new ArrayList<>();
        // btnSelectAll = view.findViewById(R.id.fragment_mesage_all_select_all);
        adapter = new StudentAdapterForTeachMessage(this, studentItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
        getStudnet();
        tvCourseName.setText(courseName);
        tvStuCount.setText(adapter.getItemCount() + "");
        ivSelecAll.setOnClickListener(v -> {
            selectAll();
        });
    }

    private void selectAll() {
        for (int i = 0; i <= adapter.getItemCount() - 1; i++) {
            View view = recyclerView.getChildAt(i);
            CustomCheckBox cb = view.findViewById(R.id.get_student_message_item_checkbox);
            cb.setChecked(true);
        }

    }

    private void init() {
        Intent intent = getIntent();
        courseName = intent.getStringExtra("coursename");
        characteristic = intent.getStringExtra("CHARACTRISTIC");

        tvCourseName = findViewById(R.id.activity_send_message_to_student_by_course_tvcoursename);
        tvStuCount = findViewById(R.id.activity_send_message_to_student_by_course_tv_stu_count);
        ivSelecAll = findViewById(R.id.activity_send_message_to_student_by_course_IV);
    }

    private void getStudnet() {
        AndroidNetworking.initialize(this);
        AndroidNetworking.post(Urls.host + Urls.getStudentForMessageByCourse)
                .addBodyParameter("char", characteristic)
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    //this loop repeating to count of course list
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        String cName = object.getString("name");
                        String cCode = object.getString("codestudent");
                        // add items from db and save to arraylist
                        studentItems.add(new Student(cName, cCode));
                        adapter.notifyDataSetChanged();
                        Log.d("names", cName);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplication(), e.toString() + "", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(getApplication(), "ایراد در دریافت اسامی", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void initData(View view) {
        //this method get data from any item if checked and send to final message sending page
        Intent intent=new Intent(this, FinalSendMessageActivity.class);
        List<String> stuname;
        stuname = new ArrayList<>();
        List<String> stucodes;
        stucodes=new ArrayList<>();
        for (int i = 0; i <= adapter.getItemCount() - 1; i++) {
           //get item from recycler
            View view1 = recyclerView.getChildAt(i);
            CustomCheckBox cb = view1.findViewById(R.id.get_student_message_item_checkbox);
            if(cb.isChecked()){
              //init textviews
                TextView tvname=view1.findViewById(R.id.get_student_message_item_tvname);
                TextView tvcode=view1.findViewById(R.id.get_student_message_item_tvcode);
              //get data from textviews
                String selectedName=tvname.getText().toString();
                String selectedCode=tvcode.getText().toString();
              //set data to list
                stuname.add(selectedName);
                stucodes.add(selectedCode);
            }
        }
        //send lis with intent
        intent.putStringArrayListExtra("codes", (ArrayList<String>) stucodes);
        intent.putStringArrayListExtra("names", (ArrayList<String>) stuname);
        //this is for authentication receiver
        intent.putExtra("flagRole","1");
        startActivity(intent);

    }
}
