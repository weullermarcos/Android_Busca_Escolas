package com.example.weullermarcos.buscaescolas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
    ListView lstEscolas;

    ArrayList<Escola> escolas = new ArrayList<Escola>();
    ArrayAdapter<Escola> adpEscolas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_escolas);

        lstEscolas = (ListView) findViewById(R.id.busca_escolas_lstEscolas);
        txtResposta = (TextView) findViewById(R.id.busca_escolas_txtResposta);
        edtNome = (EditText) findViewById(R.id.busca_escolas_edtNome);
        btnPesquisar = (Button) findViewById(R.id.busca_escolas_btnPesquisar);

        adpEscolas = new ArrayAdapter<Escola>(this, android.R.layout.simple_list_item_1);
        lstEscolas.setAdapter(adpEscolas);

        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //limpa dados anteriores
                adpEscolas.clear();

                //TODO
                //Fazer a busca de acordo com os filtros fornecidos
                String filtro = "nome=" + edtNome.getText().toString();
                fazRequisicao(filtro);

            }
        });

        lstEscolas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Escola escola = (Escola) (lstEscolas.getItemAtPosition(i));
                txtResposta.setText("Escola selecionada: " + escola.getNome());

                Intent intent = new Intent(BuscaEscolasActivity.this, DetalharEscolaActivity.class);

                intent.putExtra("ESCOLA", escola);
//
                startActivity(intent);

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

                //desserializando
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Escola>>(){}.getType();
                escolas = new Gson().fromJson(response, listType);

                //preenchendo adapter
                for (Escola escola : escolas) {

                    adpEscolas.add(escola);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                txtResposta.setText("Deu errado...");

            }
        });

        Log.d("TESTE","ADICIONANDO A FILA");

        //adiciona a string de requisição a fila de execução
        queue.add(stringRequest);

    }
}
