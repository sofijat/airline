
import hibernate.Aerodrom;
import hibernate.AvioKompanija;
import hibernate.Avion;
import hibernate.DBHelper;
import hibernate.ImaGejt;
import hibernate.ImaTerminal;
import hibernate.Imalicencu;
import hibernate.Iznajmljivanje;
import hibernate.Korisnik;
import hibernate.Let;
import hibernate.Proizvodi;
import hibernate.Proizvodjac;
import hibernate.Stjuardese;
import hibernate.Terminal;
import hibernate.Tip;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

@SessionScoped
@ManagedBean
public class controller implements Serializable{
    //database helper
    public static DBHelper dbHelper;
    public static String greskaPoruka;
    public static String infoPoruka;
    static {
        dbHelper=new DBHelper();
        greskaPoruka="";
        infoPoruka="";
    }
    //GOST PRETRAGA
    private List<Let> danasnji_letovi;
    private List<Let> filtrirano;
    private int povratni; //0-jedan smer, 1-povratni
    private String polaziste, odrediste;
    private Date datum_polaska, datum_povratka;
    private int broj_osoba; //1-min, 8-max
    private boolean direktan; //true-da
    private boolean prikaz=false;
    //ZA REZERVACIJU
    private String ime;
    private String prezime;
    private String brojpasosa;
    private String brojkartice;
    private int id_rezer_let;
    private String moj_broj;
    private String jedinst_sifra;
    private Let rezervisani;
    //IZMENA STATUSA, DESTINACIJE ITD
    private int novistatus;
    private String noviaer;
    private double duzinapreostala;
    private double prosecnabrzina;
    private Let odabrani_let;
    private Let letIzmeniGejt;
    private List<SelectItem> odlazni_gejtovi;
    private List<SelectItem> dolazni_gejtovi;
    private String gejt1, gejt2;
    //RADNIK
    private List<SelectItem> tipovi;
    private int filterProizvodjac;
    private int filterModel;
    private int filterBrMesta;
    private Date filterOd;
    private Date filterDo;
    private List<Avion> filtriraniAvioni;
    private double evri;
    //ADMIN
    private String aiata;
    private String anaziv;
    private String azemlja;
    private int abrojpisti;
    private String agrad;
    private Aerodrom aaerodrom;
    private String aterminal;
    private String agejt;
    private Terminal anoviterm;
    private String knaziv;
    private String kadresa;
    private String kzemlja;
    private String ksajt;
    private String kemail;
    private int pilot_za_licencu;
    private String licenca_za_pilota;
    private int avProizvodjac;
    private int avModel;
    private String avNaziv;
    //nov let
    private String lpolazni;
    private String lodredisni;
    private String lgejtpolaz;
    private String lgejtodred;
    private Date lduzina;
    private Date ldatum;
    private int lkompani;
    private int lkapetan;
    private int lkopilot;
    private int lavion;
    private boolean lcarter;
    private List<SelectItem> dostupni_av;
    private List<SelectItem> dostupni_pil;
    private List<SelectItem> stjuardese;
    private List<String> izabrane_stj;
         
    public controller(){
        filtriraniAvioni=null;
        danasnji_letovi=new ArrayList<Let>();
        Date datum=Calendar.getInstance().getTime();
        danasnji_letovi=dbHelper.getLetByDatum(datum);
        if (danasnji_letovi.size()>0) prikaz=true;
    }
    
    public List<Let> filterPolaz(){
        List<Let> nova=new ArrayList<Let>();
        if (polaziste.equals("")){
            nova=dbHelper.getAllLet();
        }
        else {
            nova=dbHelper.getLetByPolaz(polaziste);
        }
        return nova;
    }
    
    public List<Let> filterOdred(){
        List<Let> nova=new ArrayList<Let>();
        if (odrediste.equals("")){
            nova=dbHelper.getAllLet();
        }
        else nova=dbHelper.getLetByDolaz(odrediste);
        return nova;
    }
    
    public List<Let> filterDatumPolaska(){
        List<Let> nova=new ArrayList<Let>();
        if (polaziste.equals("")){
            nova=dbHelper.getAllLet();
        }
        else {
            nova=dbHelper.getLetByDatum(datum_polaska);
        }
        return nova;
    }
    
    public List<Let> filterDatumPovratka(){
        List<Let> nova=new ArrayList<Let>();
        if (polaziste.equals("")){
            nova=dbHelper.getAllLet();
        }
        else {
            nova=dbHelper.getLetByDatum(datum_povratka);
        }
        return nova;
    }
    
