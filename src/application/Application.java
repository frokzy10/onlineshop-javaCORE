package application;

import application.dataHandlers.SupplierDataHandler;
import application.dataHandlers.UserDataHandler;
import application.entity.ProductEntity;
import application.entity.UserEntity;
import application.file_worker.UserFileWorker;
import application.serialize.Serialize;
import application.util.ScannerUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Application {
    private final Scanner scanner;
    private final UserDataHandler userDataHandler;
    private final SupplierDataHandler supplierDataHandler;

    public Application(Scanner scanner, UserDataHandler userDataHandler, SupplierDataHandler supplierDataHandler) {
        this.scanner = scanner;
        this.userDataHandler = userDataHandler;
        this.supplierDataHandler = supplierDataHandler;
    }

    public void run() {
        label:
        while (true) {
            System.out.print("Введите команду: ");
            String command = ScannerUtil.nextLine();

            switch (command) {
                case "exit":
                    System.out.print("Спасибо что пользовались приложением: ");
                    break label;
                case "get_all_users":
                    get_all_users();
                    break;
                case "register":
                    register();
                    break;
                case "logout":
                    logout();
                    break;
                case "user_by_param":
                    user_by_param();
                    break;
                case "login":
                    login();
                    break;
                case "help":
                    help();
                    break;
                case "user_info":
                    user_info();
                    break;
                case "add_item":
                    add_item();
                    break;
                case "info_items":
                    info_items();
                    break;
                case "update_item":
                    update_item();
                    break;
                default:
                    System.out.println("Такой команды нет");
                    break;
            }
        }
        System.out.println("Приложение закончилось");
    }

    //    Логгирование
    private void login() {
        System.out.print("Введите email пользоветеля чтобы войти: ");
        String email = scanner.next();
        System.out.print("Введите password пользователя: ");
        String password = scanner.next();
        userDataHandler.login(email, password);
    }

    private void logout() {
        userDataHandler.logout();
    }

    //    Инфо о user
    private void user_info() {
        System.out.println("Инфо о user");
        userDataHandler.user_info();
    }

    //    Логика Регистрации
    private void register() {
        System.out.println("Началась регистрация: ");
        UserEntity userEntity = new UserEntity();

        System.out.println("Введите имя пользователя: ");
        userEntity.setUsername(scanner.next());

        System.out.println("Введите email: ");
        userEntity.setEmail(scanner.next());
        System.out.println("Введите пароль: ");
        userEntity.setPassword(scanner.next());
        System.out.println("повторите ваш пароль: ");
        if (!userEntity.getPassword().equals(scanner.next())) {
            System.out.println("Пароли не совпадают. Ошибка регистрации (Начните с начала)");
            return;
        }
        userEntity.setSpeciality(chooseUserSpeciality());
        userDataHandler.userSave(userEntity);
    }

    public String chooseUserSpeciality() {
        String[] arr = {"Продавец", "Покупатель", "Поставщик"};

        System.out.println("Выберите вашу специальность:");

        for (int i = 0; i < arr.length; i++) {
            System.out.println((i + 1) + ". " + arr[i]);
        }

        int choice = -1;

        while (choice < 1 || choice > arr.length) {
            System.out.println("Введите номер специальности (от 1 до " + arr.length + "): ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice < 1 || choice > arr.length) {
                    System.out.println("Ошибка! Введите номер от 1 до " + arr.length);
                }
            } else {
                System.out.println("Ошибка! Введите корректное число.");
                scanner.next();
            }
        }

        return arr[choice - 1];
    }
    //    Логика посика user
    private void user_by_param() {
        System.out.print("Введите имя или id user чтобы получить его: ");
        System.out.println(userDataHandler.getUserByParam(scanner.next()));
        System.out.println(userDataHandler.getUserByParam(ScannerUtil.nextInt()));
    }

    //    Вывод всех user
    private void get_all_users() {
        System.out.println("Список пользователей в системе:");

        UserFileWorker fileWorker = new UserFileWorker("/Users/nurdinbakytbekov/Desktop/users.txt");
        List<UserEntity> list = fileWorker.readData();
        for (UserEntity userEntity : list) {
            System.out.println(userEntity);
        }
    }

    //    Логика магазина

    //    Логика продуктов
    private void add_item() {
        if (userDataHandler.loggedUser == null) {
            System.out.print("Войдите или зарегистрируйтесь в систему!");
            return;
        }

        if (!Objects.equals(userDataHandler.loggedUser.getSpeciality(), "Поставщик")) {
            System.out.print("Вы не поставщик, вы " + userDataHandler.loggedUser.getSpeciality());
            return;
        }

        ProductEntity p = new ProductEntity();

        System.out.println("Введите имя товара: ");
        String name = ScannerUtil.nextLine();
        System.out.println("Вы сделали имя " + name);

        System.out.println("Введите описание: ");
        String description = ScannerUtil.nextLine();
        System.out.println("Вы сделали описание " + description);

        double price = 0;
        while (true) {
            System.out.println("Введите цену товара:");
            String priceInput = ScannerUtil.nextLine();
            try {
                price = Double.parseDouble(priceInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректную цену (число).");
            }
        }

        p.setName(name);
        p.setPrice(price);
        p.setSupplier(userDataHandler.loggedUser);
        p.setDescription(description);
        supplierDataHandler.addItem(userDataHandler.loggedUser, p);
        System.out.println("Товар успешно добавлен!");
    }

    private void update_item() {
        if (!Objects.equals(userDataHandler.loggedUser.getSpeciality(), "Поставщик")) {
            System.out.println("Вы не поставщик, вы " + userDataHandler.loggedUser.getSpeciality());
            return;
        }
        if (userDataHandler.loggedUser == null) {
            System.out.println("Войдите или зарегистрируйтесь в систему!");
            return;
        }
        ProductEntity p = new ProductEntity();

        System.out.print("Введите id товара чтобы его обновить: ");
        String id = ScannerUtil.nextLine();
        p.setId(Integer.parseInt(id));

        System.out.println("Введите новое имя товара: ");
        String name = ScannerUtil.nextLine();
        p.setName(name);
        System.out.println("Введите новое описание товара: ");
        String description = ScannerUtil.nextLine();
        p.setDescription(description);
        System.out.println("Введите новую стоимость товара ");
        String priceInput = scanner.next();
        p.setPrice(Double.parseDouble(priceInput));

        p.setSupplier(userDataHandler.loggedUser);
        supplierDataHandler.updateItem(userDataHandler.loggedUser, p);
    }

    private void info_items() {
        if (userDataHandler.loggedUser == null) {
            System.out.println("Войдите или зарегистрируйтесь в систему!");
            return;
        }

        if (!Objects.equals(userDataHandler.loggedUser.getSpeciality(), "Поставщик")) {
            System.out.println("Вы не поставщик");
            return;
        }
        System.out.println(supplierDataHandler.getProductsInfo(userDataHandler.loggedUser));
    }

    //    help
    private void help() {
        System.out.println("""
                register - регистрация нового пользователя.
                login - авторизация пользователя.
                logout - выход из учетной записи.
                catalog - просмотр каталога товаров.
                search [запрос]: поиск товара по названию или описанию.
                add_to_cart [ID товара]: добавление товара в корзину.
                cart - просмотр корзины покупок.
                checkout - оформление заказа.
                orders - просмотр истории заказов (для покупателей).
                add_product - добавление нового товара (для продавцов).
                all_new_product - вывод новых товаров которые появились у поставщиков (Для продавца)
                update_product [ID товара] - редактирование товара (для продавцов).
                update_stock [ID товара] [новое количество]: обновление количества товара (для поставщиков).
                leave_review [ID товара] [оценка] [текст отзыва]: оставить отзыв о товаре (для покупателей).
                help - вывод списка команд с описание для каждого пользователя свой список команд
                """);
    }
}
