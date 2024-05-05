package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.util.DaoType;
import jm.task.core.jdbc.util.Util;

import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserService userService = Util.userServiceOf(DaoType.HIBERNATE);

        userService.dropUsersTable();
        userService.createUsersTable();

        List<User> userList = List.of(
                User.builder()
                        .name("Ivan")
                        .lastName("Ivanov")
                        .age(22)
                        .build(),
                User.builder()
                        .name("Petr")
                        .lastName("Petrov")
                        .age(25)
                        .build(),
                User.builder()
                        .name("Alexey")
                        .lastName("Alexeev")
                        .age(30)
                        .build(),
                User.builder()
                        .name("Sergey")
                        .lastName("Sergeev")
                        .age(30)
                        .build()
        );
        userList.forEach(userService::saveUser);
        userService.removeUserById(userList.get(1).getId());
        userService.saveUser(
                User.builder()
                        .name("Alexey")
                        .lastName("Ivanov")
                        .age(45)
                        .build()
        );

        List<User> users = userService.getAllUsers();

        users.stream()
                .sorted(Comparator.comparing(User::getAge))
                .forEach(System.out::println);
    }
}