    public String pretraga(){
        if (broj_osoba<1 || broj_osoba>8){
            greskaPoruka="Broj osoba da bude izmedju 1 i 8";
            saveMessage();
            return null;
        }
        List<Let> pol=filterPolaz();
        List<Let> odr=filterOdred();
        List<Let> datPol=filterDatumPolaska();
        List<Let> datOdr=filterDatumPovratka();
        boolean nasao_odlaznu=false;
        boolean nasao_povratnu=false;
        if (povratni == 0) {
            pol.retainAll(odr);
            pol.retainAll(datPol);
            filtrirano = pol;

            if (direktan == false) {
                pol = filterPolaz();
                pol.retainAll(datPol);
                for (Let i : pol) {
                    for (Let j : odr) {
                        if (i.getOdredisniAer().getIata().equals(j.getPolazniAer().getIata()) && i.getCarter()==0 && j.getCarter()==0) {
                            filtrirano.add(i);
                            filtrirano.add(j);
                        }
                    }
                }
            }
            if (!filtrirano.isEmpty()){
                List<Let> noval=new ArrayList<Let>();
                for (Let f:filtrirano){
                    if (f.getCarter()==0){
                        noval.add(f);
                    }
                }
                if (!noval.isEmpty()){
                    filtrirano.clear();
                    filtrirano.addAll(noval);
                }
                else {
                    filtrirano.clear();
                    return null;
                }
            }
            else return null;
        }
        else if (povratni == 1) {
            filtrirano = new ArrayList<Let>();

            pol.retainAll(odr);
            pol.retainAll(datPol); //svi polasci za tu destinaciju za odredjeni datum
            String temp;
            temp = polaziste;
            polaziste = odrediste;
            odrediste = temp;
            List<Let> pol1 = filterPolaz();
            List<Let> odr1 = filterOdred();
            List<Let> dat1 = filterDatumPovratka();
            pol1.retainAll(odr1);
            pol1.retainAll(dat1);
            if (!pol1.isEmpty()) {
                List<Let> noval = new ArrayList<Let>();
                for (Let f : pol1) {
                    if (f.getCarter() == 0) {
                        noval.add(f);
                    }
                }
                if (!noval.isEmpty()) {
                    pol1.clear();
                    pol1.addAll(noval);
                } else {
                    pol1.clear();
                }
                if (!pol1.isEmpty()) nasao_povratnu=true;
                else nasao_povratnu=false;
            }
            if (!pol.isEmpty()){
                List<Let> noval = new ArrayList<Let>();
                for (Let f : pol) {
                    if (f.getCarter() == 0) {
                        noval.add(f);
                    }
                }
                if (!noval.isEmpty()) {
                    pol.clear();
                    pol.addAll(noval);
                } else {
                    pol.clear();
                }
                if (!pol.isEmpty()) nasao_odlaznu=true;
                else nasao_odlaznu=false;
            }
            for (Let l : pol) {
                filtrirano.add(l);
            }
            for (Let l : pol1) {
                filtrirano.add(l);
            }

            if (direktan == false) {
                temp = polaziste;
                polaziste = odrediste;
                odrediste = temp;
                pol = filterPolaz();
                pol.retainAll(datPol);
                odr.retainAll(datPol);
                
                for (Let i : pol) {
                    for (Let j : odr) {
                        if (i.getOdredisniAer().getIata().equals(j.getPolazniAer().getIata()) && i.getCarter()==0 && j.getCarter()==0) {
                            filtrirano.add(i);
                            filtrirano.add(j);
                            if (nasao_odlaznu==false) nasao_odlaznu=true;
                        }
                    }
                }

                temp = polaziste;
                polaziste = odrediste;
                odrediste = temp;
                pol1=filterPolaz();
                odr1=filterOdred();
                dat1=filterDatumPovratka();
                pol1.retainAll(dat1);
                odr1.retainAll(dat1);
                
                for (Let i : pol1) {
                    for (Let j : odr1) {
                        if (i.getOdredisniAer().getIata().equals(j.getPolazniAer().getIata()) && i.getCarter()==0 && j.getCarter()==0) {
                            filtrirano.add(i);
                            filtrirano.add(j);
                            if (nasao_povratnu==false) nasao_povratnu=true;
                        }
                    }
                }
                
            }
            if (nasao_povratnu==false || nasao_odlaznu==false){
                filtrirano.clear();
                return null;
            }
        }
        return "rezultatPretrage?faces-redirected=true";
    }
    
    public String skoci(int id){
        id_rezer_let=id;
        return "rezervacija?faces-redirected=true";
    }
    
    public String rezevisi(){
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        moj_broj="";
        Random rand=new Random();
        int b;
        char c;
        for (int i=0; i<8; i++){
            b=rand.nextInt(AB.length());
            c=AB.charAt(b);
            moj_broj+=c;
        }
        return "potvrdaRezer?faces-redirected=true";
    }
    
    public String potvrdiRez(){
       if (moj_broj.equals(jedinst_sifra)){
           Let let = dbHelper.getLetById(id_rezer_let);
           rezervisani=let;
           int num = let.getBrSlobodnihMesta();
           num -= broj_osoba;
           if (num<0){
               greskaPoruka="Nema dovoljno slobodnih mesta, karta nije rezervisana";
               saveMessage();
               return null;
           }
           let.setBrSlobodnihMesta(num);

           dbHelper.updateLet(let);
           infoPoruka = "Uspesno ste rezervisali kartu";
           saveMessage();
        }
        else {
            greskaPoruka="Niste uneli ispravnu sifru, karta nije rezervisana";
            saveMessage();
            return null;
        }
        return "karta?faces-redirected=true";
    }

    public void saveMessage(){
        if (!greskaPoruka.isEmpty()){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Greska", ""+greskaPoruka));
            controller.greskaPoruka="";
        }
        else if (!infoPoruka.isEmpty()){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", ""+infoPoruka));
            infoPoruka="";
        }
    }
    
    public void noviStatusLeta(Let l){
        if (novistatus==3){
            Calendar cal=Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String str=sdf.format(cal.getTime());
            l.setVremeSletanja(str);
        }
        
        l.setStatus(novistatus);
        
        String izm=l.getIzmene();
        if (izm==null) izm="";
        izm+=userController.korisnik.getIme()+" "+userController.korisnik.getPrezime()+" je izmenio status leta\n";
        l.setIzmene(izm);
        
        dbHelper.updateLet(l);
        infoPoruka="Uspesno ste promenili status leta";
        saveMessage();
    }
    
