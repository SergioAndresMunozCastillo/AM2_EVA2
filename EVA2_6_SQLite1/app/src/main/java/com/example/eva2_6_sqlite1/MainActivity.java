package com.example.eva2_6_sqlite1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    EditText edtTxtNombre, edtTxtEdad;
    Switch  swtchInfectado;
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
        String myDbPath = direccionArch + "/" + "alumnosinfectados";
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

    public void agregarDatos1(View v){
        sNombre = edtTxtNombre.getText().toString();
        iEdad = Integer.parseInt(edtTxtEdad.getText().toString());
        db.execSQL("insert into alumnos_infectados (name, edad, infected) values(" +  "'" + sNombre + "'" + ", " + iEdad + ", " + "'" + sInfectado + "'" + ");");
    }

    public void mostrarMensaje(String titulo, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.show();
    }

    public void agregarDatos2(View v){
        boolean insertado = myDb.insertData(edtTxtNombre.getText().toString(), edtTxtEdad.getText().toString(), sInfectado);
        if(insertado == true){
            Toast.makeText(MainActivity.this, "Se agregaron los datos", Toast.LENGTH_LONG);
        }else{
            Toast.makeText(MainActivity.this, "No se agregaron los datos", Toast.LENGTH_LONG);
        }
    }

    public void mostrarTodo(View v){
        Cursor res = myDb.tomarDatos();
        if(res.getCount() == 0){
            mostrarMensaje("Error", "Nada encontrado");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            buffer.append("ID: " + res.getString(0) + "\n");
            buffer.append("NOMBRE: " + res.getString(1) + "\n");
            buffer.append("EDAD: " + res.getString(2) + "\n");
            buffer.append("INFECTADO: " + res.getString(3) + "\n\n");
        }
        mostrarMensaje("Datos", buffer.toString());
    }
}