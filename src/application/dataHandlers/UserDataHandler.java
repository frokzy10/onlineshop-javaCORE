package application.dataHandlers;

import application.serialize.Serialize;
import application.entity.UserEntity;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDataHandler {
    private List<UserEntity> usersList = new ArrayList<>();
    private UserEntity loggedUser = null;

    public UserEntity userSave(UserEntity user) {
        Serialize serialize = new Serialize("/Users/nurdinbakytbekov/Desktop/users.txt");

        usersList = serialize.deserialize();
        if (usersList == null) {
            usersList = new ArrayList<>();
        }
        for (UserEntity userEntity : usersList) {
            if (Objects.equals(userEntity.getEmail(), user.getEmail())) {
                System.out.println("Пользователь с таким email уже есть! Попробуйте другой");
                return null;
            }
        }

        user.setId(generateUniqueId(usersList));
        usersList.add(user);
        serialize.serialize(usersList);
        loggedUser = user;
        return user;
    }

    public void user_info(){
        if (loggedUser != null) {
            System.out.println("Username : " + loggedUser.getUsername());
            System.out.println("Email : " + loggedUser.getEmail());
            System.out.println("Speciality : " + loggedUser.getSpeciality());
        }else{
            System.out.println("Не найден");
        }
    }

    public List<UserEntity> getUserByParam(String data) {
        Serialize serialize = new Serialize("/Users/nurdinbakytbekov/Desktop/users.txt");
        usersList = serialize.deserialize();

        if (usersList == null) {
            System.out.println("Список пуст");
            return null;
        }
        List<UserEntity> newListForUsers = new ArrayList<>();
        for (UserEntity user : usersList) {
            if (isNumeric(data) && Objects.equals(user.getId(), Integer.parseInt(data)) || Objects.equals(user.getUsername(), data)) {
                newListForUsers.add(user);
            }
        }
        System.out.println("Ничего не найдено");
        return newListForUsers;
    }

    public UserEntity login(String email, String password) {
        Serialize serialize = new Serialize("/Users/nurdinbakytbekov/Desktop/users.txt");
        usersList = serialize.deserialize();

        for (UserEntity user : usersList) {
            if (Objects.equals(user.getEmail(), email) && Objects.equals(user.getPassword(), password)) {
                System.out.println("Вы успешно зашли на аккаунт: " + user.getEmail());
                loggedUser = user;
                return user;
            }
        }
        System.out.println("Неверный email или пароль!");
        return null;
    }

    //    Доп.методы для основной логики
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private final int generateUniqueId(List<UserEntity> users) {
        int newId = 0;
        boolean unique;
        do {
            unique = true;
            for (UserEntity user : users) {
                if (user.getId() == newId) {
                    unique = false;
                    newId++;
                    break;
                }
            }
        } while (!unique);
        return newId;
    }
}
