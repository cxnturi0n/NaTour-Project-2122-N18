package org.natour.utilities;

import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;

public class GeoDistanceTest extends TestCase {

    public void test_p1LatitudeGreaterThan90_p2LatitudeLessThanMinus90_p1LongitudeBetweenMinus180And180_p2LongitudeGreaterThan180() {

        double p1_latitude = 92.432;
        double p1_longitude = 123.123;

        double p2_latitude = -912.31;
        double p2_longitude = 190.123;

        Assertions.assertThrows(IllegalArgumentException.class, () -> GeoDistance.haversineDistance(p1_latitude,p1_longitude,p2_latitude,p2_longitude));
    }

    public void test_p1LatitudeGreaterThan90_p2LatitudeLessThanMinus90_p1LongitudeLessThanMinus180_p2LongitudeBetweenMinus180And180() {

        double p1_latitude = 92.432;
        double p1_longitude = -4123.123;

        double p2_latitude = -912.31;
        double p2_longitude = -123.3;

        Assertions.assertThrows(IllegalArgumentException.class, () -> GeoDistance.haversineDistance(p1_latitude,p1_longitude,p2_latitude,p2_longitude));
    }

    public void test_p1LatitudeGreaterThan90_p2LatitudeLessThanMinus90_p1LongitudeLessThanMinus180_p2LongitudeLessThanMinus180() {

        double p1_latitude = 92.432;
        double p1_longitude = -1254.123;

        double p2_latitude = -912.31;
        double p2_longitude = -191.3;

        Assertions.assertThrows(IllegalArgumentException.class, () -> GeoDistance.haversineDistance(p1_latitude,p1_longitude,p2_latitude,p2_longitude));
    }

    public void test_p1LatitudeGreaterThan90_p2LatitudeLessThanMinus90_p1LongitudeLessThanMinus180_p2LongitudeGreaterThan180() {

        double p1_latitude = 92.432;
        double p1_longitude = -191.123;

        double p2_latitude = -912.31;
        double p2_longitude = 1.3;

        Assertions.assertThrows(IllegalArgumentException.class, () -> GeoDistance.haversineDistance(p1_latitude,p1_longitude,p2_latitude,p2_longitude));
    }

    public void test_p1LatitudeGreaterThan90_p2LatitudeLessThanMinus90_p1LongitudeGreaterThan180_p2LongitudeBetweenMinus180And180() {

        double p1_latitude = 92.432;
        double p1_longitude = 191.123;

        double p2_latitude = -912.31;
        double p2_longitude = 1.3;

        Assertions.assertThrows(IllegalArgumentException.class, () -> GeoDistance.haversineDistance(p1_latitude,p1_longitude,p2_latitude,p2_longitude));
    }

    public void test_p1LatitudeGreaterThan90_p2LatitudeLessThanMinus90_p1LongitudeGreaterThan180_p2LongitudeLessThanMinus180() {

        double p1_latitude = 92.432;
        double p1_longitude = 191.123;

        double p2_latitude = -912.31;
        double p2_longitude = -1231.3;

        Assertions.assertThrows(IllegalArgumentException.class, () -> GeoDistance.haversineDistance(p1_latitude,p1_longitude,p2_latitude,p2_longitude));
    }

    //dal 72 al poi vanno fatti


}