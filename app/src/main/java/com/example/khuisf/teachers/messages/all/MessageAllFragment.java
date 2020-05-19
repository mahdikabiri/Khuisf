package com.example.khuisf.teachers.messages.all;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.khuisf.teachers.messages.FinalSendMessageActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MessageAllFragment extends Fragment {
    ArrayList<Student> studentItems;
    Button btnSelectAll, btnSend;
    FloatingActionButton fab2;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.get_all_stu_formessage_teacher, container, false);
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
        btnSend = view.findViewById(R.id.fragment_mesage_all_send);
        fab2 = view.findViewById(R.id.fagg);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "ss", Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new StudentAdapterForTeachMessage(getContext(), studentItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setAdapter(adapter);
        getStudnet(0);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    moveAnimation();
                } else {
                    moveAnimationright();
                }
            }
        });


        btnSelectAll.setOnClickListener(v -> {
            studentItems.clear();
            getStudnet(1);
        });
        fab2.setOnClickListener(v -> {
            initData();

        });
        btnSend.setOnClickListener(v -> {
            initData();
        });
    }

    public void moveAnimation() {
        fab2.animate().translationX(-300).setDuration(500);
    }

    public void moveAnimationright() {
        fab2.animate().translationX(+300).setDuration(500);
    }

    public void initData() {
        //this method get data from any item if checked and send to final message sending page
        Intent intent = new Intent(getActivity(), FinalSendMessageActivity.class);
        List<String> stuname;
        stuname = new ArrayList<>();
        List<String> stucodes;
        stucodes = new ArrayList<>();
        for (int i = 0; i <= adapter.getItemCount() - 1; i++) {
            int selectedState = studentItems.get(i).getState();
            if (selectedState == 1) {
                String selectedName = studentItems.get(i).getName();
                String selectedCode = studentItems.get(i).getCode();
                stuname.add(selectedName);
                stucodes.add(selectedCode);
            }
        }
        //send lis with intent
        intent.putStringArrayListExtra("codes", (ArrayList<String>) stucodes);
        intent.putStringArrayListExtra("names", (ArrayList<String>) stuname);
        //this is for authentication receiver
        intent.putExtra("flagRole", "1");
        if (!stuname.isEmpty()) {
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "حد اقل یک نفر را انتخاب کنید", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectAll() {
        for (int i = 0; i <= adapter.getItemCount() - 1; i++) {
           /* View view = recyclerView.getChildAt(i);
            CustomCheckBox cb = view.findViewById(R.id.get_student_message_item_checkbox);
            cb.setChecked(true);*/
        }
    }

    private void getStudnet(int status) {
        AndroidNetworking.initialize(getActivity());
        AndroidNetworking.post(getString(R.string.host) + getString(R.string.getStudentForMessage))
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
                        String cPic = object.getString("pic");
                        // add items from db and save to arraylist
                        studentItems.add(new Student(cName, cCode, cPic, status));
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
