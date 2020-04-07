package com.example.khuisf.messgeainbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khuisf.R;
import com.example.khuisf.entitys.Course;
import com.example.khuisf.entitys.Message;

import java.util.ArrayList;

public class GetMessageAdapter extends RecyclerView.Adapter<GetMessageAdapter.CourseViewHolder> {
    LayoutInflater inflater;
    Context context;
    private ArrayList<Message> mMessage;

    public GetMessageAdapter(Context context, ArrayList<Message> messages) {
        this.context = context;
        mMessage = messages;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.inbox_item, parent, false);
        return new CourseViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Message currentItem = mMessage.get(position);
        holder.tvSender.setText(currentItem.getSender());
        holder.tvText.setText(currentItem.getText());


       /* AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("درس را حذف می کنید؟");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "بله",
                new DialogInterface.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        SharedPreferences preferences = context.getSharedPreferences("prefs", MODE_PRIVATE);
                        deleteCourse(holder,preferences.getString("code",""),holder.tvChar.getText().toString(),context);
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
        });*/
    }

   /* private void deleteCourse(CourseViewHolder holder, String studentCode, String characteristic, Context context) {
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.host + Urls.deleteCourse)
                .addBodyParameter("char", characteristic)
                .addBodyParameter("code", studentCode)
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                Log.d("mures",response);
                if(response.equals("1")){
                    holder.cl.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGray));
                    holder.btnDelete.setVisibility(View.GONE);
                    Toast.makeText(context, "با موفقیت حذف شد", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(context, "درس حذف نشد", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(context, "ارور شبکه", Toast.LENGTH_SHORT).show();
            }
        });

    }
*/
    @Override
    public int getItemCount() {
        return mMessage.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSender,tvText;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSender = itemView.findViewById(R.id.inbox_item_tv_sender);
            tvText = itemView.findViewById(R.id.inbox_item_tv_text);
        }
    }

}
