package com.example.khuisf.notes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.khuisf.R;
import com.example.khuisf.entitys.note.Note;
import com.example.khuisf.entitys.test.Test;
import com.example.khuisf.tools.TimePickerFragment;

public class EditNoteDialog extends DialogFragment {
    private OnTestEdit onNoteEdit;
    private TextView tvShowTime;
    private Note note;

    private ExampleDialogListener listener;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        /*if (context instanceof OnTestEdit) {
            onNoteEdit = (OnTestEdit) context;
        }*/
        try {
            listener= (ExampleDialogListener) context;
        } catch (ClassCastException e) {
        //    throw  new ClassCastException(context.toString()+"must implement edialoglistener");
        
        }
    }
/*

    public interface OnNoteEdit {
        void onEdit(Note note);
    }

*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            note = getArguments().getParcelable("note");
    }


    public static EditNoteDialog newInstance(Note note) {
        Bundle args = new Bundle();
        args.putParcelable("note", note);
        EditNoteDialog fragment = new EditNoteDialog();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


        //View view = LayoutInflater.from(getContext()).inflate(R.layout.edit_note_dialog, null, false);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_note_dialog, null, false);
        builder.setView(view).setTitle("test");

        Button btnSetTime = view.findViewById(R.id.edit_note_dialog_btn_open_timepicker);
        Button btnSave = view.findViewById(R.id.edit_note_dialog_btn_save);
        final EditText edtTitle = view.findViewById(R.id.edit_note_dialog_edt_title);
        final EditText edtDescription = view.findViewById(R.id.edit_note_dialog_edt_description);
        tvShowTime = view.findViewById(R.id.edit_note_dialog_tv_showtime);


        if (note != null) {
            edtTitle.setText(note.getTitle());
            edtDescription.setText(note.getDescription());
        }


        btnSetTime.setOnClickListener(v -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getChildFragmentManager(), "time picker");
        });

        btnSave.setOnClickListener(v -> {
            if (edtTitle.length() > 0 && edtDescription.length() > 0) {
               /* Note note = new Note();
                note.setTitle(edtTitle.getText().toString());
                note.setDescription(edtDescription.getText().toString());
                note.setDay(1);
                note.setTime("test");
                */
               String username=edtTitle.getText().toString();
               String password=edtDescription.getText().toString();
                listener.applyText(username,password);

                // onNoteEdit.onEdit(note);
            }
            dismiss();
        });
        return builder.create();
    }


    public interface OnTestEdit {
        void onEdit(Test test);
    }


    public interface ExampleDialogListener {
        void applyText(String test1, String test2);
    }
/*
    public static EditNoteDialog newInstance(Note note) {
        Bundle args = new Bundle();
        args.putParcelable("note", note);
        EditNoteDialog fragment = new EditNoteDialog();
        fragment.setArguments(args);
        return fragment;
    }*/
/*
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        tvShowTime.setText("ساعت:" + hourOfDay + ":" + minute);
    }*/
}
