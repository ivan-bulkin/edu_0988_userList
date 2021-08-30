package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.database.UserBaseHelper;
import com.example.myapplication.database.UserDbSchema;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

public class Users {
    private ArrayList<User> userList;
    private SQLiteDatabase database;
    private Context context;

    public Users(Context context) {
        this.context = context.getApplicationContext();
        this.database = new UserBaseHelper(this.context).getWritableDatabase();//создали подключение к БД SQLiteDatabase
    }

    //метод редактирования пользователя. Сохраняем данные пользователя
    public void updateUser(User user) {
        ContentValues values = getContentValues(user);
        String stringUuid = user.getUuid().toString();
        database.update(UserDbSchema.UserTable.NAME, values, UserDbSchema.Cols.UUID + "=?", new String[]{stringUuid});
    }

    //метод удаления пользователя
    public void deleteUser(UUID uuid) {
//        database.delete(UserDbSchema.UserTable.NAME, UserDbSchema.Cols.UUID + "='" + uuid + "'", null);
        String stringUuid = uuid.toString();
        //так делать правильнее, чтобы программу не взломали передавая всякие запросы в виде параметров
        database.delete(UserDbSchema.UserTable.NAME, UserDbSchema.Cols.UUID + "=?", new String[]{stringUuid});
    }

    //метод добавления мользователя в базу данных
    public void addUser(User user) {

/*         Runnable runnable = new Runnable() {
           String host = "http://0988.vozhzhaev.ru/handlerAddUser.php?name=" + user.getUserName() + "&lastname=" + user.getUserLastName() + "&phone=" + user.getPhone() + "&uuid=" + user.getUuid().toString();

            @Override
            public void run() {
                try {
                    URL url = new URL(host);//http://0988.vozhzhaev.ru/handlerAddUser.php  http://m97726el.beget.tech/handlerAddUser.php  http://m97726el.beget.tech/file.txt  http://www.dolo.ru/0988/handlerAddUser.php
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(is);
                    int i;
                    StringBuilder result = new StringBuilder();
                    while ((i = reader.read()) != -1) {
                        result.append((char) i);
                    }
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(runnable);
        t.start();*/
/*        try {
            System.out.println("addUser");
            URL url = new URL("http://m97726el.beget.tech/handlerAddUser.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream is = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);
            int i;
            StringBuilder result = new StringBuilder();
            while ((i=reader.read()) != -1){
                result.append((char)i);
            }
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
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

    //метод чтения всех данных из таблицы для SQLiteDatabase Android, которая создаётся и есть в телефоне
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
        return this.userList;
    }
}
