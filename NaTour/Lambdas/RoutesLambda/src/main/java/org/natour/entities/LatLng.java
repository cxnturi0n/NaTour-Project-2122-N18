package org.natour.entities;

public class LatLng {

    private float latitude;
    private float longitude;

    public LatLng(){

    }

    public LatLng(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "LatLng{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public boolean isLatLngValid(LatLng p1){
        if(p1 == null)
            throw new NullPointerException();
        if(p1.getLatitude()>90||p1.getLatitude()<-90)
            throw new IllegalArgumentException("Wrong latitude");
        if(p1.getLongitude()>180||p1.getLongitude()<-180)
            throw new IllegalArgumentException("Wrong longitude");
        return true;
    }
}
