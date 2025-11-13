package com.example.tarottales.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.tarottales.Model.Planet;

import java.util.ArrayList;
import java.util.List;

public class PlanetDAO extends DBContext {

    public PlanetDAO(@Nullable Context context) {
        super(context);
    }

    @SuppressLint("Range")
    public List<Planet> getAllPlanets() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TB_PLANET;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, null);

        List<Planet> planets = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Planet p = new Planet();
                p.setId(cursor.getInt(cursor.getColumnIndex("id")));
                p.setName(cursor.getString(cursor.getColumnIndex("name")));
                planets.add(p);
            } while (cursor.moveToNext());
        }
        return planets;
    }

    @SuppressLint("Range")
    public Planet getPlanetById(int planetId) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TB_PLANET + " WHERE id = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(planetId)});

        if (cursor.moveToFirst()) {
            Planet p = new Planet();
            p.setId(cursor.getInt(cursor.getColumnIndex("id")));
            p.setName(cursor.getString(cursor.getColumnIndex("name")));
            return p;
        }
        return null;
    }
}
