package com.cinamidea.natour_2022.report;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.entities.Report;
import com.cinamidea.natour_2022.utilities.UserType;

public class ReportActivity extends AppCompatActivity implements ReportContract.View {

    private ImageButton button_back;
    private Button button_send;
    private RadioGroup radio_group;
    private String report_type;
    private String route_name;
    private ReportContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        presenter = new ReportPresenter(this);

        route_name = getIntent().getStringExtra("route_name");
        button_back = findViewById(R.id.activityReport_backbutton);
        radio_group = findViewById(R.id.activityReport_radiogroup);
        button_send = findViewById(R.id.activityReport_send);
        report_type = "Wrong";
        listeners();

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

        UserType user_type = new UserType(this);
        presenter.sendReportButtonClicked(user_type.getUserType()+user_type.getIdToken(), new Report(route_name, title,description, user_type.getUsername(), report_type));

    }


    @Override
    public void reportDone(String message) {
        Log.e("me",message);
        //TODO TOAST + ACTIVITY FINISH
    }

    @Override
    public void displayError(String message) {
            //TODO TOAST + ACTIVITY FINISH
    }
}