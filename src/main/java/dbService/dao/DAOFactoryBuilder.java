package dbService.dao;

import dbService.utils.FilePath;
import dbService.utils.PropertiesReader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DAOFactoryBuilder {

    private static DAOFactoryBuilder daoFactoryBuilder = null;

    private Map<String, Class> mapFactory;
    private Map<String, DAO> mapCache;

    private DAOFactoryBuilder() {
        mapFactory = new HashMap<>();
        mapCache = new HashMap<>();
        Properties properties = PropertiesReader.getProperties(FilePath.DAO_PROPERTIES);

        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String param = (String) entry.getKey();
            if (param.toUpperCase().contains("DAO")) {
                try {
                    mapFactory.put(param, Class.forName((String) entry.getValue()));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static DAOFactoryBuilder getInstance() {
        if (daoFactoryBuilder == null) {
            daoFactoryBuilder = new DAOFactoryBuilder();
        }

        return daoFactoryBuilder;
    }

    public DAO getDAO(String interfaceName) {
        try {
            DAO obj = mapCache.getOrDefault(interfaceName, null);

            if (obj == null) {
                Class factory = mapFactory.get(interfaceName);
                Method method = factory.getDeclaredMethod("get"+interfaceName);
                Object daoFactory = factory.newInstance();
                obj = (DAO) method.invoke(daoFactory);

                mapCache.put(interfaceName,obj);
            }

            return obj;

        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
