package dbService;

import dbService.dataSets.User;
import dbService.utils.FilePath;
import dbService.utils.PropertiesReader;
import org.hibernate.cfg.Configuration;
import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public final class DBConnector {
/*
    //?autoReconnect=true&useSSL=false - чтобы не ругался на ssl
    //useLegacyDatetimeCode=false&serverTimezone=UTC - чтобы не ругался на таймзону
*/
    private static Properties properties;

    private static class DBConnectorHolder{
        static final DBConnector HOLDER_INSTANCE = new DBConnector();
    }
    static{
        properties = PropertiesReader.getProperties(FilePath.CONNECTION_PROPERTIES);
    }

    public static DBConnector getInstance(){
        return DBConnectorHolder.HOLDER_INSTANCE;
    }

    private DBConnector(){

    }

    @SuppressWarnings("UnusedDeclaration")
    public Connection getConnection() {

        try {
            DriverManager.registerDriver((Driver) Class.forName(properties.getProperty("DB_CLASS_NAME")).newInstance());
            return DriverManager.getConnection(properties.getProperty("DB_CONNECTION_STRING"), properties.getProperty("USER"), properties.getProperty("PASS"));
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Configuration getConfigutarion() {

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);

        configuration.setProperty("hibernate.dialect", properties.getProperty("DIALECT"));
        configuration.setProperty("hibernate.connection.driver_class", properties.getProperty("DB_CLASS_NAME"));
        configuration.setProperty("hibernate.connection.url", properties.getProperty("DB_CONNECTION_STRING"));
        configuration.setProperty("hibernate.connection.username", properties.getProperty("USER"));
        configuration.setProperty("hibernate.connection.password", properties.getProperty("PASS"));
        configuration.setProperty("hibernate.show_sql", properties.getProperty("SHOW_SQL"));
        configuration.setProperty("hibernate.hbm2ddl.auto", properties.getProperty("HBN2DDL"));

        return configuration;

    }

    public EntityManager getEntityManager() {

        Map<String, Object> config = new HashMap<>();
        //Configuring JDBC properties
        config.put("javax.persistence.jdbc.url", properties.getProperty("DB_CONNECTION_STRING"));
        config.put("javax.persistence.jdbc.user", properties.getProperty("USER"));
        config.put("javax.persistence.jdbc.password", properties.getProperty("PASS"));
        config.put("javax.persistence.jdbc.driver", properties.getProperty("DB_CLASS_NAME"));
        //Hibernate properties
        config.put("hibernate.dialect", properties.getProperty("DIALECT"));
        config.put("hibernate.hbm2ddl.auto", properties.getProperty("HBN2DDL"));
        config.put("hibernate.show_sql", properties.getProperty("SHOW_SQL"));

        EntityManagerFactory entityManagerFactory = new HibernatePersistenceProvider().createContainerEntityManagerFactory(
                archiverPersistenceUnitInfo(),
                config);
        return entityManagerFactory.createEntityManager();

    }

    private PersistenceUnitInfo archiverPersistenceUnitInfo() {
        return new PersistenceUnitInfo() {
            @Override
            public String getPersistenceUnitName() {
                return "ApplicationPersistenceUnit";
            }

            @Override
            public String getPersistenceProviderClassName() {
                return "org.hibernate.jpa.HibernatePersistenceProvider";
            }

            @Override
            public PersistenceUnitTransactionType getTransactionType() {
                return PersistenceUnitTransactionType.RESOURCE_LOCAL;
            }

            @Override
            public DataSource getJtaDataSource() {
                return null;
            }

            @Override
            public DataSource getNonJtaDataSource() {
                return null;
            }

            @Override
            public List<String> getMappingFileNames() {
                return Collections.emptyList();
            }

            @Override
            public List<URL> getJarFileUrls() {
                try {
                    return Collections.list(this.getClass()
                            .getClassLoader()
                            .getResources(""));
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public URL getPersistenceUnitRootUrl() {
                return null;
            }

            @Override
            public List<String> getManagedClassNames() {
                return Collections.emptyList();
            }

            @Override
            public boolean excludeUnlistedClasses() {
                return false;
            }

            @Override
            public SharedCacheMode getSharedCacheMode() {
                return null;
            }

            @Override
            public ValidationMode getValidationMode() {
                return null;
            }

            @Override
            public Properties getProperties() {
                return new Properties();
            }

            @Override
            public String getPersistenceXMLSchemaVersion() {
                return null;
            }

            @Override
            public ClassLoader getClassLoader() {
                return null;
            }

            @Override
            public void addTransformer(ClassTransformer transformer) {
            }

            @Override
            public ClassLoader getNewTempClassLoader() {
                return null;
            }
        };
    }
}
