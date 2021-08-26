package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

    RecyclerView recyclerView;//создаём переменную recyclerView, чтобы потом её найти на активности и использовать
    //RecyclerView это компонент для создания динамических списков в Android
    //добавить RecyclerView можно через File-->Project Structure и чтобы его найте надо в поиске вбить recyclerview маленькими буквами
    UserAdapter userAdapter;//объявляем класс userAdapter, чтобы отобраожать информацию о пользователям на экране(в recyclerView)
    //    ArrayList<String> userList = new ArrayList<>();//создаём массив пользователей userList, в нём будем хранить имена пользователей
    ArrayList<User> userList = new ArrayList<>();//создаём массив пользователей userList, в нём будем хранить имена пользователей
    Button addUserBtn;

    @Override
    //Метод, который срабатывает при создании приложения(при запуске программы)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//Выполняем наследование и переопределяем метод суперкласса
        setContentView(R.layout.activity_main);//R означает Ресурс. Берём внешний вид приложения из activity_main.xml
/*        for (int i = 0; i < 100; i++) {//запускаем цикл, который создаст нам 100 имён пользователей
            userList.add("Пользователь " + i);//создаём имена пользователей вида: Пользователь 1, Пользователь 2, Пользователь 3 ... и кладём эти имена в массив userList
        }*/
/*        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setUserName("Пользователь " + i);
            user.setUserLastName("Фамилия " + i);
            userList.add(user);
        }*/


        addUserBtn = findViewById(R.id.addUserBtn);
        recyclerView = findViewById(R.id.recyclerView);//находим recyclerView на активности, чтобы можно было далее с ним работать(находим его по идентификатору)
        //по такому же типу мы находим любые другие элементы, которые размещаем на активности: кнопки, контейнеры с текстом и т.д.
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));//для того, чтобы отображать информацию в recyclerView на экране, нам необходимо пользоваться LayoutManager. Для этого у recyclerView мы вызываем метод setLayoutManager. Мы выбрали именно LinearLayoutManager. Также указываем активность MainActivity.this
        //у recyclerView могут быть три(так в инструкции написано) разных менеджера компоновки:
        //LinearLayoutManager упорядочивает элементы в одномерном списке.(размещаем элементы друг под другом)
        //GridLayoutManager размещает все элементы в двухмерной сетке.
        //Если сетка расположена вертикально, GridLayoutManager пытается сделать все элементы в каждой строке одинаковой ширины и высоты, но разные строки могут иметь разную высоту.
        //Если сетка расположена горизонтально, GridLayoutManager пытается сделать так, чтобы все элементы в каждом столбце имели одинаковую ширину и высоту, но разные столбцы могут иметь разную ширину.
        //StaggeredGridLayoutManager аналогичен GridLayoutManager, но не требует, чтобы элементы в строке имели одинаковую высоту (для вертикальных сеток) или элементы в одном столбце имели одинаковую ширину (для горизонтальных сеток). В результате элементы в строке или столбце могут смещаться друг относительно друга.
        //в конструктор UserAdapter при создании объекта мы передаём userList(список пользователей)
        //нажатие кнопки Добавить Пользователя
        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerViewInit();
    }

    private void recyclerViewInit() {
        Users users = new Users(MainActivity.this);
        userList = users.getUserList();//получаем пользователей из класса Users
        userAdapter = new UserAdapter(userList);//для того, чтобы потобржать информацию на экране, мы используем UserAdapter. UserAdapter передаёт recyclerView всю нужную информацию для отображения
        recyclerView.setAdapter(userAdapter);//вызываем метод setAdapter для того, чтобы установить Adapter для нашего recyclerView
    }

    //объект UserHolder отвечает за создание отдельного элемента списка
    private class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemTextView;//создаём эту переменную, чтобы далее работать с элементом экрана itemTextView(TextView)
        User user;

        //реализуем конструктор UserHolder
        public UserHolder(LayoutInflater inflater, ViewGroup viewGroup) {
            super(inflater.inflate(R.layout.single_item, viewGroup, false));//указываем какой именно макет надо раздувать(в данном случае single_item)
            //single_item мы разработали отдельно, создав его в res-->layout(добавили TextView(поля для вывода) и divider(горизонтальная линия))
            itemTextView = itemView.findViewById(R.id.itemTextView);//находим по идентификатору элемент itemTextView, куда будем писать текст
            itemView.setOnClickListener(this);
        }

        public void bind(String userName, User user) {//метод, который устанавливает имя пользователя в элемент экрана itemTextView
            this.user = user;
            itemTextView.setText(userName);//собственно, здесь мы и передаём имя пользователя
        }

        @Override//клик по имени пользователя, открывает активность с данными пользователя
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }

    //создаём объект UserAdapter
    private class UserAdapter extends RecyclerView.Adapter<UserHolder> {
        ArrayList<User> users;//создаём массив users с именами пользователей в классе  UserAdapter

        public UserAdapter(ArrayList<User> users) {//конструктор UserAdapter принимает ArrayList<String>(список пользоватлелей)
            this.users = users;
        }

        @Override
        //RecyclerView вызывает этот метод(onCreateViewHolder) всякий раз, когда ему нужно создать новый ViewHolder. Метод создает и инициализирует объект ViewHolderи связанный с ним объект View, но не заполняет его содержимое - объект ViewHolder ещё не был привязан к конкретным данным.
        //создаём отдельные элементы нашего списка
        //вызывается столько раз, сколько элементов одновременно помещается на экране
        public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {//создаётся инфлюатор, который будет раздувать наш объект
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);//inflater - раздуватель :-)
            return new UserHolder(inflater, parent);//создаём объект UserHolder
        }

        @Override
        //RecyclerView вызывает этот метод(onBindViewHolder) для связывания ViewHolder с данными. Метод извлекает соответствующие данные и использует их для заполнения макета держателя представления. Например, если RecyclerView отображается список имен, метод может найти соответствующее имя в списке и заполнить TextView виджет держателя представления.
        public void onBindViewHolder(UserHolder userHolder, int position) {
            User user = users.get(position);//получает из списка пользователей пользователя по position 0,1,2,3 ..... Мы находим пользователя по этому индексу
            String userString = user.getUserName() + "\n" + user.getUserLastName();
            userHolder.bind(userString, user);//вызываем метод bind
        }

        @Override
        //RecyclerView вызывает этот метод(getItemCount), чтобы получить размер набора данных. Например, в приложении адресной книги это может быть общее количество адресов. RecyclerView использует это, чтобы определить, когда больше нет элементов, которые могут быть отображены.
        //узнаём сколько всего элементов надо отобразить на экране
        public int getItemCount() {//геттер, который получает сколько всегоо элементов надо отобразить на экране
            return users.size();//возвращает сколько элементов показать на экране
        }
    }
}

