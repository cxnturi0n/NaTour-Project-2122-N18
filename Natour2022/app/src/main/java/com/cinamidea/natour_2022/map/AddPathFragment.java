package com.cinamidea.natour_2022.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cinamidea.natour_2022.R;
import com.google.android.gms.maps.CameraUpdateFactory;
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
    private ArrayList<Marker> markers;
    private ArrayList<Marker> AllMarkers;
    private List<LatLng> path;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            add_path_map=googleMap;
            markers = new ArrayList<Marker>(2);
            AllMarkers = new ArrayList<Marker>();
            path = new ArrayList<>();

            add_path_map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                MarkerOptions options = new MarkerOptions();

                @Override
                public void onMapClick(LatLng point) {
                    if (markers.size() == 0) {
                        Marker start_marker = add_path_map.addMarker(new MarkerOptions().position(point).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        markers.add(start_marker);
                        path.add(start_marker.getPosition());
                    } else {
                        //IL secondo marker il colore rosso
                        Marker marker = add_path_map.addMarker(new MarkerOptions().position(point).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        markers.add(marker);
                        AllMarkers.add(marker);
                        path.add(marker.getPosition());
                    }

                    PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.RED).width(16);
                    Polyline polyline = add_path_map.addPolyline(opts);


                }

            });


            add_path_map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(@NonNull LatLng latLng) {
                    if (path.size() >= 1 ) {
                        Marker end_marker = add_path_map.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title("You are here")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        path.add(end_marker.getPosition());
                        //Aggiungiamo una linea rossa tra i marker
                        PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.RED).width(16);
                        Polyline polyline = add_path_map.addPolyline(opts);


                        polyline=add_path_map.addPolyline(opts);
                        removeAllMarkers();
                        path.clear();



                    } else {
                        //Controllo su ultimo marker
                        Toast.makeText(getContext(), "Non puoi aggiungere un altro segnaposto di fine percorso", Toast.LENGTH_LONG).show();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
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
    }
}