    public void prinudnoSleteo(Let l){
        if (noviaer.equals(l.getOdredisniAer().getIata())){
            greskaPoruka="Taj aerodrom vec predstavlja odrediste";
            saveMessage();
            return;
        }
        if (l.getStatus()==4){
            greskaPoruka="Let je vec prinudno sleteo";
            saveMessage();
            return;
        }
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String str = sdf.format(cal.getTime());
        l.setVremeSletanja(str);
        
        l.setStatus(4);
        Aerodrom a=dbHelper.getAerodromByIata(noviaer);
        l.setNovaDestinacija(a);
        
        String izm=l.getIzmene();
        if (izm==null) izm="";
        izm+=userController.korisnik.getIme()+" "+userController.korisnik.getPrezime()+" je dodao prinudno sletanje na novu destinaciju\n";
        l.setIzmene(izm);
        
        dbHelper.updateLet(l);
        infoPoruka="Uspesno ste promenili destinaciju";
        saveMessage();
    }
    
    public String promeniOcekivano(Let l){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        int hours=cal.get(Calendar.HOUR_OF_DAY);
        int minutes=cal.get(Calendar.MINUTE);
        int seconds=cal.get(Calendar.SECOND);
        
        double rez=duzinapreostala/prosecnabrzina;
        int h=(int) rez;
        int m=(int)((rez-h)*60);
        int s=(int)(((rez-h)*60)%1);
        hours+=h;
        if (hours>=24) hours-=24;
        minutes+=m;
        seconds+=s;
        String h1=""+hours;
        if (hours<10) h1="0"+h1;
        String m1=""+minutes;
        if (minutes<10) m1="0"+m1;
        String s1=""+seconds;
        if (seconds<10) s1+="0"+s1;
        String novo=h1+":"+m1+":"+s1;
        l.setVremeOcekivano(novo);
        
        String izm=l.getIzmene();
        if (izm==null) izm="";
        izm+=userController.korisnik.getIme()+" "+userController.korisnik.getPrezime()+" je izmenio ocekivano vreme\n";
        l.setIzmene(izm);
        
        dbHelper.updateLet(l);
        infoPoruka="Uspesno ste promenili ocekivano vreme";
        saveMessage();
        
        return null;
    }
    
    public String promenaGejt(Let l){
        letIzmeniGejt=l;
        List<ImaGejt> gejtovi=controller.dbHelper.getAllImaGejt();
        List<ImaTerminal> terminali=controller.dbHelper.getAllImaTerminal();
        List<ImaTerminal> novat=new ArrayList<ImaTerminal>();
        List<ImaGejt> novag=new ArrayList<ImaGejt>();
        odlazni_gejtovi=new ArrayList<SelectItem>();
        dolazni_gejtovi=new ArrayList<SelectItem>();
        for (ImaTerminal t:terminali){
            if (t.getAerodrom().getIata().equals(l.getPolazniAer().getIata())){
                novat.add(t);
            }
        }
        terminali=novat;
        for (ImaGejt g:gejtovi){
            for (ImaTerminal t:terminali){
               if (g.getTerminal().getIdTerminal()==t.getImaTerminalPK().getIdTermi())
                   novag.add(g);
            }
        }
        gejtovi=novag;
        for (ImaGejt g:gejtovi){
            odlazni_gejtovi.add(new SelectItem(g.getImaGejtPK().getIdGe(), g.getImaGejtPK().getIdGe()));
        }
        
        gejtovi=controller.dbHelper.getAllImaGejt();
        terminali=controller.dbHelper.getAllImaTerminal();
        novag.clear();
        novat.clear();
        
        for (ImaTerminal t:terminali){
            if (t.getAerodrom().getIata().equals(l.getOdredisniAer().getIata())){
                novat.add(t);
            }
        }
        terminali=novat;
        for (ImaGejt g:gejtovi){
            for (ImaTerminal t:terminali){
               if (g.getTerminal().getIdTerminal()==t.getImaTerminalPK().getIdTermi())
                   novag.add(g);
            }
        }
        gejtovi=novag;
        for (ImaGejt g:gejtovi){
            dolazni_gejtovi.add(new SelectItem(g.getImaGejtPK().getIdGe(), g.getImaGejtPK().getIdGe()));
        }
        
        return "izmenaGejt?faces-redirected=true";
    }
    
    public void manjeGejtovaPolaz() {
        List<ImaGejt> gejtovii = controller.dbHelper.getAllImaGejt();
        List<ImaTerminal> termi = controller.dbHelper.getAllImaTerminal();
        odlazni_gejtovi=new ArrayList<SelectItem>();
        for (ImaTerminal t : termi) {
            if (t.getAerodrom().getIata().equals(lpolazni)) {
                for (ImaGejt g : gejtovii) {
                    if (g.getTerminal().getIdTerminal() == t.getImaTerminalPK().getIdTermi()) {
                        odlazni_gejtovi.add(new SelectItem(g.getImaGejtPK().getIdGe(), g.getImaGejtPK().getIdGe()));
                    }
                }
            }
        }
    }

    public void manjeGejtovaOdred() {
        List<ImaGejt> gejtovii = controller.dbHelper.getAllImaGejt();
        List<ImaTerminal> termi = controller.dbHelper.getAllImaTerminal();
        dolazni_gejtovi=new ArrayList<SelectItem>();
        for (ImaTerminal t : termi) {
            if (t.getAerodrom().getIata().equals(lodredisni)) {
                for (ImaGejt g : gejtovii) {
                    if (g.getTerminal().getIdTerminal() == t.getImaTerminalPK().getIdTermi()) {
                        dolazni_gejtovi.add(new SelectItem(g.getImaGejtPK().getIdGe(), g.getImaGejtPK().getIdGe()));
                    }
                }
            }
        }
    }
    
