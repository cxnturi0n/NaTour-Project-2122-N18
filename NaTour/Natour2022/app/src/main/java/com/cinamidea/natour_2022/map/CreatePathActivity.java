package com.cinamidea.natour_2022.map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.SigninFragment;
import com.cinamidea.natour_2022.routes_callbacks.InsertRouteCallback;
import com.cinamidea.natour_2022.routes_util.Route;
import com.cinamidea.natour_2022.routes_util.RoutesHTTP;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

public class CreatePathActivity extends AppCompatActivity {

    private ImageButton button_back;
    private TagGroup mTagGroup;
    private EditText edittext_addtag;
    private List<String> tags = new ArrayList<>();
    private RadioButton rb_easy, rb_medium, rb_hard, rb_extreme;
    private RadioButton rb_checked;

    //TODO:New variable
    private Button button_continue;
    private EditText title;
    private EditText description;
    private CheckBox disability_access;
    private List<LatLng> path;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_path);

        setupComponents();
        setListeners();
        radioButtonListeners();
    }

    private void setupComponents() {

        path = new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null)
            path = intent.getParcelableArrayListExtra("path");


        dialog = new ProgressDialog(this);
        button_back = findViewById(R.id.activityCreatePath_backbutton);
        mTagGroup = findViewById(R.id.tag_group);
        edittext_addtag = findViewById(R.id.activityCreatePath_addtag);
        rb_easy = findViewById(R.id.activityCreatePath_easy);
        rb_medium = findViewById(R.id.activityCreatePath_medium);
        rb_hard = findViewById(R.id.activityCreatePath_hard);
        rb_extreme = findViewById(R.id.activityCreatePath_extreme);
        rb_checked = rb_easy;
        mTagGroup.clearFocus();

        //TODO:new components
        button_continue = findViewById(R.id.activityCreatePath_continue);
        title = findViewById(R.id.activityCreatePath_title);
        description = findViewById(R.id.activityCreatePath_description);
        disability_access = findViewById(R.id.activityCreatePath_disability);


    }

    private void setListeners() {

        button_back.setOnClickListener(view -> finish());

        edittext_addtag.setOnEditorActionListener((view, actionId, event) -> {
            if (event == null) {
                if (actionId == EditorInfo.IME_ACTION_DONE) ;
                else if (actionId == EditorInfo.IME_ACTION_NEXT) ;
                else return false;
            } else if (actionId == EditorInfo.IME_NULL) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) ;
                else return true;
            } else return false;

            String tag_to_add = edittext_addtag.getText().toString();
            if (tags.size() < 10) {

                if (tag_to_add.length() != 0) {

                    if (!tags.contains(tag_to_add)) {

                        tags.add(tag_to_add);
                        mTagGroup.setTags(tags);
                        edittext_addtag.clearFocus();
                        edittext_addtag.getText().clear();
                        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    }

                }

            } else {

                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Toast.makeText(getApplicationContext(), "Hai raggiunto il massimo numero di tag", Toast.LENGTH_SHORT).show();

            }
            return true;   // Consume the event

        });

        button_continue.setOnClickListener(view -> {
            insertRouteOnDb(path);
            dialog.setMessage("Please wait...");
            dialog.show();

        });

    }

    private void radioButtonListeners() {

        rb_easy.setOnClickListener(view -> {

            if (rb_checked != null) {

                rb_checked.setChecked(false);
                rb_checked.setTextColor(getResources().getColor(R.color.natour_black));
                rb_easy.setChecked(true);
                rb_easy.setTextColor(getResources().getColor(R.color.natour_white));
                rb_checked = rb_easy;

            }

        });

        rb_medium.setOnClickListener(view -> {

            if (rb_checked != null) {

                rb_checked.setChecked(false);
                rb_checked.setTextColor(getResources().getColor(R.color.natour_black));
                rb_medium.setChecked(true);
                rb_medium.setTextColor(getResources().getColor(R.color.natour_white));
                rb_checked = rb_medium;

            }

        });

        rb_hard.setOnClickListener(view -> {

            if (rb_checked != null) {

                rb_checked.setChecked(false);
                rb_checked.setTextColor(getResources().getColor(R.color.natour_black));
                rb_hard.setChecked(true);
                rb_hard.setTextColor(getResources().getColor(R.color.natour_white));
                rb_checked = rb_hard;

            }

        });

        rb_extreme.setOnClickListener(view -> {

            if (rb_checked != null) {

                rb_checked.setChecked(false);
                rb_checked.setTextColor(getResources().getColor(R.color.natour_black));
                rb_extreme.setChecked(true);
                rb_extreme.setTextColor(getResources().getColor(R.color.natour_white));
                rb_checked = rb_extreme;

            }

        });


    }


    private void insertRouteOnDb(List<LatLng> path) {
        String level = checkLevel();
        if (checkLevel() != "error") {

            Route route = new Route(title.getText().toString(), description.getText().toString(),
                    SigninFragment.chat_username, level, 7.8f, 0, checkDisabilityAccess(), path, tokenizedTags(tags));

            String user_type;
            SharedPreferences sharedPreferences;

            sharedPreferences = this.getSharedPreferences("natour_tokens", MODE_PRIVATE);
            String id_token = sharedPreferences.getString("id_token", null);
            if (id_token != null)
                user_type = "Cognito";
            else {
                id_token = this.getSharedPreferences("google_tokens", MODE_PRIVATE).getString("id_token", null);
                user_type = "Google";
            }
            RoutesHTTP.insertRoute(user_type, route, id_token, new InsertRouteCallback(this, dialog));
        }
    }

    private String checkLevel() {
        if (rb_easy.isChecked())
            return "Easy";
        if (rb_extreme.isChecked())
            return "Extreme";
        if (rb_medium.isChecked())
            return "Medium";
        if (rb_hard.isChecked())
            return "Hard";
        else
            return "error";

    }

    private boolean checkDisabilityAccess() {
        if (disability_access.isChecked())
            return true;
        else
            return false;
    }

    private String tokenizedTags(List<String> tags) {

        if (tags.size()==0) return "";

        String tokenized_tags = "";

        for(String tag : tags) {

            tokenized_tags += tag + ";";

        }

        tokenized_tags = tokenized_tags.substring(0, tokenized_tags.length()-1);


        return tokenized_tags;

    }


}