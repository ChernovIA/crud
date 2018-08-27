package dbService.dao.UserDAO;

import dbService.DBException;
import dbService.dao.DAO;
import dbService.dataSets.User;

import java.util.List;

public interface UsersDAO extends DAO {


    void createTable() throws DBException;

    void dropTable() throws DBException;

    User get(long id) throws DBException;

    User get(String login) throws DBException;

    List<User> getTable(int limit) throws DBException;

    void addUser(User user) throws DBException;

    void upDateUser(User user) throws DBException;

    void deleteUser(long id) throws DBException;
}
