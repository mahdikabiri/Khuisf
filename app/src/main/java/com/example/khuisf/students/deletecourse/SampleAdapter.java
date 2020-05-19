package com.example.khuisf.students.deletecourse;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.khuisf.R;
import com.example.khuisf.entitys.Course;
import com.tr4android.recyclerviewslideitem.SwipeAdapter;
import com.tr4android.recyclerviewslideitem.SwipeConfiguration;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class SampleAdapter extends SwipeAdapter implements View.OnClickListener {
    ArrayList<Course> mDataset;
    LayoutInflater inflater;

    private Context mContext;

    public SampleAdapter(Context context, ArrayList<Course> courses) {
        mContext = context;
        this.inflater = LayoutInflater.from(context);
        mDataset = courses;

    }

    @Override
    public SampleViewHolder onCreateSwipeViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.swipeable_item, parent, true);
        return new SampleViewHolder(v);
    }

    @Override
    public void onBindSwipeViewHolder(RecyclerView.ViewHolder swipeViewHolder, int i) {
        SampleViewHolder sampleViewHolder = (SampleViewHolder) swipeViewHolder;
        Course currentItem = mDataset.get(i);

        // ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        //drawable.getPaint().setColor(mContext.getResources().getColor(colors[((int) (Math.random() * (colors.length - 1)))]));
        //sampleViewHolder.avatarView.setBackgroundDrawable(drawable);
        sampleViewHolder.tvChar.setText(currentItem.getCharac());
        sampleViewHolder.tvDay.setText(currentItem.getDay());
        sampleViewHolder.tvTime.setText(currentItem.getTime());
        sampleViewHolder.tvTitle.setText(currentItem.getTitle());
    }

    @Override
    public SwipeConfiguration onCreateSwipeConfiguration(Context context, int position) {
        return new SwipeConfiguration.Builder(context)
                .setLeftBackgroundColorResource(R.color.color_delete)
                .setDrawableResource(R.drawable.ic_delete)
                .setLeftUndoable(true)
                .setLeftUndoDescription(R.string.action_deleted)
                .setDescriptionTextColorResource(android.R.color.white)
                .setLeftSwipeBehaviour(SwipeConfiguration.SwipeBehaviour.NORMAL_SWIPE)
                .setRightSwipeBehaviour(SwipeConfiguration.SwipeBehaviour.NO_SWIPE)
                .build();
    }

    @Override
    public void onSwipe(int position, int direction) {
        SharedPreferences preferences = mContext.getSharedPreferences("prefs", MODE_PRIVATE);
        String stuCode = preferences.getString("code", "");
        Course currentItem = mDataset.get(position);
        String courseChar = currentItem.getCharac();
        String courseName = currentItem.getTitle();
        if (direction == SWIPE_LEFT) {
            mDataset.remove(position);
            notifyItemRemoved(position);
            deleteCourse(stuCode, courseChar, mContext, courseName);

        } else {
            Toast toast = Toast.makeText(mContext, "Marked item as read at position " + position, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onClick(View view) {
        // We need to get the parent of the parent to actually have the proper view

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void deleteCourse(String studentCode, String characteristic, Context context, String courseName) {
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(context.getString(R.string.host) + context.getString(R.string.deleteCourse))
                .addBodyParameter("char", characteristic)
                .addBodyParameter("code", studentCode)
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                Log.d("mures", response);
                if (response.equals("1")) {
                    Toast.makeText(context, courseName + " با موفقیت حذف شد ", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "درس حذف نشد", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(context, "ارور شبکه", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public class SampleViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvDay;
        public TextView tvTime;
        public TextView tvChar;
        ConstraintLayout contentView;

        public SampleViewHolder(View view) {
            super(view);
            contentView = view.findViewById(R.id.item_delete_layout);
            tvTitle = itemView.findViewById(R.id.item_delete_course_tv_title);
            tvTime = itemView.findViewById(R.id.item_delete_course_tv_time);
            tvDay = itemView.findViewById(R.id.item_delete_course_tv_day);
            tvChar = itemView.findViewById(R.id.item_delete_courseـtv_char);
            contentView.setOnClickListener(SampleAdapter.this);
        }
    }

}

