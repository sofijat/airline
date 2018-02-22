
import hibernate.Aerodrom;
import hibernate.AvioKompanija;
import hibernate.Avion;
import hibernate.Imalicencu;
import hibernate.Iznajmljivanje;
import hibernate.Korisnik;
import hibernate.Let;
import hibernate.Proizvodi;
import hibernate.Proizvodjac;
import hibernate.Stjuardese;
import hibernate.Tip;
import hibernate.Zahtev;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;


@SessionScoped
@ManagedBean
public class userController implements Serializable{
    //ulogovani korisnik
    public static Korisnik korisnik;
    public List<SelectItem> kompanis;
    //login info 
    private String logusername;
    private String logpassword;
    //za promenu passworda
    private String newPassword;
    //za registrovanje novog korisnika
    private String username;
    private String password;
    private String potvrdipassword;
    private String ime;
    private String prezime;
    private String email;
    private String pol;
    private Date datum;
    private int tip;
    private int kompanija;
    //lista zahteva
    private List<Zahtev> zahtevi;
    //format emaila
    private static final String EMAIL_PATTERN
            = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
  
    //sve licence
    private List<SelectItem> sve_licence;
    private String mojalicenca;
    private int novakompanija;
    //svi letovi za pilota/kopilota
    private List<Let> moji_letovi;
    private List<SelectItem> svi_aerodromi;
    //za stjuardesu
    private List<Let> prosli_letovi;
    private List<Let> buduci_letovi;
    //za radnika
    private List<Let> aktuelni_letovi;
    private List<Avion> proizvedeni;
    private List<SelectItem> proizvodjaci;
    private List<Iznajmljivanje> mojiZahtIznajm;
    //za admina
    private List<AvioKompanija> aviokomps;
    private List<SelectItem> pilotiselect;   
    private List<SelectItem> svi_avioni;   
    
    public userController(){
        korisnik=null;
        resetString();
        zahtevi=controller.dbHelper.getAllZahtev();
        kompanis=new ArrayList<SelectItem>();
        sve_licence=new ArrayList<SelectItem>();
        svi_aerodromi=new ArrayList<SelectItem>();
        proizvodjaci=new ArrayList<SelectItem>();
        mojiZahtIznajm=new ArrayList<Iznajmljivanje>();
        pilotiselect=new ArrayList<SelectItem>();
        svi_avioni=new ArrayList<SelectItem>();
        
        List<Avion> sviavi=controller.dbHelper.getAllAvion();
        for (Avion a:sviavi){
            svi_avioni.add(new SelectItem(a.getIdAvion(), a.getNaziv()));
        }
  
        List<Korisnik> svikori = controller.dbHelper.getAllKorisnik();
        for (Korisnik k : svikori) {
            if (k.getTip() == 0) {
                pilotiselect.add(new SelectItem(k.getIdKorisnik(), k.getIme() + " " + k.getPrezime()));
            }
        }

        List<Proizvodjac> proi=controller.dbHelper.getAllProizvodjac();
        for (int i=0; i<proi.size(); i++){
            proizvodjaci.add(new SelectItem(proi.get(i).getIdProizvodjac(), proi.get(i).getNaziv()));
        }
        
        List<Aerodrom> aeri=controller.dbHelper.getAllAerodrom();
        for (int i=0; i<aeri.size(); i++){
            svi_aerodromi.add(new SelectItem(aeri.get(i).getIata(), aeri.get(i).getNaziv()));
        }
        
        List<Tip> licence=controller.dbHelper.getAllTip();
        for (int i=0; i<licence.size(); i++){
            sve_licence.add(new SelectItem(licence.get(i).getBrojLicence(), licence.get(i).getBrojLicence()));
        }
        List<AvioKompanija> sve=controller.dbHelper.getAllAvioKompanija();
        for (int i=0; i<sve.size(); i++){
            kompanis.add(new SelectItem(sve.get(i).getIdKompanija(), sve.get(i).getNaziv()));
        }
    }
    
