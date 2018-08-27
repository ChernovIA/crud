package dbService.dao;

import com.sun.xml.internal.ws.handler.HandlerException;
import dbService.DBConnector;
import dbService.DBException;
import dbService.dataSets.User;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import java.util.List;

public class UsersDAOHibernateImpl implements UsersDAO {

    private SessionFactory sessionFactory;
    private EntityManager entityManager;

    public UsersDAOHibernateImpl(){
        this.sessionFactory = DBConnector.createSessionFactory(DBConnector.getMysqlConfigutarionHibernate());
    }

    @Override
    public void createTable() throws DBException {
        //hibernate create table automatically
    }

    @Override
    public void dropTable() throws DBException {

        List<User> users = getTable(0);
        for (User user : users) {
            deleteUser(user.getId());
        }

    }

    @Override
    public User get(long id) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            User user = (User)session.get(User.class, id);
            session.close();
            return user;
        }
        catch (HibernateException e){
            throw new DBException(e);
        }
    }

    @Override
    public User get(String login) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("login",login));
            User user = (User) criteria.uniqueResult();
            session.close();
            return user;
        }
        catch (HibernateException e){
            throw new DBException(e);
        }
    }

    @Override
    public List<User> getTable(int limit) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Criteria criteria = session.createCriteria(User.class);
            if (limit > 0) {
                criteria.setMaxResults(limit);
            }
            List<User> users = (List<User>) criteria.list();
            session.close();
            return users;
        }
        catch (HibernateException e){
            throw new DBException(e);
        }
    }

    @Override
    public void addUser(User user) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
            session.close();
        }
        catch (HibernateException e){
            throw new DBException(e);
        }
    }

    @Override
    public void upDateUser(User user) throws DBException {
        addUser(user);
    }

    @Override
    public void deleteUser(long id) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(get(id));
            transaction.commit();
            session.close();
        }
        catch (HandlerException e){
            throw new DBException(e);
        }
    }
}
