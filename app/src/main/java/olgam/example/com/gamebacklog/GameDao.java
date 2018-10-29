package olgam.example.com.gamebacklog;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao

public interface GameDao {


    @Query("SELECT * FROM game")
    List<Game> getAllGames();

    @Insert
    void insertGames(Game games);

    @Delete
    void deleteGames(Game games);

    @Query("DELETE FROM game")
    void deleteAll();

    @Update
    void updateGames(Game games);


}
