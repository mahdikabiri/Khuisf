package com.example.khuisf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class attendancerAdapter extends RecyclerView.Adapter<attendancerAdapter.CourseViewHolder> {
    private ArrayList<Course> mstudents;
    LayoutInflater inflater;
    Context context;

    public attendancerAdapter(Context context, ArrayList<Course> courses) {
       this.context=context;
        mstudents = courses;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view= inflater.inflate(R.layout.attendanec_item,parent,false);
        return new CourseViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course currentItem= mstudents.get(position);
        holder.tvName.setText(currentItem.getTitle());

    }

    @Override
    public int getItemCount() {
        return mstudents.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder{
        public TextView tvName;
        public RadioGroup radioGroup;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.attendancer_tv_name);
           radioGroup=itemView.findViewById(R.id.attendancer_rg);
        }
    }

}
