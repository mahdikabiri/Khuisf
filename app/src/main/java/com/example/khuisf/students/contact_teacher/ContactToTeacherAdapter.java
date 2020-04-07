package com.example.khuisf.students.contact_teacher;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khuisf.R;
import com.example.khuisf.entitys.Course;
import com.example.khuisf.entitys.Teacher;
import com.example.khuisf.teachers.messages.bycourses.SendMessageToStudentByCourseActivity;

import java.util.ArrayList;

public class ContactToTeacherAdapter extends RecyclerView.Adapter<ContactToTeacherAdapter.CourseViewHolder> {
    LayoutInflater inflater;
    Context context;

    private ArrayList<Teacher> mCourses;

    public ContactToTeacherAdapter(Context context, ArrayList<Teacher> courses) {
        this.context = context;
        mCourses = courses;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.teacher_list_item, parent, false);
        return new CourseViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Teacher currentItem = mCourses.get(position);
        holder.tvTeacherName.setText(currentItem.getTeacherName());
        holder.tvCourseName.setText(currentItem.getCourseName());

      /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, SendMessageToStudentByCourseActivity.class);
                intent.putExtra("CHARACTRISTIC",currentItem.getCharac());
                intent.putExtra("coursename",currentItem.getTitle());
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTeacherName;
        public TextView tvCourseName;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTeacherName = itemView.findViewById(R.id.teacher_list_item_tv_teachername);
            tvCourseName = itemView.findViewById(R.id.teacher_list_item_tv_course_name);
        }
    }

}
