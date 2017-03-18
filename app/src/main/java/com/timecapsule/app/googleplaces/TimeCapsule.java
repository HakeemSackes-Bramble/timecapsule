package com.timecapsule.app.googleplaces;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.List;

/**
 * Created by hakeemsackes-bramble on 3/10/17.
 */

public class TimeCapsule implements Parcelable {

    private String title;
    private String description;
    private double latitude;
    private double longitude;
    private List<File> mediaList; /* still need to add audio and video*/

    protected TimeCapsule(Parcel in) {
        title = in.readString();
        description = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<TimeCapsule> CREATOR = new Creator<TimeCapsule>() {
        @Override
        public TimeCapsule createFromParcel(Parcel in) {
            return new TimeCapsule(in);
        }

        @Override
        public TimeCapsule[] newArray(int size) {
            return new TimeCapsule[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<File> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<File> mediaList) {
        this.mediaList = mediaList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}
