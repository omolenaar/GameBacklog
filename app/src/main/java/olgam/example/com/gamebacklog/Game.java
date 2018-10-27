package olgam.example.com.gamebacklog;

import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable {
    private String title;
    private String platform;
    private String status;
    private String date;
    private String notes;

    public Game(String title, String platform, String status, String notes, String date) {
        this.title = title;
        this.platform = platform;
        this.status = status;
        this.date = date;
        this.notes = notes;
    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Game(Parcel in){
        this.title = in.readString();
        this.platform = in.readString();
        this.status = in.readString();
        this.date = in.readString();
        this.notes = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(this.title);
        out.writeString(this.platform);
        out.writeString(this.status);
        out.writeString(this.date);
        out.writeString(this.notes);
    }
}
