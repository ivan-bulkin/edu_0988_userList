package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    //    EditText phoneEditTextPhone;//поле ввода номера телефона Пользователя
    EditText phoneEditTextPhoneMask;//поле ввода номера телефона Пользователя по маске ввода от ru.tinkoff.decoro
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        setTitle(getResources().getText(R.string.app_name_add_user));//задаём заголовок окна
        userNameEditTextView = findViewById(R.id.userNameEditTextView);
        userLastNameEditTextView = findViewById(R.id.userLastNameEditTextView);
//        phoneEditTextPhone = findViewById(R.id.phoneEditTextPhone);
        phoneEditTextPhoneMask = findViewById(R.id.phoneEditTextPhoneMask);
        addBtn = findViewById(R.id.addBtn);
        FormatWatcher formatWatcher = new MaskFormatWatcher(
                MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)//маска для номера телефона
        );
        formatWatcher.installOn(phoneEditTextPhoneMask);//тут аргументом может быть любой TextView(EditText)
        phoneEditTextPhoneMask.setText("+7");//делаем это, чтобы сразу отображать +7
//        phoneEditTextPhone.addTextChangedListener(new TextWatcher() { int length_before = 0; @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { length_before = s.length(); } @Override public void onTextChanged(CharSequence s, int start, int before, int count) { } @Override public void afterTextChanged(Editable s) { if (length_before < s.length()) { if (s.length() == 3 || s.length() == 7) s.append("-"); if (s.length() > 3) { if (Character.isDigit(s.charAt(3))) s.insert(3, "-"); } if (s.length() > 7) { if (Character.isDigit(s.charAt(7))) s.insert(7, "-"); } } } });
//        EditText inputField = (EditText) findViewById(R.id.phoneEditTextPhone); inputField.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
//        phoneEditTextPhone.addTextChangedListener(textWatcher); } TextWatcher textWatcher = new TextWatcher() { private boolean mFormatting; // this is a flag which prevents the stack overflow. private int mAfter; @Override public void onTextChanged(CharSequence s, int start, int before, int count) { // nothing to do here.. } //called before the text is changed... @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { //nothing to do here... mAfter = after; // flag to detect backspace.. } @Override public void afterTextChanged(Editable s) { // Make sure to ignore calls to afterTextChanged caused by the work done below if (!mFormatting) { mFormatting = true; // using US or RU formatting... if(mAfter!=0) // in case back space ain't clicked... { String num =s.toString(); String data = PhoneNumberUtils.formatNumber(num, "RU"); if(data!=null) { s.clear(); s.append(data); Log.i("Number", data);//8 (999) 123-45-67 or +7 999 123-45-67 } } mFormatting = false; } } };
//после некоторого времени изысканий, как сделать маску ввода телефона для обычного EditText принял решение использовать всё-таки метод от ru.tinkoff.decoro
//есть аналогичный метод MaskedEditText от compile 'ru.egslava:MaskedEditText:1.0.5' https://github.com/egslava/edittext-mask, но у меня же уже есть ru.tinkoff.decoro

        //нажатие кнопки Добавить
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(AddUserActivity.this, "Здесь скоро будет город-сад", Toast.LENGTH_SHORT).show();
                //Нельзя создавать абсолютно пустых пользователей. Должно быть задано или имя или фамилия. Делаем проверку и, если нет ни имени, ни фамилии, дальще не идём
                //Также не дадим создать Пользователя, если в поле Имя и Фамилия навбивали только пробелы
                if (userNameEditTextView.getText().toString().trim().equals("") && userLastNameEditTextView.getText().toString().trim().equals("")) {
                    Toast.makeText(AddUserActivity.this, "Заполните поле Имя или Фамилия", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = new User();
                user.setUserName(userNameEditTextView.getText().toString());
                user.setUserLastName(userLastNameEditTextView.getText().toString());
//                user.setPhone(phoneEditTextPhone.getText().toString());
                //используем тернарный оператор: если номер телефона всё-таки не введён, введено только "+7 (", то ничего в поле номера телефона не сохраняем
                user.setPhone(phoneEditTextPhoneMask.getText().toString().equals("+7 (") ? "" : phoneEditTextPhoneMask.getText().toString());
//                System.out.println(phoneEditTextPhoneMask.getText().toString());
                Users users = new Users(AddUserActivity.this);
                users.addUser(user);
                onBackPressed();//нажатие кнопки назад
            }
        });
    }
}