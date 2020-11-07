package com.example.sqlite2.inscripcion;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite2.AddActivity;
import com.example.sqlite2.CustomAdapter;
import com.example.sqlite2.DatabaseHelper;
import com.example.sqlite2.MainActivity;
import com.example.sqlite2.R;
import com.example.sqlite2.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Inscripcion extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button;

    DatabaseHelper myDB;
    ArrayList<String> ins_id, ins_date, ins_carne, student_id;
    CustomAdapterInscripcion customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insc);

        recyclerView = findViewById(R.id.recyclerView2);
        add_button = findViewById(R.id.add_button_ins);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inscripcion.this, AddActivityIns.class);
                startActivityForResult(intent,1);
            }
        });

        myDB = new DatabaseHelper(Inscripcion.this);
        ins_id = new ArrayList<>();
        ins_carne = new ArrayList<>();
        ins_date = new ArrayList<>();
        student_id = new ArrayList<>();

        displayDataInArrays();

        customAdapter = new CustomAdapterInscripcion(Inscripcion.this,this,ins_id,ins_carne,ins_date,student_id);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Inscripcion.this));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
            recreate();
    }

    void displayDataInArrays(){
        Cursor cursor = myDB.readAllDataIns();

        if(cursor.getCount() == 0)
            Toast.makeText(this,"No hay datos almacenados.",Toast.LENGTH_SHORT).show();
        else
            while(cursor.moveToNext()){

                ins_id.add(cursor.getString(0));
                ins_carne.add(cursor.getString(1));
                ins_date.add(cursor.getString(2));
                student_id.add(cursor.getString(3));
            }
    }
}
