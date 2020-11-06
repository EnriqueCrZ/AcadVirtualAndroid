package com.example.sqlite2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    EditText title_input, author_input, pages_input;
    Button update_button, delete_button;

    String id, title, author, pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.title_input_new);
        author_input = findViewById(R.id.author_input_new);
        pages_input = findViewById(R.id.pages_inputs_new);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        //data
        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if(ab != null)
            ab.setTitle("Actualizar "+title);

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper myDB = new DatabaseHelper(UpdateActivity.this);

                title = title_input.getText().toString().trim();
                author = author_input.getText().toString().trim();
                pages = pages_input.getText().toString().trim();
                long result = myDB.updateData(id,title,author,pages);

                if (result == -1){
                    //Toast.makeText(UpdateActivity.this,"Fallo en actualizar registro",Toast.LENGTH_SHORT).show();
                    Log.d("Error","Fallo en actualizar");
                }
                else{
                    UpdateActivity.super.finish();
                    //Toast.makeText(UpdateActivity.this,title+" actualizado.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });


    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("author") && getIntent().hasExtra("pages")){
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            pages = getIntent().getStringExtra("pages");

            title_input.setText(title);
            author_input.setText(author);
            pages_input.setText(pages);

        }else{
            Toast.makeText(this,"Registro inexistente",Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar "+title+"?");
        builder.setMessage("Seguro que quieres eliminar "+title+"?");
        builder.setPositiveButton("Simon", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHelper myDB = new DatabaseHelper(UpdateActivity.this);
                title = title_input.getText().toString().trim();

                long result = myDB.deleteData(id,title);

                if(result != -1)
                    UpdateActivity.super.finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}