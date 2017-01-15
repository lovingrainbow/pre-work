package com.example.quietus.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    EditText editText;
    Button btnSave;
    int EditPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        //EditText
        editText = (EditText)findViewById(R.id.etEditItem);
        String sEditItem = getIntent().getStringExtra("EditItem");
        editText.setText(sEditItem);
        EditPosition = getIntent().getIntExtra("EditPosition", 0);
        editText.setSelection(editText.getText().length());
        //Button Save
        btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditItemActivity.this, ToDo.class);
                intent.putExtra("EditItem", editText.getText().toString());
                intent.putExtra("EditPosition", EditPosition);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}
