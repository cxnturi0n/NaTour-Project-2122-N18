package com.cinamidea.natour_2022.map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;

import com.cinamidea.natour_2022.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GPXFragment extends Fragment {

    private GoogleMap gpx_map;
    private int FILE_REQUEST_CODE = 1;
    private String gpx_content;
    private List<LatLng> path;
    private ImageButton button_add, button_cancel, button_success;

    private List<LatLng> punti_gpx;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            gpx_map=googleMap;
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_g_p_x, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        button_add = view.findViewById(R.id.activityMapGPX_plus);
        button_success = view.findViewById(R.id.activityMapGPX_success);
        button_cancel = view.findViewById(R.id.activityMapGPX_cancel);

        setListeners();

    }



    //Metodi gpx

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }

            Uri uri = data.getData();
            if(!checkGPX(uri)) {

                Toast.makeText(getContext(), "Error GPX", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                gpx_content = readTextFromUri(uri);
                punti_gpx = new ArrayList<>();
                XmlPullParser xpp = getParser();
                InputStream gpxIn = convertStringToInputStream(gpx_content);
                path = new ArrayList<>();
                punti_gpx=drawFromGpx(gpxIn, punti_gpx, xpp);
                button_add.setVisibility(View.GONE);
                button_cancel.setVisibility(View.VISIBLE);
                button_success.setVisibility(View.VISIBLE);
                moveCameraOnGpxRoute();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }


    private void openFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, FILE_REQUEST_CODE);
    }


    private  List<LatLng> GpxParser(XmlPullParser parser, InputStream gpxIn) throws XmlPullParserException, IOException {
        // We use a List<> as we need subList for paging later
        List<LatLng> latLngs = new ArrayList<>();
        parser.setInput(gpxIn, null);
        parser.nextTag();

        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            if (parser.getName().equals("trkpt")) {
                // Save the discovered latitude/longitude attributes in each <trkpt>.
                latLngs.add(new LatLng(
                        Double.valueOf(parser.getAttributeValue(null, "lat")),
                        Double.valueOf(parser.getAttributeValue(null, "lon"))));
            }
            // Otherwise, skip irrelevant data
        }

        return latLngs;

    }

    private String readTextFromUri(Uri uri) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream =
                     getActivity().getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        return stringBuilder.toString();
    }

    private InputStream convertStringToInputStream(String string) {
        InputStream inputStream = new ByteArrayInputStream(string.getBytes());
        return inputStream;

    }

    private XmlPullParser getParser() {
        XmlPullParserFactory factory = null;
        try {
            factory = XmlPullParserFactory.newInstance();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        factory.setNamespaceAware(true);
        XmlPullParser xpp = null;
        try {
            xpp = factory.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return xpp;
    }

    private List<LatLng> drawFromGpx(InputStream inputStream, List<LatLng> latLngs, XmlPullParser parser) {
        try {
            latLngs = GpxParser(parser, inputStream);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (LatLng punto : latLngs) {
            //MarkerOptions options = new MarkerOptions().position(punto).icon(BitmapDescriptorFactory.defaultMarker());
            //gpx_map.addMarker(options);
            path.add(punto);
            if (punto == latLngs.get(latLngs.size()-1)){
                MarkerOptions options = new MarkerOptions().position(punto).icon(BitmapDescriptorFactory.defaultMarker());
                gpx_map.addMarker(options);

            } else if(punto == latLngs.get(0)){
                MarkerOptions options = new MarkerOptions().position(punto).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                gpx_map.addMarker(options);
            }

            else{
                PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.RED).width(10);
                gpx_map.addPolyline(opts);
            }

        }
        return  latLngs;

    }

    private void setListeners() {

        button_add.setOnClickListener(view -> {

            openFile();

        });

        button_cancel.setOnClickListener(view -> {

            button_add.setVisibility(View.VISIBLE);
            button_cancel.setVisibility(View.GONE);
            button_success.setVisibility(View.GONE);
            path.clear();
            gpx_map.clear();
        });

        button_success.setOnClickListener(view -> {

            startActivity(new Intent(getActivity(), CreatePathActivity.class));

        });

    }

    private boolean checkGPX(Uri uri) {

        String file_name = DocumentFile.fromSingleUri(getContext(), uri).getName();
        String extension = file_name.substring(file_name.lastIndexOf("."));
        Log.e("TAG", extension);

        return (extension.equals(".gpx")) ? true : false;

    }

    private void moveCameraOnGpxRoute() {
        Criteria criteria = new Criteria();

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(punti_gpx.get(5).latitude,punti_gpx.get(5).longitude))
                .zoom(12)
                .tilt(40)
                .build();
        gpx_map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }


}