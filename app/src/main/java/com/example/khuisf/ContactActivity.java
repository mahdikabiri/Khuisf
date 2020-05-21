package com.example.khuisf;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.khuisf.tools.ButtonDesign;
import com.example.khuisf.tools.GetDataFromSH;
import com.thekhaeng.pushdownanim.PushDownAnim;
import com.valdesekamdem.library.mdtoast.MDToast;

public class ContactActivity extends AppCompatActivity {
    Button btnSend;
    EditText edtName, edtSubject, edtText;
    ImageButton ivTelegram, ivGmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        init();
        ButtonDesign.setDesign(btnSend, this);


        PushDownAnim.setPushDownAnimTo(ivTelegram)
                .setScale(PushDownAnim.MODE_SCALE, 0.7f);
        PushDownAnim.setPushDownAnimTo(ivGmail)
                .setScale(PushDownAnim.MODE_SCALE, 0.7f);

        btnSend.setOnClickListener(v -> {
            getReadyValues(edtName.getText().toString(), edtSubject.getText().toString().trim(), edtText.getText().toString());
        });

    }

    private void getReadyValues(String name, String subject, String text) {
        if (subject.isEmpty() || text.isEmpty()) {
            MDToast mdToast = MDToast.makeText(getApplicationContext(), "ورودی ها را کنترل کنید", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
            mdToast.show();
        } else {
            String nationalCode = GetDataFromSH.getNationalCodeFromSharedPrefs(this);
            sendValeus(name, subject, text, nationalCode);
        }


    }

    private void sendValeus(String name, String subject, String text, String nationalCode) {
        Toast.makeText(this, nationalCode + "", Toast.LENGTH_SHORT).show();
    /*    AndroidNetworking.post(getString(R.string.host) + getString(R.string.login_url))
                .addBodyParameter("name", name)
                .addBodyParameter("subject", subject)
                .addBodyParameter("text", text)
                .addBodyParameter("national_code", GetDataFromSH.getCodeFromSharedPrefs(this))
                .setTag("LOGIN")
                .build();*/
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