    public void promenaGejta() throws ParseException{
        Date danas=new Date();
        long duration=danas.getTime()-letIzmeniGejt.getDatumPoletanja().getTime();
        long difMin=TimeUnit.MILLISECONDS.toMinutes(duration);
        if (difMin>30 || difMin<0){
            letIzmeniGejt.setGejtOdlaz(gejt1);
        }
        else {
            greskaPoruka="Polazni gejt mozete menjati do 30min pre polaska";
            saveMessage();
            return;
        }
        SimpleDateFormat dtf=new SimpleDateFormat("HH:mm");
        Date vreme=dtf.parse(letIzmeniGejt.getDuzinaLeta());
        
        Date datum=new Date();
        datum.setTime(letIzmeniGejt.getDatumPoletanja().getTime());
        int sati=datum.getHours()+vreme.getHours();
        if (sati>23) sati-=24;
        int minuti=datum.getMinutes()+vreme.getMinutes();
        if (minuti>59){
            sati+=1;
            if (sati>23) sati-=24;
            minuti-=60;
        }
        datum.setHours(sati);
        datum.setMinutes(minuti);
        duration=datum.getTime()-danas.getTime();
        difMin=TimeUnit.MILLISECONDS.toMinutes(duration);
        if (difMin<30 && difMin>0){
            greskaPoruka="Odredisni gejt mozete menjati do 30min pre sletanja";
            saveMessage();
            return;
        }
        
        letIzmeniGejt.setGejtDolaz(gejt2);
        
        String izm=letIzmeniGejt.getIzmene();
        if (izm==null) izm="";
        izm+=userController.korisnik.getIme()+" "+userController.korisnik.getPrezime()+" je izmenio polazni i/ili"
                + " odredisni gejt\n";
        letIzmeniGejt.setIzmene(izm);
        
        dbHelper.updateLet(letIzmeniGejt);
        infoPoruka="Uspesno ste promenili gejt(ove)";
        saveMessage();
    }
     
    public void manjeTipova(){
        Tip t=null;
        tipovi=new ArrayList<SelectItem>();
        List<Proizvodi> pro=controller.dbHelper.getAllProizvodi();
        for (Proizvodi p: pro){
            if (p.getProizvodjac().getIdProizvodjac()==filterProizvodjac){
                t=dbHelper.getTipById(p.getProizvodiPK().getIdTipp());
                tipovi.add(new SelectItem(p.getProizvodiPK().getIdTipp(), t.getNaziv()));
            }
        }    
    }
    
    public void manjeTipova1(){
        Tip t=null;
        tipovi=new ArrayList<SelectItem>();
        List<Proizvodi> pro=controller.dbHelper.getAllProizvodi();
        for (Proizvodi p: pro){
            if (p.getProizvodjac().getIdProizvodjac()==avProizvodjac){
                t=dbHelper.getTipById(p.getProizvodiPK().getIdTipp());
                tipovi.add(new SelectItem(p.getProizvodiPK().getIdTipp(), t.getNaziv()));
            }
        }    
    }
    
    public String nadji(){
        List<Avion> avioni=dbHelper.getAllAvion();
        List<Let> sviletovi=dbHelper.getAllLet();
        for (Let l:sviletovi){
            if (l.getDatumPoletanja().after(filterOd) && l.getDatumPoletanja().before(filterDo)){
                avioni.remove(l.getIdAvion());
            }
        }
        List<Avion> nova=new ArrayList<Avion>();
        for (Avion a:avioni){
            if (a.getIdTtip().getMaxSedista()>=filterBrMesta)
                nova.add(a);
        }
        avioni.clear();
        avioni.addAll(nova);
        nova.clear();
        if (filterProizvodjac!=0){
            for (Avion a: avioni){
                if (a.getIdProizvodjac().getIdProizvodjac()==filterProizvodjac)
                    nova.add(a);
            }
        }
        avioni.clear();
        avioni.addAll(nova);
        nova.clear();
        if (filterModel!=0){
            for (Avion a:avioni){
                if (a.getIdTtip().getIdTip()==filterModel)
                    nova.add(a);
            }
            avioni.clear();
            avioni.addAll(nova);
            nova.clear();
        }

        for (Avion a:avioni){
            if (a.getIdKompanija()!=0 && a.getIdKompanija()!=userController.korisnik.getIdKompani().getIdKompanija()){
                nova.add(a);
            }
        }
        avioni.clear();
        avioni.addAll(nova);
        nova.clear();
        
        filtriraniAvioni=avioni;
        if (filtriraniAvioni.isEmpty()){
            infoPoruka="Nema rezultata za datu pretragu";
            saveMessage();
        }
        return null;
    }
    
    public String iznajmi(Avion a){
        filtriraniAvioni.clear();
        List<Iznajmljivanje> svaiz=dbHelper.getAllIznajmljivanje();
        AvioKompanija avk=dbHelper.getAvioKompById(a.getIdKompanija());
        Iznajmljivanje iz=new Iznajmljivanje(svaiz.size()+1, filterOd, filterDo, (int)evri, 0, a, userController.korisnik.getIdKompani(), avk);
        dbHelper.addNewIznajm(iz);
        infoPoruka="Uspesno ste poslali zahtev za iznajmljivanje aviona";
        saveMessage();
        
        return null;
    }
    
    public void dodajAerodrom(){
        Aerodrom a=new Aerodrom(aiata, anaziv, azemlja, abrojpisti, agrad);
        aaerodrom=a;
        dbHelper.addNewAerodrom(a);
        infoPoruka="Uspesno ste dodali novi aerodrom";
        saveMessage();
    }
    
