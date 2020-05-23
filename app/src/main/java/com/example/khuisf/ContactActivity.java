package com.example.khuisf;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.khuisf.tools.ButtonDesign;
import com.example.khuisf.tools.GetDataFromSH;
import com.phantomvk.slideback.SlideActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;
import com.valdesekamdem.library.mdtoast.MDToast;

import cn.pedant.SweetAlert.SweetAlertDialog;
import maes.tech.intentanim.CustomIntent;

import static maes.tech.intentanim.CustomIntent.customType;

public class ContactActivity extends SlideActivity {
    Button btnSend;
    EditText edtName, edtSubject, edtText;
    ImageButton ivTelegram, ivGmail;

    @Override
    public void finishAfterTransition() {
        super.finishAfterTransition();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        init();
        ButtonDesign.setDesign(btnSend, this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PushDownAnim.setPushDownAnimTo(ivTelegram)
                .setScale(PushDownAnim.MODE_SCALE, 0.8f);
        PushDownAnim.setPushDownAnimTo(ivGmail)
                .setScale(PushDownAnim.MODE_SCALE, 0.8f);

        btnSend.setOnClickListener(v -> {
            getReadyValues(edtName.getText().toString(), edtSubject.getText().toString().trim(), edtText.getText().toString());
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }
    private void getReadyValues(String name, String subject, String text) {
        if (subject.isEmpty() || text.isEmpty()) {
            //check fields
            MDToast mdToast = MDToast.makeText(getApplicationContext(), "ورودی ها را کنترل کنید", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
            mdToast.show();
        } else {
            //send message
            new SweetAlertDialog(this, SweetAlertDialog.BUTTON_NEGATIVE)
                    .setTitleText("آیا مطمنید؟")
                    .setConfirmText("بله")

                    .setConfirmClickListener(sDialog -> {
                        sDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                        sendValeus(name, subject, text, GetDataFromSH.getNationalCodeFromSharedPrefs(this), sDialog);
                    })
                    .show();
        }
    }


    private void sendValeus(String name, String subject, String text, String nationalCode, SweetAlertDialog sDialog) {
        Toast.makeText(this, nationalCode + "", Toast.LENGTH_SHORT).show();
        AndroidNetworking.post(getString(R.string.host) + getString(R.string.contactUs_url))
                .addBodyParameter("name", name)
                .addBodyParameter("subject", subject)
                .addBodyParameter("text", text)
                .addBodyParameter("national_code", nationalCode)
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                if (response.equals("done")) {
                    sDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    sDialog.setTitleText("پیام شما ارسال شد ");
                    sDialog.setConfirmButton("باشه", sweetAlertDialog -> {
                        sweetAlertDialog.dismissWithAnimation();
                        setNullEdts();
                    });
                } else {
                    sDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    sDialog.setTitleText("ارسال پیام با مشکل مواجه شد");
                }
            }

            @Override
            public void onError(ANError anError) {
                MDToast mdToast = MDToast.makeText(getApplicationContext(), getString(R.string.network_err), MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                mdToast.show();
            }
        });
    }

    private void setNullEdts() {
        edtText.setText("");
        edtName.setText("");
        edtSubject.setText("");
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(ContactActivity.this,"right-to-left");
    }

    private void init() {
        btnSend = findViewById(R.id.contactus_btn_send_message);
        edtName = findViewById(R.id.contact_edt_name);
        edtSubject = findViewById(R.id.contact_edt_subject);
        edtText = findViewById(R.id.contact_edt_text);
        ivTelegram = findViewById(R.id.contactus_iv_telegram);
        ivGmail = findViewById(R.id.contactus_iv_gmail);

    }
}
