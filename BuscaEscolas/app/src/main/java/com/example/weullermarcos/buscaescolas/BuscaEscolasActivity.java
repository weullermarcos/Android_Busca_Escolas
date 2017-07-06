package com.example.weullermarcos.buscaescolas;

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

public class BuscaEscolasActivity extends AppCompatActivity {

    Button btnPesquisar;
    EditText edtNome;
    TextView txtResposta;
    ArrayList<Escola> escolas = new ArrayList<Escola>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_escolas);

        txtResposta = (TextView) findViewById(R.id.busca_escolas_txtResposta);
        edtNome = (EditText) findViewById(R.id.busca_escolas_edtNome);
        btnPesquisar = (Button) findViewById(R.id.busca_escolas_btnPesquisar);

        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO
                //Fazer a busca de acordo com os filtros fornecidos
                String filtro = "nome=" + edtNome.getText().toString();
                fazRequisicao(filtro);

            }
        });

    }

    private void fazRequisicao(String filtro){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://mobile-aceite.tcu.gov.br:80/nossaEscolaRS/rest/escolas?"+filtro;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                txtResposta.setText("Deu Certo");
                Log.d("TESTE","DEU CERTO");

                Gson gson = new Gson();

                Type listType = new TypeToken<ArrayList<Escola>>(){}.getType();

                escolas = new Gson().fromJson(response, listType);

                Log.d("TESTE", "PASSOU DO DESSERIALIZE");

                for (Escola escola : escolas) {

                    Log.d("TESTE", escola.getNome());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                txtResposta.setText("Deu errado...");
                Log.d("TESTE","DEU ERRADO");

            }
        });

        Log.d("TESTE","ADICIONANDO A FILA");

        //adiciona a string de requisição a fila de execução
        queue.add(stringRequest);


    }
}
