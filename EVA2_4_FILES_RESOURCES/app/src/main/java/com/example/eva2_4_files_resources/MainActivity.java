package com.example.eva2_4_files_resources;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    TextView loremTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loremTxt = findViewById(R.id.loremTxt);
    }

    @Override
    protected void onStart() {
        super.onStart();
        InputStream is = getResources().openRawResource(R.raw.lorem);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String sCadena;
        try{
            while((sCadena = br.readLine()) != null){
                loremTxt.append(sCadena);
                loremTxt.append("\n");
            }
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
