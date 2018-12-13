package com.endikaaguilera.mvpitunesapi.models;

import android.os.Parcel;
import android.os.Parcelable;

public class IATrackData implements Parcelable {

    private final String artistName;
    private final String trackName;
    private final String collectionViewUrl;
    private final String previewUrl;
    private final String artworkUrl100;

    private IATrackData(Parcel in) {
        artistName = in.readString();
        trackName = in.readString();
        collectionViewUrl = in.readString();
        previewUrl = in.readString();
        artworkUrl100 = in.readString();
    }

    public static final Creator<IATrackData> CREATOR = new Creator<IATrackData>() {
        @Override
        public IATrackData createFromParcel(Parcel in) {
            return new IATrackData(in);
        }

        @Override
        public IATrackData[] newArray(int size) {
            return new IATrackData[size];
        }
    };

    public String getArtistName() {
        return artistName;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getCollectionViewUrl() {
        return collectionViewUrl;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(artistName);
        parcel.writeString(trackName);
        parcel.writeString(collectionViewUrl);
        parcel.writeString(previewUrl);
        parcel.writeString(artworkUrl100);
    }

}