    public void resetString(){
        logusername="";
        logpassword="";
        newPassword="";
        username="";
        password="";
        potvrdipassword="";
        ime="";
        prezime="";
        email="";
        pol="";
    }
    
    
    public String logIn(){
        korisnik=controller.dbHelper.getKorisnikByUsername(logusername);
        mojiZahtIznajm.clear();
        
        if (korisnik==null){
            controller.greskaPoruka="Uneli ste nepostojece korisnicko ime";
            saveMessage();
            return null;
        }
        if (!korisnik.getLozinka().equals(logpassword)){
            controller.greskaPoruka="Uneli ste pogresnu lozinku";
            saveMessage();
            return null;
        }
        if (korisnik.getOdobren()==0){
            controller.greskaPoruka="Morate sacekati da administrator odobri vas zahtev za registraciju";
            saveMessage();
            return null;
        }
        if (korisnik.getTip()==0){//pilot
            moji_letovi=new ArrayList<Let>();
            List<Let> nova=new ArrayList<Let>();
            moji_letovi=controller.dbHelper.getLetsByPilot(korisnik.getIdKorisnik());
            nova=controller.dbHelper.getLetsByKopilot(korisnik.getIdKorisnik());
            moji_letovi.removeAll(nova);
            moji_letovi.addAll(nova);
            
            resetString();
            List<String> mlicence=controller.dbHelper.getLicenceById(korisnik.getIdKorisnik());
            if (!mlicence.isEmpty()){
                return "pilot?faces-redirected=true";
            }
            else return "pilotLicenc?faces-redirected=true";
        }
        else if (korisnik.getTip()==1){//stjuardesa
            prosli_letovi=new ArrayList<Let>();
            buduci_letovi=new ArrayList<Let>();
            resetString();
            Date danas=new Date();
            List<Let> svi=controller.dbHelper.getAllLet();
            List<Stjuardese> ss=controller.dbHelper.getStjuardByID(korisnik.getIdKorisnik());
            for (Let l:svi){
                for (Stjuardese s:ss){
                    if (l.getIdLet()==s.getLet().getIdLet()){
                        if (danas.after(l.getDatumPoletanja())){
                            prosli_letovi.add(l);
                        }
                        else {
                            buduci_letovi.add(l);
                        }
                    }
                }
            }
            
            
            return "stjuardesa?faces-redirected=true";
        }
        else if (korisnik.getTip()==2){//radnik
            aktuelni_letovi=new ArrayList<Let>();
            List<Let> nova=controller.dbHelper.getAllLet();
            List<Iznajmljivanje> sviiz=controller.dbHelper.getAllIznajmljivanje();

            
            Date danas=new Date();
            for (Let l:nova){
                if (l.getDatumPoletanja().after(danas) && korisnik.getIdKompani().getIdKompanija()==l.getIdKompan()){
                    aktuelni_letovi.add(l);
                }
            }
            for (Iznajmljivanje iz:sviiz){
                if (iz.getKompZa().getIdKompanija()==korisnik.getIdKompani().getIdKompanija() && iz.getPrihvaceno()==0)
                    mojiZahtIznajm.add(iz);
            }
            if (mojiZahtIznajm.isEmpty()){
                controller.infoPoruka="Nemate zahteve za iznajmljivanje";
                saveMessage();
            }
            
            Collections.sort(aktuelni_letovi);
            resetString();
            proizvedeni=controller.dbHelper.getAvioniByKompId(0);
            
            return "radnik?faces-redirected=true";
        }
        else if (korisnik.getTip()==3){//admin
            resetString();
            zahtevi=controller.dbHelper.getAllZahtev();
            aviokomps=controller.dbHelper.getAllAvioKompanija();
            
            return "admin?faces-redirected=true";
        }
        return null;
    }
    
    public String logOut(){
        korisnik=null;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        return "index?faces-redirect=true";
    }
    
    public String promeniLozinku(){
        korisnik=controller.dbHelper.getKorisnikByUsername(logusername);
        if (korisnik==null){
            controller.greskaPoruka="Uneli ste nepostojece korisnicko ime";
            saveMessage();
            return null;
        }
        if (!korisnik.getLozinka().equals(logpassword)){
            controller.greskaPoruka="Stara lozinka koju ste uneli nije ispravna";
            saveMessage();
            return null;
        }
        if (checkPassword(newPassword)==false){
            return null;
        }
        if (logpassword.equals(newPassword)){
            controller.greskaPoruka="Nova lozinka ne sme biti ista kao stara";
            saveMessage();
            return null;
        }
        korisnik.setLozinka(newPassword);
        controller.dbHelper.updateKorisnik(korisnik);
        korisnik=null;
        logpassword="";
        newPassword="";
        
        controller.infoPoruka="Uspesno ste promenili lozinku";
        saveMessage();
        return null;
    }
    
