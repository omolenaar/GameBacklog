package com.example.olgam.gamebacklog;

import android.os.Parcel;
import android.os.Parcelable;

class Game implements Parcelable {
    //fields
    String title;
    String notes;
    String date;
    String platform;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    private enum status {
        WANTED ("Want to play"),
        PLAY ("Playing"),
        STALLED ("Stalled"),
        DROPPED ("Dropped");

        status(String dropped) {
        }
    }

    public Game(String title, String notes, String date, String status) {
        this.title = title;
        this.notes = notes;
        this.date = date;
        this.platform = platform;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
