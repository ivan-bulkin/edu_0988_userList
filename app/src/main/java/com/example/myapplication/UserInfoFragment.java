package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class UserInfoFragment extends Fragment {
    private TextView userNameTextView;
    private TextView phoneTextView;
    private User user;
    private Button deleteBtn;
    private Button editUserDataBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        user = (User) bundle.getSerializable("user");
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = layoutInflater.inflate(R.layout.activity_user_info, viewGroup, false);
        getActivity().setTitle(R.string.app_name_user_info);//изменяем заголовок окна для фрагмента с данными пользователя
//        setContentView(R.layout.activity_user_info);
//        setTitle(getResources().getText(R.string.app_name_user_info));//задаём заголовок окна
        userNameTextView = view.findViewById(R.id.userNameTextView);
        phoneTextView = view.findViewById(R.id.phoneTextView);
//        user = (User) getIntent().getSerializableExtra("user");
        userNameTextView.setText(user.getUserName() + "\n" + user.getUserLastName());
        phoneTextView.setText(user.getPhone());
        deleteBtn = view.findViewById(R.id.deleteBtn);
        editUserDataBtn = view.findViewById(R.id.editUserDataBtn);

        //нажатие кнопки Удалить Пользователя
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Users users = new Users(getActivity());
                users.deleteUser(user.getUuid());//удаляем пользователя
                getActivity().onBackPressed();//возвращаемся на предыдущий фрагмент
            }
        });

        //нажатие кнопки Редактировать Пользователя
        editUserDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //создаём и открываем активность редактирования данных пользователя
                Intent intent = new Intent(getContext(), UserEditActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
//                System.out.println("UserInfoActivity Пользователь " + user.getUserName() + " " + user.getUserLastName() + " " + user.getPhone());
            }
        });
        return view;
    }
}