    private boolean checkPassword(String pass){
        boolean ret=true;
        FacesContext context=FacesContext.getCurrentInstance();
        boolean hasUpperCase=!pass.equals(pass.toLowerCase());
        boolean hasLowerCase=!pass.equals(pass.toUpperCase());
        boolean hasDigit = pass.matches(".*\\d.*");
        int broj_spec=0;
        int broj_malih=0;
        boolean greska=false;
        
        
        for (int i=0; i<pass.length(); i++){
            char ch=pass.charAt(i);
            if (Character.isLowerCase(ch)){
                broj_malih++;
            }
            else if (!Character.isLetter(ch) && !Character.isDigit(ch) && !Character.isSpaceChar(ch))
                broj_spec++;
        }
        for (int i=0; i<pass.length()-3; i++){
            char first=pass.charAt(i);
            char second=pass.charAt(i+1);
            char third=pass.charAt(i+2);
            
            if (first==second && second==third) {
                greska=true;
                break;
            }
        }
        if (greska==true){
            ret=false;
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Greska", "Lozinka ne sme da sadrzi vise od dva uzastopna identicna karaktera"));
        }
        if (broj_malih<3){
            ret=false;
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Greska", "Lozinka mora da sadrzi bar 3 mala slova"));
        }
        if (!(pass.length()>=8 && pass.length()<=10)){
            ret=false;
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Greska", "Lozinka mora da sadrzi izmedju 8 i 10 znakova"));
        }
        if (!hasUpperCase){
            ret=false;
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Greska", "Lozinka mora sadrzati bar jedno veliko slovo"));
        }
        if (!hasDigit){
            ret=false;
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Greska", "Lozinka mora sadrzati bar jedan broj"));
        }
        if (broj_spec<1){
            ret=false;
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Greska", "Lozinka mora sadrzati bar jedan specijalni znak"));
        }
        if (!(Character.isLowerCase(pass.charAt(0)) || Character.isUpperCase(pass.charAt(0)))){
            ret=false;
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Greska", "Lozinka mora poceti velikim ili malim slovom"));
        }
        return ret;
    }
    
    public void saveMessage(){
        if (!controller.greskaPoruka.isEmpty()){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Greska", ""+controller.greskaPoruka));
            controller.greskaPoruka="";
        }
        else if (!controller.infoPoruka.isEmpty()){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", ""+controller.infoPoruka));
            controller.infoPoruka="";
        }
    }
    
    public String registrujSe() throws ParseException{
        List<Korisnik> svi=controller.dbHelper.getAllKorisnik();
        for (int i=0; i<svi.size(); i++){
            if (svi.get(i).getKorisnickoIme().equals(username)){
               controller.greskaPoruka="Korisnicko ime vec postoji";
               saveMessage();
               return null; 
            }
        }
        
        if (checkPassword(password) && checkPassword(potvrdipassword)){
            if (!password.equals(potvrdipassword)){
                controller.greskaPoruka="Lozinke moraju biti identicne";
                saveMessage();
                return null;
            }
            
            Date danas=new Date();

            if (danas.getYear()-datum.getYear()<18){
                controller.greskaPoruka="Ne mozete se registrovati ukoliko imate manje od 18 godina";
                saveMessage();
                return null;
            } else if (danas.getYear() - datum.getYear() == 18) {
                if (danas.getMonth() < datum.getMonth()) {
                    controller.greskaPoruka = "Ne mozete se registrovati ukoliko imate manje od 18 godina";
                    saveMessage();
                    return null;
                } else if (danas.getMonth() == danas.getMonth()) {
                    if (danas.getDay() < datum.getDay()) {
                        controller.greskaPoruka = "Ne mozete se registrovati ukoliko imate manje od 18 godina";
                        saveMessage();
                        return null;
                    }
                }
            }
            
            Pattern pattern=Pattern.compile(EMAIL_PATTERN);
            Matcher matcher=pattern.matcher(email);
            if (matcher.matches()){
                List<Korisnik> korisnici=controller.dbHelper.getAllKorisnik();
                AvioKompanija avk=controller.dbHelper.getAvioKompById(kompanija);
                Korisnik kor=new Korisnik(korisnici.size()+1, ime, prezime, pol, datum, email, username, password, tip, 0, avk);
                controller.dbHelper.addNewKorisnik(kor);
                
                kor=controller.dbHelper.getKorisnikByUsername(username);
                List<Zahtev> zaht=controller.dbHelper.getAllZahtev();
                Zahtev z=new Zahtev(0, kor);
                controller.dbHelper.addNewZahtev(z);
                zahtevi=controller.dbHelper.getAllZahtev();
                resetString();
                return "index?faces-redirected=true";
            }
            else {
                controller.greskaPoruka="Email koji ste naveli nije validan";
                saveMessage();
                return null;
            }
        }
        return null;
    }
    
