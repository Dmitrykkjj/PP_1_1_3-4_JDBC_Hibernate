package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.DaoType;

import java.util.List;

public class UserServiceImpl implements UserService {

    public UserServiceImpl(DaoType daoType) {
        this.userDao = switch (daoType) {
            case JDBC -> new UserDaoJDBCImpl();
            case HIBERNATE -> new UserDaoHibernateImpl();
        };
    }

    private final UserDao userDao;

    public void createUsersTable() {
        userDao.createUsersTable();
    }

    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    public void saveUser(User user) {
        userDao.saveUser(user.getName(), user.getLastName(), user.getAge());
    }

    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}
