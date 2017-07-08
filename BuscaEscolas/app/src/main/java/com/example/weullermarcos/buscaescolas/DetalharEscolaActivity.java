package com.example.weullermarcos.buscaescolas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.weullermarcos.buscaescolas.Models.Escola;

public class DetalharEscolaActivity extends AppCompatActivity {

    TextView txtCabecalho, txtRede, txtEmail, txtEndereco;
    TextView txtEsferaAdministrativa, txtSituacaoFuncionamento;
    TextView txtQtdSalas, txtQtdSalasUtilizadas, txtTelefone;
    TextView txtCNPJ, txtQtdAlunos, txtQtdFuncionarios;
    TextView txtZona;

    Button btnVerNoMapa;

    AlertDialog alerta;

    Escola escola = new Escola();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhar_escola);

        txtCabecalho = (TextView) findViewById(R.id.detalhar_escola_txtCabecalho);
        txtEndereco = (TextView) findViewById(R.id.detalhar_escola_txtEndereco);
        txtRede = (TextView) findViewById(R.id.detalhar_escola_txtRede);
        txtZona = (TextView) findViewById(R.id.detalhar_escola_txtZona);
        txtEmail = (TextView) findViewById(R.id.detalhar_escola_txtEmail);
        txtCNPJ = (TextView) findViewById(R.id.detalhar_escola_txtCNPJ);
        txtTelefone = (TextView) findViewById(R.id.detalhar_escola_txtTelefone);
        txtEsferaAdministrativa = (TextView) findViewById(R.id.detalhar_escola_txtEsferaAdministrativa);
        txtSituacaoFuncionamento = (TextView) findViewById(R.id.detalhar_escola_txtSituacaoFuncionamento);
        txtQtdSalas = (TextView) findViewById(R.id.detalhar_escola_txtQtdSalas);
        txtQtdSalasUtilizadas = (TextView) findViewById(R.id.detalhar_escola_txtQtdSalasUtilizadas);
        txtQtdAlunos = (TextView) findViewById(R.id.detalhar_escola_txtQtdAlunos);
        txtQtdFuncionarios = (TextView) findViewById(R.id.detalhar_escola_txtQtdFuncionarios);

        btnVerNoMapa = (Button) findViewById(R.id.detalhar_escola_btnVerNoMapa);

        btnVerNoMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: direcionar e mostrar no mapa

                Intent intent = new Intent(DetalharEscolaActivity.this, MapsActivity.class);
                intent.putExtra("ESCOLA", escola);
                startActivity(intent);

            }
        });

        Bundle bundle = getIntent().getExtras();

        //verificando se existe o parametro com a chave informada
        if(bundle.containsKey("ESCOLA")){

            //recuperando escola
            escola = (Escola) bundle.getSerializable("ESCOLA");

            if(escola != null){

                txtCabecalho.setText(escola.getNome());
                txtRede.setText(escola.getRede());
                txtZona.setText(escola.getZona());
                txtEmail.setText(escola.getEmail());
                txtTelefone.setText(escola.getTelefone());
                txtCNPJ.setText(escola.getCnpj());
                txtEsferaAdministrativa.setText(escola.getEsferaAdministrativa());
                txtSituacaoFuncionamento.setText(escola.getSituacaoFuncionamento());
                txtQtdSalas.setText(String.valueOf(escola.getQtdSalasExistentes()));
                txtQtdSalasUtilizadas.setText(String.valueOf(escola.getQtdSalasUtilizadas()));
                txtQtdAlunos.setText(String.valueOf(escola.getQtdAlunos()));
                txtQtdFuncionarios.setText(String.valueOf(escola.getQtdFuncionarios()));

                txtEndereco.setText(escola.getEndereco().getDescricao() + " - " +
                        escola.getEndereco().getBairro() + " - " +
                        escola.getEndereco().getCep() + " - " +
                        escola.getEndereco().getMunicipio() + " - " +
                        escola.getEndereco().getUf());

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
