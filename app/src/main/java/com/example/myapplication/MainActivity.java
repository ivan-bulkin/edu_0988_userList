package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

//2021-08-21
//документация по RecyclerView находится здесь https://developer.android.com/guide/topics/ui/layout/recyclerview

public class MainActivity extends AppCompatActivity {//это наш класс MainActivity, в коротом лежит весь код программы(приложения)
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override//переопределяем метод onCreate
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment currentFragment = fragmentManager.findFragmentByTag("main_fragment");//при повороте экрана каждый раз создавался новый фрагмент со списком пользователей. Теперь это не делается
        if (currentFragment == null) {
            System.out.println("onCreate");
            Fragment fragment = new UserListFragment();
            fragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment, "main_fragment").commit();
        }
    }

    @Override//переопределяем метод нажатия кнопки Назад onBackPressed
    public void onBackPressed() {
        Fragment currentFragment = fragmentManager.findFragmentByTag("main_fragment");
        if (currentFragment != null && currentFragment.isVisible()) {
            super.onBackPressed();
        } else {
            Fragment fragment = new UserListFragment();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment, "main_fragment").commit();
        }
    }

    //метод, который вызывает фрагмент с данными пользователя
    public static void changeFragment(View view, User user) {
        //Получаем хостинговую активность
        FragmentActivity activity = (FragmentActivity) view.getContext();
        //Создаём  фрагмент менеджер
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        //Создаём сам фрагмент
        Fragment fragment = new UserInfoFragment();
        //надо положить пользователя, которого собираемся отобразить
        //создаём Bundle (это как коллекция)
        Bundle bundle = new Bundle();
        //Записываем пользователя в bundle
        bundle.putSerializable("user", user);
        //Добавляем bundle к фрагменту
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    //метод, который вызывает фрагмент с добавлением пользователя
    public static void addFragment(View view) {
        //Получаем хостинговую активность
        FragmentActivity activity = (FragmentActivity) view.getContext();
        //Создаём  фрагмент менеджер
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        //Создаём сам фрагмент
        Fragment fragment = new UserAddFragment();
        //Показываем сам фрагмент
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    //метод, который вызывает фрагмент с редактированием данных пользователя
    public static void editFragment(View view, User user) {
        //Получаем хостинговую активность
        FragmentActivity activity = (FragmentActivity) view.getContext();
        //Создаём  фрагмент менеджер
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        //Создаём сам фрагмент
        Fragment fragment = new UserEditFragment();
        //надо положить пользователя, которого собираемся отобразить
        //создаём Bundle (это как коллекция)
        Bundle bundle = new Bundle();
        //Записываем пользователя в bundle
        bundle.putSerializable("user", user);
        //Добавляем bundle к фрагменту
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }
}

