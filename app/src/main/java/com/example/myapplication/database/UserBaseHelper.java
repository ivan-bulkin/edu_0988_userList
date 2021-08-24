package com.example.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;//очень нужно для апгрейда базы данных на телефоне у пользователя
    private static final String DATABASE_NAME = "userBase.db";//.db обязательно ставим

    public UserBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + UserDbSchema.UserTable.NAME + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserDbSchema.Cols.UUID + "," +
                UserDbSchema.Cols.USERNAME + "," +
                UserDbSchema.Cols.USERLASTNAME + "," +
                UserDbSchema.Cols.PHONE + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
