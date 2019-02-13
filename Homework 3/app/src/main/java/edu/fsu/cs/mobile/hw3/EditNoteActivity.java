package edu.fsu.cs.mobile.hw3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.Date;

public class EditNoteActivity extends AppCompatActivity {
    public static final String ACTION_NEW_NOTE = "new";
    public static final String ACTION_EDIT_NOTE = "edit";

    private EditText title;
    private EditText note;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        title = findViewById(R.id.editText_title);
        note = findViewById(R.id.editText_note);
        submit = findViewById(R.id.button_submit);

        // if note is passed in, populate title & note body
        Intent intent = getIntent();
        if(intent.getAction().equals(ACTION_EDIT_NOTE) && intent.hasExtra("note")) {
            MyNote thisNote = intent.getParcelableExtra("note");
            title.setText(thisNote.getTitle());
            note.setText(thisNote.getNote());
        }

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean error = false;
                if(title.getText().equals("") || note.getText().equals("")) {
                    error = true;
                    Toast.makeText(EditNoteActivity.this, "Error: empty fields",
                            Toast.LENGTH_SHORT).show();
                }

                if(!error) {
                    Date date = new Date();
                    Timestamp timestamp = new Timestamp(date.getTime());
                    MyNote thisNote = new MyNote(title.getText().toString(), note.getText().toString(), timestamp.toString());

                    // send thisNote back to where this activity was called from
                    Intent intent = new Intent();
                    intent.putExtra("note", thisNote);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
