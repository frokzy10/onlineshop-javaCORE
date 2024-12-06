package application;

import application.dataHandlers.UserDataHandler;
import application.entity.UserEntity;
import application.serialize.Serialize;

import java.util.List;
import java.util.Scanner;

public class Application {
    private Scanner scanner;
    private UserDataHandler userDataHandler;

    public Application(Scanner scanner, UserDataHandler userDataHandler) {
        this.scanner = scanner;
        this.userDataHandler = userDataHandler;
    }

    public void run() {
        System.out.println("Welcome");

        while (true) {
            System.out.println("Введите команду: ");
            String command = scanner.next();

            if (command.equals("exit")) {
                System.out.println("Спасибо что пользовались приложением: ");
                break;
            } else if (command.equals("get_all_users")) {
                get_all_users();
            } else if (command.equals("register")) {
                register();
            } else if (command.equals("user_by_param")) {
                user_by_param();
            } else System.out.println("Такой команды нет");
        }
        System.out.println("Приложение закончилось");
    }

    private void register() {
        System.out.println("Началась регистрация: ");
        UserEntity userEntity = new UserEntity();

        System.out.println("Введите имя пользователя");
        userEntity.setUsername(scanner.next());
        System.out.println("Введите email: ");
        userEntity.setEmail(scanner.next());
        System.out.println("Введите пароль: ");
        userEntity.setPassword(scanner.next());
        System.out.println("повторите ваш пароль");
        if (!userEntity.getPassword().equals(scanner.next())) {
            System.out.println("Пароли не совпадают. Ошибка регистрации (Начните с начала)");
            return;
        }
        userDataHandler.userSave(userEntity);
    }
    private void user_by_param(){
        System.out.println("Введите имя или id user чтобы получить его: ");
        String user = scanner.next();
        System.out.println(userDataHandler.getUserByParam(user));
    }

    private void get_all_users() {
        System.out.println("Список пользователей в системе: ");
        Serialize serialize = new Serialize("/Users/nurdinbakytbekov/Desktop/users.txt");

        List<UserEntity> userEntities = serialize.deserialize();
        if (userEntities == null || userEntities.isEmpty()) {
            System.out.println("Пользователей пока нет.");
        } else {
            userEntities.forEach(user -> System.out.println("ID: " + user.getId() + ", Username: " + user.getUsername() + ", Email: " + user.getEmail()  + ", Password: " + user.getPassword()));
        }
    }
}
