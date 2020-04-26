package com.example.khuisf.teachers.attendancer;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khuisf.R;
import com.example.khuisf.entitys.Student;
import com.example.khuisf.entitys.StudentAttendancer;
import com.polyak.iconswitch.IconSwitch;

import org.json.JSONObject;

import java.util.ArrayList;

public class AttendancerAdapter extends RecyclerView.Adapter<AttendancerAdapter.CourseViewHolder> {
    public JSONObject jsonObject = new JSONObject();
    LayoutInflater inflater;
    Context context;
    private ArrayList<StudentAttendancer> mstudents;

    public AttendancerAdapter(Context context, ArrayList<StudentAttendancer> students, JSONObject jsonObjectonnvar) {
        this.context = context;
        mstudents = students;
        this.inflater = LayoutInflater.from(context);
    }



    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.attendanec_item, parent, false);
        return new CourseViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        StudentAttendancer currentItem = mstudents.get(position);
        holder.tvName.setText(currentItem.getName());
        holder.tvCode.setText(currentItem.getCode());
        generateStatus(currentItem.getStatus(),holder);
        holder.iconSwitch.setCheckedChangeListener(current -> {
            if (current==IconSwitch.Checked.LEFT){
                //gheybat =0
                holder.iconSwitch.setAlpha(1);
                currentItem.setStatus(0);
            }else if(current==IconSwitch.Checked.RIGHT){
                holder.iconSwitch.setAlpha(1);
                currentItem.setStatus(1);
            }
        });

        if(currentItem.getStatus()==2){
            holder.iconSwitch.setAlpha((float) .1);
        }
    }

    private void generateStatus(int status, CourseViewHolder holder) {
        if (status==0){
            //gheybat
            holder.iconSwitch.setChecked(IconSwitch.Checked.LEFT);
        }else if(status==1){
            //hozoor
            holder.iconSwitch.setChecked(IconSwitch.Checked.RIGHT);
        }else {
            holder.iconSwitch.setChecked(IconSwitch.Checked.LEFT);
            holder.iconSwitch.setAlpha((float) .2);
        }

    }


    @Override
    public int getItemCount() {
        return mstudents.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvCode;
        public IconSwitch iconSwitch;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.attendancer_tv_name);
            tvCode = itemView.findViewById(R.id.attendancer_tv_code);
            iconSwitch = itemView.findViewById(R.id.attendance_item_icon_switch);



        }


    }

}
