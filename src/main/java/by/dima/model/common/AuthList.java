package by.dima.model.common;

import java.io.Serializable;

public enum AuthList implements Serializable {

    AUTHORIZATION, //Метка для пользователей, прошедших авторизацию в системе
    UNAUTHORIZED, //Метка для пользователей, непрошедших авторизацию в системе
    UNAUTHENTICATED, //Метка для пользователей, не зарегистрированных в системе
}
