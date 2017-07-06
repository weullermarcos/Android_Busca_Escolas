package com.example.weullermarcos.buscaescolas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.weullermarcos.buscaescolas.Models.Escola;

public class DetalharEscolaActivity extends AppCompatActivity {

    EditText edtNomeEscola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhar_escola);

        edtNomeEscola = (EditText) findViewById(R.id.detalhar_escola_edtNomeEscola);

        Bundle bundle = getIntent().getExtras();

        //verificando se existe o parametro com a chave informada
        if(bundle.containsKey("ESCOLA")){

            //recuperando escola
            Escola escola = (Escola) bundle.getSerializable("ESCOLA");

            if(escola != null){

                edtNomeEscola.setText(escola.getNome());
            }
        }
    }
}
