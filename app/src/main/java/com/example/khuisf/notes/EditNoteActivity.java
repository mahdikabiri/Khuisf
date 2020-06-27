package com.example.khuisf.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.khuisf.R;
import com.example.khuisf.entitys.note.Note;
import com.example.khuisf.tools.ButtonDesign;

import net.igenius.customcheckbox.CustomCheckBox;

public class EditNoteActivity extends AppCompatActivity {
    private static final String[] paths = {"روزی انتخاب نشده", "شنبه", "یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنجشنبه", "جمعه"};
    EditText edtTitle;
    EditText edtDescription;
    Button btnSave;
    Intent in = getIntent();
    Note mynote;
    LinearLayout constraintLayout;
    int position = -1;
    //this is for save or edit mode
    //true=save ;false=edit mode
    boolean flagSaveMode;
    Spinner spinner;
    CustomCheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        init();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditNoteActivity.this,
                android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        constraintLayout.setBackgroundColor(getResources().getColor(R.color.colorWiteg));
                        spinner.setBackgroundColor(getResources().getColor(R.color.fff));

                        break;
                    case 1:
                        constraintLayout.setBackgroundColor(getResources().getColor(R.color.red_light));
                        spinner.setBackground(getDrawable(R.drawable.for_spinner_select_days_satorday));
                        break;
                    case 2:
                        spinner.setBackground(getDrawable(R.drawable.for_spinner_select_days_sunday));
                        constraintLayout.setBackgroundColor(getResources().getColor(R.color.orange_light));
                        break;
                    case 3:
                        spinner.setBackground(getDrawable(R.drawable.for_spinner_select_days_monday));
                        constraintLayout.setBackgroundColor(getResources().getColor(R.color.yellow_light));
                        break;
                    case 4:
                        spinner.setBackground(getDrawable(R.drawable.for_spinner_select_days_tuesday));
                        constraintLayout.setBackgroundColor(getResources().getColor(R.color.green_light));
                        break;
                    case 5:
                        spinner.setBackground(getDrawable(R.drawable.for_spinner_select_days_wednesday));
                        constraintLayout.setBackgroundColor(getResources().getColor(R.color.blue_light));
                        break;
                    case 6:
                        spinner.setBackground(getDrawable(R.drawable.for_spinner_select_days_thursday));
                        constraintLayout.setBackgroundColor(getResources().getColor(R.color.blued_light));
                        break;
                    case 7:
                        spinner.setBackground(getDrawable(R.drawable.for_spinner_select_days_friday));
                        constraintLayout.setBackgroundColor(getResources().getColor(R.color.pink_light));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        LinearLayout linearLayout = findViewById(R.id.activity_edit_layout_alarm);

        checkBox = findViewById(R.id.activity_edit_note_checkbox);
        checkBox.setOnCheckedChangeListener((checkBox1, isChecked) -> {
            if (isChecked) {
                linearLayout.setVisibility(View.VISIBLE);
            } else {
                linearLayout.setVisibility(View.GONE);
            }
        });


        Intent in = getIntent();
        if (getIntent().hasExtra("note")) {
            flagSaveMode = false;
            setTitle("ویرایش یادداشت");
            mynote = in.getParcelableExtra("note");
            edtDescription.setText(mynote.getDescription());
            edtTitle.setText(mynote.getTitle());
            selectDay(mynote.getDay());
            //edit mode (my intent has value)
        } else {
            flagSaveMode = true;
            setTitle("افزودن یادداشت");
            //save mode (my intent has not value)
        }


        btnSave.setOnClickListener(v -> {
            Intent intent = new Intent();

            if (!edtTitle.getText().toString().trim().equals("") || !edtDescription.getText().toString().trim().equals("") || 0 != spinner.getSelectedItemPosition()) {
                if (spinner.getSelectedItemPosition() != 0) {
                    Note note = new Note();
                    note.setTitle(edtTitle.getText().toString());
                    note.setDescription(edtDescription.getText().toString());
                    note.setDay(getIntDay(spinner.getSelectedItemPosition()));

                    if (!flagSaveMode) note.setId(mynote.getId());
                    intent.putExtra("note", note);
                    setResult(-1, intent);
                    finish();
                } else Toast.makeText(this, "روز را انتخاب کنید", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "ورودی هارا کنترل کنید!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void selectDay(int day) {
        switch (day) {
            case 0:
                spinner.setSelection(1);
                break;
            case 1:
                spinner.setSelection(2);
                break;
            case 2:
                spinner.setSelection(3);
                break;
            case 3:
                spinner.setSelection(4);
                break;
            case 4:
                spinner.setSelection(5);
                break;
            case 5:
                spinner.setSelection(6);
                break;
            case 6:
                spinner.setSelection(7);
                break;
        }

    }

    private int getIntDay(int selectedItemPosition) {
        int dayCode;
        switch (selectedItemPosition) {
            case 1:
                dayCode = 0;
                break;
            case 2:
                dayCode = 1;
                break;
            case 3:
                dayCode = 2;
                break;
            case 4:
                dayCode = 3;
                break;
            case 5:
                dayCode = 4;
                break;
            case 6:
                dayCode = 5;
                break;
            case 7:
                dayCode = 6;
                break;
            default:
                dayCode = 10;
                break;
        }
        return dayCode;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_student, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void init() {
        edtTitle = findViewById(R.id.activity_edit_note_edt_title);
        edtDescription = findViewById(R.id.activity_edit_note_edt_description);
        btnSave = findViewById(R.id.activity_edit_note_btn_save);
        spinner = findViewById(R.id.spinner);
        constraintLayout = findViewById(R.id.activity_all_notes);
        ButtonDesign.setDesign(btnSave, this);
    }
/*
    public static EditNoteActivity newInstance(Note note) {
        
        Bundle args = new Bundle();
        
        EditNoteActivity fragment = new EditNoteActivity();
        fragment.setArguments(args);
        return fragment;
    }*/

}