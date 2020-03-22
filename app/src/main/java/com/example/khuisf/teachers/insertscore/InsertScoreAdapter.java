package com.example.khuisf.teachers.insertscore;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.khuisf.R;
import com.example.khuisf.entitys.Student;
import com.example.khuisf.entitys.Urls;

import java.util.ArrayList;

public class InsertScoreAdapter extends RecyclerView.Adapter<InsertScoreAdapter.CourseViewHolder> {
    LayoutInflater inflater;
    Context context;
    private String characteristic;
    private ArrayList<Student> mstudents;

    public InsertScoreAdapter(Context context, ArrayList<Student> students,String charac) {
        this.context = context;
        mstudents = students;
        // take this var for send to db
        characteristic=charac;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.insertscore_item, parent, false);
        return new CourseViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Student currentItem = mstudents.get(position);
        holder.tvName.setText(currentItem.getName());
        holder.tvCode.setText(currentItem.getCode());


        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("ایا نمره را ثبت میکنید؟");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "بله",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        holder.btnInsert.setVisibility(View.GONE);
                        holder.edtScore.setBackgroundColor(Color.GREEN);
                        holder.edtScore.setEnabled(false);
                        //get score again for function
                        String score=holder.edtScore.getText().toString().trim();
                        float finalVal = Float.parseFloat(score);
                        Toast.makeText(context, "ثبت شد"+finalVal, Toast.LENGTH_SHORT).show();
                       // context.getIntent().getStringExtra("title");


                        sendScoreToDb(score,holder.tvCode.getText().toString(),characteristic);
                    }
                });

        builder1.setNegativeButton(
                "خیر",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        holder.btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworking.initialize(context);
                    String score=holder.edtScore.getText().toString().trim();
                if (checkInput(score)) {
                    // the scoer passed filters
                    //ask question from user are you sure (complate code writed in alert dialog button)
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else {
                    holder.edtScore.setBackgroundColor(Color.RED);
                }
            }
        });


    }




    private void sendScoreToDb(String score, String studentCode, String charac) {
        AndroidNetworking.post(Urls.host+Urls.setScore)
                .addBodyParameter("characteristic",charac)
                .addBodyParameter("code",studentCode)
                .addBodyParameter("score",score)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response+"", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(context, anError+"", Toast.LENGTH_SHORT).show();
                    }
                });
}




    private boolean checkInput(String scoretext) {
        if (!scoretext.equals("")) {

            float finalVal = Float.parseFloat(scoretext);
            if (checkScore(finalVal)) {
                return true;
            } else {
                //score is ore biger form 20 or slmaller form 0
                Toast.makeText(context, "نمره را کتنرل کنید", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        //score is empty or space
        Toast.makeText(context, "نمره را وارد کنید", Toast.LENGTH_SHORT).show();
        return false;
    }


    private boolean checkScore(float score) {
        if (score > 20) {
            return false;
        } else if (score < 0) {
            return false;
        } else return true;
    }

    @Override
    public int getItemCount() {
        return mstudents.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvCode;
        public EditText edtScore;
        public Button btnInsert;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.insertscore_item_tv_name);
            tvCode = itemView.findViewById(R.id.insertscore_item_tv_code);
            edtScore = itemView.findViewById(R.id.insertscore_item_edt_insertscore);
            btnInsert = itemView.findViewById(R.id.insertscore_item_btn_insert);
        }
    }

}
