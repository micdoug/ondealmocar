package web.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import web.entity.Funcionario;
import web.entity.Votacao;

import javax.persistence.Query;
import java.util.Collection;
import java.util.Map;

@Repository
public class FuncionarioDAO implements DAO<Funcionario> {


    @Override
    public void create(Funcionario value) {
        Session session = HibernateSessionFactory.getInstance().createSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.save(value);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Funcionario value) {
        Session session = HibernateSessionFactory.getInstance().createSession();
        System.out.println("salvando objeto");
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.update(value);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(int id) {
        Session session = HibernateSessionFactory.getInstance().createSession();
        Funcionario funcionario = session.get(Funcionario.class, id);
        if (funcionario != null) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.delete(funcionario);
            transaction.commit();
        }
        session.close();
    }

    @Override
    public Collection<Funcionario> getAll() {
        Session session = HibernateSessionFactory.getInstance().createSession();

        Collection<Funcionario> funcionarios = session.createQuery("from Funcionario")
                .list();

        session.close();
        return funcionarios;
    }

    @Override
    public Funcionario getById(int id) {
        Session session = HibernateSessionFactory.getInstance().createSession();

        Funcionario funcionario = session.get(Funcionario.class, id);

        session.close();
        return funcionario;
    }

    @Override
    public Collection<Funcionario> getFiltered(Map<String, Object> filters) {
        Session session = HibernateSessionFactory.getInstance().createSession();

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("from Funcionario f where ");
        int nroffilters = filters.size();
        for(String key: filters.keySet()) {
            nroffilters--;
            if (nroffilters == 0) {
                strBuilder.append("f."+ key + " = :" + key + " " );
            }
            else {
                strBuilder.append("f."+ key + " = :" + key + " and ");
            }

        }
        Query query = session.createQuery(strBuilder.toString());
        for(String key: filters.keySet()) {
            query.setParameter(key, filters.get(key));
        }

        Collection<Funcionario> funcionarios = query.getResultList();

        session.close();
        return funcionarios;
    }
}
