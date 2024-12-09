package application;

import application.dataHandlers.SupplierDataHandler;
import application.dataHandlers.UserDataHandler;
import application.entity.ProductEntity;
import application.entity.UserEntity;
import application.serialize.Serialize;

import java.sql.SQLOutput;
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
        System.out.println("Welcome");

        label:
        while (true) {
            System.out.println("Введите команду: ");
            String command = scanner.next();

            switch (command) {
                case "exit":
                    System.out.println("Спасибо что пользовались приложением: ");
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
        System.out.println("Введите email пользоветеля чтобы войти");
        String email = scanner.next();
        System.out.println("Введите password пользователя");
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
        userEntity.setSpeciality(chooseUserSpeciality());
        userDataHandler.userSave(userEntity);
    }

    private String chooseUserSpeciality() {
        List<String> newListForUsers = new ArrayList<>(List.of("Продавец", "Покупатель", "Поставщик"));

        for (int i = 0; i < newListForUsers.size(); i++) {
            System.out.println((i + 1) + ". " + newListForUsers.get(i));
        }
        System.out.println("Введите номер специальности:");

        while (true) {
            try {
                int choice = Integer.parseInt(scanner.next());
                if (choice >= 1 && choice <= newListForUsers.size()) {
                    return newListForUsers.get(choice - 1);
                } else {
                    System.out.println("Выберите номер от 1 до " + newListForUsers.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число!");
            }
        }
    }

    //    Логика посика user
    private void user_by_param() {
        System.out.println("Введите имя или id user чтобы получить его: ");
        String user = scanner.next();
        System.out.println(userDataHandler.getUserByParam(user));
    }

    //    Вывод всех user
    private void get_all_users() {
        System.out.println("Список пользователей в системе: ");
        Serialize serialize = new Serialize("/Users/nurdinbakytbekov/Desktop/users.txt");

        List<UserEntity> userEntities = serialize.deserialize();
        if (userEntities == null || userEntities.isEmpty()) {
            System.out.println("Пользователей пока нет.");
        } else {
            userEntities.forEach(user -> System.out.println("ID: " + user.getId() + ", Username: " + user.getUsername() + ", Email: " + user.getEmail() + ", Password: " + user.getPassword() + ", Speciality: " + user.getSpeciality()));
        }
    }

//    Логика магазина

//    Логика продуктов
    private void add_item() {
        if (userDataHandler.loggedUser == null) {
            System.out.println("Войдите или зарегистрируйтесь в систему!");
            return;
        }

        if (!Objects.equals(userDataHandler.loggedUser.getSpeciality(), "Поставщик")) {
            System.out.println("Вы не поставщик, вы " + userDataHandler.loggedUser.getSpeciality());
            return;
        }

        ProductEntity p = new ProductEntity();

        System.out.println("Введите имя товара: ");
        String name = scanner.next();
        p.setName(name);

        System.out.println("Введите описание: ");
        String description = scanner.next();
        p.setDescription(description);

        boolean validPrice = false;
        double price = 0.0;
        while (!validPrice) {
            System.out.println("Введите цену товара: ");
            String priceInput = scanner.next();
            try {
                price = Double.parseDouble(priceInput);
                validPrice = true;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректную цену (число).");
            }
        }

        p.setPrice(price);
        p.setSupplier(userDataHandler.loggedUser);

        supplierDataHandler.addItem(userDataHandler.loggedUser, p);
        System.out.println("Товар успешно добавлен!");
    }
    private void update_item(){
        if (!Objects.equals(userDataHandler.loggedUser.getSpeciality(), "Поставщик")) {
            System.out.println("Вы не поставщик, вы " + userDataHandler.loggedUser.getSpeciality());
            return;
        }
        if (userDataHandler.loggedUser == null) {
            System.out.println("Войдите или зарегистрируйтесь в систему!");
            return;
        }
        ProductEntity p = new ProductEntity();

        System.out.println("Введите id товара чтобы его обновить: ");
        String id = scanner.next();
        p.setId(Integer.parseInt(id));

        System.out.println("Введите новое имя товара: ");
        String name = scanner.next();
        p.setName(name);
        System.out.println("Введите новое описание товара: ");
        String description = scanner.next();
        p.setDescription(description);
        System.out.println("Введите новую стоимость товара ");
        String priceInput = scanner.next();
        p.setPrice(Double.parseDouble(priceInput));

        p.setSupplier(userDataHandler.loggedUser);
        supplierDataHandler.updateItem(userDataHandler.loggedUser,p);
    }

    private void info_items(){
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
