package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.database.UserBaseHelper;
import com.example.myapplication.database.UserDbSchema;

import java.util.ArrayList;
import java.util.UUID;

public class Users {
    private ArrayList<User> userList;
    private SQLiteDatabase database;
    private Context context;

    public Users(Context context) {
        this.context = context.getApplicationContext();
        this.database = new UserBaseHelper(this.context).getWritableDatabase();//создали подключение к БД
    }

    //метод редактирования пользователя
    public void updateUser(User user) {

    }

    //метод удаления пользователя
    public void deleteUser(UUID uuid) {
        database.delete(UserDbSchema.UserTable.NAME, UserDbSchema.Cols.UUID + "='" + uuid + "'", null);
//        System.out.println(uuid);
    }

    //метод добавления мользователя в базу данных
    public void addUser(User user) {
        ContentValues values = getContentValues(user);
        database.insert(UserDbSchema.UserTable.NAME, null, values);
    }

    private static ContentValues getContentValues(User user) {//значения передаются изолированно от команды
        ContentValues values = new ContentValues();
        values.put(UserDbSchema.Cols.UUID, user.getUuid().toString());
        values.put(UserDbSchema.Cols.USERNAME, user.getUserName());
        values.put(UserDbSchema.Cols.USERLASTNAME, user.getUserLastName());
        values.put(UserDbSchema.Cols.PHONE, user.getPhone());
        return values;
    }

    private UserCursorWrapper queryUsers() {
        Cursor cursor = database.query(UserDbSchema.UserTable.NAME, null, null, null, null, null, null);
        return new UserCursorWrapper(cursor);
    }

    public ArrayList<User> getUserList() {
        this.userList = new ArrayList<>();
        UserCursorWrapper cursorWrapper = queryUsers();
        try {
            cursorWrapper.moveToFirst();//возвращаем курсор на первую строчку БД
            while (!cursorWrapper.isAfterLast()) {
                User user = cursorWrapper.getUser();
                userList.add(user);
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }
/*        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setUserName("Пользователь " + i);
            user.setUserLastName("Фамилия " + i);
            userList.add(user);
        }*/
        return this.userList;
    }
}
