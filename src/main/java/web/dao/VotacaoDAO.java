package web.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.entity.Funcionario;
import web.entity.Restaurante;
import web.entity.Votacao;
import web.entity.Voto;
import web.helpers.VotoModel;

import javax.persistence.Query;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public class VotacaoDAO implements DAO<Votacao> {

    @Override
    public void create(Votacao value) {
        Session session = HibernateSessionFactory.getInstance().createSession();
        Transaction transaction = session.getTransaction();

        session.save(value);

        transaction.commit();
        session.close();
    }

    @Override
    public void update(Votacao value) {
        Session session = HibernateSessionFactory.getInstance().createSession();
        Transaction transaction = session.getTransaction();

        session.update(value);

        transaction.commit();
        session.close();
    }

    @Override
    public void delete(int id) {
        Session session = HibernateSessionFactory.getInstance().createSession();
        Transaction transaction = session.getTransaction();

        Votacao votacao = session.get(Votacao.class, id);
        session.delete(votacao);

        transaction.commit();
        session.close();
    }

    @Override
    public Collection<Votacao> getAll() {
        Session session = HibernateSessionFactory.getInstance().createSession();

        Collection<Votacao> votacoes = session.createQuery("from Votacao v order by v.restaurante.nome").list();
        session.close();

        return votacoes;
    }

    @Override
    public Votacao getById(int id) {
        Session session = HibernateSessionFactory.getInstance().createSession();

        Votacao v = session.get(Votacao.class, id);

        session.close();

        return v;
    }

    @Override
    public Collection<Votacao> getFiltered(Map<String, Object> filters) {
        Session session = HibernateSessionFactory.getInstance().createSession();

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("from Votacao v where ");
        int nroffilters = filters.size();
        for(String key: filters.keySet()) {
            nroffilters--;
            if (nroffilters == 0) {
                strBuilder.append("v."+ key + " = :" + key + " " );
            }
            else {
                strBuilder.append("v."+ key + " = :" + key + " and ");
            }

        }
        Query query = session.createQuery(strBuilder.toString());
        for(String key: filters.keySet()) {
            query.setParameter(key, filters.get(key));
        }

        Collection<Votacao> votacoes = query.getResultList();

        session.close();
        return votacoes;
    }

    public Votacao getTopOfDay(LocalDate day, List<Integer> blacklist) {
        Session session = HibernateSessionFactory.getInstance().createSession();

        List<Votacao> votacoes = session.createQuery("from Votacao v where v.restaurante.id not in :blacklist and v.data = :data order by v.numeroVotos desc")
                .setParameter("blacklist", blacklist)
                .setParameter("data", day)
                .setMaxResults(1)
                .getResultList();

        session.close();
        if (votacoes.size() == 0) {
            return null;
        }
        else {
            return votacoes.get(0);
        }
    }

    public void votar(VotoModel model) {
        Session session = HibernateSessionFactory.getInstance().createSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        try {
            // Verifica se já existe um registro de votação
            LocalDate today = LocalDate.now();

            List<Votacao> votacoes = session.createQuery("from Votacao v where v.restaurante.id = :restauranteId and v.data = :today")
                    .setParameter("restauranteId", model.getRestauranteId())
                    .setParameter("today", today)
                    .list();
            Votacao votacao;
            if (votacoes.size() == 0) {
                votacao = new Votacao();
                votacao.setRestaurante(session.get(Restaurante.class, model.getRestauranteId()));
                votacao.setData(today);
                votacao.setNumeroVotos(1);
                session.save(votacao);
            }
            else {
                votacao = votacoes.get(0);
                votacao.setNumeroVotos(votacao.getNumeroVotos() + 1);
                session.update(votacao);
            }
            Voto voto = new Voto();
            voto.setData(today);
            voto.setFuncionario(session.get(Funcionario.class, model.getFuncionarioId()));
            session.save(voto);
            transaction.commit();
        }
        catch (Exception exc) {
            transaction.rollback();
            throw exc;
        }
        session.close();
    }
}