    public void odobri(Zahtev z){
        Korisnik k=z.getIdKori();
 
        k.setOdobren(1);
        controller.dbHelper.updateKorisnik(k);
        controller.dbHelper.deleteZahtev(z);
        zahtevi=controller.dbHelper.getAllZahtev();
        controller.infoPoruka="Uspesno ste odobrili zahtev za registraciju novog korisnika";
        saveMessage();
    }
    
    public void odbij(Zahtev z){
        controller.dbHelper.deleteZahtev(z);
        controller.dbHelper.deleteKorisnik(z.getIdKori());
        zahtevi=controller.dbHelper.getAllZahtev();
        controller.infoPoruka="Uspesno ste odbili zahtev za registraciju novog korisnika";
        saveMessage();
    }
    
    public String novaLicenca(){
        Imalicencu il=new Imalicencu(korisnik.getIdKorisnik(), mojalicenca);
        controller.dbHelper.addNewImalicencu(il);
        return "pilot?faces-redirected=true";
    }
    
    public void promeniKompaniju(){
        AvioKompanija avk=controller.dbHelper.getAvioKompById(novakompanija);
        if (korisnik.getIdKompani().getNaziv().equals(avk.getNaziv())){
            controller.greskaPoruka="Vec ste zaposleni u toj kompaniji";
            saveMessage();
            return;
        }
        korisnik.setIdKompani(avk);
        controller.dbHelper.updateKorisnik(korisnik);
        controller.infoPoruka="Uspesno ste promenili kompaniju";
        saveMessage();
    }
    
    public void dodajUflotu(Avion a){
        proizvedeni.remove(a);
        a.setIdKompanija(korisnik.getIdKompani().getIdKompanija());
        controller.dbHelper.updateAvion(a);
        controller.infoPoruka="Uspesno ste dodali avion u flotu svoje kompanije";
        saveMessage();
    }
    
    public void odbijPonudu(Iznajmljivanje iz){
        mojiZahtIznajm.remove(iz);
        controller.dbHelper.deleteIznajm(iz);
        controller.infoPoruka="Odbili ste ponudu";
        saveMessage();
    }
    
    public void prihvatiPonudu(Iznajmljivanje iz){
       iz.setPrihvaceno(1);
       mojiZahtIznajm.remove(iz);
       controller.dbHelper.updateIznajm(iz);
       controller.infoPoruka="Prihvatili ste ponudu";
       saveMessage();
    }
   
    public String skociPromenaKomp(){
        return "promenaKomp?faces-redirected=true";
    }
    
    public String skokNaIndeks(){
        return "index?faces-redirected=true";
    }
    
    public String skokNaPilot(){
        return "pilot?faces-redirected=true";
    }
    
    public String skokNaStjuard(){
        return "stjuardesa?faces-redirected=true";
    }
    
    public String skokNaRadnik(){
        return "radnik?faces-redirected=true";
    }
    
    public String skokNaAdmin(){
        return "admin?faces-redirected=true";
    }
    
    public static Korisnik getKorisnik() {
        return korisnik;
    }

    public String getLogusername() {
        return logusername;
    }

