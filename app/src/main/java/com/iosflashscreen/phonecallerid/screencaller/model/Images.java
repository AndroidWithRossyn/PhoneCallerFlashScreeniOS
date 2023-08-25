package com.iosflashscreen.phonecallerid.screencaller.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Images implements Parcelable {
    private String url;

    public Images() {

    }

    public Images(String url) {

        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // Parcelable implementation
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(url);
    }

    public static final Creator<Images> CREATOR = new Creator<Images>() {
        public Images createFromParcel(Parcel in) {
            return new Images(in);
        }

        public Images[] newArray(int size) {
            return new Images[size];
        }
    };

    private Images(Parcel in) {
        url = in.readString();
    }
}
