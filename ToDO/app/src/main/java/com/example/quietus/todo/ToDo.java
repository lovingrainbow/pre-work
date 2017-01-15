package com.example.quietus.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.example.quietus.todo.R.layout.activity_edit_item;
import static com.example.quietus.todo.R.layout.activity_to_do;

public class ToDo extends AppCompatActivity {
    ArrayList<String> ToDoItems;
    ArrayAdapter<String> ToDoAdapter;
    ListView lvToDoItems;
    EditText etAddItem;
    Button btnAddItem;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        //listview init

        lvToDoItems = (ListView)findViewById(R.id.lvItems);
        populateArrayItems();
        //listview
        lvToDoItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ToDoItems.remove(position);
                ToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        //listview edit
        lvToDoItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("EditPosition", position);
                intent.putExtra("EditItem", ToDoItems.get(position));
                intent.setClass(ToDo.this, EditItemActivity.class);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });


        //edittext init
        etAddItem = (EditText)findViewById(R.id.etAddItem);
        //button init
        btnAddItem = (Button)findViewById(R.id.btnAddItem);
        //button event
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDoItems.add(etAddItem.getText().toString());
                etAddItem.setText("");
                writeItems();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            ToDoItems.set(data.getExtras().getInt("EditPosition"), data.getExtras().getString("EditItem"));
            ToDoAdapter.notifyDataSetChanged();
            writeItems();
        }
        //super.onActivityResult(requestCode, resultCode, data);
    }


    private  void populateArrayItems(){
        readItems();
        //ToDoItems = new ArrayList<String>();
        ToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ToDoItems);
        lvToDoItems.setAdapter(ToDoAdapter);
    }

    private  void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            ToDoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e){
            ToDoItems = new ArrayList<String>();
        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(todoFile, ToDoItems);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
