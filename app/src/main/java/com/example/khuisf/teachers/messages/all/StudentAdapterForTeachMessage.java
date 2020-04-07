package com.example.khuisf.teachers.messages.all;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khuisf.R;
import com.example.khuisf.entitys.Course;
import com.example.khuisf.entitys.Student;

import net.igenius.customcheckbox.CustomCheckBox;

import java.util.ArrayList;

public class StudentAdapterForTeachMessage extends RecyclerView.Adapter<StudentAdapterForTeachMessage.CourseViewHolder> {
    LayoutInflater inflater;
    Context context;

    private ArrayList<Student> mCourses;

    public StudentAdapterForTeachMessage(Context context, ArrayList<Student> courses) {
        this.context = context;
        mCourses = courses;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.get_student_message_item, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Student studentItem = mCourses.get(position);
        holder.tvStuName.setText(studentItem.getName());
        holder.tvStuCode.setText(studentItem.getCode());
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        public TextView tvStuName,tvStuCode;
        public CustomCheckBox cbCheck;


        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStuName = itemView.findViewById(R.id.get_student_message_item_tvname);
            tvStuCode = itemView.findViewById(R.id.get_student_message_item_tvcode);
            cbCheck = itemView.findViewById(R.id.get_student_message_item_checkbox);

        }
    }

}
