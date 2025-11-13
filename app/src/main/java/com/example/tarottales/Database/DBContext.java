package com.example.tarottales.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBContext extends SQLiteOpenHelper {
    private static final String DB_NAME = "tarotales_demo.db";
    private static final int DB_VERSION = 1;

    public static final String TB_TAROTCARD = "TarotCard";
    public static final String TB_PLANET = "Planet";
    public static final String TB_ELEMENT = "Element";
    public static final String TB_ZODIAC = "Zodiac";

    public DBContext(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Bảng Element
        String sqlElement = "CREATE TABLE Element (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL" +
                ");";
        db.execSQL(sqlElement);

        // Bảng Planet
        String sqlPlanet = "CREATE TABLE Planet (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL" +
                ");";
        db.execSQL(sqlPlanet);

        // Bảng Zodiac
        String sqlZodiac = "CREATE TABLE Zodiac (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL" +
                ");";
        db.execSQL(sqlZodiac);

        // Bảng TarotCard
        String sqlTarotCard = "CREATE TABLE TarotCard (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL" +
                ");";
        db.execSQL(sqlTarotCard);

        // Bảng liên kết sơ bộ (TarotCard ↔ Element)
        String sqlTarotCardElement = "CREATE TABLE TarotCard_Element (" +
                "tarotCardId INTEGER, " +
                "elementId INTEGER, " +
                "PRIMARY KEY (tarotCardId, elementId)" +
                ");";
        db.execSQL(sqlTarotCardElement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Đơn giản: xóa bảng cũ và tạo lại
        db.execSQL("DROP TABLE IF EXISTS TarotCard_Element");
        db.execSQL("DROP TABLE IF EXISTS TarotCard");
        db.execSQL("DROP TABLE IF EXISTS Planet");
        db.execSQL("DROP TABLE IF EXISTS Element");
        db.execSQL("DROP TABLE IF EXISTS Zodiac");
        onCreate(db);
    }
}
