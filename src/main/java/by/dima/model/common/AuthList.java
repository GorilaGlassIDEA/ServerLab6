package by.dima.model.common;

import java.io.Serializable;

public enum AuthList implements Serializable {

    AUTHORIZATION, //Метка для пользователей, прошедших авторизацию в системе
    UNAUTHORIZED, //Метка для пользователей, непрошедших авторизацию в системе
    GET_STATUS, // Узнать статус пользователя
    IS_EXIST, // Пользователь уже существует
    NOT_EXIST, // Пользователь не существует
    REQUEST_REGISTER,// Запрос на регистрацию
    NONE
}
