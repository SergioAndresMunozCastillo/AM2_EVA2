package com.example.eva2_8_sqlite3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import java.io.File;

public class MainActivity extends AppCompatActivity {
    EditText edtTxtNombre, edtTxtEdad, edtTxtId;
    Switch swtchInfectado;
    Button btnConsult, btnActualizar;
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
        edtTxtId = findViewById(R.id.edtTxtId);
        swtchInfectado = findViewById(R.id.switchInfectado);
        btnConsult = findViewById(R.id.btnVer);
        btnActualizar = findViewById(R.id.btnIrActualizar);
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
        //COMIENZA A REALIZARSE LA TRANSACCION DEL FRAGMENT MANAGER
        //TAMBIÉN COLOCAMOS UNA INSTANCIA DEL FRAGMENTO CON LA LISTA
        //Y EL ONCLICKLISTENER PARA EL BOTON


        //ONCLICKLISTENER
        btnConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CON EL CURSOR SE TOMA EL RESULTADO DEL QUERY
                Cursor res = myDb.tomarDatos();
                //REVISA SI HAY DATOS ENCONTRADOS EN LA CONSULTA
                if(res.getCount() == 0){
                    // mostrarMensaje("Error", "Nada encontrado");
                    return;
                }
        /*SE ALMACENA CADA FILA EN UN BUFFER TIPO STRING Y LUEGO SE ACUMULA CADA
        FILA EN UNA POSICION DE UN ARREGLO STRING QUE ES ENVIADO COMO UN BUNDLE PARA EL FRAG
         */
                StringBuffer buffer = new StringBuffer();
                String[] cumuloDatos = new String[10];
                int cont = 0;
                while(res.moveToNext()){
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("NOMBRE: " + res.getString(1) + "\n");
                    buffer.append("EDAD: " + res.getString(2) + "\n");
                    buffer.append("INFECTADO: " + res.getString(3) + "\n\n");
                    cumuloDatos[cont] = buffer.toString();
                    cont ++;
                }
                Bundle b1 = new Bundle();
                b1.putStringArray("querydatos", cumuloDatos);
                b1.putInt("contador", cont);
                cont = 0;
                System.out.println(cumuloDatos[1]);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ConsultFragment cf = new ConsultFragment();
                cf.setArguments(b1);
                ft.replace(R.id.container,cf).addToBackStack(null).commit();
            }
        });

    }

    public void actualizarDatos(View v){
        boolean isUpdate = myDb.updateData(edtTxtId.getText().toString(), edtTxtNombre.getText().toString(), edtTxtEdad.getText().toString(), sInfectado);
        if(isUpdate){
            Toast.makeText(MainActivity.this, "Datos actualizados", Toast.LENGTH_SHORT);
        }else{
            Toast.makeText(MainActivity.this, "Datos actualizados", Toast.LENGTH_SHORT);
        }
    }
    public void agregarDatos(View v){
        boolean insertado = myDb.insertData(edtTxtNombre.getText().toString(), edtTxtEdad.getText().toString(), sInfectado);
        if(insertado == true){
            Toast.makeText(MainActivity.this, "Se agregaron los datos", Toast.LENGTH_LONG);
        }else{
            Toast.makeText(MainActivity.this, "No se agregaron los datos", Toast.LENGTH_LONG);
        }
    }

    public void borrarDatos(View v){
        Integer deletedRows = myDb.deleteData(edtTxtId.getText().toString());
        if(deletedRows > 0){
            Toast.makeText(MainActivity.this, "Datos eliminados", Toast.LENGTH_SHORT);
        }else{
            Toast.makeText(MainActivity.this, "Datos no eliminados", Toast.LENGTH_SHORT);
        }
    }
}