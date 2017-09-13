package web.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import web.entity.Restaurante;
import web.entity.Votacao;

import javax.persistence.Query;
import java.util.Collection;
import java.util.Map;

@Repository
public class RestauranteDAO implements DAO<Restaurante> {

    public void create(Restaurante restaurante) {
        Session session = HibernateSessionFactory.getInstance().createSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.save(restaurante);
        transaction.commit();
        session.close();
    }

    public void update(Restaurante restaurante) {
        Session session = HibernateSessionFactory.getInstance().createSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.update(restaurante);
        transaction.commit();
        session.close();
    }

    public void delete(int id) {
        Session session = HibernateSessionFactory.getInstance().createSession();
        Restaurante restaurante = session.get(Restaurante.class, id);
        if (restaurante != null) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.delete(restaurante);
            transaction.commit();
        }
        session.close();
    }

    public Collection<Restaurante> getAll() {
        Session session = HibernateSessionFactory.getInstance().createSession();
        Collection<Restaurante> restaurantes = session.createQuery("from Restaurante").list();
        session.close();
        return restaurantes;
    }

    public Restaurante getById(int id) {
        Session session = HibernateSessionFactory.getInstance().createSession();
        Restaurante restaurante = session.get(Restaurante.class, id);
        session.close();
        return restaurante;
    }

    @Override
    public Collection<Restaurante> getFiltered(Map<String, Object> filters) {
        Session session = HibernateSessionFactory.getInstance().createSession();

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("from Restaurante r where ");
        int nroffilters = filters.size();
        for(String key: filters.keySet()) {
            nroffilters--;
            if (nroffilters == 0) {
                strBuilder.append("r."+ key + " = :" + key + " " );
            }
            else {
                strBuilder.append("r."+ key + " = :" + key + " and ");
            }

        }
        Query query = session.createQuery(strBuilder.toString());
        for(String key: filters.keySet()) {
            query.setParameter(key, filters.get(key));
        }

        Collection<Restaurante> restaurantes = query.getResultList();

        session.close();
        return restaurantes;
    }

}
