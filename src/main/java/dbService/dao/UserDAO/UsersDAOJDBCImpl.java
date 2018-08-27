package dbService.dao.UserDAO;

import dbService.DBConnector;
import dbService.DBException;
import dbService.dataSets.User;
import dbService.executor.Executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDAOJDBCImpl implements UsersDAO {

    private Executor executor;
    private Connection connection;

    public UsersDAOJDBCImpl()
    {
        this.connection = DBConnector.getInstance().getConnection();
        this.executor = new Executor(connection);
    }

    @Override
    public void createTable() throws DBException {
        try {
            executor.execUpdate("create table if not exists users (id bigint auto_increment,  login varchar(256) UNIQUE, password varchar(256), name varchar(256), role varchar(256),primary key (id))");
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void dropTable() throws DBException {
        try {
            executor.execUpdate("drop table users");
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public User get(long id) throws DBException {
        try {
            return executor.execQuery("select * from users where id=" + id, result -> {
                result.next();
                return getUsersDataSetFromResult(result);
            });
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public User get(String login) throws DBException {
        try {
            return executor.execQuery("select * from users where login='" + login + "'", result -> {
                result.next();
                return getUsersDataSetFromResult(result);
            });
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<User> getTable(final int limit) throws DBException {
        final List<User> users = new ArrayList<>();

        /*
        if(true){
            throw new DBException(new Exception("Error here!"));
        }*/

        try {
            return executor.execQuery("select * from users", result -> {
                while (result.next()){
                    users.add(getUsersDataSetFromResult(result));
                    if (limit > 0 && users.size() == limit){
                        break;
                    }
                }
                return users;
            });
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    private User getUsersDataSetFromResult(ResultSet result) throws SQLException {
        return new User(result.getLong(1), result.getString(2), result.getString(3), result.getString(4),result.getString(5));
    }

    @Override
    public void addUser(User user) throws DBException {
        try {
            executor.execUpdate("insert into users (login,password,name,role) values ('" + user.getLogin() + "','"
                    + user.getPassword() + "','"
                    + user.getName() + "','"
                    + user.getRole() + "')");
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void upDateUser(User user) throws DBException {
        try {
            executor.execUpdate("UPDATE users Set password = '" + user.getPassword() + "', name ='" + user.getName() + "', role ='" + user.getRole() + "' where id = '" + user.getId() + "'");
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void deleteUser(long id) throws DBException {
        try {
            executor.execUpdate("DELETE from users where id = '" + id + "'");
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
