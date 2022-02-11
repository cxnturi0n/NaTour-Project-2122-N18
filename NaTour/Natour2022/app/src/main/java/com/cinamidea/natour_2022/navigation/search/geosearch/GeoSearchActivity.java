package com.cinamidea.natour_2022.navigation.search.geosearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cinamidea.natour_2022.R;
import com.google.android.gms.maps.model.LatLng;
import com.paulrybitskyi.persistentsearchview.PersistentSearchView;

import java.util.ArrayList;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

public class GeoSearchActivity extends AppCompatActivity {

    private Fragment map_fragment;
    private List<String> tags = new ArrayList<>();
    private List<String> difficulties = new ArrayList<>();
    private String range;
    private String min_duration;
    private boolean is_disability;
    private Dialog dialog;

    private static PersistentSearchView persistentSearchView;
    private static LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_search);

        map_fragment = new SearchMapFragment();
        persistentSearchView = findViewById(R.id.activityGeoSearch_search);
        persistentSearchView.showRightButton();

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activityGeoSearch_map, map_fragment);
        fragmentTransaction.commit();

        dialog =  new Dialog(GeoSearchActivity.this);
        dialog.setContentView(R.layout.dialog_search_filters);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(false);

        listeners();

    }

    private void listeners() {

        persistentSearchView.setOnRightBtnClickListener(view -> {
            dialog.show();

            EditText addtag = dialog.findViewById(R.id.activitySearch_addtag);
            TagGroup tagGroup = dialog.findViewById(R.id.activitySearch_taggroup);
            RadioButton easy = dialog.findViewById(R.id.activitySearch_easy);
            RadioButton medium = dialog.findViewById(R.id.activitySearch_medium);
            RadioButton hard = dialog.findViewById(R.id.activitySearch_hard);
            RadioButton extreme = dialog.findViewById(R.id.activitySearch_extreme);

            addTag(addtag, tagGroup);
            difficultyListener(easy, medium, hard, extreme);

            dialog.findViewById(R.id.activitySearch_cancel).setOnClickListener(v -> {

                dialog.dismiss();
                tags.clear();
                difficulties.clear();
                easy.setChecked(false);
                medium.setChecked(false);
                hard.setChecked(false);
                extreme.setChecked(false);
                tagGroup.setTags(tags);
                ((EditText)dialog.findViewById(R.id.activitySearch_range)).setText("");
                ((EditText)dialog.findViewById(R.id.activitySearch_duration)).setText("");
                ((CheckBox)dialog.findViewById(R.id.activitySearch_disability)).setChecked(false);

            });

            dialog.findViewById(R.id.activitySearch_ok).setOnClickListener(v -> {

                range = ((EditText)dialog.findViewById(R.id.activitySearch_range)).getText().toString();
                min_duration = ((EditText)dialog.findViewById(R.id.activitySearch_duration)).getText().toString();
                is_disability = ((CheckBox)dialog.findViewById(R.id.activitySearch_disability)).isChecked();

                dialog.dismiss();

            });

        });

    }

    private void addTag(EditText addtag, TagGroup tagGroup) {

        addtag.setOnEditorActionListener((view, actionId, event) -> {
            if (event == null) {
                if (actionId == EditorInfo.IME_ACTION_DONE) ;
                else if (actionId == EditorInfo.IME_ACTION_NEXT) ;
                else return false;
            } else if (actionId == EditorInfo.IME_NULL) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) ;
                else return true;
            } else return false;

            String tag_to_add = addtag.getText().toString();
            if (tags.size() < 10) {

                if (tag_to_add.length() != 0) {

                    if (!tags.contains(tag_to_add)) {

                        tags.add(tag_to_add);
                        tagGroup.setTags(tags);
                        addtag.clearFocus();
                        addtag.getText().clear();
                        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    }

                }

            } else {

                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Toast.makeText(getApplicationContext(), "Hai raggiunto il massimo numero di tag", Toast.LENGTH_SHORT).show();

            }
            return true;
        });

    }

    private void difficultyListener(RadioButton easy, RadioButton medium, RadioButton hard, RadioButton extreme) {

        easy.setOnClickListener(v -> {

            if(!difficulties.contains("Easy")) difficulties.add("Easy");

        });

        medium.setOnClickListener(v -> {

            if(!difficulties.contains("Medium")) difficulties.add("Medium");

        });

        hard.setOnClickListener(v -> {

            if(!difficulties.contains("Hard")) difficulties.add("Hard");

        });

        extreme.setOnClickListener(v -> {

            if(!difficulties.contains("Extreme")) difficulties.add("Extreme");

        });

    }

    public static void setLatLng(LatLng latLng) {
        GeoSearchActivity.latLng = latLng;
    }

}