package com.example.khuisf;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FortgetPassActivity extends AppCompatActivity {
    Button btnSend;
    EditText edtPhoneNumber;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotget_pass);
        btnSend = findViewById(R.id.forget_pass_btn_send_number);
        edtPhoneNumber = findViewById(R.id.forget_pass_edt_phone_number);
        PushDownAnim.setPushDownAnimTo(btnSend)
                .setScale(PushDownAnim.MODE_SCALE, 0.89f);

        btnSend.setOnClickListener(v -> {
            String getNumber = edtPhoneNumber.getText().toString().trim();
            if (getNumber.length() < 10) {
                Toast.makeText(this, "کد را کامل وارد کنید", Toast.LENGTH_SHORT).show();
            } else {

                getReadyNum(getNumber);
            }

        });

        //this is for low versions andorid can not show ripple
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            btnSend.setBackground(getResources().getDrawable(R.drawable.custom_tv_blue_bg));
        } else {
            btnSend.setBackground(getResources().getDrawable(R.drawable.ripple_foe_loginbtn));
        }

        /*btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeInputViewPhone.animate();

                codeInputViewPhone.getCode();
            }
        });*/
    }

    private void getReadyNum(String getNumber) {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("کد " + getNumber + " صحیح است؟")
                .setContentText("پیامک به شماره مربوط به کد ملی ثبت شده ارسال می شود")
                .setConfirmText("بله")
                .setConfirmClickListener(sDialog -> {
                    SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                  //  pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    sendPhonNumber(getNumber, sDialog,pDialog);

                })
                .show();
    }

    private void sendPhonNumber(String nationalCode, SweetAlertDialog sDialog, SweetAlertDialog pDialog) {
        AndroidNetworking.post(getString(R.string.host) + getString(R.string.checkPhoneNumberForSms))
                .addBodyParameter("national_code", nationalCode)
                .setTag("test")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                       pDialog.dismiss();
                        if (!response.equals(getString(R.string.phone_number_not_found))) {
                            sDialog
                                  .setContentText(getString(R.string.access_to_simcard))
                                    .setTitleText(response)
                                    .setConfirmText("بله" )
                                    .setCancelButton("خیر", s -> {
                                        s.dismiss();
                                    })
                                    .setConfirmClickListener(s ->{
                                        Toast.makeText(FortgetPassActivity.this, "sendig message", Toast.LENGTH_SHORT).show();
                                    })
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        } else {
                            Toast.makeText(FortgetPassActivity.this, getString(R.string.phone_number_not_found), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(FortgetPassActivity.this, "ایراد در شبکه", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
