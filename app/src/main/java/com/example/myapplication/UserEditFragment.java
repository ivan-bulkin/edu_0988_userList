package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class UserEditFragment extends Fragment {

    EditText userNameEditTextView;//поле ввода Имени Пользователя
    EditText userLastNameEditTextView;//поле ввода Фамилии Пользователя
    EditText phoneEditTextPhoneMask;//поле ввода номера телефона Пользователя по маске ввода от ru.tinkoff.decoro
    Button saveBtn;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        user = (User) bundle.getSerializable("user");//получаем данные о пользователе, чтобы потом заполнить этими данными фрагмент Редактировать данные
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = layoutInflater.inflate(R.layout.activity_user_edit, viewGroup, false);
        getActivity().setTitle(R.string.app_name_user_edit);//изменяем заголовок окна для фрагмента с редактированием данных о пользователе
        userNameEditTextView = view.findViewById(R.id.userNameEditTextView);
        userLastNameEditTextView = view.findViewById(R.id.userLastNameEditTextView);
        phoneEditTextPhoneMask = view.findViewById(R.id.phoneEditTextPhoneMask);
        saveBtn = view.findViewById(R.id.saveBtn);
        FormatWatcher formatWatcher = new MaskFormatWatcher(
                MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)//маска для номера телефона
        );
        formatWatcher.installOn(phoneEditTextPhoneMask);//тут аргументом может быть любой TextView(EditText)
        phoneEditTextPhoneMask.setText("+7");//делаем это, чтобы сразу отображать +7
        userNameEditTextView.setText(user.getUserName());
        userLastNameEditTextView.setText(user.getUserLastName());
        phoneEditTextPhoneMask.setText(user.getPhone());

        //нажатие кнопки Сохранить
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Нельзя так отредактировать пользователя, что у него не останется ни имени, ни фамилии. Должно быть задано или имя или фамилия. Делаем проверку и, если нет ни имени, ни фамилии, дальше не идём
                //Также не дадим сохранить новые данные Пользователя, если в поле Имя и Фамилия навбивали только пробелы
                if (userNameEditTextView.getText().toString().trim().equals("") && userLastNameEditTextView.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Заполните поле Имя или Фамилия", Toast.LENGTH_SHORT).show();
                    return;
                }
                user.setUserName(userNameEditTextView.getText().toString());
                user.setUserLastName(userLastNameEditTextView.getText().toString());
                //используем тернарный оператор: если номер телефона всё-таки не введён, введено только "+7 (", то ничего в поле номера телефона не сохраняем
                //замечание: номер телефона хранится в БД в виде +7 (999) 999-99-99, т.е. есть куча пробелов, скобок, тире и знак плюс. Я-бы всё-таки хранил в базе только цифры, что 100% ускорит поиск по поле номер телефона, но сейчас это не цель работы. Хотя, надо сделать всего-лишь два преобразования: когда сохраянем номер телефона надо удалить не нужные символы и когда показываем номер телефона надо добавить не нужные символы для красоты
                user.setPhone(phoneEditTextPhoneMask.getText().toString().equals("+7 (") ? "" : phoneEditTextPhoneMask.getText().toString());
                Users users = new Users(getActivity());
                users.updateUser(user);//сохраняем данные пользователя
                Toast.makeText(getActivity(), "Сохранено", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
