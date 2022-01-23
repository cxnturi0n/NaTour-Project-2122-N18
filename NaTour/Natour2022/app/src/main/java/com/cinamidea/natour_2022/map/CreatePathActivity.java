package com.cinamidea.natour_2022.map;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cinamidea.natour_2022.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_path);

        setupComponents();
        setListeners();
        radioButtonListeners();

    }

    private void setupComponents() {

        button_back = findViewById(R.id.activityCreatePath_backbutton);
        mTagGroup = findViewById(R.id.tag_group);
        edittext_addtag = findViewById(R.id.activityCreatePath_addtag);
        rb_easy = findViewById(R.id.activityCreatePath_easy);
        rb_medium = findViewById(R.id.activityCreatePath_medium);
        rb_hard = findViewById(R.id.activityCreatePath_hard);
        rb_extreme = findViewById(R.id.activityCreatePath_extreme);
        rb_checked = rb_easy;
        mTagGroup.clearFocus();

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
            if(tags.size() < 10) {

                if(tag_to_add.length()!=0) {

                    if(!tags.contains(tag_to_add)) {

                        tags.add(tag_to_add);
                        mTagGroup.setTags(tags);
                        edittext_addtag.clearFocus();
                        edittext_addtag.getText().clear();
                        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    }

                }

            }
            else {

                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Toast.makeText(getApplicationContext(), "Hai raggiunto il massimo numero di tag", Toast.LENGTH_SHORT).show();

            }
            return true;   // Consume the event

        });

    }

    private void radioButtonListeners() {

        rb_easy.setOnClickListener(view -> {

            if(rb_checked!=null) {

                rb_checked.setChecked(false);
                rb_checked.setTextColor(getResources().getColor(R.color.natour_black));
                rb_easy.setChecked(true);
                rb_easy.setTextColor(getResources().getColor(R.color.natour_white));
                rb_checked = rb_easy;

            }

        });

        rb_medium.setOnClickListener(view -> {

            if(rb_checked!=null) {

                rb_checked.setChecked(false);
                rb_checked.setTextColor(getResources().getColor(R.color.natour_black));
                rb_medium.setChecked(true);
                rb_medium.setTextColor(getResources().getColor(R.color.natour_white));
                rb_checked = rb_medium;

            }

        });

        rb_hard.setOnClickListener(view -> {

            if(rb_checked!=null) {

                rb_checked.setChecked(false);
                rb_checked.setTextColor(getResources().getColor(R.color.natour_black));
                rb_hard.setChecked(true);
                rb_hard.setTextColor(getResources().getColor(R.color.natour_white));
                rb_checked = rb_hard;

            }

        });

        rb_extreme.setOnClickListener(view -> {

            if(rb_checked!=null) {

                rb_checked.setChecked(false);
                rb_checked.setTextColor(getResources().getColor(R.color.natour_black));
                rb_extreme.setChecked(true);
                rb_extreme.setTextColor(getResources().getColor(R.color.natour_white));
                rb_checked = rb_extreme;

            }

        });

    }

}