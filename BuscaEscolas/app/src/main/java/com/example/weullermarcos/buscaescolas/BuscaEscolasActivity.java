package com.example.weullermarcos.buscaescolas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Spinner;
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
    EditText edtNome, edtMunicipio;
    ListView lstEscolas;
    Spinner spnUF;
    Spinner spnRede;

    ArrayList<Escola> escolas = new ArrayList<Escola>();
    ArrayAdapter<Escola> adpEscolas;
    ArrayAdapter<String> adpUF;
    ArrayAdapter<String> adpRede;
    AlertDialog alerta;
    Dialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_escolas);

        lstEscolas = (ListView) findViewById(R.id.busca_escolas_lstEscolas);
        edtNome = (EditText) findViewById(R.id.busca_escolas_edtNome);
        edtMunicipio = (EditText) findViewById(R.id.busca_escolas_edtMunicipio);
        spnUF = (Spinner) findViewById(R.id.busca_escolas_spnUF);
        spnRede = (Spinner) findViewById(R.id.busca_escolas_spnRede);
        btnPesquisar = (Button) findViewById(R.id.busca_escolas_btnPesquisar);

        //configurando adapter de escolas
        adpEscolas = new ArrayAdapter<Escola>(this, android.R.layout.simple_list_item_1);
        lstEscolas.setAdapter(adpEscolas);

        //configurando adapter de Rede
        adpRede = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adpRede.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRede.setAdapter(adpRede);

        //configurando adapter de UF
        adpUF = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adpUF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnUF.setAdapter(adpUF);

        populaAdapters();

        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //limpa dados anteriores
                adpEscolas.clear();

                closeDialog();
                createProgressDialog("Aguarde uns instantes!", "Carregando...", BuscaEscolasActivity.this);

                String filtro = criarFiltro();
                fazRequisicao(filtro);

            }
        });

        lstEscolas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Escola escola = (Escola) (lstEscolas.getItemAtPosition(i));

                Intent intent = new Intent(BuscaEscolasActivity.this, DetalharEscolaActivity.class);

                intent.putExtra("ESCOLA", escola);
//
                startActivity(intent);

            }
        });

    }

    private void fazRequisicao(String filtro){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://mobile-aceite.tcu.gov.br:80/nossaEscolaRS/rest/escolas"+filtro;

        Log.d("URL", url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //fechando caixas de dialogo
                closeDialog();

                //desserializando
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Escola>>(){}.getType();
                escolas = new Gson().fromJson(response, listType);

                //se não encontrou nada
                if(escolas.size() == 0){

                    AlertDialog.Builder builder = new AlertDialog.Builder(BuscaEscolasActivity.this);
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

                AlertDialog.Builder builder = new AlertDialog.Builder(BuscaEscolasActivity.this);
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

    private String criarFiltro(){

        String filtro = "?";

        filtro += "nome=" + edtNome.getText().toString().replace(" ", "%20").trim();
        filtro += "&rede=" + spnRede.getSelectedItem().toString();
        filtro += "&municipio=" + edtMunicipio.getText().toString().replace(" ", "%20").trim();
        filtro += "&uf=" + spnUF.getSelectedItem().toString();

        Log.d("FILTRO", filtro);

        return filtro;
    }

    public void createProgressDialog(String message, String title, Context context) {
        closeDialog();

        mDialog = ProgressDialog.show(context, title, message, false, false);
    }

    public void closeDialog() {
        if(mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }

    private void populaAdapters(){

        //popula spinner de rede
        adpRede.add("Pública");
        adpRede.add("Privada");

        //popula spinner de UF
        adpUF.add("AC");
        adpUF.add("AL");
        adpUF.add("AM");
        adpUF.add("AP");
        adpUF.add("BA");
        adpUF.add("CE");
        adpUF.add("DF");
        adpUF.add("ES");
        adpUF.add("GO");
        adpUF.add("MA");
        adpUF.add("MG");
        adpUF.add("MS");
        adpUF.add("MT");
        adpUF.add("PA");
        adpUF.add("PB");
        adpUF.add("PE");
        adpUF.add("PI");
        adpUF.add("PR");
        adpUF.add("RJ");
        adpUF.add("RN");
        adpUF.add("RO");
        adpUF.add("RR");
        adpUF.add("RS");
        adpUF.add("SC");
        adpUF.add("SE");
        adpUF.add("SP");
        adpUF.add("TO");

    }
}
