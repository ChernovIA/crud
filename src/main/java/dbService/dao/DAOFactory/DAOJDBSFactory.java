package dbService.dao.DAOFactory;

import dbService.dao.UserDAO.UsersDAO;
import dbService.dao.UserDAO.UsersDAOJDBCImpl;

public class DAOJDBSFactory implements DAOFactory {
    @Override
    public UsersDAO getUserDAO() {
        return new UsersDAOJDBCImpl();
    }
}