    public void dodajTerminal(){
        anoviterm=null;
        if (aaerodrom==null){
            greskaPoruka="Prvo morate dodati aerodrom";
            saveMessage();
            return;
        }
        List<Terminal> svitermi=dbHelper.getAllTerminal();
        Terminal t=new Terminal(svitermi.size()+1, aterminal);
        dbHelper.addNewTerminal(t);
        ImaTerminal ite=new ImaTerminal(aaerodrom.getIata(), t.getIdTerminal());
        dbHelper.addNewImaTerminal(ite);
        anoviterm=t;

        infoPoruka="Uspesno ste dodali terminal aerodromu";
        saveMessage();
    }
    
    public void dodajGejt(){
        if (anoviterm==null){
            greskaPoruka="Prvo morate dodati terminal";
            saveMessage();
            return;
        }

        ImaGejt ig=new ImaGejt(anoviterm.getIdTerminal(), agejt);
        dbHelper.addNewImaGejt(ig);
        infoPoruka="Uspesno ste dodali gejt terminalu";
        saveMessage();
    }
    
    public void izmenaKompanije(AvioKompanija k){
        dbHelper.updateAvioKom(k);
        infoPoruka="Uspesno ste izmenili podatke o avio kompaniji";
        saveMessage();
    }
    
    public void novaAvioKomp(){
        List<AvioKompanija> sveav=dbHelper.getAllAvioKompanija();
        AvioKompanija novaa=new AvioKompanija(sveav.size()+1, knaziv, kadresa, kzemlja, ksajt, kemail);
        knaziv="";
        kadresa="";
        kzemlja="";
        ksajt="";
        kemail="";
        dbHelper.addNewAvioKompanija(novaa);
        infoPoruka="Uspesno ste dodali novu avio kompaniju";
        saveMessage();
    }
    
    public void dodeliLicencu(){
        List<Imalicencu> imali=dbHelper.getAllLicence();
        for (Imalicencu i:imali){
            if (i.getKorisnik().getIdKorisnik()==pilot_za_licencu && i.getImalicencuPK().getBrojLicence().equals(licenca_za_pilota)){
                greskaPoruka="Pilot vec ima tu licencu";
                saveMessage();
                return;
            }
        }
        Imalicencu noval=new Imalicencu(pilot_za_licencu, licenca_za_pilota);
        dbHelper.addNewImalicencu(noval);
        infoPoruka="Uspesno ste dodelili licencu pilotu";
        saveMessage();
    }
    
    public void dodajPrimerak(){
        List<Avion> avions=dbHelper.getAllAvion();
        Proizvodjac pr=dbHelper.getProizvodjacById(avProizvodjac);
        Tip t=dbHelper.getTipById(avModel);
        Avion av=new Avion(avions.size()+1, avNaziv, 0, pr, t);

        dbHelper.addNewAvion(av);
        infoPoruka="Uspesno ste dodali nov primerak aviona";
        saveMessage();
    }
   
    public void dodajSliku(){
        infoPoruka="Uspesno ste dodali sliku";
        saveMessage();
    }
    
    public void smanjiNaOsnovuKomp() {
        dostupni_av = new ArrayList<SelectItem>();
        stjuardese=new ArrayList<SelectItem>();
        List<Avion> avs = dbHelper.getAvioniByKompId(lkompani);
        for (Avion a : avs) {
            dostupni_av.add(new SelectItem(a.getIdAvion(), a.getNaziv()));
        }
        List<Korisnik> sviko=dbHelper.getAllKorisnik();
        for (Korisnik k:sviko){
            if (k.getTip()==1 && k.getIdKompani().getIdKompanija()==lkompani && k.getOdobren()==1){
                stjuardese.add(new SelectItem(k.getIdKorisnik()+"", k.getIme()+" "+k.getPrezime()));
            }
        }
    }
    
    public void smanjiPilote() {
        dostupni_pil = new ArrayList<SelectItem>();
        List<Korisnik> piloti = dbHelper.getAllPilot();
        Avion a = dbHelper.getAvionById(lavion);
        List<Korisnik> nova = new ArrayList<Korisnik>();

        List<Imalicencu> licence = dbHelper.getAllLicence();
        for (Imalicencu l : licence) {
            if (l.getImalicencuPK().getBrojLicence().equals(a.getIdTtip().getBrojLicence())) {
                nova.add(l.getKorisnik());
            }
        }
        piloti.retainAll(nova);
        for (Korisnik k : piloti) {
            if (k.getIdKompani().getIdKompanija() == lkompani) {
                dostupni_pil.add(new SelectItem(k.getIdKorisnik(), k.getIme() + " " + k.getPrezime()));
            }
        }
    }
    
    public void smanjiKopilote(){
        Korisnik k=dbHelper.getKornisnikById(lkapetan);
        SelectItem it=new SelectItem(k.getIdKorisnik(), k.getIme()+" "+k.getPrezime());
        dostupni_pil.remove(it);
    }
    
