//package com.timecapsule.app.geofence;
//
//import com.google.android.gms.location.Geofence;
//
///**
// * Created by tarynking on 3/7/17.
// */
//
//public class timeCapsuleGeoFence implements Geofence {
//    // Instance variables
//    private final String mId;
//    private final double mLatitude;
//    private final double mLongitude;
//    private final float mRadius;
//    private long mExpirationDuration;
//    private int mTransitionType;
//
//    /**
//     * @param geofenceId The Geofence's request ID.
//     * @param latitude Latitude of the Geofence's center in degrees.
//     * @param longitude Longitude of the Geofence's center in degrees.
//     * @param radius Radius of the geofence circle in meters.
//     * @param expiration Geofence expiration duration.
//     * @param transition Type of Geofence transition.
//     */
//    public timeCapsuleGeoFence(String geofenceId, double latitude, double longitude, float radius,
//                               long expiration, int transition) {
//        // Set the instance fields from the constructor.
//        this.mId = geofenceId;
//        this.mLatitude = latitude;
//        this.mLongitude = longitude;
//        this.mRadius = radius;
//        this.mExpirationDuration = expiration;
//        this.mTransitionType = transition;
//    }
//
//    // Instance field getters.
//    public String getId() {
//        return mId;
//    }
//    public double getLatitude() {
//        return mLatitude;
//    }
//    public double getLongitude() {
//        return mLongitude;
//    }
//    public float getRadius() {
//        return mRadius;
//    }
//    public long getExpirationDuration() {
//        return mExpirationDuration;
//    }
//    public int getTransitionType() {
//        return mTransitionType;
//    }
//
//    /**
//     * Creates a NearbyLocation Services Geofence object from a SimpleGeofence.
//     * @return A Geofence object.
//     */
//    public Geofence toGeofence() {
//        // Build a new Geofence object.
//        return new Geofence.Builder()
//                .setRequestId(mId)
//                .setTransitionTypes(mTransitionType)
//                .setCircularRegion(mLatitude, mLongitude, mRadius)
//                .setExpirationDuration(mExpirationDuration)
//                .build();
//    }
//
//    @Override
//    public String getRequestId() {
//        return null;
//    }
//}
