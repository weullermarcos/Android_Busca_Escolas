package com.example.weullermarcos.buscaescolas;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weullermarcos.buscaescolas.Models.Escola;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_FINE_LOCATION = 0;

    Button btnEsolasProximas, btnBuscarEscolas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MENSAGEM: ","VAI CHAMAR O LOAD");

        loadPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_FINE_LOCATION);

        btnEsolasProximas = (Button) findViewById(R.id.main_btnEsolasProximas);
        btnBuscarEscolas = (Button) findViewById(R.id.main_btnBuscarEscolas);

        btnEsolasProximas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //chamando intent de forma implicita - Olhar configuração no AndroidManifest.xml
                Intent intent = new Intent("ESCOLAS_POR_LOCALIZACAO");
                intent.addCategory("ESCOLAS_POR_LOCALIZACAO");
                startActivity(intent);
            }
        });

        btnBuscarEscolas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, BuscaEscolasActivity.class);
                startActivity(intent);
            }
        });

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        Log.d("MENSAGEM: ","ENTROU NO ONREQUEST");

        switch (requestCode) {
            case REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // granted
                    Log.d("MENSAGEM: ","ACEITOU");
                }
                else{
                    // no granted
                    Log.d("MENSAGEM: ","NÃO ACEITOU");
                }
                return;
            }
        }
    }

    private void loadPermissions(String perm, int requestCode) {

        Log.d("MENSAGEM: ","ENTROU NO LOAD");

        if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
                ActivityCompat.requestPermissions(this, new String[]{perm},requestCode);
            }
        }
    }

}
