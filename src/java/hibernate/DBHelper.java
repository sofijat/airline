package hibernate;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date; 
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;

public class DBHelper implements Serializable{
    
    public Korisnik getKorisnikByUsername(String username){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        Korisnik korisnik=null;
        
        boolean avail=session.isConnected();
        
        try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Korisnik WHERE korisnicko_ime='" +username+"'");
            List<Korisnik> korisnici = (List<Korisnik>) upit.list();
            if (!korisnici.isEmpty()) {
                korisnik = korisnici.get(0);
            }
            tr.commit();
        } catch (HibernateException ex){
            ex.printStackTrace();
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        
        return korisnik;
    }
    
    public Let getLetById(int id){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        Let let=null;
      try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Let WHERE id_let="+id);
            List<Let> letovi = (List<Let>) upit.list();
            if (!letovi.isEmpty()) {
                let = letovi.get(0);
            }
            tr.commit();
        } catch (HibernateException ex){
            ex.printStackTrace();
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        
        return let;
    }
    
    public Korisnik getKornisnikById(int id){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        Korisnik kori=null;
      try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Korisnik WHERE id_korisnik="+id);
            List<Korisnik> korisnici = (List<Korisnik>) upit.list();
            if (!korisnici.isEmpty()) {
                kori = korisnici.get(0);
            }
            tr.commit();
        } catch (HibernateException ex){
            ex.printStackTrace();
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        
        return kori;
    }
    
    public Proizvodjac getProizvodjacById(int id){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        Proizvodjac pro=null;
      try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Proizvodjac WHERE id_proizvodjac="+id);
            List<Proizvodjac> proiz = (List<Proizvodjac>) upit.list();
            if (!proiz.isEmpty()) {
                pro = proiz.get(0);
            }
            tr.commit();
        } catch (HibernateException ex){
            ex.printStackTrace();
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        
        return pro;
    }
    
    public Aerodrom getAerodromByIata(String iat){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        Aerodrom aer=null;
      try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Aerodrom WHERE iata='"+iat+"'");
            List<Aerodrom> aerodromi = (List<Aerodrom>) upit.list();
            if (!aerodromi.isEmpty()) {
                aer = aerodromi.get(0);
            }
            tr.commit();
        } catch (HibernateException ex){
            ex.printStackTrace();
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        
        return aer;
    }
    
    public Tip getTipById(int id){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        Tip tip=null;
      try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Tip WHERE id_tip="+id);
            List<Tip> tipovi = (List<Tip>) upit.list();
            if (!tipovi.isEmpty()) {
                tip = tipovi.get(0);
            }
            tr.commit();
        } catch (HibernateException ex){
            ex.printStackTrace();
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        
        return tip;
    }
    
    public Avion getAvionById(int id){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        Avion av=null;
      try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Avion WHERE id_avion="+id);
            List<Avion> avioni = (List<Avion>) upit.list();
            if (!avioni.isEmpty()) {
                av = avioni.get(0);
            }
            tr.commit();
        } catch (HibernateException ex){
            ex.printStackTrace();
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        
        return av;
    }
    
    public List<String> getLicenceById(int id){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<String> licence=null;
      try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Imalicencu WHERE id_pilot="+id);
            licence = (List<String>) upit.list();
            tr.commit();
        } catch (HibernateException ex){
            ex.printStackTrace();
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        
        return licence;
    }
    
    public List<Let> getLetsByPilot(int id){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<Let> letovi=null;
      try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Let WHERE id_kapetan="+id);
            letovi = (List<Let>) upit.list();
            tr.commit();
        } catch (HibernateException ex){
            ex.printStackTrace();
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        
        return letovi;
    }
    
    
    public List<Avion> getAvioniByKompId(int id){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<Avion> avioni=null;
      try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Avion WHERE id_kompanija="+id);
            avioni = (List<Avion>) upit.list();
            tr.commit();
        } catch (HibernateException ex){
            ex.printStackTrace();
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        
        return avioni;
    }
    
    public List<Let> getLetsByKopilot(int id){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<Let> letovi=null;
      try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Let WHERE id_kopilot="+id);
            letovi = (List<Let>) upit.list();
            tr.commit();
        } catch (HibernateException ex){
            ex.printStackTrace();
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        
        return letovi;
    }
    
    public List<Imalicencu> getAllLicence(){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<Imalicencu> licence=null;
        
        try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Imalicencu ");
            licence = upit.list();
            
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        return licence;
    }
    
