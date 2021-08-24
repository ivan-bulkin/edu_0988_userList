package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ru.tinkoff.decoro.*;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class AddUserActivity extends AppCompatActivity {

    EditText userNameEditTextView;//поле ввода Имени Пользователя
    EditText userLastNameEditTextView;//поле ввода Фамилии Пользователя
    EditText phoneEditTextPhone;//поле ввода номера телефона Пользователя
    EditText phoneEditTextPhoneMask;//поле ввода номера телефона Пользователя по маске ввода от ru.tinkoff.decoro
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        setTitle(getResources().getText(R.string.app_name_add_user));//задаём заголовок окна
        userNameEditTextView = findViewById(R.id.userNameEditTextView);
        userLastNameEditTextView = findViewById(R.id.userLastNameEditTextView);
        phoneEditTextPhone = findViewById(R.id.phoneEditTextPhone);
        phoneEditTextPhoneMask = findViewById(R.id.phoneEditTextPhoneMask);
        addBtn = findViewById(R.id.addBtn);
        FormatWatcher formatWatcher = new MaskFormatWatcher(
                MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)//маска для номера телефона
        );
        formatWatcher.installOn(phoneEditTextPhoneMask);//тут аргументом может быть любой TextView(EditText)
        phoneEditTextPhoneMask.setText("+7");

        //нажатие кнопки Добавить
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddUserActivity.this, "Здесь скоро будет город-сад", Toast.LENGTH_SHORT).show();
            }
        });
    }
}