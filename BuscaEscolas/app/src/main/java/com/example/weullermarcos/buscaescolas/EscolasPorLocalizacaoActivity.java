package com.example.weullermarcos.buscaescolas;

import android.*;
import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    Dialog mDialog;
    ArrayList<Escola> escolas = new ArrayList<Escola>();
    ArrayAdapter<Escola> adpEscolas;
    Location minhaLocalizacao;

    EditText edtRaio;
    Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolas_por_localizacao);

        Log.d("MENSAGEM", "ONCREATE");

        lstEscolas = (ListView) findViewById(R.id.escolas_por_localizacao_lstEscolas);

        adpEscolas = new ArrayAdapter<Escola>(this, android.R.layout.simple_list_item_1);
        lstEscolas.setAdapter(adpEscolas);

        edtRaio = (EditText) findViewById(R.id.escolas_por_localizacao_edtRaio);
        btnBuscar = (Button) findViewById(R.id.escolas_por_localizacao_btnBuscar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //limpa dados anteriores
                adpEscolas.clear();

                closeDialog();
                createProgressDialog("Aguarde uns instantes!", "Carregando...", EscolasPorLocalizacaoActivity.this);

                fazRequisicao(minhaLocalizacao, edtRaio.getText().toString());

            }
        });

        lstEscolas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Escola escola = (Escola) (lstEscolas.getItemAtPosition(i));
                Intent intent = new Intent(EscolasPorLocalizacaoActivity.this, DetalharEscolaActivity.class);
                intent.putExtra("ESCOLA", escola);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("MENSAGEM", "ONRESUME");

        capturaLocalizacaoAtual();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("MENSAGEM", "ONPAUSE");

        //limpa o raio
        edtRaio.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("MENSAGEM", "ONDESTROY");

        //limpa dados anteriores
        adpEscolas.clear();
    }

    private void capturaLocalizacaoAtual(){

        try {

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {

                        Log.d("LATITUDE", String.valueOf(location.getLatitude()));
                        Log.d("LONGITUDE", String.valueOf(location.getLongitude()));

                        minhaLocalizacao = location;
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

    private void fazRequisicao(Location location, String raio){

        if(location == null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(EscolasPorLocalizacaoActivity.this);
            builder.setTitle("Erro");
            builder.setMessage("Localização atual não encontrada.");
            builder.setNeutralButton("OK", null);

            alerta = builder.create();
            alerta.show();

            return;
        }


        if(raio.isEmpty() || raio.trim() == "" || raio == null)
            raio = "5";

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://mobile-aceite.tcu.gov.br:80/nossaEscolaRS" +
                    "/rest/escolas/latitude/" + String.valueOf(location.getLatitude()) +
                    "/longitude/" + String.valueOf(location.getLongitude()) +
                    "/raio/" + raio;

        Log.d("URL", url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //fechando caixa de dialogo
                closeDialog();

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

                //fechando caixas de dialogo
                closeDialog();

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

    }

    public void createProgressDialog(String message, String title, Context context) {
        closeDialog();

        mDialog = ProgressDialog.show(context, title, message, false, false);
    }

    public void closeDialog() {
        if(mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }
}