    public void dodajLet(){
        Aerodrom apol=dbHelper.getAerodromByIata(lpolazni);
        List<Let> letovi=dbHelper.getLetByDatum(ldatum);
        List<Let> nova=new ArrayList<Let>();
        if (izabrane_stj.size()<3){
            greskaPoruka="Morate izabrati najmanje tri stjuardese";
            saveMessage();
            return;
        }
        
        for (Let l:letovi){
            if (l.getDatumPoletanja().getHours()==ldatum.getHours() &&
                    l.getDatumPoletanja().getMinutes()==ldatum.getMinutes() &&
                    l.getPolazniAer().getIata().equals(apol.getIata()))
                nova.add(l);
        }
        if (nova.size()>=apol.getBrojPisti()){
            greskaPoruka="U jednom trenutku ne moze biti vise letova od broja pisti (polazni aerodrom)";
            saveMessage();
            return;
        }
        Aerodrom aodr=dbHelper.getAerodromByIata(lodredisni);
        if (aodr.getIata().equals(apol.getIata())){
            greskaPoruka="Polazni i odredisni aerodrom ne sme biti isti";
            saveMessage();
            return;
        }
        nova.clear();
        for (Let l:letovi){
            if (l.getDatumPoletanja().getHours()==ldatum.getHours() &&
                    l.getDatumPoletanja().getMinutes()==ldatum.getMinutes() &&
                    l.getPolazniAer().getIata().equals(aodr.getIata()))
                nova.add(l);
        }
        if (nova.size()>=aodr.getBrojPisti()){
            greskaPoruka="U jednom trenutku ne moze biti vise letova od broja pisti (odredisni aerodrom)";
            saveMessage();
            return;
        }
        
        SimpleDateFormat dtf=new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dat=new SimpleDateFormat("HH:ss");
        int car=0;
        if (lcarter==true) car=1;
        
        Calendar cal=Calendar.getInstance();
        Date dolazak=cal.getTime();
        int sati=ldatum.getHours()+lduzina.getHours();
        if (sati>23) sati-=24;
        int minuti=ldatum.getMinutes()+lduzina.getMinutes();
        if (minuti>59){
            sati+=1;
            if (sati>23) sati-=24;
            minuti-=60;
        }
                
        dolazak.setHours(sati);
        dolazak.setMinutes(minuti);
        dolazak.setSeconds(ldatum.getSeconds());
        String ocekivano=dtf.format(dolazak);
       
        Avion av=dbHelper.getAvionById(lavion);
        int br_dana=0;
        int godina=ldatum.getYear();
        int mesec=ldatum.getMonth();
        if (mesec==0 || mesec==2 || mesec==4 || mesec==6 || mesec==7 || mesec==9 || mesec==11) br_dana=31;
        else if (mesec==3 || mesec==5 || mesec==8 || mesec==10) br_dana=30;
        else if (mesec==2){
            if (godina%4==0) br_dana=29;
            else br_dana=28;        
        }
        if (car == 1) {
            boolean nasao=false;
            List<Iznajmljivanje> liz=dbHelper.getAllIznajmljivanje();
            
            for (Iznajmljivanje iz:liz){
                if (iz.getKompZa().getIdKompanija()==lkompani && ldatum.after(iz.getDatumOd()) && ldatum.before(iz.getDatumDo()) && iz.getIdavion().getIdAvion()==lavion && iz.getPrihvaceno()==1){
                    nasao=true;
                    break;
                }
            }
            if (nasao==true){
                nasao=false;
            }
            else {
                greskaPoruka="U tom periodu zeljeni avion nije iznajmljen kompaniji";
                saveMessage();
                return;
            }
            
            Let let = new Let(1, lduzina.getHours()+":"+lduzina.getMinutes(), ldatum, ocekivano, ocekivano, 0, lkompani, lkapetan, lkopilot, lgejtpolaz, lgejtodred, av.getIdTtip().getMaxSedista(), car, av, aodr, apol);
            dbHelper.addNewLet(let);
            for (int i = 0; i < izabrane_stj.size(); i++) {
                Korisnik k = dbHelper.getKornisnikById(Integer.parseInt(izabrane_stj.get(i)));
                Stjuardese s = new Stjuardese(let.getIdLet(), k.getIdKorisnik());
                dbHelper.addNewStjuardese(s);
            }
            infoPoruka = "Uspesno ste dodali novi let";
            saveMessage();
        }
        else {
            int dan=ldatum.getDate();
            while (dan<=br_dana){
                Let let = new Let(1, lduzina.getHours()+":"+lduzina.getMinutes(), ldatum, ocekivano, ocekivano, 0, lkompani, lkapetan, lkopilot, lgejtpolaz, lgejtodred, av.getIdTtip().getMaxSedista(), car, av, aodr, apol);
                dbHelper.addNewLet(let);
                for (int i=0; i<izabrane_stj.size(); i++){
                    Korisnik k = dbHelper.getKornisnikById(Integer.parseInt(izabrane_stj.get(i)));
                    Stjuardese s = new Stjuardese(let.getIdLet(), k.getIdKorisnik());
                    dbHelper.addNewStjuardese(s);
                }
                dan += 7;
                ldatum.setDate(dan);
            }
            resetNoviLet();
            infoPoruka = "Uspesno ste dodali novi let";
            saveMessage();
        }
        
       
    }
    
    public void resetNoviLet(){
        lavion=0;
        lcarter=false;
        ldatum=null;
        lduzina=null;
        lgejtodred=null;
        lgejtpolaz=null;
        lkapetan=0;
        lkompani=0;
        lkopilot=0;
        lodredisni=null;
        lpolazni=null;
    }
    
    public static DBHelper getDbHelper() {
        return dbHelper;
    }

    public static String getGreskaPoruka() {
        return greskaPoruka;
    }

    public static String getInfoPoruka() {
        return infoPoruka;
    }

    public int getPovratni() {
        return povratni;
    }

    public String getPolaziste() {
        return polaziste;
    }

    public String getOdrediste() {
        return odrediste;
    }

    public Date getDatum_polaska() {
        return datum_polaska;
    }

    public Date getDatum_povratka() {
        return datum_povratka;
    }

    public int getBroj_osoba() {
        return broj_osoba;
    }

    public boolean isDirektan() {
        return direktan;
    }

    public boolean isPrikaz() {
        return prikaz;
    }

    public static void setDbHelper(DBHelper dbHelper) {
        controller.dbHelper = dbHelper;
    }

    public static void setGreskaPoruka(String greskaPoruka) {
        controller.greskaPoruka = greskaPoruka;
    }