    public List<Iznajmljivanje> getAllIznajmljivanje(){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<Iznajmljivanje> izn=null;
        
        try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Iznajmljivanje ");
            izn = upit.list();
            
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        return izn;
    }
    
    public List<Korisnik> getAllKorisnik(){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<Korisnik> korisnici=null;
        
        try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Korisnik ");
            korisnici = upit.list();
            
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        return korisnici;
    }
    
    public List<Proizvodi> getAllProizvodi(){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<Proizvodi> pro=null;
        
        try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Proizvodi ");
            pro = upit.list();
            
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        return pro;
    }
    
    public List<Let> getLetByDatum(Date datum){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<Let> letovi=null;
        
        try {
            Date stari=new Date();
            stari.setTime(datum.getTime());
            tr = session.beginTransaction();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            datum.setHours(0);
            datum.setMinutes(0);
            datum.setSeconds(0);
            String datOd=df.format(datum);
            datum.setHours(23);
            datum.setMinutes(59);
            datum.setSeconds(59);
            String datDo=df.format(datum);
            Date datumOd=df.parse(datOd);
            Date datumDo=df.parse(datDo);
            datum.setTime(stari.getTime());
            Criteria crt=session.createCriteria(Let.class);
            crt.add(Restrictions.between("datumPoletanja", datumOd, datumDo));
            letovi=crt.list();
            
            tr.commit();
        } catch (Exception ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        return letovi;
    }
    
    public List<Let> getLetByPolaz(String polaz){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<Let> letovi=null;
        
        try {
            tr = session.beginTransaction();
            String str="%"+polaz;
            polaz=str+"%";
            
            Query upit = session.createQuery("SELECT let FROM Let as let INNER JOIN "+
                    "let.polazniAer as aer WHERE aer.naziv LIKE '"+polaz+"' OR "+
                    "aer.zemlja LIKE "+"'"+polaz+"' OR "+"aer.grad LIKE "+"'"+polaz+"'");
            letovi = upit.list();
            
            tr.commit();
        } catch (Exception ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        return letovi;
    }
    
    public List<ImaGejt> getAllImaGejt(){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<ImaGejt> gejtovi=null;
        
        try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM ImaGejt ");
            gejtovi = upit.list();
            
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        return gejtovi;
    }
    
    public List<Korisnik> getAllPilot(){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<Korisnik> piloti=null;
        
        try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Korisnik WHERE tip="+0);
            piloti = upit.list();
            
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        return piloti;
    }
    
    public List<ImaTerminal> getAllImaTerminal(){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<ImaTerminal> termi=null;
        
        try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM ImaTerminal ");
            termi = upit.list();
            
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        return termi;
    }
    
    public List<Stjuardese> getStjuardByID(int id){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<Stjuardese> stjuar=null;
        
        try {
            tr = session.beginTransaction();
            
            Query upit = session.createQuery("FROM Stjuardese WHERE id_korisnikk="+id);
            stjuar = upit.list();
            
            tr.commit();
        } catch (Exception ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        return stjuar;
    }
    
    public List<Let> getLetByDolaz(String dolaz){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<Let> letovi=null;
        
        try {
            tr = session.beginTransaction();
            String str="%"+dolaz;
            dolaz=str+"%";
            
            Query upit = session.createQuery("SELECT let FROM Let as let INNER JOIN "+
                    "let.odredisniAer as aer WHERE aer.naziv LIKE '"+dolaz+"' OR "+
                    "aer.zemlja LIKE "+"'"+dolaz+"' OR "+"aer.grad LIKE "+"'"+dolaz+"'");
            letovi = upit.list();
            
            tr.commit();
        } catch (Exception ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        return letovi;
    }
    
    public void addNewKorisnik(Korisnik k){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        
        try {
            tr = session.beginTransaction();
            session.save(k);
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }     
    }
    
    public void addNewLet(Let le){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        
        try {
            tr = session.beginTransaction();
            session.save(le);
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }     
    }
    
        public void addNewAvion(Avion av){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        
        try {
            tr = session.beginTransaction();
            session.save(av);
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }     
    }
    
    public void addNewImalicencu(Imalicencu il){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        
        try {
            tr = session.beginTransaction();
            session.save(il);
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }     
    }
    
    public void addNewAerodrom(Aerodrom a){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        
        try {
            tr = session.beginTransaction();
            session.save(a);
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }     
    }
    
    public void addNewZahtev(Zahtev z){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        
        try {
            tr = session.beginTransaction();
            session.save(z);
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }     
    }
    
    public void addNewAvioKompanija(AvioKompanija a){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        
        try {
            tr = session.beginTransaction();
            session.save(a);
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }     
    }
    
    public void addNewIznajm(Iznajmljivanje iz){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        
        try {
            tr = session.beginTransaction();
            session.save(iz);
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }     
    }
    
    public void addNewTerminal(Terminal term){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        
        try {
            tr = session.beginTransaction();
            session.save(term);
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }     
    }
    
    public void addNewImaGejt(ImaGejt ig){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        
        try {
            tr = session.beginTransaction();
            session.save(ig);
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }     
    }
    
    public void addNewStjuardese(Stjuardese s){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        
        try {
            tr = session.beginTransaction();
            session.save(s);
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }     
    }
    
    public void addNewImaTerminal(ImaTerminal ite){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        
        try {
            tr = session.beginTransaction();
            session.save(ite);
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }     
    }
      
    public List<Avion> getAllAvion(){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<Avion> avioni=null;
        
        try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Avion ");
            avioni = upit.list();
            
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        return avioni;
    }
    
    public List<AvioKompanija> getAllAvioKompanija(){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<AvioKompanija> kompanije=null;
        
        try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM AvioKompanija ");
            kompanije = upit.list();
            
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        return kompanije;
    }
    
    
    public List<Let> getAllLet(){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<Let> letovi=null;
        
        try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Let ");
            letovi = upit.list();
            
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        return letovi;
    }
    
    public List<Zahtev> getAllZahtev(){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<Zahtev> zahtevi=null;
        
        try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Zahtev ");
            zahtevi = upit.list();
            
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        return zahtevi;
    }
    
    public List<Proizvodjac> getAllProizvodjac(){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<Proizvodjac> proizvo=null;
        
        try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Proizvodjac ");
            proizvo = upit.list();
            
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        return proizvo;
    }
    
    public List<Terminal> getAllTerminal(){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<Terminal> terminali=null;
        
        try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Terminal ");
            terminali = upit.list();
            
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        return terminali;
    }
    
    public List<Tip> getAllTip(){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<Tip> tipovi=null;
        
        try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Tip ");
            tipovi = upit.list();
            
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        return tipovi;
    }
    
    public List<Aerodrom> getAllAerodrom(){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        List<Aerodrom> aerodromi=null;
        
        try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM Aerodrom ");
            aerodromi = upit.list();
            
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        return aerodromi;
    }
    
    public void updateKorisnik(Korisnik k){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        
        try{
            tr=session.beginTransaction();
            session.update(k);
            tr.commit();
        } catch(HibernateException ex){
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            session.close();
        }
    }
    
    public void updateAvioKom(AvioKompanija av){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        
        try{
            tr=session.beginTransaction();
            session.update(av);
            tr.commit();
        } catch(HibernateException ex){
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            session.close();
        }
    }
    
    public void updateLet(Let le){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        
        try{
            tr=session.beginTransaction();
            session.update(le);
            if (tr!=null) tr.commit();
        } catch(Exception ex){
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            session.close();
        }
    }
    
    public void updateAvion(Avion a){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        
        try{
            tr=session.beginTransaction();
            session.update(a);
            if (tr!=null) tr.commit();
        } catch(Exception ex){
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            session.close();
        }
    }
    
    public void updateIznajm(Iznajmljivanje iz){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        
        try{
            tr=session.beginTransaction();
            session.update(iz);
            if (tr!=null) tr.commit();
        } catch(Exception ex){
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            session.close();
        }
    }
    
    public AvioKompanija getAvioKompById(int id){
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        AvioKompanija kompanija=null;
        
        try {
            tr = session.beginTransaction();
            Query upit = session.createQuery("FROM AvioKompanija WHERE id_kompanija = " + id);
            List<AvioKompanija> kompanije = upit.list();
            if (!kompanije.isEmpty()) {
                kompanija = kompanije.get(0);
            }
            tr.commit();
        } catch (HibernateException ex){
            if (tr!=null){
                tr.rollback();
            }
        } finally {
            session.close();
        }
        
        return kompanija;
    }
    
    public void deleteZahtev(Zahtev z) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            session.delete(z);
            tr.commit();
        } catch (HibernateException e) {
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            session.close();
        }
    }
    
    public void deleteKorisnik(Korisnik k) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            session.delete(k);
            tr.commit();
        } catch (HibernateException e) {
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            session.close();
        }
    }
    
     public void deleteIznajm(Iznajmljivanje iz) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            session.delete(iz);
            tr.commit();
        } catch (HibernateException e) {
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            session.close();
        }
    }
}
