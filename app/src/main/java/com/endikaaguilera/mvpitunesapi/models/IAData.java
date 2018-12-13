package com.endikaaguilera.mvpitunesapi.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class IAData implements Parcelable {

    private final ArrayList<IATrackData> balkanBeatBox;
    private final ArrayList<IATrackData> crazyTown;
    private final ArrayList<IATrackData> cypressHill;
    private final ArrayList<IATrackData> gunsNRoses;
    private final ArrayList<IATrackData> ledZeppelin;
    private final ArrayList<IATrackData> metallica;
    private final ArrayList<IATrackData> nirvana;
    private final ArrayList<IATrackData> pinkFloyd;
    private final ArrayList<IATrackData> redHotChiliPeppers;
    private final ArrayList<IATrackData> theBeatles;
    private final ArrayList<IATrackData> theRollingStones;
    private final ArrayList<IATrackData> theWho;

    private IAData(Parcel in) {
        balkanBeatBox = in.createTypedArrayList(IATrackData.CREATOR);
        crazyTown = in.createTypedArrayList(IATrackData.CREATOR);
        cypressHill = in.createTypedArrayList(IATrackData.CREATOR);
        gunsNRoses = in.createTypedArrayList(IATrackData.CREATOR);
        ledZeppelin = in.createTypedArrayList(IATrackData.CREATOR);
        metallica = in.createTypedArrayList(IATrackData.CREATOR);
        nirvana = in.createTypedArrayList(IATrackData.CREATOR);
        pinkFloyd = in.createTypedArrayList(IATrackData.CREATOR);
        redHotChiliPeppers = in.createTypedArrayList(IATrackData.CREATOR);
        theBeatles = in.createTypedArrayList(IATrackData.CREATOR);
        theRollingStones = in.createTypedArrayList(IATrackData.CREATOR);
        theWho = in.createTypedArrayList(IATrackData.CREATOR);
    }

    public static final Creator<IAData> CREATOR = new Creator<IAData>() {
        @Override
        public IAData createFromParcel(Parcel in) {
            return new IAData(in);
        }

        @Override
        public IAData[] newArray(int size) {
            return new IAData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(balkanBeatBox);
        dest.writeTypedList(crazyTown);
        dest.writeTypedList(cypressHill);
        dest.writeTypedList(gunsNRoses);
        dest.writeTypedList(ledZeppelin);
        dest.writeTypedList(metallica);
        dest.writeTypedList(nirvana);
        dest.writeTypedList(pinkFloyd);
        dest.writeTypedList(redHotChiliPeppers);
        dest.writeTypedList(theBeatles);
        dest.writeTypedList(theRollingStones);
        dest.writeTypedList(theWho);
    }

    public ArrayList<IATrackData> getBalkanBeatBox() {
        return balkanBeatBox;
    }

    public ArrayList<IATrackData> getData(int index) {

        switch (index) {

            case 0:
                return this.balkanBeatBox;

            case 1:
                return this.crazyTown;

            case 2:
                return this.cypressHill;

            case 3:
                return this.gunsNRoses;

            case 4:
                return this.ledZeppelin;

            case 5:
                return this.metallica;

            case 6:
                return this.nirvana;

            case 7:
                return this.pinkFloyd;

            case 8:
                return this.redHotChiliPeppers;

            case 9:
                return this.theBeatles;

            case 10:
                return this.theRollingStones;

            case 11:
                return this.theWho;

        }

        return null;

    }

}
