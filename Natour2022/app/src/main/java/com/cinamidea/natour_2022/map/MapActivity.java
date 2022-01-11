package com.cinamidea.natour_2022.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.CustomAuthActivity;

public class MapActivity extends CustomAuthActivity {


    private ImageButton button_back;
    private Button button_addpath, button_importgpx, button_allpaths, button_help;
    private Fragment add_path_fragment, gpx_fragment, visualize_map_fragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        setupViewComponents();
        mainListeners();
        button_allpaths.setClickable(false);
        addPathListeners();


    }


    private void mainListeners() {

        button_back.setOnClickListener(view -> finish());

        button_addpath.setOnClickListener(view -> {

            button_addpath.setClickable(false);

            if (!button_allpaths.isClickable()) {
                button_allpaths.setClickable(true);
                button_allpaths.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.ic_globe), null, null);
            } else {
                button_importgpx.setClickable(true);
                button_importgpx.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.ic_file), null, null);
            }

            button_help.setVisibility(View.VISIBLE);
            button_addpath.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.ic_add_road_active), null, null);
            changeFragment(R.id.map, add_path_fragment);
        });

        button_allpaths.setOnClickListener(view -> {

            button_allpaths.setClickable(false);

            if (!button_addpath.isClickable()) {
                button_addpath.setClickable(true);
                button_help.setVisibility(View.GONE);
                button_addpath.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.ic_add_road), null, null);
            } else {
                button_importgpx.setClickable(true);
                button_importgpx.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.ic_file), null, null);
            }

            button_allpaths.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.ic_globe_active), null, null);
            changeFragment(R.id.map, visualize_map_fragment);
        });

        button_importgpx.setOnClickListener(view -> {

            button_importgpx.setClickable(false);

            if (!button_addpath.isClickable()) {
                button_addpath.setClickable(true);
                button_help.setVisibility(View.GONE);
                button_addpath.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.ic_add_road), null, null);
            } else {
                button_allpaths.setClickable(true);
                button_allpaths.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.ic_globe), null, null);
            }

            button_importgpx.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.ic_file_active), null, null);
            changeFragment(R.id.map,gpx_fragment);




        });




    }



    private void addPathListeners() {

        button_help.setOnClickListener(view -> {

            Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_SHORT).show();

        });

    }


    @Override
    protected void setupViewComponents() {
        button_back = findViewById(R.id.activityMap_backbutton);
        button_addpath = findViewById(R.id.activityMap_addpath);
        button_importgpx = findViewById(R.id.activityMap_importgpx);
        button_allpaths = findViewById(R.id.activityMap_allpaths);
        button_help = findViewById(R.id.activityMap_help);

        gpx_fragment = new GpxFragment();
        add_path_fragment = new AddPathFragment();
        visualize_map_fragment = new VisualizeMapFragment();

    }


}