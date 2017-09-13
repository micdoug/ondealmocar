package web.dao;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import web.entity.Funcionario;
import web.entity.Restaurante;
import web.entity.Votacao;
import web.entity.Voto;

public class HibernateSessionFactory {

    private static HibernateSessionFactory instance;

    public static HibernateSessionFactory getInstance() {
        if (instance == null) {
            instance = new HibernateSessionFactory();
        }
        return instance;
    }

    private SessionFactory factory;

    private HibernateSessionFactory() {
        factory = new Configuration()
                .configure()
                .addAnnotatedClass(Funcionario.class)
                .addAnnotatedClass(Restaurante.class)
                .addAnnotatedClass(Votacao.class)
                .addAnnotatedClass(Voto.class)
                .buildSessionFactory();
    }

    public Session createSession() {
        Session session = this.factory.openSession();
        return session;
    }

}
