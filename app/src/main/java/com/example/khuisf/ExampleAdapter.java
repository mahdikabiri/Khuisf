package com.example.khuisf;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<ExapleItem> mExapleItems;
    public ExampleAdapter(ArrayList<ExapleItem> exapleItems) {
        mExapleItems=exapleItems;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.emaple_item,parent,false);
    ExampleViewHolder evh=new ExampleViewHolder(view);
        return evh;
    }



    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExapleItem currentItem=mExapleItems.get(position);
        holder.tvTitle.setText(currentItem.getTitle());
        holder.tvDay.setText(currentItem.getDay());
        holder.tvTime.setText(currentItem.getTime());
    }

    @Override
    public int getItemCount() {
        return mExapleItems.size();
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView tvTitle;
        public TextView tvDay;
        public TextView tvTime;
        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.item_tv_title);
           tvTime=itemView.findViewById(R.id.item_tv_time);
           tvDay=itemView.findViewById(R.id.item_tv_day);
        }
    }

}
