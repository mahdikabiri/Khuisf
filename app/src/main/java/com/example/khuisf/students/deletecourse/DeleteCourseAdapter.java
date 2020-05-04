package com.example.khuisf.students.deletecourse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.khuisf.R;
import com.example.khuisf.entitys.Course;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class DeleteCourseAdapter extends RecyclerView.Adapter<DeleteCourseAdapter.CourseViewHolder> {
    LayoutInflater inflater;
    Context context;
    private ArrayList<Course> mCourses;

    public DeleteCourseAdapter(Context context, ArrayList<Course> courses) {
        this.context = context;
        mCourses = courses;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.getcours_delete_item, parent, false);
        return new CourseViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course currentItem = mCourses.get(position);
        holder.tvTitle.setText(currentItem.getTitle());
        holder.tvDay.setText(currentItem.getDay());
        holder.tvTime.setText(currentItem.getTime());
        holder.tvChar.setText(currentItem.getCharac());


        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("درس را حذف می کنید؟");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "بله",
                new DialogInterface.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        SharedPreferences preferences = context.getSharedPreferences("prefs", MODE_PRIVATE);
                        deleteCourse(holder, preferences.getString("code", ""), holder.tvChar.getText().toString(), context);
                    }
                });

        builder1.setNegativeButton(
                "خیر",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder1.create();
                builder1.show();
            }
        });
    }

    private void deleteCourse(CourseViewHolder holder, String studentCode, String characteristic, Context context) {
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(context.getString(R.string.host) + context.getString(R.string.deleteCourse))
                .addBodyParameter("char", characteristic)
                .addBodyParameter("code", studentCode)
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                Log.d("mures", response);
                if (response.equals("1")) {
                    holder.cl.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGray));
                    holder.btnDelete.setVisibility(View.GONE);
                    Toast.makeText(context, "با موفقیت حذف شد", Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvDay;
        public TextView tvTime;
        public TextView tvChar;
        public Button btnDelete;
        ConstraintLayout cl;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.item_delete_course_tv_title);
            tvTime = itemView.findViewById(R.id.item_delete_course_tv_time);
            tvDay = itemView.findViewById(R.id.item_delete_course_tv_day);
            tvChar = itemView.findViewById(R.id.item_delete_courseـtv_char);
            btnDelete = itemView.findViewById(R.id.item_delete_course_btn_del);
            cl = itemView.findViewById(R.id.item_delete_layout);
        }
    }

}
