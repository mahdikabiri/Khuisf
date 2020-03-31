package com.example.khuisf.students.watchattendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khuisf.R;
import com.example.khuisf.entitys.Attendance;

import java.util.ArrayList;

public class AdapterForAttendance extends RecyclerView.Adapter<AdapterForAttendance.CourseViewHolder> {

    LayoutInflater inflater;
    Context context;
    private ArrayList<Attendance> mitems;

    public AdapterForAttendance(Context context, ArrayList<Attendance> attendancesItems) {
        this.context = context;
        mitems = attendancesItems;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.attendace_item, parent, false);
        return new CourseViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Attendance currentItem = mitems.get(position);
        holder.tvDate.setText(currentItem.getDate());
        //check what is status and set icon to image view
        String status=currentItem.getStatus();
        if(status.equals("1")){
            holder.ivStatus.setImageResource(R.drawable.ic_tick);
        }else if(status.equals("0")){
            holder.ivStatus.setImageResource(R.drawable.ic_cancel);
        }else if(status.equals("-1")){
            holder.ivStatus.setImageResource(R.drawable.ic_null);
        }

        //holder.ivStatus.setText(currentItem.getCharac());

     /*   holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ShowAttendanceActivity.class);
               *//* intent.putExtra(MainActivity.CHARAC,currentItem.getCharac());
                intent.putExtra(MainActivity.NAME,currentItem.getTitle());
                intent.putExtra(MainActivity.DAY,currentItem.getDay());
                intent.putExtra(MainActivity.TIME,currentItem.getTime());
*//*
                context.startActivity(intent);
               // Toast.makeText(context, holder.tvTime.getText(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mitems.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDate;
        public ImageView ivStatus;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tem2_tv_title);
            ivStatus = itemView.findViewById(R.id.attendance_item_iv_status);
        }
    }

}
