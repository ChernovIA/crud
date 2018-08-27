package dbService;

import dbService.dao.DAOFactoryBuilder;
import dbService.dao.UserDAO.UserDAOFactory;
import dbService.dao.UserDAO.UsersDAO;
import dbService.dataSets.Roles;
import dbService.dataSets.User;
import dbService.errorsEntity.StatusEntity;

import java.util.List;

public final class UserService {

    private UsersDAO usersDAO;

    public UserService() {
        DAOFactoryBuilder daoFactoryBuilder = DAOFactoryBuilder.getInstance();

        //usersDAO = (UsersDAO) daoFactoryBuilder.getDAO("UserDAO");
        usersDAO = UserDAOFactory.getInstance();
    }

    public StatusEntity createTable() {
        StatusEntity result = new StatusEntity();
        try {
            dropTable();
            usersDAO.createTable();
        }
        catch (DBException e){
            result.setError(e);
        }
        return result;
    }

    public StatusEntity dropTable() {
        StatusEntity result = new StatusEntity();
        try {
            usersDAO.dropTable();
        }
        catch (DBException e){
            result.setError(e);
        }
        return result;
    }

    public StatusEntity<User> getUser(long id) {
        StatusEntity<User> result = new StatusEntity<>();
        try {
            result.setData(usersDAO.get(id));
        }
        catch (DBException e){
            result.setError(e);
        }
        return result;
    }

    public StatusEntity<User> getUser(String login) {
        StatusEntity<User> result = new StatusEntity<>();
        try {
            result.setData(usersDAO.get(login));
        }
        catch (DBException e){
            result.setError(e);
        }
        return result;
    }

    public StatusEntity<String> addUser(User user) {
        StatusEntity<String> result = new StatusEntity<>();
        try {
            usersDAO.addUser(user);
            //result.setData(usersDAO.get(user.getLogin()).getLogin());
        }
        catch (DBException e){
            result.setError(e);
        }
        return result;
    }


    public StatusEntity<?> deleteUser(long id) {
        StatusEntity<?> result = new StatusEntity<>();
        try {
            usersDAO.deleteUser(id);
        }
        catch (DBException e){
            result.setError(e);
        }
        return result;
    }

    public StatusEntity<?> upDateUser(User user) {
        StatusEntity<?> result = new StatusEntity<>();
        try {
            usersDAO.upDateUser(user);
        }
        catch (DBException e){
            result.setError(e);
        }
        return result;
    }

    public StatusEntity<String> addTestUsers()  {
        StatusEntity<String> result = new StatusEntity<>();
        try {
            User uds1 = new User("Admin", "admin","Ilya Chernov", Roles.ADMIN);
            User uds2 = new User("Moderator", "mod");
            User uds3 = new User("JavaProger", "java");

            usersDAO.addUser(uds1);
            usersDAO.addUser(uds2);
            usersDAO.addUser(uds3);
        }
        catch (DBException e){
            result.setError(e);
        }
        return result;
    }

    public StatusEntity<List<User>> getUsersDataTable() {
        StatusEntity<List<User>> result = new StatusEntity<>();
        try {
            result.setData(usersDAO.getTable(0));
        }
        catch (DBException e){
            result.setError(e);
        }
        return result;
    }

}
