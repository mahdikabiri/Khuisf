package com.example.khuisf.teachers.messages.bycourses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khuisf.CourseInfoActivity;
import com.example.khuisf.MainActivity;
import com.example.khuisf.R;
import com.example.khuisf.entitys.Course;
import com.example.khuisf.teachers.attendancer.AttendancerActivity;

import java.util.ArrayList;

public class CourseAdapterForTeachMessage extends RecyclerView.Adapter<CourseAdapterForTeachMessage.CourseViewHolder> {
    LayoutInflater inflater;
    Context context;

    private ArrayList<Course> mCourses;

    public CourseAdapterForTeachMessage(Context context, ArrayList<Course> courses) {
        this.context = context;
        mCourses = courses;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.getcours_item, parent, false);
        return new CourseViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course currentItem = mCourses.get(position);
        holder.tvTitle.setText(currentItem.getTitle());
        holder.tvDay.setText(currentItem.getDay());
        holder.tvTime.setText(currentItem.getTime());
        holder.tvChar.setText(currentItem.getCharac());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, SendMessageToStudentByCourseActivity.class);
                intent.putExtra("CHARACTRISTIC",currentItem.getCharac());
                intent.putExtra("coursename",currentItem.getTitle());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvDay;
        public TextView tvTime;
        public TextView tvChar;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.item_getcourse_tv_title);
            tvTime = itemView.findViewById(R.id.item_getcourse_tv_time);
            tvDay = itemView.findViewById(R.id.item_getcourse_tv_day);
            tvChar = itemView.findViewById(R.id.item_getcourse_tv_char);
        }
    }

}
