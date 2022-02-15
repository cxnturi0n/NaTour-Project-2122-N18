package com.cinamidea.natour_2022.navigation;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.utilities.auth.UserSharedPreferences;
import com.cinamidea.natour_2022.utilities.http.AdminHTTP;
import com.cinamidea.natour_2022.utilities.http.callbacks.admin.SendMailCallback;

public class AdminActivity extends AppCompatActivity {

    private ImageButton button_back;
    private Button button_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        setupXMLComponents();
        listeners();

    }

    private void setupXMLComponents() {

        button_back = findViewById(R.id.activityAdmin_backbutton);
        button_send = findViewById(R.id.activityAdmin_send);

    }

    private final void listeners() {

        button_back.setOnClickListener(view -> finish());

        button_send.setOnClickListener(view -> {

            String subject = ((EditText) findViewById(R.id.activityAdmin_title)).getText().toString();
            String body = ((EditText) findViewById(R.id.activityAdmin_description)).getText().toString();

            if (isSendable(subject, body)) {

                UserSharedPreferences userSharedPreferences = new UserSharedPreferences(this);
                new AdminHTTP().sendMail(userSharedPreferences.getId_token(), subject, body, new SendMailCallback(this));
            } else openErrorDialog();

        });

    }

    private boolean isSendable(String subject, String body) {
        return subject.length() >= 0 && body.length() >= 0;
    }

    private void openErrorDialog() {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.error_message_layout);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_alert_dialog));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ((TextView) dialog.findViewById(R.id.messageError_message)).setText(R.string.activityAdmin_error);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        dialog.findViewById(R.id.messageError_button).setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.setOnCancelListener(dialogInterface -> {
            dialog.dismiss();
        });

    }

}