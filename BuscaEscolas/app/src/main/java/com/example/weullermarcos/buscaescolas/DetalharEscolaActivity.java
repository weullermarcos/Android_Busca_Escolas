package com.example.weullermarcos.buscaescolas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.weullermarcos.buscaescolas.Models.Escola;

public class DetalharEscolaActivity extends AppCompatActivity {

    TextView txtCabecalho, txtRede;

    AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhar_escola);

        txtCabecalho = (TextView) findViewById(R.id.detalhar_escola_txtCabecalho);
        txtRede = (TextView) findViewById(R.id.detalhar_escola_txtRede);

        Bundle bundle = getIntent().getExtras();

        //verificando se existe o parametro com a chave informada
        if(bundle.containsKey("ESCOLA")){

            //recuperando escola
            Escola escola = (Escola) bundle.getSerializable("ESCOLA");

            if(escola != null){

                txtCabecalho.setText(escola.getNome());
                txtRede.setText(escola.getRede());
            }
            else{
                exibeErroERetorna();
            }
        }
        else{

            exibeErroERetorna();
        }
    }

    private void exibeErroERetorna(){

        AlertDialog.Builder builder = new AlertDialog.Builder(DetalharEscolaActivity.this);
        builder.setTitle("Erro");
        builder.setMessage("Erro ao detalhar escola.");

        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                finish();
            }
        });

        alerta = builder.create();
        alerta.show();
    }

}
