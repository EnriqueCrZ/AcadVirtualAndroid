package com.example.sqlite2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME = "Academia.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_STUDENT = "student";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_STUDENT_NAME = "student_name";
    public static final String COLUMN_STUDENT_LAST = "student_last_name";
    public static final String COLUMN_STUDENT_PICTURE = "picture";

    public static final String TABLE_INSCRIPCION = "inscripcion";
    public static final String COLUMN_ID2 = "_id";
    public static final String COLUMN_CARNE = "carne";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_STUDENT = "student_id";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_STUDENT
                + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_STUDENT_NAME + " TEXT, "
                + COLUMN_STUDENT_LAST + " TEXT, "
                + COLUMN_STUDENT_PICTURE + " BLOB)");
        db.execSQL("CREATE TABLE " + TABLE_INSCRIPCION + " (" + COLUMN_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + COLUMN_CARNE + " TEXT, "
        + COLUMN_DATE + " DATE, "
        + COLUMN_STUDENT + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSCRIPCION);
        onCreate(db);
    }

    long addBook(String title, String author, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_STUDENT_NAME, title);
        cv.put(COLUMN_STUDENT_LAST, author);
        cv.put(COLUMN_STUDENT_PICTURE, image);

        long result = db.insert(TABLE_STUDENT, null, cv);

        if (result == -1)
            Toast.makeText(context, "Fallo en agregar registro", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, title + " agregado exitosamente", Toast.LENGTH_SHORT).show();

        return result;
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_STUDENT;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null)
            cursor = db.rawQuery(query, null);

        return cursor;
    }

    long updateData(String row_id, String title, String author, byte[] pages) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STUDENT_NAME, title);
        cv.put(COLUMN_STUDENT_LAST, author);
        cv.put(COLUMN_STUDENT_PICTURE, pages);

        long result = db.update(TABLE_STUDENT, cv, " _id=?", new String[]{row_id});

        if (result == -1)
            Toast.makeText(context, "Fallo en actualizar registro", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, String.valueOf(title) + " actualizado.", Toast.LENGTH_SHORT).show();

        return result;
    }

    long deleteData(String row_id, String title) {
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(TABLE_STUDENT, " _id=?", new String[]{row_id});

        if (result == -1)
            Toast.makeText(context, "Fallo en eliminar registro", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, String.valueOf(title) + " eliminado.", Toast.LENGTH_SHORT).show();

        return result;
    }

    void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_STUDENT);
    }
}
