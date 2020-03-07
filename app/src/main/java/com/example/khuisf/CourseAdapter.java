package com.example.khuisf;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private ArrayList<Course> mCourses;
    LayoutInflater inflater;
    Context context;

    public CourseAdapter(Context context,ArrayList<Course> courses) {
       this.context=context;
        mCourses = courses;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view= inflater.inflate(R.layout.emaple_item,parent,false);
        return new CourseViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course currentItem= mCourses.get(position);
        holder.tvTitle.setText(currentItem.getTitle());
        holder.tvDay.setText(currentItem.getDay());
        holder.tvTime.setText(currentItem.getTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,holder.tvTime.getText(), Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context,CourseInfoActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder{
        public TextView tvTitle;
        public TextView tvDay;
        public TextView tvTime;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.item_tv_title);
           tvTime=itemView.findViewById(R.id.item_tv_time);
           tvDay=itemView.findViewById(R.id.item_tv_day);
        }
    }

}
