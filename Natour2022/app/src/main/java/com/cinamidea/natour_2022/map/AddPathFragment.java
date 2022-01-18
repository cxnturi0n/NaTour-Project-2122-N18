package com.cinamidea.natour_2022.map;

import static android.content.Context.MODE_PRIVATE;

import static com.cinamidea.natour_2022.map.AllPathsFragment.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.SigninFragment;
import com.cinamidea.natour_2022.routes_callbacks.InsertRouteCallback;
import com.cinamidea.natour_2022.routes_callbacks.RoutesCallback;
import com.cinamidea.natour_2022.routes_util.Route;
import com.cinamidea.natour_2022.routes_util.RoutesHTTP;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class AddPathFragment extends Fragment {

    private GoogleMap add_path_map;
    private ArrayList<Marker>markers = new ArrayList<Marker>(2);
    private ArrayList<Marker>AllMarkers = new ArrayList<Marker>();
    private List<LatLng> path = new ArrayList<>();
    private ImageButton button_success, button_cancel;
    private int check_long_press_map_click = 0;

    public ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        dialog = new ProgressDialog(getContext());
        return inflater.inflate(R.layout.fragment_add_path, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        button_cancel = view.findViewById(R.id.activityMap_cancel);
        button_success = view.findViewById(R.id.activityMap_success);
        setListeners();

    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            add_path_map=googleMap;

            add_path_map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                MarkerOptions options = new MarkerOptions();

                @Override
                public void onMapClick(LatLng point) {

                    if (markers.size() == 0 && check_long_press_map_click == 0) {
                        Marker start_marker = add_path_map.addMarker(new MarkerOptions().position(point).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        markers.add(start_marker);
                        path.add(start_marker.getPosition());
                        button_cancel.setVisibility(View.VISIBLE);
                        check_long_press_map_click = 1;

                    } else if(check_long_press_map_click==1) {
                        //IL secondo marker il colore rosso
                        Marker marker = add_path_map.addMarker(new MarkerOptions().position(point).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        markers.add(marker);
                        AllMarkers.add(marker);
                        path.add(marker.getPosition());
                    }

                    PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.RED).width(10);
                    Polyline polyline = add_path_map.addPolyline(opts);

                }

            });


            add_path_map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(@NonNull LatLng latLng) {

                    if(check_long_press_map_click==1) {

                        Marker end_marker = add_path_map.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title("You are here")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        path.add(end_marker.getPosition());
                        //Aggiungiamo una linea rossa tra i marker
                        PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.RED).width(10);
                        Polyline polyline = add_path_map.addPolyline(opts);


                        polyline=add_path_map.addPolyline(opts);
                        removeAllMarkers();

                        check_long_press_map_click = 2;
                        button_success.setVisibility(View.VISIBLE);

                    }

                }
            });



        }
    };

    private void removeAllMarkers() {
        for (Marker mLocationMarker: AllMarkers) {
            mLocationMarker.remove();
        }
        AllMarkers.clear();

    }

    private void setListeners() {

        button_cancel.setOnClickListener(view -> {

            button_cancel.setVisibility(View.GONE);
            if(button_success.getVisibility()==View.VISIBLE)
                button_success.setVisibility(View.GONE);

            add_path_map.clear();
            markers.clear();
            path.clear();
            check_long_press_map_click = 0;

        });



        button_success.setOnClickListener(view -> {
            insertRouteOnDb(path);
            dialog.setMessage("Caricamento.....");
            dialog.show();
            path.clear();
            button_success.setVisibility(View.GONE);
            markers.clear();

        });

    }

    public void onRemove() {

        add_path_map.clear();
        markers.clear();
        path.clear();

    }

    private void insertRouteOnDb(List<LatLng> path) {
        Route route = new Route("Sentiero 1", "Sentiero lungo la valle della morte",
                SigninFragment.chat_username, "Extreme", 7.8f, 0, false, path);

        SharedPreferences sharedPreferences;
        sharedPreferences = getActivity().getSharedPreferences("natour_tokens", MODE_PRIVATE);
        RoutesHTTP.insertRoute(route, sharedPreferences.getString("id_token", null), new InsertRouteCallback(getActivity(), dialog));

    }


}