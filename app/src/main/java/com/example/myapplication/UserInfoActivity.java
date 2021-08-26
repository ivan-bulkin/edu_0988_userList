package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserInfoActivity extends AppCompatActivity {
    private TextView userNameTextView;
    private TextView phoneTextView;
    private User user;
    private Button deleteBtn;
    private Button editUserDataBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        setTitle(getResources().getText(R.string.app_name_user_info));//задаём заголовок окна
        userNameTextView = findViewById(R.id.userNameTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        user = (User) getIntent().getSerializableExtra("user");
        userNameTextView.setText(user.getUserName() + "\n" + user.getUserLastName());
        phoneTextView.setText(user.getPhone());
        deleteBtn = findViewById(R.id.deleteBtn);
        editUserDataBtn = findViewById(R.id.editUserDataBtn);

        //нажатие кнопки Удалить Пользователя
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.getUuid();
                Users users = new Users(UserInfoActivity.this);
                users.deleteUser(user.getUuid());//удаляем пользователя
                onBackPressed();//нажатие кнопки назад
            }
        });

        //нажатие кнопки Редактировать Пользователя
        editUserDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //создаём и открываем активность редактирования данных пользователя
                Intent intent = new Intent(UserInfoActivity.this, UserEditActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                System.out.println("UserInfoActivity Пользователь " + user.getUserName() + " " + user.getUserLastName() + " " + user.getPhone());
            }
        });

    }
}