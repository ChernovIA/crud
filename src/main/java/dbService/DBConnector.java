package dbService;

import dbService.dataSets.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.*;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class DBConnector {

    private static final String DB_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DB_CONNECTION_STRING = "jdbc:mysql://localhost:3306/UserDataBase?autoReconnect=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";

    //?autoReconnect=true&useSSL=false - чтобы не ругался на ssl
    //useLegacyDatetimeCode=false&serverTimezone=UTC - чтобы не ругался на таймзону
    private static final String DB_PARAMETRIES_STRING = "?autoReconnect=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USER = "sa";
    private static final String PASS = "saPass";

    private static final String DIALECT = "org.hibernate.dialect.MySQLDialect";
    private static final String HBN2DDL = "update";
    private static final String SHOW_SQL = "true";
    @SuppressWarnings("UnusedDeclaration")
    public static Connection getMysqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName(DB_CLASS_NAME).newInstance());

            /*StringBuilder url = new StringBuilder();
            url.
                    append(DB_CONNECTION_STRING).
                    append(DB_PARAMETRIES_STRING);*/

            Connection connection = DriverManager.getConnection(DB_CONNECTION_STRING, USER, PASS);
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Configuration getMysqlConfigutarionHibernate() {

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);

        configuration.setProperty("hibernate.dialect", DIALECT);
        configuration.setProperty("hibernate.connection.driver_class", DB_CLASS_NAME);
        configuration.setProperty("hibernate.connection.url", DB_CONNECTION_STRING);
        configuration.setProperty("hibernate.connection.username", USER);
        configuration.setProperty("hibernate.connection.password", PASS);
        configuration.setProperty("hibernate.show_sql", SHOW_SQL);
        configuration.setProperty("hibernate.hbm2ddl.auto", HBN2DDL);
        return configuration;

    }
    public static EntityManagerFactory getMysqlConfigutarionJPA_test() {

        Map<String,Object> config = new HashMap<>();
        //Configuring JDBC properties
        config.put("hibernate.connection.url", DB_CONNECTION_STRING);
        config.put("hibernate.connection.user", USER);
        config.put("hibernate.connection.password", PASS);
        config.put("hibernate.connection.driver_class", DB_CLASS_NAME);
        //Hibernate properties
        config.put("hibernate.dialect", DIALECT);
        config.put("hibernate.hbm2ddl.auto", HBN2DDL);
        config.put("hibernate.show_sql", SHOW_SQL);

        Persistence.getPersistenceUtil();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernateCRUD",config);

        return emf;

    }

    public static EntityManagerFactory getMysqlConfigutarionJPA() {

        Map<String,Object> config = new HashMap<>();
        //Configuring JDBC properties
        config.put("javax.persistence.jdbc.url", DB_CONNECTION_STRING);
        config.put("javax.persistence.jdbc.user", USER);
        config.put("javax.persistence.jdbc.password", PASS);
        config.put("javax.persistence.jdbc.driver", DB_CLASS_NAME);
        //Hibernate properties
        config.put("hibernate.dialect", DIALECT);
        config.put("hibernate.hbm2ddl.auto", HBN2DDL);
        config.put("hibernate.show_sql", SHOW_SQL);

        EntityManagerFactory entityManagerFactory = new HibernatePersistenceProvider().createContainerEntityManagerFactory(
                archiverPersistenceUnitInfo(),
                config);

        return entityManagerFactory;

    }

    public static void printConnectionInfo(Connection connection) {

        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            System.out.println("DB name - " + databaseMetaData.getDatabaseProductName());
            System.out.println("DB version - " + databaseMetaData.getDatabaseProductVersion());
            System.out.println("DB driver - " + databaseMetaData.getDriverName());
            System.out.println("DB driver version - " + databaseMetaData.getDriverVersion());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static PersistenceUnitInfo archiverPersistenceUnitInfo() {
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
