package application.dataHandlers;

import application.serialize.Serialize;
import application.entity.UserEntity;
import application.service.UserService;
import application.util.GenerateUniqueClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDataHandler implements UserService {
    private List<UserEntity> usersList = new ArrayList<>();
    public UserEntity loggedUser = null;


    @Override
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
        int id = GenerateUniqueClass.generateId(usersList,UserEntity::getId);
        user.setId(id);
        usersList.add(user);
        serialize.serialize(usersList);
        loggedUser = user;
        return user;
    }
    @Override
    public void logout(){
        System.out.println("Вы вышли из аккаунта");
        loggedUser = null;
    }
    @Override
    public void user_info(){
        if (loggedUser != null) {
            System.out.println("Username : " + loggedUser.getUsername());
            System.out.println("Email : " + loggedUser.getEmail());
            System.out.println("Speciality : " + loggedUser.getSpeciality());
        }else{
            System.out.println("Не найден");
        }
    }
    @Override
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

    @Override
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
}
