package com.example.khuisf;

import android.content.Intent;
import android.net.Uri;
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
        PushDownAnim.setPushDownAnimTo(ivTelegram)
                .setScale(PushDownAnim.MODE_SCALE, 0.8f);
        PushDownAnim.setPushDownAnimTo(ivGmail)
                .setScale(PushDownAnim.MODE_SCALE, 0.8f);

        btnSend.setOnClickListener(v -> {
            getReadyValues(edtName.getText().toString(), edtSubject.getText().toString().trim(), edtText.getText().toString());
        });
        ivTelegram.setOnClickListener(v -> {
          /*  Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            waIntent.setPackage("org.telegram.messenger");
            startActivity(waIntent);*/
            /*final String appName = "org.telegram.messenger";
             String msg = "test message";
            final boolean isAppInstalled = isAppAvailable(getApplicationContext(), appName);
            if (isAppInstalled)
            {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                myIntent.setPackage(appName);
                myIntent.putExtra(Intent.EXTRA_TEXT, msg);//
                startActivity(Intent.createChooser(myIntent, "Share with"));
            }
            else
            {
                Toast.makeText(this, "Telegram not Installed", Toast.LENGTH_SHORT).show();
            }*/

            Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/mahdikabiri96"));
            startActivity(telegram);
        });

        ivGmail.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "mahdikabiri47@gmail.com", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "اپلیکیشن دانشگاه");
            startActivity(Intent.createChooser(emailIntent, null));
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            CustomIntent.customType(ContactActivity.this, "right-to-left");
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

    private void init() {
        //set back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnSend = findViewById(R.id.contactus_btn_send_message);
        edtName = findViewById(R.id.contact_edt_name);
        edtSubject = findViewById(R.id.contact_edt_subject);
        edtText = findViewById(R.id.contact_edt_text);
        ivTelegram = findViewById(R.id.contactus_iv_telegram);
        ivGmail = findViewById(R.id.contactus_iv_gmail);

    }
}
