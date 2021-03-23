package com.example.pitchperfect;

import java.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.*;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper
{

    public static final String PLAYER_DATA = "PLAYER_DATA";
    public static final String COLUMN_PLAYER_NAME = "PLAYER_NAME";
    public static final String COLUMN_BALL_SPEED = "BALL_SPEED";
    public static final String COLUMN_BALL_TIMETOPLATE = "BALL_TIMETOPLATE";
    public static final String COLUMN_BALL_RPM = "BALL_RPM";
    public static final String COLUMN_PLAYER_ID = "PLAYER_ID";

    public DatabaseHelper(@Nullable Context context)
    {
        super(context, "player.db", null, 1);
    }

    // called when database is first accessed
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createTableStatement = "CREATE TABLE " + PLAYER_DATA + " (" + COLUMN_PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PLAYER_NAME + " TEXT, " + COLUMN_BALL_SPEED + " FLOAT, " + COLUMN_BALL_TIMETOPLATE + " FLOAT, " + COLUMN_BALL_RPM + " FLOAT)";
        db.execSQL(createTableStatement);
    }

    // called when database version number is updated
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public boolean addOne(PlayerModel playerModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PLAYER_NAME, playerModel.getName());
        cv.put(COLUMN_BALL_SPEED, playerModel.getSpeed());
        cv.put(COLUMN_BALL_TIMETOPLATE, playerModel.getTime_to_plate());
        cv.put(COLUMN_BALL_RPM, playerModel.getRpm());

        long insert = db.insert(PLAYER_DATA, null, cv);
        if(insert == -1)
            return false;
        else
            return true;
    }

    public boolean deleteOne(PlayerModel playerModel)
    {
        String queryString = "DELETE FROM " + PLAYER_DATA + "WHERE " + COLUMN_PLAYER_ID + " = " + playerModel.getPlayer_id();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor results = db.rawQuery(queryString, null);

        if(results.moveToFirst())
        {
            return true;
        }
        else
            return false;
    }


    public List<PlayerModel> getEveryone()
    {
        List<PlayerModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + PLAYER_DATA;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor results = db.rawQuery(queryString, null);

        if(results.moveToFirst())
        {
            // loop through the results and create new player object for each row
            // put them in return list (from a few lines up)
            do{
                int playerID = results.getInt(0);
                String playerName = results.getString(1);
                float ballSpeed = results.getFloat(2);
                float timeToPlate = results.getFloat(3);
                float ballRPM = results.getFloat(4);

                PlayerModel newPlayer = new PlayerModel(playerID, playerName, ballSpeed, timeToPlate, ballRPM);
                returnList.add(newPlayer);

            }while(results.moveToNext());
        }
        else
        {
            // failed, empty list
        }

        results.close();
        db.close();

        return returnList;
    }
}
