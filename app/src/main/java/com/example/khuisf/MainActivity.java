package com.example.khuisf;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.androidnetworking.AndroidNetworking;
import com.example.khuisf.messgeainbox.InboxActivity;
import com.example.khuisf.students.contact_teacher.ContactToTeacherActivity;
import com.example.khuisf.tools.MyTools;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import maes.tech.intentanim.CustomIntent;
import me.cheshmak.android.sdk.core.config.CheshmakConfig;

public class MainActivity extends AppCompatActivity {
    public static final String CHARAC = "characteristic";
    public static final String NAME = "coursename";
    public static final String DAY = "courseday";
    public static final String TIME = "coursetime";
    NavigationView navigationView;
    int role;
    LinearLayout layoutStu, layoutTeach;
    CircleImageView circleImageViewNavProf;
    private AppBarConfiguration mAppBarConfiguration;
    public static Context context;


    private long backPressedTime;
    private Toast backTost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AndroidNetworking.initialize(this);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);


        init();

        context = this;
        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        View headView = navigationView.getHeaderView(0);
        TextView tv_access = headView.findViewById(R.id.nav_header_access_tv);
        TextView tv_name = headView.findViewById(R.id.nav_header_name_tv);
        role = preferences.getInt("role", 0);
        //set menu
        setMenu(role);
        tv_access.setText(setAccess(role));
        String name = preferences.getString("name", "");
        tv_name.setText(name);
        String code = preferences.getString("code", "null");
       /* if (code.equals("null")) {
            startActivity(new Intent(MainActivity.this, InfoActivity.class));

        }*/
        //getInfo(String.valueOf(role),name);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.weekly_plan_student, R.id.nav_insertscore, R.id.nav_attendaner,
                R.id.nav_score, R.id.nav_share, R.id.select_courses, R.id.deleteCourses, R.id.see_attendace,
                R.id.nav_send_message_teacher, R.id.contactTeacherFragment, R.id.nav_week, R.id.show_all_notes)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        circleImageViewNavProf = navigationView.getHeaderView(0).findViewById(R.id.nav_header_img_prof);


        //for delay in get data from web and save in prefreces use the hanler class to run after 3 secondes
        Handler handler = new Handler();
        Runnable r = () -> {
            String picUrl = preferences.getString("pic", "");
            setImageProf(picUrl);
        };
        handler.postDelayed(r, 3000);


    }

    private void configCheshmak() {
        String gConfig = CheshmakConfig.getString("block_state", "not");
    }

    private void setImageProf(String picUrl) {
        if (picUrl.isEmpty()) {
            circleImageViewNavProf.setImageResource(R.drawable.ic_error_load);
        } else {
            Picasso.get().load(picUrl).placeholder(R.drawable.ic_avatar_placeholfrt).into(circleImageViewNavProf);
        }
    }

    private void init() {
        navigationView = findViewById(R.id.img_prof_drawer_menu);
        //  circleImageViewNavProf = findViewById(R.id.nav_header_img_prof);
    }

    private void setMenu(int role) {
        //this menu sett menu by user roles
        if (role == 1) navigationView.inflateMenu(R.menu.stu_menu);
        else if (role == 2) navigationView.inflateMenu(R.menu.teacher_menu);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        // menuInflater.inflate(R.menu.menu_main_student, menu);
        if (role == 1) {
            menuInflater.inflate(R.menu.menu_main_student, menu);
        } else if (role == 2) {
            menuInflater.inflate(R.menu.menu_main_teacher, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itme_action_settings) {
            Toast.makeText(this, "به زودی ...", Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(MainActivity.this, ElipsisActivity.class));
        } else if (item.getItemId() == R.id.itme_img_prof) {
            startActivity(new Intent(MainActivity.this, InfoActivity.class));
        } else if (item.getItemId() == R.id.itme_img_message) {
            startActivity(new Intent(MainActivity.this, InboxActivity.class));
        } else if (item.getItemId() == R.id.itme_action_sendmessage) {
            if (role == 1) {
                startActivity(new Intent(this, ContactToTeacherActivity.class));
               /* getSupportFragmentManager().beginTransaction()
                        .add(android.R.id.content, new getCouserForSendMessageTeachFragment()).commit();*/
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private String setAccess(int access) {
        if (access == 1) return "دانشجو";
        else if (access == 2) return "استاد";
        else if (access == 3) return "پرسنل";
        return "";
    }

/*
    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backTost.cancel();
            super.onBackPressed();
            return;
        } else {
            backTost = Toast.makeText(context, "برای خروج دوباره فشار دهید", Toast.LENGTH_SHORT);
            backTost.show();
        }
        backPressedTime = System.currentTimeMillis();
    }*/

    public void open_ac_support(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, ContactActivity.class);
        startActivity(intent);
        CustomIntent.customType(MainActivity.this, "left-to-right");
    }

    public void logout(MenuItem item) {

        MyTools.logout(this);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}