    public String getLogpassword() {
        return logpassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPotvrdipassword() {
        return potvrdipassword;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getEmail() {
        return email;
    }

    public String getPol() {
        return pol;
    }

    public Date getDatum() {
        return datum;
    }

    public int getTip() {
        return tip;
    }

    public int getKompanija() {
        return kompanija;
    }

    public List<Zahtev> getZahtevi() {
        return zahtevi;
    }

    public static String getEMAIL_PATTERN() {
        return EMAIL_PATTERN;
    }

    public static void setKorisnik(Korisnik korisnik) {
        userController.korisnik = korisnik;
    }

    public void setLogusername(String logusername) {
        this.logusername = logusername;
    }

    public void setLogpassword(String logpassword) {
        this.logpassword = logpassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPotvrdipassword(String potvrdipassword) {
        this.potvrdipassword = potvrdipassword;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public void setKompanija(int kompanija) {
        this.kompanija = kompanija;
    }

    public void setZahtevi(List<Zahtev> zahtevi) {
        this.zahtevi = zahtevi;
    }

    public List<SelectItem> getKompanis() {
        return kompanis;
    }

    public void setKompanis(List<SelectItem> kompanis) {
        this.kompanis = kompanis;
    }

    public List<SelectItem> getSve_licence() {
        return sve_licence;
    }

    public String getMojalicenca() {
        return mojalicenca;
    }

    public void setSve_licence(List<SelectItem> sve_licence) {
        this.sve_licence = sve_licence;
    }

    public void setMojalicenca(String mojalicenca) {
        this.mojalicenca = mojalicenca;
    }

    public void setNovakompanija(int novakompanija) {
        this.novakompanija = novakompanija;
    }

    public int getNovakompanija() {
        return novakompanija;
    }

    public List<Let> getMoji_letovi() {
        return moji_letovi;
    }

    public void setMoji_letovi(List<Let> moji_letovi) {
        this.moji_letovi = moji_letovi;
    }

    public List<SelectItem> getSvi_aerodromi() {
        return svi_aerodromi;
    }

    public void setSvi_aerodromi(List<SelectItem> svi_aerodromi) {
        this.svi_aerodromi = svi_aerodromi;
    }

    public List<Let> getProsli_letovi() {
        return prosli_letovi;
    }

    public List<Let> getBuduci_letovi() {
        return buduci_letovi;
    }

    public void setProsli_letovi(List<Let> prosli_letovi) {
        this.prosli_letovi = prosli_letovi;
    }

    public void setBuduci_letovi(List<Let> buduci_letovi) {
        this.buduci_letovi = buduci_letovi;
    }

    public List<Let> getAktuelni_letovi() {
        return aktuelni_letovi;
    }

    public void setAktuelni_letovi(List<Let> aktuelni_letovi) {
        this.aktuelni_letovi = aktuelni_letovi;
    }

    public List<Avion> getProizvedeni() {
        return proizvedeni;
    }

    public void setProizvedeni(List<Avion> proizvedeni) {
        this.proizvedeni = proizvedeni;
    }

    public List<SelectItem> getProizvodjaci() {
        return proizvodjaci;
    }


    public void setProizvodjaci(List<SelectItem> proizvodjaci) {
        this.proizvodjaci = proizvodjaci;
    }

    public List<Iznajmljivanje> getMojiZahtIznajm() {
        return mojiZahtIznajm;
    }

    public void setMojiZahtIznajm(List<Iznajmljivanje> mojiZahtIznajm) {
        this.mojiZahtIznajm = mojiZahtIznajm;
    }

    public List<AvioKompanija> getAviokomps() {
        return aviokomps;
    }

    public void setAviokomps(List<AvioKompanija> aviokomps) {
        this.aviokomps = aviokomps;
    }

    public List<SelectItem> getPilotiselect() {
        return pilotiselect;
    }

    public void setPilotiselect(List<SelectItem> pilotiselect) {
        this.pilotiselect = pilotiselect;
    }

    public List<SelectItem> getSvi_avioni() {
        return svi_avioni;
    }

    public void setSvi_avioni(List<SelectItem> svi_avioni) {
        this.svi_avioni = svi_avioni;
    }

}
