package com.example.eva2_9_sqlite4;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Environment;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    EditText edtTxtNombre, edtTxtEdad;
    Switch swtchInfectado;
    String sNombre, sInfectado;
    int iEdad;
    SQLiteDatabase db;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Esta linea es para hacer una instancia del databasehelper, lo borraremos cuando hagamos la segunda app para dejar la otra
        myDb = new DatabaseHelper(this);
        //Lo que sigue es parte dle codigo original de la app 1
        edtTxtNombre = findViewById(R.id.edtTxtNombre);
        edtTxtEdad = findViewById(R.id.edtTxtEdad);
        swtchInfectado = findViewById(R.id.switchInfectado);
        File direccionArch = getApplication().getFilesDir();
        String myDbPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        Toast.makeText(getApplicationContext(), myDbPath, Toast.LENGTH_LONG);
        swtchInfectado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    // CODE SWITCH: AQUI SE IDENTIFICA SI EL SWITCH ESTA ACTIVO O NO
                    sInfectado = "SI";
                }else{
                    sInfectado = "NO";
                }
            }
        });
        try {
            SQLiteDatabase db = this.openOrCreateDatabase("alumnosInfectados", MODE_PRIVATE, null);
            //Creamos la tabla si aun no existe
            db.execSQL("create table IF NOT EXISTS alumnos_infectados (" +
                    "id integer PRIMARY KEY autoincrement, " +
                    "name text, " +
                    "edad int," +
                    "infected text DEFAULT 'NO');");
        }catch (SQLiteException e){
            e.printStackTrace();
        }
    }
}
