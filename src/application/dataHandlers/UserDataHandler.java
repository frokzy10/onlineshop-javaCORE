package application.dataHandlers;

import application.file_worker.UserFileWorker;
import application.entity.UserEntity;
import application.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDataHandler implements UserService {
    private List<UserEntity> usersList = new ArrayList<>();
    public UserEntity loggedUser = null;
    private int id;
    private UserFileWorker userFileWorker;


    public UserDataHandler() {
    }

    @Override
    public UserEntity userSave(UserEntity user) {
        user.setId(this.id++);
        userFileWorker = new UserFileWorker("/Users/nurdinbakytbekov/Desktop/users.txt");

        if (usersList == null) {
            usersList = new ArrayList<>();
        }
        for (UserEntity userEntity : usersList) {
            if (Objects.equals(userEntity.getEmail(), user.getEmail())) {
                System.out.println("Пользователь с таким email уже есть! Попробуйте другой");
                return null;
            }
        }
        usersList.add(user);
        userFileWorker.writeData(usersList);
        loggedUser = user;
        return user;
    }

    @Override
    public void logout() {
        System.out.println("Вы вышли из аккаунта");
        loggedUser = null;
    }

    @Override
    public void user_info() {
        if (loggedUser != null) {
            System.out.println("Username : " + loggedUser.getUsername());
            System.out.println("Email : " + loggedUser.getEmail());
            System.out.println("Speciality : " + loggedUser.getSpeciality());
        } else {
            System.out.println("Не найден");
        }
    }

    public List<UserEntity> getUserByParam(int id) {
        userFileWorker = new UserFileWorker("/Users/nurdinbakytbekov/Desktop/users.txt");
        usersList = userFileWorker.readData();

        if (usersList == null) {
            System.out.println("Список пуст");
            return null;
        }
        List<UserEntity> newListForUsers = new ArrayList<>();
        for (UserEntity user : usersList) {
            if (Objects.equals(user.getId(), id)) {
                newListForUsers.add(user);
                break;
            }
        }
        if (newListForUsers.isEmpty()){
            System.out.println("Ничего нету!");
            return null;
        }
        return newListForUsers;
    }
    public List<UserEntity> getUserByParam(String name) {
        userFileWorker = new UserFileWorker("/Users/nurdinbakytbekov/Desktop/users.txt");
        usersList = userFileWorker.readData();

        if (usersList == null) {
            System.out.println("Список пуст");
            return null;
        }
        List<UserEntity> newListForUsers = new ArrayList<>();
        for (UserEntity user : usersList) {
            if (Objects.equals(user.getUsername(), name)) {
                newListForUsers.add(user);
            }
        }
        if (newListForUsers.isEmpty()){
            System.out.println("Ничего нету!");
            return null;
        }
        return newListForUsers;
    }

    @Override
    public UserEntity login(String email, String password) {
        userFileWorker = new UserFileWorker("/Users/nurdinbakytbekov/Desktop/users.txt");
        List<UserEntity> usersData = userFileWorker.readData();

        if (usersData == null || usersData.isEmpty()) {
            System.out.println("Список пользователей пуст или данные не удалось загрузить.");
            return null;
        }

        for (UserEntity userData : usersData) {
            if (Objects.equals(userData.getEmail(), email) && Objects.equals(userData.getPassword(), password)) {
                System.out.println("Вы успешно ввошли в аккаунт: " + userData.getUsername());
                System.out.println("Данные аккаунта: " + userData);
                loggedUser = userData;
                return userData;
            }
        }

        System.out.println("Неверный email или пароль!");
        return null;
    }
}
