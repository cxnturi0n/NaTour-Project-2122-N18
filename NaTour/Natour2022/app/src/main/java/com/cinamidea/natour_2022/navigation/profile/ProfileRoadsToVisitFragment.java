package com.cinamidea.natour_2022.navigation.profile;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.SigninFragment;
import com.cinamidea.natour_2022.auth_util.UserType;
import com.cinamidea.natour_2022.navigation.HomeActivity;
import com.cinamidea.natour_2022.navigation.RecyclerViewAdapter;
import com.cinamidea.natour_2022.routes_callbacks.GetFavouritesCallback;
import com.cinamidea.natour_2022.routes_callbacks.RoutesCallback;
import com.cinamidea.natour_2022.routes_util.Route;
import com.cinamidea.natour_2022.routes_util.RoutesHTTP;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;

public class ProfileRoadsToVisitFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private View view;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_roads_to_visit, container, false);
        setupViewComponents(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!HomeActivity.counter_updated[2]) {
            recyclerView.setAdapter(null);
            progressBar.setVisibility(View.VISIBLE);
            loadRoutes();
            HomeActivity.is_updated = false;
            HomeActivity.counter_updated[2] = true;
        }
    }

    private void setupViewComponents(View view) {

        recyclerView = view.findViewById(R.id.fragmentToVisit_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = view.findViewById(R.id.fragmentToVisit_progress);
        loadRoutes();

    }

    private ArrayList<Route> jsonToRoutesParsing(String response) {
        Gson gson = new Gson();
        ArrayList<Route> routes = new ArrayList<>();
        Route[] routes_array = gson.fromJson(removeQuotesAndUnescape(response), Route[].class);
        for(int i = 0; i < routes_array.length;i++) {

            routes.add(routes_array[i]);
        }

        return routes;

    }

    private String removeQuotesAndUnescape(String uncleanJson) {
        String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");
        return StringEscapeUtils.unescapeJava(noQuotes);
    }

    private void loadRoutes() {

        UserType user_type = new UserType(getActivity());
        RoutesHTTP.getToVisitRoutes(user_type.getUser_type(), SigninFragment.current_username, user_type.getId_token(),
                new RoutesCallback() {
                    @Override
                    public void handleStatus200(String response) {
                        RoutesHTTP.getFavouriteRoutes("Cognito", SigninFragment.current_username, user_type.getId_token(), new GetFavouritesCallback(recyclerView, recyclerViewAdapter, getActivity(), progressBar, true, jsonToRoutesParsing(response)));
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
                });


    }
}