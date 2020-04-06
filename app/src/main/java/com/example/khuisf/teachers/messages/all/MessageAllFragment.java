package com.example.khuisf.teachers.messages.all;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.igenius.customcheckbox.CustomCheckBox;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MessageAllFragment extends Fragment {
    ArrayList<Student> studentItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    ImageButton btnSelectAll;
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_teacher_all, container, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AndroidNetworking.initialize(getContext());
        recyclerView = getActivity().findViewById(R.id.fragment_message_teacher_all_recycler);
        studentItems = new ArrayList<>();
        btnSelectAll = view.findViewById(R.id.fragment_mesage_all_select_all);
        fab = view.findViewById(R.id.fragment_message_teacher_all_fab);
        adapter = new StudentAdapterForTeachMessage(getContext(), studentItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(adapter);
        getStudnet();
        btnSelectAll.setOnClickListener(v -> {
            selectAll();
        });

        fab.setOnClickListener(v -> {
            initData();
        });
    }

    public void initData() {
        //this method get data from any item if checked and send to final message sending page
        Intent intent = new Intent(getActivity(), FinalSendMessageActivity.class);
        List<String> stuname;
        stuname = new ArrayList<>();
        List<String> stucodes;
        stucodes = new ArrayList<>();
        for (int i = 0; i <= adapter.getItemCount() - 1; i++) {
            //get item from recycler
            View view1 = recyclerView.getChildAt(i);
            CustomCheckBox cb = view1.findViewById(R.id.get_student_message_item_checkbox);
            if (cb.isChecked()) {
                //init textviews
                TextView tvname = view1.findViewById(R.id.get_student_message_item_tvname);
                TextView tvcode = view1.findViewById(R.id.get_student_message_item_tvcode);
                //get data from textviews
                String selectedName = tvname.getText().toString();
                String selectedCode = tvcode.getText().toString();
                //set data to list
                stuname.add(selectedName);
                stucodes.add(selectedCode);
            }
        }
        //send lis with intent
        intent.putStringArrayListExtra("codes", (ArrayList<String>) stucodes);
        intent.putStringArrayListExtra("names", (ArrayList<String>) stuname);
        startActivity(intent);
    }


    private void selectAll() {
        for (int i = 0; i <= adapter.getItemCount() - 1; i++) {
            View view = recyclerView.getChildAt(i);
            CustomCheckBox cb = view.findViewById(R.id.get_student_message_item_checkbox);
            cb.setChecked(true);
        }

    }

    private void getStudnet() {
        AndroidNetworking.initialize(getActivity());
        AndroidNetworking.post(Urls.host + Urls.getStudentForMessage)
                .addBodyParameter("teacher_code", getNameFromSharedRefs())
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
                    Toast.makeText(getActivity(), e.toString() + "", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(getActivity(), "ایراد در دریافت اسامی", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private String getNameFromSharedRefs() {
        SharedPreferences preferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        return preferences.getString("code", "");
    }

}
