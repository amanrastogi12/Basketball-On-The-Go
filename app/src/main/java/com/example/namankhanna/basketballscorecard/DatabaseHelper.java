package com.example.namankhanna.basketballscorecard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DATABASE_HELPER";

    public static final String DATABASE_NAME = "Teams.db";
    public static final String TABLE_TEAM = "Team";
    public static final String ID = "Id";
    public static final String TEAM_1 = "Team1";
    public static final String TEAM_2 = "Team2";
    public static final String PLAYER_NAME = "Name";
    public static final String PLAYER_TNUM = "TNo";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "Create table " + TABLE_TEAM + "(" +
                ID + " Integer Primary Key Autoincrement," +
                TEAM_1 + " Text," +
                TEAM_2 + " Text)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists " + TABLE_TEAM);
        onCreate(sqLiteDatabase);
    }

    public void writeTeams(String team1, String team2) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEAM_1, team1);
        contentValues.put(TEAM_2, team2);
        long res = database.insert(TABLE_TEAM, null, contentValues);
        if(res != -1) {
            Log.d(TAG, "Teams written successfully");
            createPlayersTable(team1, team2);
        }
    }

    private void createPlayersTable(String team1, String team2) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query1 = "Create table " + team1 + "(" +
                ID + " Integer Primary Key Autoincrement," +
                PLAYER_NAME + " Text," +
                PLAYER_TNUM + " Integer)";
        String query2 = "Create table " + team2 + "(" +
                ID + " Integer Primary Key Autoincrement," +
                PLAYER_NAME + " Text," +
                PLAYER_TNUM + " Integer)";
        database.execSQL(query1);
        database.execSQL(query2);
    }

    public Cursor readTeams() {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "Select * from " + TABLE_TEAM;
        Cursor cursor = database.rawQuery(query, null);
        return cursor;
    }
}
