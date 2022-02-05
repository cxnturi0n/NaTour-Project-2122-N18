package com.cinamidea.natour_2022.map;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.routes_callbacks.RoutesCallback;
import com.cinamidea.natour_2022.routes_util.Route;
import com.cinamidea.natour_2022.routes_util.RoutesHTTP;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.List;
import java.util.Locale;

public class AllPathsFragment extends Fragment {

    private GoogleMap map;
    private ProgressDialog dialog;
    private List<LatLng> path;


    public static boolean locationPermissionGranted;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2;
    private LocationManager locationManager;
    private Location current_location;


    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            map.getUiSettings().setCompassEnabled(false);
            readRouteFromDb("Cognito");

            dialog.setMessage("Loading all routes, please wait.....");
            dialog.setCancelable(false);
            dialog.show();
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_all_paths, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog = new ProgressDialog(getContext());

        locationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        dialog.setOnDismissListener(dialogInterface -> {

            if (!gpsIsEnabled()) {
                showGPSDisabledDialog();
            }

        });

    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);


        }
    }

    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
                //TODO:Zoom sulla mappa
                if (current_location!= null) {
                    zoomBasedOnCurrentLocation();
                }else {
                    Toast.makeText(getContext(), "Null", Toast.LENGTH_SHORT).show();
                }
            } else {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);

            }
        } catch (SecurityException e) {

        }
    }


    private boolean gpsIsEnabled() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        } else {
            return false;
        }
    }

    public void showGPSDisabledDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("GPS Disabled");
        builder.setMessage("Gps is disabled, in order to use the application properly you need to enable GPS of your device");

        builder.setPositiveButton("Enable GPS", (dialog, which) ->
                startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)).setNegativeButton("No, Just Exit", (dialog, which) -> {
        });
        AlertDialog mGPSDialog = builder.create();
        mGPSDialog.show();
    }


    //TODO:Completare la read dal db
    private void readRouteFromDb(String user_type) {
        SharedPreferences sharedPreferences;
        sharedPreferences = getActivity().getSharedPreferences("natour_tokens", MODE_PRIVATE);
        RoutesHTTP.getAllRoutes(user_type, sharedPreferences.getString("id_token", null), new RoutesCallback() {
            @Override
            public void handleStatus200(String response) {
                dialog.dismiss();

                getActivity().runOnUiThread(() -> {
                    getCurrentLocation();
                    Route[] routes = jsonToRoutesParsing(response);
                    drawRoutes(routes);
                    getLocationPermission();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            updateLocationUI();
                        }
                    }, 6000);

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
        });

    }

    private Route[] jsonToRoutesParsing(String response) {
        Gson gson = new Gson();
        Route[] routes = gson.fromJson(removeQuotesAndUnescape(response), Route[].class);
        return routes;

    }

    private String removeQuotesAndUnescape(String uncleanJson) {
        String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");
        return StringEscapeUtils.unescapeJava(noQuotes);
    }

    private void drawRoutes(Route[] routes) {
        for (int i = 0; i < routes.length; i++) {
            path = routes[i].getCoordinates();
            map.addMarker(new MarkerOptions().position(path.get(0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            map.addMarker(new MarkerOptions().position(path.get(path.size() - 1)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.RED).width(10);
            Polyline polyline = map.addPolyline(opts);
        }

    }

    private Location getCurrentLocation() {

        current_location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
        return current_location;
    }

    private void zoomBasedOnCurrentLocation(){
        Location location = getCurrentLocation();
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                .zoom(8)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

}