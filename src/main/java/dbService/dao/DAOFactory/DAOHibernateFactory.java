package dbService.dao.DAOFactory;

import dbService.dao.UserDAO.UsersDAO;
import dbService.dao.UserDAO.UsersDAOHibernateImpl;

public class DAOHibernateFactory implements DAOFactory {
    @Override
    public UsersDAO getUserDAO() {
        return new UsersDAOHibernateImpl();
    }
}
