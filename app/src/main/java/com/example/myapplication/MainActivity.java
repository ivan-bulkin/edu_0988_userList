package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

//2021-08-21
//документация по RecyclerView находится здесь https://developer.android.com/guide/topics/ui/layout/recyclerview

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;//создаём переменную recyclerView, чтобы потом её найти на активности и использовать
    //RecyclerView это компонент для создания динамических списков в Android
    UserAdapter userAdapter;//
    ArrayList<String> userList = new ArrayList<>();//создаём массив пользователей userList, в нём будем хранить имена пользователей

    @Override
    //Метод, который срабатывает при создании приложения(при запуске программы)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < 100; i++) {//запускаем цикл, который создаст нам 100 имён пользователей
            userList.add("Пользователь " + i);//создаём имена пользователей вида: Пользователь 1, Пользователь 2, Пользователь 3 ... и кладём эти имена в массив userList
        }
        recyclerView = findViewById(R.id.recyclerView);//находим recyclerView на активности, чтобы можно было далее с ним работать
        //по такому же типу мы находим любые другие элементы, которые размещаем на активности: кнопки, контейнеры с текстом и т.д.
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        userAdapter = new UserAdapter(userList);
        recyclerView.setAdapter(userAdapter);
    }

    private class UserHolder extends RecyclerView.ViewHolder {
        TextView itemTextView;

        public UserHolder(LayoutInflater inflater, ViewGroup viewGroup) {
            super(inflater.inflate(R.layout.single_item, viewGroup, false));
            itemTextView = itemView.findViewById(R.id.itemTextView);
        }

        public void bind(String userName) {
            itemTextView.setText(userName);
        }
    }

    private class UserAdapter extends RecyclerView.Adapter<UserHolder> {
        ArrayList<String> users;

        public UserAdapter(ArrayList<String> users) {
            this.users = users;
        }

        @Override
        public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);//inflater - раздуватель :-)
            return new UserHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(UserHolder userHolder, int position) {
            String user = users.get(position);
            userHolder.bind(user);
        }

        @Override
        public int getItemCount() {
            return users.size();//сколько элементов показать на экране
        }
    }
}

