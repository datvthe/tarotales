package com.example.tarottales.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.tarottales.Model.Zodiac;

import java.util.ArrayList;
import java.util.List;

public class ZodiacDAO extends DBContext {

    public ZodiacDAO(@Nullable Context context) {
        super(context);
    }

    @SuppressLint("Range")
    public List<Zodiac> getAllZodiacs() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TB_ZODIAC;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, null);

        List<Zodiac> zodiacs = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Zodiac z = new Zodiac();
                z.setId(cursor.getInt(cursor.getColumnIndex("id")));
                z.setName(cursor.getString(cursor.getColumnIndex("name")));
                zodiacs.add(z);
            } while (cursor.moveToNext());
        }
        return zodiacs;
    }

    @SuppressLint("Range")
    public Zodiac getZodiacById(int zodiacId) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TB_ZODIAC + " WHERE id = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(zodiacId)});

        if (cursor.moveToFirst()) {
            Zodiac z = new Zodiac();
            z.setId(cursor.getInt(cursor.getColumnIndex("id")));
            z.setName(cursor.getString(cursor.getColumnIndex("name")));
            return z;
        }
        return null;
    }
}
