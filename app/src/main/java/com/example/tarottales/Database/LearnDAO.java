package com.example.tarottales.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.tarottales.Model.Element;
import com.example.tarottales.Model.TarotCard;

import java.util.ArrayList;
import java.util.List;

public class LearnDAO extends DBContext {

    public LearnDAO(@Nullable Context context) {
        super(context);
    }

    //<editor-fold desc="Basic TarotCard Methods">

    // Get all TarotCards
    @SuppressLint("Range")
    public List<TarotCard> getListCard() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TB_TAROTCARD;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, null);

        List<TarotCard> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                TarotCard card = new TarotCard();
                card.setId(cursor.getInt(cursor.getColumnIndex("id")));
                card.setName(cursor.getString(cursor.getColumnIndex("name")));
                list.add(card);
            } while (cursor.moveToNext());
        }
        return list;
    }

    // Get TarotCard detail by id
    @SuppressLint("Range")
    public TarotCard getCardById(int cardId) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TB_TAROTCARD + " WHERE id = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(cardId)});

        if (cursor.moveToFirst()) {
            TarotCard card = new TarotCard();
            card.setId(cursor.getInt(cursor.getColumnIndex("id")));
            card.setName(cursor.getString(cursor.getColumnIndex("name")));
            return card;
        }
        return null;
    }

    //</editor-fold>

    //<editor-fold desc="Basic Element Methods">

    // Get all Elements
    @SuppressLint("Range")
    public List<Element> getListElement() {
        List<Element> result = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TB_ELEMENT;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Element e = new Element();
                e.setId(cursor.getInt(cursor.getColumnIndex("id")));
                e.setName(cursor.getString(cursor.getColumnIndex("name")));
                result.add(e);
            } while (cursor.moveToNext());
        }
        return result;
    }

    // Get Element by id
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

    //</editor-fold>
}
