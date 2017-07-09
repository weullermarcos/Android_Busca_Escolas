package com.example.weullermarcos.buscaescolas;

import android.content.Intent;
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

    Button btnEsolasProximas, btnBuscarEscolas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
