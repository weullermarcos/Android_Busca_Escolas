package com.example.weullermarcos.buscaescolas;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

    private static final int REQUEST_FINE_LOCATION = 0;

    Button btnEsolasProximas, btnBuscarEscolas;
    AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_FINE_LOCATION);

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


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.d("MENSAGEM: ","NÀO TEM PERMISSÃO DESABILITA O BOTÃO");
            btnEsolasProximas.setEnabled(false);
        }
        else{

            Log.d("MENSAGEM: ","TEM PERMISSÃO habilita O BOTÃO");
            btnEsolasProximas.setEnabled(true);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // granted
                    Log.d("MENSAGEM: ","ACEITOU");
                    btnEsolasProximas.setEnabled(true);
                    exibeNotificacao("Acesso a localização concedido!");
                }
                else{
                    // no granted
                    Log.d("MENSAGEM: ","NÃO ACEITOU");
                    btnEsolasProximas.setEnabled(false);

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Aviso");
                    builder.setMessage("Algumas funcionalidades podem não funcionar corretamente, caso a permissão de localização não seja concedida!");
                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            exibeNotificacao("Sem acesso a localização!");
                        }
                    });

                    alerta = builder.create();
                    alerta.show();

                }
                return;
            }
        }
    }

    private void exibeNotificacao(String mensagem){

        NotificationManager nm = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent it = new Intent(this, MainActivity.class);

        PendingIntent pIntent = PendingIntent.getActivity(this,0,it,PendingIntent.FLAG_ONE_SHOT);

        Notification n = new Notification.Builder(this)
                .setContentTitle("Busca Escola")
                .setContentText(mensagem)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        nm.notify(0,n);

    }

    private void loadPermissions(String perm, int requestCode) {

        Log.d("MENSAGEM: ","ENTROU NO LOAD");

        if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
                ActivityCompat.requestPermissions(this, new String[]{perm},requestCode);
            }
        }
    }
}
