package web.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import web.entity.Votacao;
import web.entity.Voto;

import javax.persistence.Query;
import java.util.Collection;
import java.util.Map;

@Repository
public class VotoDAO implements DAO<Voto> {
    @Override
    public void create(Voto value) {
        Session session = HibernateSessionFactory.getInstance().createSession();

        session.save(value);

        session.close();
    }

    @Override
    public void update(Voto value) {
        Session session = HibernateSessionFactory.getInstance().createSession();

        session.update(value);

        session.close();
    }

    @Override
    public void delete(int id) {
        Session session = HibernateSessionFactory.getInstance().createSession();

        Voto voto = session.get(Voto.class, id);
        if (voto != null) {
            session.delete(voto);
        }

        session.close();
    }

    @Override
    public Collection<Voto> getAll() {
        Session session = HibernateSessionFactory.getInstance().createSession();

        Collection<Voto> votos = session.createQuery("from Voto").list();

        session.close();
        return votos;
    }

    @Override
    public Voto getById(int id) {
        Session session = HibernateSessionFactory.getInstance().createSession();

        Voto voto = session.get(Voto.class, id);

        session.close();
        return voto;
    }

    @Override
    public Collection<Voto> getFiltered(Map<String, Object> filters) {
        Session session = HibernateSessionFactory.getInstance().createSession();

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("from Voto v where ");
        int nroffilters = filters.size();
        for(String key: filters.keySet()) {
            nroffilters--;
            if (nroffilters == 0) {
                strBuilder.append("v."+ key + " = :" + key.replace('.', '_') + " " );
            }
            else {
                strBuilder.append("v."+ key + " = :" + key.replace('.', '_') + " and ");
            }

        }
        Query query = session.createQuery(strBuilder.toString());
        for(String key: filters.keySet()) {
            query.setParameter(key.replace('.', '_'), filters.get(key));
        }

        Collection<Voto> votos = query.getResultList();

        session.close();
        return votos;
    }
}
