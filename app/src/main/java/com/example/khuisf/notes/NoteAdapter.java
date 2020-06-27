package com.example.khuisf.notes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khuisf.R;
import com.example.khuisf.database.AppDatabase;
import com.example.khuisf.entitys.note.Note;
import com.example.khuisf.entitys.note.NoteDao;

import java.util.ArrayList;
import java.util.List;

import me.samthompson.bubbleactions.BubbleActions;
import me.samthompson.bubbleactions.Callback;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> implements Filterable {

    private NoteCallback noteCallback;

    private List<Note> notes = new ArrayList<>();
    private List<Note> allNotes = new ArrayList<>();

    private boolean useWitheItem;
    private Context context;
    private NoteDao noteDao;


    public NoteAdapter(NoteCallback noteCallback, boolean useWitheItem, Context context) {
        this.noteCallback = noteCallback;
        this.useWitheItem = useWitheItem;

        this.context = context;
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        noteDao = appDatabase.noteDao();
    }

    public NoteAdapter(NoteCallback noteCallback, boolean useWitheItem, Context context, List<Note> notes) {
        this.noteCallback = noteCallback;
        this.useWitheItem = useWitheItem;

        this.context = context;
        allNotes = notes;
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        noteDao = appDatabase.noteDao();
    }

    public void addNotes(List<Note> notes) {
        this.notes.addAll(notes);
        notifyDataSetChanged();
    }

    public void addNote(Note note) {
        this.notes.add(0, note);
        notifyItemInserted(0);
    }

    public void delteNote(int position) {
        notes.remove(position);
        notifyItemRemoved(position);
    }

    public void updateNote(Note note, int position) {
        notifyItemChanged(position);
        notes.set(position, note);
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (!useWitheItem) {
            return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_all, parent, false));
        } else {
            return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_days, parent, false));

        }


    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bingNote(notes.get(position), holder);
    }

    private void initBubbleAction(View v, int adapterPosition, Note note) {
        //if(!useWitheItem){
        BubbleActions.on(v)
                .addAction("حذف", R.drawable.ic_delete_note, () -> {
                    //   noteCallback.onDeleteClick(note, adapterPosition);
                    Toast.makeText(v.getContext(), "یادداشت حذف شد ", Toast.LENGTH_SHORT).show();
                    noteDao.delete(note);
                    delteNote(adapterPosition);
                    //.delteNote(position);
                })
                .addAction("ویرایش", R.drawable.ic_edit_note, new Callback() {
                    @Override
                    public void doAction() {
                        if (!useWitheItem) {
                            noteCallback.onEditClick(note, adapterPosition);
                            Intent intent = new Intent(v.getContext(), EditNoteActivity.class);
                            intent.putExtra("note", note);
                        } else {
                        }
                        Toast.makeText(context, "برای ویرایش از تب یادداشت ها اقدام کنید", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public Filter getFilter() {
        return noteFilter;
    }

    private Filter noteFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Note> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(allNotes);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Note item : allNotes) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }

            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            notes.clear();
            notes.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvDescription;
        public LinearLayout layout;
        private ImageButton imageButton;
        private TextView tvDay;
        private CardView cardView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.item_note_all_tv_title);
            tvDescription = itemView.findViewById(R.id.item_note_all_tv_description);
            layout = itemView.findViewById(R.id.item_note_all_layout);
            //imageButton = itemView.findViewById(R.id.item_note_all_btn_options);
            cardView = itemView.findViewById(R.id.item_note_all_cardView);
            tvDay = itemView.findViewById(R.id.item_note_all_tv_day);
            //layout.setOnLongClickListener(v -> Toast.makeText(itemView.getContext(), "saalml", Toast.LENGTH_SHORT).show());
        }

        public void bingNote(Note note, NoteViewHolder holder) {
            tvDescription.setText(note.getDescription());
            tvTitle.setText(note.getTitle());
            setDay(note.getDay(), tvDay, itemView);
            holder.layout.setOnLongClickListener(v -> {
                initBubbleAction(v, getAdapterPosition(), note);
                return false;
            });
            setBackColor(note, cardView, itemView);

            layout.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), R.string.for_edit_hold, Toast.LENGTH_SHORT).show();
            });

        }
    }

    private void setDay(int day, TextView tvDay, View itemView) {
        switch (day) {
            case 0:
                tvDay.setText("شنبه");
                tvDay.setTextColor(itemView.getResources().getColor(R.color.red));
                break;
            case 1:
                tvDay.setText("یکشنبه");
                tvDay.setTextColor(itemView.getResources().getColor(R.color.orange));
                break;
            case 2:
                tvDay.setText("دوشنبه");
                tvDay.setTextColor(itemView.getResources().getColor(R.color.yellow));
                break;
            case 3:
                tvDay.setText("سه شنبه");
                tvDay.setTextColor(itemView.getResources().getColor(R.color.green));
                break;
            case 4:
                tvDay.setText("چهارشنبه");
                tvDay.setTextColor(itemView.getResources().getColor(R.color.blue));
                break;
            case 5:
                tvDay.setText("پنجشنبه");
                tvDay.setTextColor(itemView.getResources().getColor(R.color.blued));
                break;
            case 6:
                tvDay.setText("جمعه");
                tvDay.setTextColor(itemView.getResources().getColor(R.color.pink));
                break;
        }

    }

    private void setBackColor(Note note, CardView cardView, View itemView) {
        switch (note.getDay()) {
            case 0:
                cardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.red));
                break;
            case 1:
                cardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.orange));

                break;
            case 2:
                cardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.yellow));

                break;
            case 3:
                cardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.green));

                break;
            case 4:
                cardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.blue));

                break;
            case 5:
                cardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.blued));

                break;
            case 6:
                cardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.pink));

                break;
        }
    }

    public interface NoteCallback {
        void onDeleteClick(Note note, int position);

        void onEditClick(Note note, int position);

        void onOpenNote(Note note, int postion);
    }
}
