package com.example.khuisf.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khuisf.R;
import com.example.khuisf.entitys.Score;

import java.util.ArrayList;

public class AllNotesAdapter extends RecyclerView.Adapter<AllNotesAdapter.ScoreViewHolder> {

    LayoutInflater inflater;
    Context context;
    private ArrayList<Score> mCourses;

    public AllNotesAdapter(Context context, ArrayList<Score> courses) {
        this.context = context;
        mCourses = courses;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_allnotes, parent, false);
        return new ScoreViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        Score score = mCourses.get(position);
        holder.tvTitle.setText(score.getCourseName());
        holder.tvUnitTeori.setText(score.getUnitTeori());
        holder.tvUnitAmali.setText(score.getUnitAmali());
        holder.tvScore.setText(score.getScore());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent intent=new Intent(context, CourseInfoActivity.class);
                intent.putExtra(MainActivity.CHARAC,currentItem.getCharac());
                intent.putExtra(MainActivity.NAME,currentItem.getTitle());
                intent.putExtra(MainActivity.DAY,currentItem.getDay());
                intent.putExtra(MainActivity.TIME,currentItem.getTime());

                context.startActivity(intent);
                Toast.makeText(context, holder.tvTime.getText(), Toast.LENGTH_SHORT).show();*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public static class ScoreViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvUnitAmali;
        public TextView tvUnitTeori;
        public TextView tvScore;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.item_getscore_tv_title);
            tvUnitTeori = itemView.findViewById(R.id.item_getscore_unit_teori);
            tvUnitAmali = itemView.findViewById(R.id.item_getscore_unit_amali);
            tvScore = itemView.findViewById(R.id.item_getscore_tv_score);
        }
    }

}
