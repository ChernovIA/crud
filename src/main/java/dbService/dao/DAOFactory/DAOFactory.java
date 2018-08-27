package dbService.dao.DAOFactory;

import dbService.dao.UserDAO.UsersDAO;

public interface DAOFactory {

    UsersDAO getUserDAO();
}
