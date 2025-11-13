package com.example.tarottales.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.tarottales.Model.TarotCard;

import java.util.ArrayList;
import java.util.List;

public class TarotCardDAO extends DBContext {

    public TarotCardDAO(@Nullable Context context) {
        super(context);
    }

    //<editor-fold desc="Basic TarotCard Methods">

    // Lấy danh sách tất cả TarotCard
    @SuppressLint("Range")
    public List<TarotCard> getAllCards() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TB_TAROTCARD;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, null);

        List<TarotCard> cards = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                TarotCard card = new TarotCard();
                card.setId(cursor.getInt(cursor.getColumnIndex("id")));
                card.setName(cursor.getString(cursor.getColumnIndex("name")));
                cards.add(card);
            } while (cursor.moveToNext());
        }
        return cards;
    }

    // Lấy chi tiết TarotCard theo id
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
}
