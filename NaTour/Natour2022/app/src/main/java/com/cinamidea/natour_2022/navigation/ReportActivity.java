package com.cinamidea.natour_2022.navigation;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.signin.SigninFragment;
import com.cinamidea.natour_2022.entities.Report;
import com.cinamidea.natour_2022.utilities.auth.UserSharedPreferences;
import com.cinamidea.natour_2022.utilities.http.ReportHTTP;
import com.cinamidea.natour_2022.utilities.http.callbacks.report.InsertReportCallback;

public class ReportActivity extends AppCompatActivity {

    private final int MIN_TITLE_LENGTH = 3;
    private final int MIN_DESCRIPTION_LENGTH = 3;
    private ImageButton button_back;
    private Button button_send;
    private RadioGroup radio_group;
    private String report_type;
    private String route_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        route_name = getIntent().getStringExtra("route_name");
        setupXMLComponents();
        listeners();

    }

    private void setupXMLComponents() {

        button_back = findViewById(R.id.activityReport_backbutton);
        radio_group = findViewById(R.id.activityReport_radiogroup);
        button_send = findViewById(R.id.activityReport_send);
        report_type = "Wrong";

    }

    private void listeners() {

        button_back.setOnClickListener(view -> finish());

        button_send.setOnClickListener(view -> sendReport());

        radio_group.setOnCheckedChangeListener((radioGroup, i) -> {

            if (i == R.id.activityReport_inaccurate)
                report_type = "Wrong";
            else
                report_type = "Obsolete";

        });

    }

    private void sendReport() {

        String title = ((EditText) findViewById(R.id.activityReport_title)).getText().toString();
        String description = ((EditText) findViewById(R.id.activityReport_description)).getText().toString();

        if (isReportable(title, description)) {

            Report report = new Report(route_name, title, description, SigninFragment.current_username, report_type);
            UserSharedPreferences userSharedPreferences = new UserSharedPreferences(this);
            new ReportHTTP().insertReport(report, userSharedPreferences.getUser_type() + userSharedPreferences.getId_token(), new InsertReportCallback(this));

        } else openErrorDialog();

    }

    private boolean isReportable(String title, String description) {
        return title.length() >= MIN_TITLE_LENGTH && description.length() >= MIN_DESCRIPTION_LENGTH;
    }

    private void openErrorDialog() {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.error_message_layout);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_alert_dialog));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ((TextView) dialog.findViewById(R.id.messageError_message)).setText(R.string.activityReport_notitleordescription);
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