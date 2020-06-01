package com.example.khuisf.students.weeklyplan2.weeklyplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khuisf.R;
import com.example.khuisf.entitys.Course;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    BottomSheetDialog bottomSheetDialog;
    LayoutInflater inflater;
    Context context;
    TextView tvCourseName, tvCourseChar, tvCourseDay, tvCourseTeacher, tvCourseTime;
    private ArrayList<Course> mCourses;

    public CourseAdapter(Context context, ArrayList<Course> courses) {
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

                createBottomShetDialog(currentItem.getTitle(), currentItem.getDay(), currentItem.getCharac(), currentItem.getTime());
                tvCourseChar.setText(currentItem.getCharac());
                tvCourseTime.setText(currentItem.getTime());
                tvCourseDay.setText(currentItem.getDay());
                tvCourseName.setText(currentItem.getTitle());
                bottomSheetDialog.show();

            }
        });
    }


    private void createBottomShetDialog(String courseTitle, String courseDay, String courseChar, String courseTime) {
        if (bottomSheetDialog == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_dialog_course_info, null);
            tvCourseName = view.findViewById(R.id.courseinfo_tv_coursename);
            tvCourseTeacher = view.findViewById(R.id.courseinfo_teachername);
            tvCourseDay = view.findViewById(R.id.courseinfo_tv_day);
            tvCourseChar = view.findViewById(R.id.courseinfo_char);
            tvCourseTime = view.findViewById(R.id.courseinfo_tv_time);
            bottomSheetDialog = new BottomSheetDialog(context);
            bottomSheetDialog.setContentView(view);
        }
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
