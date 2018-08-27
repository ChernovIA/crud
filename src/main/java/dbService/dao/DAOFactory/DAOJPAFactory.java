package dbService.dao.DAOFactory;

import dbService.dao.UserDAO.UsersDAO;
import dbService.dao.UserDAO.UsersDAOJPAImpl;

public class DAOJPAFactory implements DAOFactory {
    @Override
    public UsersDAO getUserDAO() {
        return new UsersDAOJPAImpl();
    }
}
