package com.example.weullermarcos.buscaescolas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weullermarcos.buscaescolas.Models.Avaliacao;
import com.example.weullermarcos.buscaescolas.Models.Escola;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoActivity extends AppCompatActivity {

    Escola escola;
    AlertDialog alerta;
    ArrayAdapter<String> adpAvaliacoes;
    ArrayList<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();

    TextView txtCabecalho;
    ListView lstAvaliacoes;

    Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao);

        txtCabecalho = (TextView) findViewById(R.id.avaliacao_txtCabecalho);
        lstAvaliacoes = (ListView) findViewById(R.id.avaliacao_lstAvaliacoes);

        adpAvaliacoes = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        lstAvaliacoes.setAdapter(adpAvaliacoes);

        Bundle bundle = getIntent().getExtras();

        //verificando se existe o parametro com a chave informada
        if(bundle.containsKey("ESCOLA")) {

            //recuperando escola
            escola = (Escola) bundle.getSerializable("ESCOLA");

            if (escola != null) {
                txtCabecalho.setText(escola.getNome());

                closeDialog();
                createProgressDialog("Aguarde uns instantes!", "Carregando...", AvaliacaoActivity.this);


                fazRequisicao(String.valueOf(escola.getCodEscola()));
            }
        }

    }

    private void fazRequisicao(String filtro){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://mobile-aceite.tcu.gov.br:80/nossaEscolaRS/rest/escolas/" + filtro + "/avaliacoes";
        //

        Log.d("URL", url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                closeDialog();

                //desserializando
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Avaliacao>>(){}.getType();
                avaliacoes = new Gson().fromJson(response, listType);

                //se não encontrou nada
                if(avaliacoes.size() == 0){

                    AlertDialog.Builder builder = new AlertDialog.Builder(AvaliacaoActivity.this);
                    builder.setTitle("Aviso");
                    builder.setMessage("Nenhuma avaliação encontrada para a escola selecionada.");
                    builder.setNeutralButton("OK", null);

                    alerta = builder.create();
                    alerta.show();

                    return;
                }

                //preenchendo adapter
                for (Avaliacao avaliacao : avaliacoes) {

                    String textoAvaliacao = "";

                    textoAvaliacao = avaliacao.getTipoAvaliacao().getNome() +
                            " (" + avaliacao.getAno() + "): " + String.valueOf(avaliacao.getValor());

                    adpAvaliacoes.add(textoAvaliacao);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                closeDialog();

                AlertDialog.Builder builder = new AlertDialog.Builder(AvaliacaoActivity.this);
                builder.setTitle("Erro");
                builder.setMessage("Erro ao buscar avaliações, verifique sua conexão com a internet.");
                builder.setNeutralButton("OK", null);

                alerta = builder.create();
                alerta.show();
            }
        });


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
