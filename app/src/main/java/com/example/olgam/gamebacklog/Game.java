package com.example.olgam.gamebacklog;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "game")
class Game {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "gametitle")
    private String title;
    @ColumnInfo(name = "gamedate")
    private String date;
    @ColumnInfo(name = "gameplatform")
    private String platform;
    @ColumnInfo(name = "gamenotes")
    private String notes;


    private enum status {
        WANTED ("Want to play"),
        PLAY ("Playing"),
        STALLED ("Stalled"),
        DROPPED ("Dropped");

        status(String dropped) {
        }
    }


    public Game(String title, String date, String platform, String notes) {
        this.title = title;
        this.date = date;
        this.platform = platform;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
