package com.example.khuisf.ui.share;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.khuisf.R;
import com.example.khuisf.entitys.Urls;
import com.example.khuisf.tools.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    LinearLayout layoutStu, layoutTeach;
    TextView tvName, tvNationalCode, tvUserName, tvPhone, tvFatherName;
    //Students
    TextView tvStuCode, tvFieldStu;
    //Teachers
    TextView tvTeacherCode;
    CircleImageView ivAvatar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        SharedPreferences preferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        int role = preferences.getInt("role", 0);
        String username = preferences.getString("username", "null");


        SessionManager manager = new SessionManager(getActivity());
        if (!manager.isLogedIn()) {
            //get profile data from web
            getInfo(String.valueOf(role), username);
            manager.setLogedIn(true);

            android.os.Handler handler = new Handler();
            Runnable r = () -> {
                setTexes(role, view);
            };
            handler.postDelayed(r, 1000);
        }

        setTexes(role, view);

        Picasso.get().load(Urls.pic1Url).error(R.drawable.ic_error_load).fit().into(ivAvatar);


        view.findViewById(R.id.fragment_profile_fab).setOnClickListener(v -> {
            Toast.makeText(getActivity(), "به زودی", Toast.LENGTH_SHORT).show();
        });
    }

    private void setTexes(int role, View view) {
        SharedPreferences preferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        tvUserName.setText(preferences.getString("username", getString(R.string.unknown)));
        tvName.setText(preferences.getString("name", getString(R.string.unknown)));
        tvNationalCode.setText(preferences.getString("national_code", getString(R.string.unknown)));
        tvPhone.setText(preferences.getString("phone", getString(R.string.unknown)));
        tvFatherName.setText(preferences.getString("father_name", getString(R.string.unknown)));
        if (role == 1) {
            //is student
            layoutTeach.setVisibility(View.GONE);
            tvStuCode = view.findViewById(R.id.fragment_profile_tv_stucode);
            tvFieldStu = view.findViewById(R.id.fragment_profile_tv_field);
            tvStuCode.setText(preferences.getString("code", getString(R.string.unknown)));
            tvFieldStu.setText(preferences.getString("field", getString(R.string.unknown)));
        } else if (role == 2) {
            //is teacher
            layoutStu.setVisibility(View.GONE);
            tvTeacherCode = view.findViewById(R.id.fragment_profile_tv_teachercode);
            tvTeacherCode.setText(preferences.getString("code", getString(R.string.unknown)));
        }
    }

    private void init(View view) {
        layoutStu = view.findViewById(R.id.fragment_profile_layout_student);
        layoutTeach = view.findViewById(R.id.fragment_profile_layout_teacher);

        tvName = view.findViewById(R.id.fragment_profile_tv_name);
        tvUserName = view.findViewById(R.id.fragment_profile_tv_username);
        tvNationalCode = view.findViewById(R.id.fragment_profile_tv_nationalcode);
        tvPhone = view.findViewById(R.id.fragment_profile_tv_phonenumber);
        tvFatherName = view.findViewById(R.id.fragment_profile_tv_father);
        ivAvatar = view.findViewById(R.id.imageview_account_profile);
    }

    private void getInfo(String role, String username) {
        //get all info from web server
        AndroidNetworking.post(Urls.host + Urls.getInfo)
                .addBodyParameter("username", username)
                .addBodyParameter("role", role)
                .setTag("LOGIN")
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {

                SharedPreferences preferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
                String getedCode = null;
                String getedNationalCode = null;
                String getedFatherName = null;
                String getedPhone = null;
                String getedField = null;
                try {
                    getedCode = response.getString("code");
                    getedNationalCode = response.getString("national_code");
                    getedFatherName = response.getString("father_name");
                    getedPhone = response.getString("phone");
                    getedField = response.getString("field");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                preferences.edit().putString("code", getedCode).apply();
                preferences.edit().putString("national_code", getedNationalCode).apply();
                preferences.edit().putString("father_name", getedFatherName).apply();
                preferences.edit().putString("phone", getedPhone).apply();
                preferences.edit().putString("field", getedField).apply();
            }

            @Override
            public void onError(ANError anError) {
                Log.d("sdsd", anError.toString());
            }
        });
    }


}