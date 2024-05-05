package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();
        userDaoHibernate.dropUsersTable();
        userDaoHibernate.createUsersTable();

        userDaoHibernate.saveUser("Ivan", "Ivanov", (byte) 22);
        userDaoHibernate.saveUser("Petr", "Petrov", (byte) 25);
        userDaoHibernate.saveUser("Alexey", "Alexeev", (byte) 30);
        userDaoHibernate.saveUser("Sergey", "Sergeev", (byte) 35);
        userDaoHibernate.removeUserById(1);
        userDaoHibernate.saveUser("Alexey", "Ivanov", (byte) 45);

        List<User> users = userDaoHibernate.getAllUsers();

        users.stream()
                .sorted(Comparator.comparing(User::getAge))
                .forEach(System.out::println);
    }
}
