package com.example.weullermarcos.buscaescolas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.weullermarcos.buscaescolas.Models.Escola;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Escola escola;
    AlertDialog alerta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Bundle bundle = getIntent().getExtras();

        //verificando se existe o parametro com a chave informada
        if(bundle.containsKey("ESCOLA")){

            //recuperando escola
            escola = (Escola) bundle.getSerializable("ESCOLA");

        }
        else{

            exibeErroERetorna();
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(escola == null){
            exibeErroERetorna();
        }

        if(escola.getLatitude() == null || escola.getLongitude() == null){
            exibeErroLatLong();
            return;
        }

        // Add a marker in Sydney and move the camera
        LatLng latLong = new LatLng(escola.getLatitude(), escola.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLong).title(escola.getNome()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLong));
    }

    private void exibeErroERetorna(){

        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
        builder.setTitle("Erro");
        builder.setMessage("Erro ao visualizar escola no mapa.");

        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                finish();
            }
        });

        alerta = builder.create();
        alerta.show();
    }

    private void exibeErroLatLong(){

        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
        builder.setTitle("Erro");
        builder.setMessage("Não foi possível obter informações geográficas da escola selecionada.");

        builder.setNeutralButton("OK", null);

        alerta = builder.create();
        alerta.show();
    }
}
