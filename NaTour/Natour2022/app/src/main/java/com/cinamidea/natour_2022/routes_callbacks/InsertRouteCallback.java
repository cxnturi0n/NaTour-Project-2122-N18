package com.cinamidea.natour_2022.routes_callbacks;

import static android.widget.Toast.LENGTH_LONG;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.cinamidea.natour_2022.map.CreatePathActivity;
import com.cinamidea.natour_2022.map.MapActivity;
import com.cinamidea.natour_2022.navigation.HomeActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class InsertRouteCallback implements RoutesCallback{

    private Activity activity;
    private ProgressDialog progress_dialog;

    public InsertRouteCallback(Activity activity, ProgressDialog progress_dialog){
        this.activity = activity;
        this.progress_dialog = progress_dialog;

    }

    @Override
    public void handleStatus200(String response) {
        activity.runOnUiThread(() -> {
            progress_dialog.dismiss();
            Toast.makeText(activity, "Route added", Toast.LENGTH_SHORT).show();
            //TODO:Dopo l'inserimento andare nel schermata di dettaglio del sentiero appena creato
            activity.startActivity(new Intent(activity.getApplicationContext(), HomeActivity.class));
        });

    }

    @Override
    public void handleStatus400(String response) {

    }

    @Override
    public void handleStatus401(String response) {

    }

    @Override
    public void handleStatus500(String response) {

    }

    @Override
    public void handleRequestException(String message) {

    }
}