    public static void setInfoPoruka(String infoPoruka) {
        controller.infoPoruka = infoPoruka;
    }

    public void setPovratni(int povratni) {
        this.povratni = povratni;
    }

    public void setPolaziste(String polaziste) {
        this.polaziste = polaziste;
    }

    public void setOdrediste(String odrediste) {
        this.odrediste = odrediste;
    }

    public void setDatum_polaska(Date datum_polaska) {
        this.datum_polaska = datum_polaska;
    }

    public void setDatum_povratka(Date datum_povratka) {
        this.datum_povratka = datum_povratka;
    }

    public void setBroj_osoba(int broj_osoba) {
        this.broj_osoba = broj_osoba;
    }

    public void setDirektan(boolean direktan) {
        this.direktan = direktan;
    }

    public void setPrikaz(boolean prikaz) {
        this.prikaz = prikaz;
    }

    public List<Let> getDanasnji_letovi() {
        return danasnji_letovi;
    }

    public void setDanasnji_letovi(List<Let> danasnji_letovi) {
        this.danasnji_letovi = danasnji_letovi;
    }

    public List<Let> getFiltrirano() {
        return filtrirano;
    }

    public void setFiltrirano(List<Let> filtrirano) {
        this.filtrirano = filtrirano;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getBrojpasosa() {
        return brojpasosa;
    }

    public String getBrojkartice() {
        return brojkartice;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setBrojpasosa(String brojpasosa) {
        this.brojpasosa = brojpasosa;
    }

    public void setBrojkartice(String brojkartice) {
        this.brojkartice = brojkartice;
    }

    public void setId_rezer_let(int id_rezer_let) {
        this.id_rezer_let = id_rezer_let;
    }

    public int getId_rezer_let() {
        return id_rezer_let;
    }

    public String getMoj_broj() {
        return moj_broj;
    }

    public void setMoj_broj(String moj_broj) {
        this.moj_broj = moj_broj;
    }


    public String getJedinst_sifra() {
        return jedinst_sifra;
    }

    public void setJedinst_sifra(String jedinst_sifra) {
        this.jedinst_sifra = jedinst_sifra;
    }

    public int getNovistatus() {
        return novistatus;
    }

    public void setNovistatus(int novistatus) {
        this.novistatus = novistatus;
    }

    public String getNoviaer() {
        return noviaer;
    }

    public void setNoviaer(String noviaer) {
        this.noviaer = noviaer;
    }

    public double getDuzinapreostala() {
        return duzinapreostala;
    }

    public double getProsecnabrzina() {
        return prosecnabrzina;
    }

    public void setDuzinapreostala(double duzinapreostala) {
        this.duzinapreostala = duzinapreostala;
    }

    public void setProsecnabrzina(double prosecnabrzina) {
        this.prosecnabrzina = prosecnabrzina;
    }

  
    public void setDuzinapreostala(int duzinapreostala) {
        this.duzinapreostala = duzinapreostala;
    }

    public void setProsecnabrzina(int prosecnabrzina) {
        this.prosecnabrzina = prosecnabrzina;
    }

    public Let getOdabrani_let() {
        return odabrani_let;
    }

    public void setOdabrani_let(Let odabrani_let) {
        this.odabrani_let = odabrani_let;
    }

    public Let getLetIzmeniGejt() {
        return letIzmeniGejt;
    }

    public void setLetIzmeniGejt(Let letIzmeniGejt) {
        this.letIzmeniGejt = letIzmeniGejt;
    }

    public List<SelectItem> getOdlazni_gejtovi() {
        return odlazni_gejtovi;
    }

    public List<SelectItem> getDolazni_gejtovi() {
        return dolazni_gejtovi;
    }

    public void setOdlazni_gejtovi(List<SelectItem> odlazni_gejtovi) {
        this.odlazni_gejtovi = odlazni_gejtovi;
    }

    public void setDolazni_gejtovi(List<SelectItem> dolazni_gejtovi) {
        this.dolazni_gejtovi = dolazni_gejtovi;
    }

    public String getGejt1() {
        return gejt1;
    }

    public String getGejt2() {
        return gejt2;
    }

    public void setGejt1(String gejt1) {
        this.gejt1 = gejt1;
    }

    public void setGejt2(String gejt2) {
        this.gejt2 = gejt2;
    }

    public int getFilterProizvodjac() {
        return filterProizvodjac;
    }

    public int getFilterModel() {
        return filterModel;
    }

    public void setFilterProizvodjac(int filterProizvodjac) {
        this.filterProizvodjac = filterProizvodjac;
    }

    public void setFilterModel(int filterModel) {
        this.filterModel = filterModel;
    }

    public int getFilterBrMesta() {
        return filterBrMesta;
    }

    public Date getFilterOd() {
        return filterOd;
    }

    public Date getFilterDo() {
        return filterDo;
    }

    public void setFilterBrMesta(int filterBrMesta) {
        this.filterBrMesta = filterBrMesta;
    }

    public void setFilterOd(Date filterOd) {
        this.filterOd = filterOd;
    }

    public void setFilterDo(Date filterDo) {
        this.filterDo = filterDo;
    }

    public List<SelectItem> getTipovi() {
        return tipovi;
    }

    public void setTipovi(List<SelectItem> tipovi) {
        this.tipovi = tipovi;
    }

    public List<Avion> getFiltriraniAvioni() {
        return filtriraniAvioni;
    }

    public void setFiltriraniAvioni(List<Avion> filtriraniAvioni) {
        this.filtriraniAvioni = filtriraniAvioni;
    }

    public double getEvri() {
        return evri;
    }

    public void setEvri(double evri) {
        this.evri = evri;
    }

    public void setAiata(String aiata) {
        this.aiata = aiata;
    }

    public void setAnaziv(String anaziv) {
        this.anaziv = anaziv;
    }

    public void setAzemlja(String azemlja) {
        this.azemlja = azemlja;
    }

    public void setAbrojpisti(int abrojpisti) {
        this.abrojpisti = abrojpisti;
    }

    public void setAaerodrom(Aerodrom aaerodrom) {
        this.aaerodrom = aaerodrom;
    }

    public String getAiata() {
        return aiata;
    }

    public String getAnaziv() {
        return anaziv;
    }

    public String getAzemlja() {
        return azemlja;
    }

    public int getAbrojpisti() {
        return abrojpisti;
    }

    public Aerodrom getAaerodrom() {
        return aaerodrom;
    }


    public String getAterminal() {
        return aterminal;
    }

    public void setAterminal(String aterminal) {
        this.aterminal = aterminal;
    }

    public String getAgejt() {
        return agejt;
    }

    public void setAgejt(String agejt) {
        this.agejt = agejt;
    }

    public String getAgrad() {
        return agrad;
    }

    public void setAgrad(String agrad) {
        this.agrad = agrad;
    }

    public Terminal getAnoviterm() {
        return anoviterm;
    }

    public void setAnoviterm(Terminal anoviterm) {
        this.anoviterm = anoviterm;
    }

    public String getKnaziv() {
        return knaziv;
    }

    public void setKnaziv(String knaziv) {
        this.knaziv = knaziv;
    }

    public String getKadresa() {
        return kadresa;
    }

    public void setKadresa(String kadresa) {
        this.kadresa = kadresa;
    }

    public String getKzemlja() {
        return kzemlja;
    }

    public void setKzemlja(String kzemlja) {
        this.kzemlja = kzemlja;
    }
    

    public String getKsajt() {
        return ksajt;
    }

    public void setKsajt(String ksajt) {
        this.ksajt = ksajt;
    }

    public String getKemail() {
        return kemail;
    }

    public void setKemail(String kemail) {
        this.kemail = kemail;
    }

    public int getPilot_za_licencu() {
        return pilot_za_licencu;
    }

    public void setPilot_za_licencu(int pilot_za_licencu) {
        this.pilot_za_licencu = pilot_za_licencu;
    }

    public String getLicenca_za_pilota() {
        return licenca_za_pilota;
    }

    public void setLicenca_za_pilota(String licenca_za_pilota) {
        this.licenca_za_pilota = licenca_za_pilota;
    }

    public int getAvProizvodjac() {
        return avProizvodjac;
    }

    public void setAvProizvodjac(int avProizvodjac) {
        this.avProizvodjac = avProizvodjac;
    }

    public int getAvModel() {
        return avModel;
    }

    public void setAvModel(int avModel) {
        this.avModel = avModel;
    }

    public String getAvNaziv() {
        return avNaziv;
    }

    public void setAvNaziv(String avNaziv) {
        this.avNaziv = avNaziv;
    }

    public int getLavion() {
        return lavion;
    }

    public void setLavion(int lavion) {
        this.lavion = lavion;
    }

    public Date getLdatum() {
        return ldatum;
    }

    public void setLdatum(Date ldatum) {
        this.ldatum = ldatum;
    }

    public String getLgejtpolaz() {
        return lgejtpolaz;
    }

    public void setLgejtpolaz(String lgejtpolaz) {
        this.lgejtpolaz = lgejtpolaz;
    }

    public String getLgejtodred() {
        return lgejtodred;
    }

    public void setLgejtodred(String lgejtodred) {
        this.lgejtodred = lgejtodred;
    }

    public String getLpolazni() {
        return lpolazni;
    }

    public void setLpolazni(String lpolazni) {
        this.lpolazni = lpolazni;
    }

    public String getLodredisni() {
        return lodredisni;
    }

    public void setLodredisni(String lodredisni) {
        this.lodredisni = lodredisni;
    }

    public Date getLduzina() {
        return lduzina;
    }

    public void setLduzina(Date lduzina) {
        this.lduzina = lduzina;
    }

    public int getLkompani() {
        return lkompani;
    }

    public void setLkompani(int lkompani) {
        this.lkompani = lkompani;
    }

    public int getLkapetan() {
        return lkapetan;
    }

    public void setLkapetan(int lkapetan) {
        this.lkapetan = lkapetan;
    }

    public boolean isLcarter() {
        return lcarter;
    }

    public void setLcarter(boolean lcarter) {
        this.lcarter = lcarter;
    }

    public List<SelectItem> getDostupni_av() {
        return dostupni_av;
    }

    public void setDostupni_av(List<SelectItem> dostupni_av) {
        this.dostupni_av = dostupni_av;
    }

    public List<SelectItem> getDostupni_pil() {
        return dostupni_pil;
    }

    public void setDostupni_pil(List<SelectItem> dostupni_pil) {
        this.dostupni_pil = dostupni_pil;
    }

    public int getLkopilot() {
        return lkopilot;
    }

    public void setLkopilot(int lkopilot) {
        this.lkopilot = lkopilot;
    }

    public Let getRezervisani() {
        return rezervisani;
    }

    public void setRezervisani(Let rezervisani) {
        this.rezervisani = rezervisani;
    }

    public List<SelectItem> getStjuardese() {
        return stjuardese;
    }

    public void setStjuardese(List<SelectItem> stjuardese) {
        this.stjuardese = stjuardese;
    }

    public List<String> getIzabrane_stj() {
        return izabrane_stj;
    }

    public void setIzabrane_stj(List<String> izabrane_stj) {
        this.izabrane_stj = izabrane_stj;
    }



}
