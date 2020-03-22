package com.example.khuisf.teachers.attendancer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khuisf.R;
import com.example.khuisf.entitys.Student;

import java.util.ArrayList;

public class attendancerAdapter extends RecyclerView.Adapter<attendancerAdapter.CourseViewHolder> {
    private ArrayList<Student> mstudents;
    LayoutInflater inflater;
    Context context;

    public attendancerAdapter(Context context, ArrayList<Student> students) {
       this.context=context;
        mstudents = students;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view= inflater.inflate(R.layout.attendanec_item,parent,false);
        return new CourseViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Student currentItem= mstudents.get(position);
        holder.tvName.setText(currentItem.getName());
        holder.tvCode.setText(currentItem.getCode());

    }

    @Override
    public int getItemCount() {
        return mstudents.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder{
        public TextView tvName;
        public TextView tvCode;
        public RadioGroup radioGroup;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.attendancer_tv_name);
            tvCode=itemView.findViewById(R.id.attendancer_tv_code);
           radioGroup=itemView.findViewById(R.id.attendancer_rg);
        }
    }

}
