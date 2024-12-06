package application.dataHandlers;

import application.serialize.Serialize;
import application.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDataHandler {
    private List<UserEntity> usersList = new ArrayList<>();
    private UserEntity userEntity;

    public UserEntity userSave(UserEntity user) {
        Serialize serialize = new Serialize("/Users/nurdinbakytbekov/Desktop/users.txt");

        usersList = serialize.deserialize();
        if (usersList == null) {
            usersList = new ArrayList<>();
        }

        user.setId(generateUniqueId(usersList));
        usersList.add(user);
        serialize.serialize(usersList);

        return user;
    }
    public List<UserEntity> getUserByParam(String data) {
        Serialize serialize = new Serialize("/Users/nurdinbakytbekov/Desktop/users.txt");
        usersList = serialize.deserialize();

        if (usersList == null) {
            System.out.println("Список пуст");
            return null;
        }
        List<UserEntity> newListForUsers = new ArrayList<>();
        for (UserEntity user : usersList){
            if (isNumeric(data) && Objects.equals(user.getId(), Integer.parseInt(data)) || Objects.equals(user.getUsername(), data)) {
                newListForUsers.add(user);
            }
        }
        System.out.println("Ничего не найдено");
        return newListForUsers;
    }


    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private final int generateUniqueId(List<UserEntity> users){
        int newId = 0;
        boolean unique;
        do {
            unique = true;
            for (UserEntity user : users){
                if (user.getId() == newId){
                    unique = false;
                    newId++;
                    break;
                }
            }
        }while (!unique);
        return newId;
    }
}
