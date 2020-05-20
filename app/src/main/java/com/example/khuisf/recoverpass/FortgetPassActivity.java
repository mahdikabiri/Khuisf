package com.example.khuisf.recoverpass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.khuisf.R;
import com.example.khuisf.tools.MyNetwork;
import com.thekhaeng.pushdownanim.PushDownAnim;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FortgetPassActivity extends AppCompatActivity {
    Button btnSend;
    EditText edtPhoneNumber;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        btnSend = findViewById(R.id.forget_pass_btn_send_number);
        edtPhoneNumber = findViewById(R.id.forget_pass_edt_phone_number);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        PushDownAnim.setPushDownAnimTo(btnSend)
                .setScale(PushDownAnim.MODE_SCALE, 0.89f);

        btnSend.setOnClickListener(v -> {
            String getNumber = edtPhoneNumber.getText().toString().trim();
            if (getNumber.length() < 10) {
                Toast.makeText(this, R.string.insert_compalate_code, Toast.LENGTH_SHORT).show();
            } else {
                // startActivity(new Intent(FortgetPassActivity.this, AuthenticationActiviry.class));
                if (MyNetwork.isNetworkConnected(this)) {

                    saveNationalCodeToSH(getNumber);
                    getReadyNum(getNumber);
                } else {
                    Toast.makeText(this, R.string.check_internet_connection_presian, Toast.LENGTH_SHORT).show();
                }
            }
        });
        craeteBackgrandButton();
        /*btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeInputViewPhone.animate();

                codeInputViewPhone.getCode();
            }
        });*/
    }

    private void saveNationalCodeToSH(String getNumber) {
        SharedPreferences preferences = getSharedPreferences("code", MODE_PRIVATE);
        preferences.edit().putString("nationalcode", getNumber).apply();
    }

    private void craeteBackgrandButton() {
        //this is for low versions andorid can not show ripple
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            btnSend.setBackground(getResources().getDrawable(R.drawable.custom_tv_blue_bg));
        } else {
            btnSend.setBackground(getResources().getDrawable(R.drawable.ripple_foe_loginbtn));
        }
    }

    private void getReadyNum(String getNumber) {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("کد " + getNumber + " صحیح است؟")
                .setContentText("پیامک به شماره مربوط به کد ملی ثبت شده ارسال می شود")
                .setConfirmText("بله")
                .setConfirmClickListener(sDialog -> {
                    SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                    //  pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("جستجو...");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    sendPhonNumber(getNumber, sDialog, pDialog);
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
                                    .setConfirmText("بله")
                                    .setCancelButton("خیر", s -> {
                                        s.dismiss();
                                    })
                                    .setConfirmClickListener(s -> {
                                        //send request for send sms to user and go to autrization activity
                                        //s.dismiss();
                                        s.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                                        s.setTitle("در حال ارسال پیام");
                                        s.setContentText("");
                                        s.setCancelText("لغو");
                                        s.setCancelable(false);
                                        sendSms(nationalCode);
                                    })
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        } else {
                            sDialog
                                    .setContentText("شماره پیدا نشد")
                                    .setTitleText(response)
                                    .setConfirmText("بستن")
                                    .setConfirmClickListener(s -> {
                                        sDialog.dismiss();
                                    })
                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        pDialog.setTitle("اینترنت خود را برسی کنید");

                    }
                });
    }

    private void sendSms(String nationalCode) {
        AndroidNetworking.post(getString(R.string.host) + getString(R.string.genetareCodeForsms))
                .addBodyParameter("national_code", nationalCode)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("code", response);
                        Toast.makeText(FortgetPassActivity.this, response, Toast.LENGTH_LONG).show();
                        startAuthActiviry(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }

    public void startAuthActiviry(String response) {
        Intent intent = new Intent(FortgetPassActivity.this, AuthenticationActiviry.class);
        intent.putExtra("authCode", response);
        finish();
        startActivity(intent);
    }
}
