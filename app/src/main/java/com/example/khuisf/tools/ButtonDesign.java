package com.example.khuisf.tools;

import android.content.Context;
import android.widget.Button;

import com.example.khuisf.R;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class ButtonDesign {
    public static void setDesign(Button button, Context context) {
        //this is for low versions andorid can not show ripple
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            button.setBackground(context.getResources().getDrawable(R.drawable.custom_tv_blue_bg));
        } else {
            button.setBackground(context.getResources().getDrawable(R.drawable.ripple_foe_loginbtn));
        }


        PushDownAnim.setPushDownAnimTo(button)
                .setScale(PushDownAnim.MODE_SCALE, 0.89f);



    }
}
