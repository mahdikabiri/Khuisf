package com.example.khuisf.ui.share;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.khuisf.InfoActivity;
import com.example.khuisf.R;

import static android.content.Context.MODE_PRIVATE;

public class ShareFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        SharedPreferences preferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        int role = preferences.getInt("role", 0);

        if (role > 4) {
             startActivity(new Intent(getActivity(), InfoActivity.class));
             getActivity().finish();

        }
        return root;

    }
}
        /*

        spinner = root.findViewById(R.id.info_spinner_role);
        tvCode=root.findViewById(R.id.info_tv_num);
        tvName=root.findViewById(R.id.info_tv_name);
        tvName.setText(preferences.getString("name",""));
        createSpinner(root,preferences, spinner);
        AndroidNetworking.initialize(getActivity());



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String textFormSpinner= spinner.getSelectedItem().toString();
                //get role code form spinner and send with username to server
                String s=String.valueOf(getSelectedItem(textFormSpinner));
                preferences.edit().putString("role",s);
                getInfo(String.valueOf(getSelectedItem(textFormSpinner)),preferences.getString("username",""));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private int getSelectedItem(String textFormSpinner) {
        int roleForSend=0;
        switch (textFormSpinner){
            case "دانشجو":
                roleForSend=1;
                break;
            case "استاد":
                roleForSend=2;
                break;
            case "کارمند":
                roleForSend=3;
                break;
            case "مدیر":
                roleForSend=4;
                break;

        }
        return roleForSend;

    }


    private void createSpinner(View root, SharedPreferences preferences, Spinner s) {
        int role = preferences.getInt("role", 0);
        if(role>4){
            Toast.makeText(getActivity(), "شما چند نقش دارید لطفا یکی را انتخاب کنید", Toast.LENGTH_SHORT).show();
           // startActivity(new Intent(getActivity(), InfoActivity.class));

        }
        final List<String> list=new ArrayList<String>();

        switch (role){
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


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        s.setAdapter(dataAdapter);
    }

    private void getInfo(String role,String username) {

        AndroidNetworking.post(Urls.host + Urls.getInfo)
                .addBodyParameter("username",username)
                .addBodyParameter("role",role)
                .setTag("LOGIN")
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                SharedPreferences preferences = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);

                String getedCode = null;
                try {
                    getedCode = response.getString("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvCode.setText(getedCode);
                preferences.edit().putString("code",getedCode).apply();
                Log.d("sdsd",response.toString());
            }

            @Override
            public void onError(ANError anError) {
                Log.d("sdsd",anError.toString());
            }
        });

*/