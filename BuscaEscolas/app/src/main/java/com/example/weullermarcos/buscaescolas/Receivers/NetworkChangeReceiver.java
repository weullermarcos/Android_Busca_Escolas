package com.example.weullermarcos.buscaescolas.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by weullermarcos on 10/07/17.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {


        if (isOnline(context)) {
            Toast.makeText(context, "INTERNET CONECTADA", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context, "INTERNET DESCONECTADA", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

}
