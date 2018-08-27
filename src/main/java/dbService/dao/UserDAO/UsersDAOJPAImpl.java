package dbService.dao.UserDAO;

import dbService.DBConnector;
import dbService.DBException;
import dbService.dataSets.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class UsersDAOJPAImpl implements UsersDAO {

        private EntityManager entityManager ;

        public UsersDAOJPAImpl(){
           this.entityManager = DBConnector.getInstance().getEntityManager();
        }

        @Override
        public void createTable() {
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
                return entityManager.find(User.class, id);
            }
            catch (Exception e){
                throw new DBException(e);
            }
        }

        @Override
        public User get(String login) throws DBException {
            try {
                TypedQuery<User> tq = entityManager.createQuery("FROM User WHERE login = ?1",User.class);
                tq.setParameter(1, login);
                User user = tq.getSingleResult();
                return user;
            }
            catch (Exception e){
                throw new DBException(e);
            }
        }

        @Override
        public List<User> getTable(int limit) throws DBException {
            try {
                //entityManager.getTransaction().begin();
               /* CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<User> cq = cb.createQuery(User.class);
                Root<User> rootEntry = cq.from(User.class);
                CriteriaQuery<User> all = cq.select(rootEntry);
                TypedQuery<User> allQuery = entityManager.createQuery(all);

                List<User>users = allQuery.getResultList();*/

                entityManager.clear();
                List<User> users = entityManager.createQuery("from User",User.class).getResultList();
                //entityManager.getTransaction().commit();
                return users;
            }
            catch (Exception e){
                throw new DBException(e);
            }
        }

        @Override
        public void addUser(User user) throws DBException {
            try {
                entityManager.getTransaction().begin();
                entityManager.persist(user);
                entityManager.getTransaction().commit();
            }
            catch (Exception e){
                throw new DBException(e);
            }
        }

        @Override
        public void upDateUser(User user) throws DBException {
            try {
                entityManager.getTransaction().begin();
                entityManager.merge(user);
                entityManager.getTransaction().commit();
            }
            catch (Exception e){
                throw new DBException(e);
            }
        }

        @Override
        public void deleteUser(long id) throws DBException {
            try {
                entityManager.getTransaction().begin();
                User user = entityManager.find(User.class, id);
                entityManager.remove(user);
                entityManager.getTransaction().commit();
            }
            catch (Exception e){
                throw new DBException(e);
            }
        }
    }
