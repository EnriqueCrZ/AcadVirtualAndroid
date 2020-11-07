package com.example.sqlite2.inscripcion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite2.DatabaseHelper;
import com.example.sqlite2.R;
import com.example.sqlite2.UpdateActivity;

import java.util.ArrayList;

public class CustomAdapterInscripcion extends RecyclerView.Adapter<CustomAdapterInscripcion.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList ins_id,ins_carne,ins_date,student_id;

    CustomAdapterInscripcion(Activity activity, Context context, ArrayList ins_id, ArrayList ins_carne, ArrayList ins_date, ArrayList student_id){
        this.activity = activity;
        this.context = context;
        this.ins_id = ins_id;
        this.ins_carne = ins_carne;
        this.ins_date = ins_date;
        this.student_id = student_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fila2, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterInscripcion.MyViewHolder holder, final int position) {
        DatabaseHelper myDB = new DatabaseHelper(context);

        Object id_student = student_id.get(position);
        String student = myDB.studentName(id_student.toString());
        Log.d(student,"data");
        holder.ins_id.setText(String.valueOf(ins_id.get(position)));
        holder.ins_carne.setText(String.valueOf(ins_carne.get(position)));
        holder.ins_date.setText(String.valueOf(ins_date.get(position)));
        holder.student_id.setText(student);
    }

    @Override
    public int getItemCount() {
        return ins_id.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView ins_id, ins_carne, ins_date, student_id;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ins_id = itemView.findViewById(R.id.ins_id);
            ins_carne = itemView.findViewById(R.id.ins_carne);
            ins_date = itemView.findViewById(R.id.dateTextView);
            student_id = itemView.findViewById(R.id.student);
            mainLayout = itemView.findViewById(R.id.mainLayout2);
        }
    }
}
