package com.example.weullermarcos.buscaescolas;

import android.*;
import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weullermarcos.buscaescolas.Models.Escola;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EscolasPorLocalizacaoActivity extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationClient;

    ListView lstEscolas;
    AlertDialog alerta;
    ArrayList<Escola> escolas = new ArrayList<Escola>();
    ArrayAdapter<Escola> adpEscolas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolas_por_localizacao);

        lstEscolas = (ListView) findViewById(R.id.escolas_por_localizacao_lstEscolas);

        adpEscolas = new ArrayAdapter<Escola>(this, android.R.layout.simple_list_item_1);
        lstEscolas.setAdapter(adpEscolas);


        try {

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {

                        Log.d("LATITUDE", String.valueOf(location.getLatitude()));
                        Log.d("LONGITUDE", String.valueOf(location.getLongitude()));


                        fazRequisicao(location);
                    }
                }
            });

        }catch (SecurityException e) {

            AlertDialog.Builder builder = new AlertDialog.Builder(EscolasPorLocalizacaoActivity.this);
            builder.setTitle("Erro");
            builder.setMessage("Erro verifique se o seu GPS está ligado ou se você tem as permissões necessárias para usar esse recurso.");
            builder.setNeutralButton("OK", null);

            alerta = builder.create();
            alerta.show();

        }
    }

    private void fazRequisicao(Location location){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://mobile-aceite.tcu.gov.br:80/nossaEscolaRS" +
                    "/rest/escolas/latitude/" + String.valueOf(location.getLatitude()) +
                    "/longitude/" + String.valueOf(location.getLongitude()) +
                    "/raio/50";

        Log.d("URL", url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //desserializando
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Escola>>(){}.getType();
                escolas = new Gson().fromJson(response, listType);

                //se não encontrou nada
                if(escolas.size() == 0){

                    AlertDialog.Builder builder = new AlertDialog.Builder(EscolasPorLocalizacaoActivity.this);
                    builder.setTitle("Aviso");
                    builder.setMessage("Nenhuma escola encontrada para os filtros fornecidos.");
                    builder.setNeutralButton("OK", null);

                    alerta = builder.create();
                    alerta.show();

                    return;
                }

                //preenchendo adapter
                for (Escola escola : escolas) {

                    adpEscolas.add(escola);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EscolasPorLocalizacaoActivity.this);
                builder.setTitle("Erro");
                builder.setMessage("Erro ao buscar escolas, verifique sua conexão com a internet.");
                builder.setNeutralButton("OK", null);

                alerta = builder.create();
                alerta.show();
            }
        });

        Log.d("TESTE","ADICIONANDO A FILA");

        //adiciona a string de requisição a fila de execução
        queue.add(stringRequest);

        //TODO: Adicionar componente de LOAD

    }
}
