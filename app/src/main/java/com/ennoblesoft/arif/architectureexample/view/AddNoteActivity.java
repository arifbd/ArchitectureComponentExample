package com.ennoblesoft.arif.architectureexample.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.ennoblesoft.arif.architectureexample.R;

import es.dmoral.toasty.Toasty;

public class AddNoteActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.ennoblesoft.arif.architectureexample.view.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.ennoblesoft.arif.architectureexample.view.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.ennoblesoft.arif.architectureexample.view.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.ennoblesoft.arif.architectureexample.view.EXTRA_PRIORITY";

    private EditText etTitle, etDescription;
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etTitle = findViewById(R.id.et_title);
        etDescription = findViewById(R.id.et_description);
        numberPicker = findViewById(R.id.np);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        Intent intent = getIntent();

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            etTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            etDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        } else {
            setTitle("Add Note");
        }

    }

    private void saveNote() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        int priority = numberPicker.getValue();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            Toasty.error(this, "Title or Description missing").show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_DESCRIPTION, description);
        intent.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1)
            intent.putExtra(EXTRA_ID, id);

        setResult(RESULT_OK, intent);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
