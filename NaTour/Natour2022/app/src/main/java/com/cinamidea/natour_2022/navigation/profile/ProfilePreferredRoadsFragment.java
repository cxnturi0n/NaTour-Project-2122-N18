package com.cinamidea.natour_2022.navigation.profile;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.entities.Route;
import com.cinamidea.natour_2022.navigation.main.RecyclerViewAdapter;
import com.cinamidea.natour_2022.navigation.main.views.HomeActivity;
import com.cinamidea.natour_2022.utilities.auth.UserSharedPreferences;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;

public class ProfilePreferredRoadsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ProgressBar progressBar;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_preferred_roads, container, false);
        setupViewComponents(view);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
        if (!HomeActivity.counter_updated[1]) {
            recyclerView.setAdapter(null);
            progressBar.setVisibility(View.VISIBLE);
            loadRoutes();
            HomeActivity.is_updated = false;
            HomeActivity.counter_updated[1] = true;
        }
    }

    private void setupViewComponents(View view) {

        recyclerView = view.findViewById(R.id.fragmentPreferredRoads_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = view.findViewById(R.id.fragmentPreferredRoads_progress);
        loadRoutes();

    }

    private ArrayList<Route> jsonToRoutesParsing(String response) {
        Gson gson = new Gson();
        ArrayList<Route> routes = new ArrayList<>();
        Route[] routes_array = gson.fromJson(removeQuotesAndUnescape(response), Route[].class);
        for (int i = 0; i < routes_array.length; i++) {

            routes.add(routes_array[i]);
        }

        return routes;

    }

    private String removeQuotesAndUnescape(String uncleanJson) {
        String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");
        return StringEscapeUtils.unescapeJava(noQuotes);
    }

    private void loadRoutes() {

        UserSharedPreferences user_type = new UserSharedPreferences(getActivity());
        String id_token = user_type.getUser_type() + user_type.getId_token();
       /* new RoutesHTTP().getFavouriteRoutes(SigninFragment.current_username, id_token,
                new HTTPCallback() {
                    @Override
                    public void handleStatus200(String response) {
                        getActivity().runOnUiThread(() -> {
                            progressBar.setVisibility(View.GONE);
                            recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), jsonToRoutesParsing(response), true);
                            recyclerView.setAdapter(recyclerViewAdapter);
                        });
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
                });*/

    }

}