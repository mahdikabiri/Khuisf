package com.example.khuisf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.khuisf.ui.share.ShareFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String CHARAC="characteristic";
    public static final String NAME="coursename";
    public static final String DAY="courseday";
    public static final String TIME="coursetime";
    NavigationView navigationView;
    Button btnInfo;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);



        View headView = navigationView.getHeaderView(0);

        TextView tv_access = headView.findViewById(R.id.nav_header_access_tv);
        TextView tv_name = headView.findViewById(R.id.nav_header_name_tv);
        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        int role = preferences.getInt("role", 0);
        //set menu
        setMenu(role);
        tv_access.setText(setAccess(role));
        tv_name.setText(preferences.getString("name", ""));


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    private void setMenu(int role) {
        //this menu sett menu by user roles
        if (role == 1) navigationView.inflateMenu(R.menu.stu_menu);
        else if (role == 2) navigationView.inflateMenu(R.menu.teacher_menu);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itme_action_settings) {
            startActivity(new Intent(MainActivity.this, ElipsisActivity.class));
        }else if(item.getItemId()==R.id.itme_img_prof){
        startActivity(new Intent(MainActivity.this,InfoActivity.class));
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
}
