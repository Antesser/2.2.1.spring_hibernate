package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserByCar(String model, int series) {
        Query queryOne = sessionFactory.getCurrentSession().createQuery(
                        "From Car where model= :model and series= :series")
                .setParameter("model", model)
                .setParameter("series", series);
        Car car = (Car) queryOne.getSingleResult();

        Query queryTwo = sessionFactory.getCurrentSession().createQuery(
                "from User where car.id = :carId");
        queryTwo.setParameter("carId", car.getId());
        User user = (User) queryTwo.getSingleResult();
        return user;
    }
}
