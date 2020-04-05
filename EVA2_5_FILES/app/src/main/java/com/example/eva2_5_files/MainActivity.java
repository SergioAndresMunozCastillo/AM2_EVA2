package com.example.eva2_5_files;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    EditText edtTets;
    TextView txtTexto;
    final String ARCHIVO = "sampletets.txt";
    String sRuta_sd_card = "";
    final int PERMISO_ESCRITURA = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtTets = findViewById(R.id.edtTets);
        txtTexto = findViewById(R.id.txtTexto);
     if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISO_ESCRITURA);
     }
        sRuta_sd_card = Environment.getExternalStorageDirectory().getAbsolutePath();
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }

    public void onRead(View v){
        try{
            //FileInputStream fis = new FileInputStream(sRuta_sd_card + "/" + ARCHIVO);
            //InputStream is = openFileInput(ARCHIVO);
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), ARCHIVO);
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String sCadena;
            while((sCadena = br.readLine()) != null){
                txtTexto.append(sCadena);
                txtTexto.append("\n");
            }
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void onWrite(View v){

        try{

            //OutputStream out = openFileOutput(ARCHIVO, 0);
            //FileOutputStream fos = new FileOutputStream(sRuta_sd_card + "/" + ARCHIVO);
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), ARCHIVO);
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            String sCadena;
            sCadena = edtTets.getText().toString();
            byte[] byteTets = sCadena.getBytes();
            fos.write(byteTets);
            fos.write('\n');
            bw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}