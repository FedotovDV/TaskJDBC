package jm.task.core.jdbc;



import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    // Создание таблицы User(ов)
    // Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
    // Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
    // Очистка таблицы User(ов)
    // Удаление таблицы


    private static List<User> users = new ArrayList<>();

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.dropUsersTable();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov", (byte) 33);
        System.out.println("User с именем – Ivan добавлен в базу данных");
        userService.saveUser("Petr", "Петров", (byte) 22);
        System.out.println("User с именем – Petr добавлен в базу данных");
        userService.saveUser("Федор", "Федоров", (byte) 44);
        System.out.println("User с именем – Федор добавлен в базу данных");
        userService.saveUser("Мария", "Ivanova", (byte) 35);
        System.out.println("User с именем – Мария добавлен в базу данных");

        users = userService.getAllUsers();
        System.out.println(users);
        userService.removeUserById(1);
        users.clear();
        users = userService.getAllUsers();
        System.out.println(users);
        userService.cleanUsersTable();
        users = userService.getAllUsers();
        System.out.println(users);
        userService.dropUsersTable();

    }
}

