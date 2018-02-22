package hibernate;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;


public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    
    static {
        try {
            
            AnnotationConfiguration ac=new AnnotationConfiguration().configure().addPackage("hibernate");
            ac.addAnnotatedClass(Aerodrom.class);
            ac.addAnnotatedClass(AvioKompanija.class);
            ac.addAnnotatedClass(Avion.class);
            ac.addAnnotatedClass(ImaGejt.class);
            ac.addAnnotatedClass(ImaGejtPK.class);
            ac.addAnnotatedClass(ImaTerminal.class);
            ac.addAnnotatedClass(ImaTerminalPK.class);
            ac.addAnnotatedClass(Imalicencu.class);
            ac.addAnnotatedClass(ImalicencuPK.class);
            ac.addAnnotatedClass(Iznajmljivanje.class);
            ac.addAnnotatedClass(Korisnik.class);
            ac.addAnnotatedClass(Let.class);
            ac.addAnnotatedClass(Proizvodi.class);
            ac.addAnnotatedClass(ProizvodiPK.class);
            ac.addAnnotatedClass(Proizvodjac.class);
            ac.addAnnotatedClass(Stjuardese.class);
            ac.addAnnotatedClass(StjuardesePK.class);
            ac.addAnnotatedClass(Terminal.class);
            ac.addAnnotatedClass(Tip.class);
            ac.addAnnotatedClass(Zahtev.class);
            
            sessionFactory=ac.buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
