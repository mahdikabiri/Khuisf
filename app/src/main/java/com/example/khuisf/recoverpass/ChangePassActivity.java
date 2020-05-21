package com.example.khuisf.recoverpass;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.khuisf.R;
import com.valdesekamdem.library.mdtoast.MDToast;

public class ChangePassActivity extends AppCompatActivity {
    Button btnChangePass;
    EditText edtPass, edtConfirmPass;
    String nationalCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        init();
        craeteBackgrandButton();
        btnChangePass.setOnClickListener(v -> {
            validationInputs(edtPass.getText().toString().trim(), edtConfirmPass.getText().toString().trim());
        });

        SharedPreferences preferences = getSharedPreferences("code", MODE_PRIVATE);
        nationalCode = preferences.getString("nationalcode", "");
    }

    private void craeteBackgrandButton() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            btnChangePass.setBackground(getResources().getDrawable(R.drawable.custom_tv_blue_bg));
        } else {
            btnChangePass.setBackground(getResources().getDrawable(R.drawable.ripple_foe_loginbtn));
        }
    }

    private void init() {
        btnChangePass = findViewById(R.id.change_pass_btn_change);
        edtPass = findViewById(R.id.changepass_edt_pass);
        edtConfirmPass = findViewById(R.id.changepass_edt_pass_confirm);
    }


    private void validationInputs(String pass, String ConfirmPass) {

        if (pass.equals(ConfirmPass)) {
            if (!pass.equals("")) {
                changePass(pass);

            } else {
                MDToast mdToast = MDToast.makeText(getApplicationContext(), "ورودی خالی است", MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING);
                mdToast.show();
            }
        } else {
            MDToast mdToast = MDToast.makeText(getApplicationContext(), "مقادیر برابر نیستند", MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING);
            mdToast.show();
        }
    }

    private void changePass(String newPassword) {
        AndroidNetworking.post(getString(R.string.host) + getString(R.string.changePass))
                .addBodyParameter("national_code", nationalCode)
                .addBodyParameter("new_pass", newPassword)
                .setTag("LOGIN")
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                if (response.equals("done")) {
                    MDToast mdToast = MDToast.makeText(getApplicationContext(), getString(R.string.password_changed) + "", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                    mdToast.show();
                    finish();
                }else {
                    Log.d("salam",response);
                    MDToast mdToast = MDToast.makeText(getApplicationContext(), getString(R.string.password_not_changed) + "", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                    mdToast.show();
                }

            }

            @Override
            public void onError(ANError anError) {
                MDToast mdToast = MDToast.makeText(getApplicationContext(),  getString(R.string.network_err), MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                mdToast.show();
            }
        });
    }


}
