package com.example.tarottales.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.tarottales.Model.Element;

import java.util.ArrayList;
import java.util.List;

public class ElementDAO extends DBContext {

    public ElementDAO(@Nullable Context context) {
        super(context);
    }

    @SuppressLint("Range")
    public List<Element> getAllElements() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TB_ELEMENT;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, null);

        List<Element> elements = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Element e = new Element();
                e.setId(cursor.getInt(cursor.getColumnIndex("id")));
                e.setName(cursor.getString(cursor.getColumnIndex("name")));
                elements.add(e);
            } while (cursor.moveToNext());
        }
        return elements;
    }

    @SuppressLint("Range")
    public Element getElementById(int elementId) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TB_ELEMENT + " WHERE id = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(elementId)});

        if (cursor.moveToFirst()) {
            Element e = new Element();
            e.setId(cursor.getInt(cursor.getColumnIndex("id")));
            e.setName(cursor.getString(cursor.getColumnIndex("name")));
            return e;
        }
        return null;
    }
}
