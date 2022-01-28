package com.cinamidea.natour_2022.navigation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.routes_util.Route;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private Button button_all, button_easy, button_medium, button_hard, button_extreme;
    private Button position_button;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Route> routes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewComponents(view);
        filterListeners();
    }

    private void setupViewComponents(View view) {

        button_all = view.findViewById(R.id.fragmentHome_all);
        button_easy = view.findViewById(R.id.fragmentHome_easy);
        button_medium = view.findViewById(R.id.fragmentHome_medium);
        button_hard = view.findViewById(R.id.fragmentHome_hard);
        button_extreme = view.findViewById(R.id.fragmentHome_extreme);
        position_button = button_all;

        recyclerView = view.findViewById(R.id.fragmentHome_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        routes = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), routes);
        recyclerView.setAdapter(recyclerViewAdapter);
        Route myroute = new Route("La mia strada", "La mia descrizione","Gesù", "Medium", 5, true);
        routes.add(myroute);
        routes.add(myroute);
        routes.add(myroute);

    }

    private void filterListeners() {

        button_all.setOnClickListener(view -> {

            setupFilterButton(button_all);

        });

        button_easy.setOnClickListener(view -> {

            setupFilterButton(button_easy);

        });

        button_medium.setOnClickListener(view -> {

            setupFilterButton(button_medium);

        });

        button_hard.setOnClickListener(view -> {

            setupFilterButton(button_hard);

        });

        button_extreme.setOnClickListener(view -> {

            setupFilterButton(button_extreme);

        });


    }

    private void setupFilterButton(Button button) {

        if(position_button.getCurrentTextColor()!=button.getCurrentTextColor()) {

            button.setBackgroundResource(R.drawable.background_home_filters);
            button.setTextColor(getResources().getColor(R.color.natour_white));
            position_button.setBackgroundResource(R.drawable.background_filter_off);
            position_button.setTextColor(getResources().getColor(R.color.natour_grey));
            position_button = button;

        }

    }

}