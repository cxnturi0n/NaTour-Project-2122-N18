package org.natour.utilities;

import org.natour.entities.LatLng;

public class GeoDistance {

    private static double rad(double x) {
        return x * Math.PI / 180;
    }


    public static double haversineDistance(LatLng p1, LatLng p2) {

        double EarthRadius = 6378137.0;
        double dLat = rad(p2.getLatitude() - p1.getLatitude());
        double dLong = rad(p2.getLongitude() - p1.getLongitude());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(rad(p1.getLatitude())) * Math.cos(rad(p2.getLatitude())) *
                        Math.sin(dLong / 2) * Math.sin(dLong / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EarthRadius * c;
    }

    public static double haversineDistance(double p1_latitude, double p1_longitude, double p2_latitude, double p2_longitude) {

        if(Math.abs(p1_latitude)>90||Math.abs(p2_latitude)>90)
            throw new IllegalArgumentException("Wrong latitude");
        if(Math.abs(p1_longitude)>180||Math.abs(p2_longitude)>180)
            throw new IllegalArgumentException("Wrong latitude");

        double EarthRadius = 6378137.0;
        double dLat = rad(p2_latitude - p1_latitude);
        double dLong = rad(p2_longitude - p1_longitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(rad(p1_latitude)) * Math.cos(rad(p2_latitude)) *
                        Math.sin(dLong / 2) * Math.sin(dLong / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EarthRadius * c;
    }

}
