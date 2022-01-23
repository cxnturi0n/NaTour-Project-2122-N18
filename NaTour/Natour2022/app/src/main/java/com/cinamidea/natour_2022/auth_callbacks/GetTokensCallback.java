package com.cinamidea.natour_2022.auth_callbacks;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cinamidea.natour_2022.auth_callbacks.AuthenticationCallback;
import com.cinamidea.natour_2022.auth_util.Tokens;
import com.cinamidea.natour_2022.navigation.HomeActivity;
import com.cinamidea.natour_2022.R;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringEscapeUtils;


public class GetTokensCallback implements AuthenticationCallback {

    private Activity activity;

    private String username;

    public GetTokensCallback(Activity activity, String username){
        this.activity = activity;
        this.username = username;
    }

    @Override
    public void handleStatus200(String response) {

        SharedPreferences natour_tokens = activity.getSharedPreferences("natour_tokens", MODE_PRIVATE);

        setTokensInSharedPreferences(natour_tokens, response);

        Intent intent = new Intent(activity, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);

    }

    @Override
    public void handleStatus400(String response) {
        setupErrorDialog(response);
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

    private void setTokensInSharedPreferences(SharedPreferences user_details, String response){


        Gson gson = new Gson();
        Tokens tokens = gson.fromJson(removeQuotesAndUnescape(response), Tokens.class);

        SharedPreferences.Editor editor = user_details.edit();

        editor.putString("id_token", tokens.getId_token());
        editor.putString("refresh_token", tokens.getRefresh_token());
        editor.putString("access_token", tokens.getAccess_token());
        editor.putString("username", username);

        editor.commit();
    }

    private String removeQuotesAndUnescape(String uncleanJson) {
        String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");
        return StringEscapeUtils.unescapeJava(noQuotes);
    }

    private void setupErrorDialog(String message) {

        activity.runOnUiThread(() -> {
            Dialog dialog = new Dialog(activity);
            dialog.setContentView(R.layout.error_message_layout);
            dialog.getWindow().setBackgroundDrawable(activity.getDrawable(R.drawable.background_alert_dialog));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ((TextView) dialog.findViewById(R.id.messageError_message)).setText(message);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();

            dialog.findViewById(R.id.messageError_button).setOnClickListener(view -> {
                dialog.hide();
            });

            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    dialog.hide();
                }
            });

        });

    }


}
