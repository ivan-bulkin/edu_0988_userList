package com.example.myapplication;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private String userName;
    private String userLastName;
    private String phone;
    private UUID uuid;//уникальный идентификатор, который завязан на времени, которое всегда идёт вперёд

    public User(UUID uuid) {
        this.uuid = uuid;
    }
    public User() {//конструктор, который создаёт уникальный UUID, а уже потом мы добавляем остальные данные для пользователя
        this.uuid = UUID.randomUUID();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UUID getUuid() {
        return uuid;
    }

}
