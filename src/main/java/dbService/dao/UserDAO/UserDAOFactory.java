package dbService.dao.UserDAO;

import dbService.utils.FilePath;
import dbService.utils.PropertiesReader;

import java.util.Properties;

public class UserDAOFactory {

    private static Properties properties;

    static{
        properties = PropertiesReader.getProperties(FilePath.USER_DAO_PROPERTIES);
    }

    public static UsersDAO getInstance(){
        String value = properties.getProperty("init");

        UserDAOType type = UserDAOType.valueOf(value);

        switch (type){
            case HIBERNATE: return new UsersDAOHibernateImpl();
            case JPA: return new UsersDAOJPAImpl();
            default: return new UsersDAOJDBCImpl();
        }
    }
}
