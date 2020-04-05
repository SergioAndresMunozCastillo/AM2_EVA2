package com.example.eva2_7_sqlite2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Alumnos.db";
    public static final String TABLE_NAME = "alumnos";
    public static final String COL_1 =  "ID";
    public static final String COL_2 =  "NOMBRE";
    public static final String COL_3 =  "EDAD";
    public static final String COL_4 =  "INFECTADO";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS alumnos (" +
                "ID integer PRIMARY KEY autoincrement, " +
                "NOMBRE text, " +
                "EDAD text," +
                "INFECTADO text DEFAULT 'NO');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor tomarDatos(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public boolean insertData(String nombre, String edad, String infectado){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, nombre);
        cv.put(COL_3, edad);
        cv.put(COL_4, infectado);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateData(String id, String  nombre, String edad, String infectado){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1,id);
        cv.put(COL_2,nombre);
        cv.put(COL_3,edad);
        cv.put(COL_4,infectado);
        db.update(TABLE_NAME, cv, "ID = ?", new String[]{ id });
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}