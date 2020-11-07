package com.example.sqlite2.inscripcion;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.sqlite2.AddActivity;
import com.example.sqlite2.DatabaseHelper;
import com.example.sqlite2.R;

import java.io.ByteArrayOutputStream;

public class AddActivityIns extends AppCompatActivity {

    EditText carne_input, fecha_input;
    Button add_button;
    Spinner spinner;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ins);

        DatabaseHelper myDB = new DatabaseHelper(AddActivityIns.this);

        carne_input = findViewById(R.id.carne);
        fecha_input = findViewById(R.id.editTextDate);
        spinner = findViewById(R.id.spinner);
        add_button = findViewById(R.id.add_button_ins);

        String[] spinnerLists = myDB.readAllDataArray();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(AddActivityIns.this,android.R.layout.simple_spinner_item, spinnerLists);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item_selected = spinner.getSelectedItem().toString();
                String[] student_id = item_selected.split("-");
                DatabaseHelper myDB = new DatabaseHelper(AddActivityIns.this);

                long result = myDB.addIns(carne_input.getText().toString().trim(),fecha_input.getText().toString().trim(),student_id[0]);

                if(result == -1)
                    //Toast.makeText(context,"Fallo en agregar registro",Toast.LENGTH_SHORT).show();
                    carne_input.setText(carne_input.getText().toString().trim());
                else{
                    carne_input.setText("");
                    fecha_input.setText("");
                    //Toast.makeText(context,"Agregado exitosamente",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}