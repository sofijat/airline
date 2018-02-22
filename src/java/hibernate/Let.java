/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ja
 */
@Entity
@Table(name = "let")
@NamedQueries({
    @NamedQuery(name = "Let.findAll", query = "SELECT l FROM Let l")})
public class Let implements Serializable, Comparable<Let> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_let")
    private Integer idLet;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "duzina_leta")
    private String duzinaLeta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum_poletanja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumPoletanja;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "vreme_planirano")
    private String vremePlanirano;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "vreme_ocekivano")
    private String vremeOcekivano;
    @Size(max = 45)
    @Column(name = "vreme_sletanja")
    private String vremeSletanja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_kompan")
    private int idKompan;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_kapetan")
    private int idKapetan;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_kopilot")
    private int idKopilot;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "gejt_odlaz")
    private String gejtOdlaz;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "gejt_dolaz")
    private String gejtDolaz;
    @Basic(optional = false)
    @NotNull
    @Column(name = "brSlobodnihMesta")
    private int brSlobodnihMesta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "carter")
    private int carter;
    @Size(max = 1000)
    @Column(name = "Izmene")
    private String izmene;
    @JoinColumn(name = "id_avion", referencedColumnName = "id_avion")
    @ManyToOne(optional = false)
    private Avion idAvion;
    @JoinColumn(name = "nova_destinacija", referencedColumnName = "iata")
    @ManyToOne
    private Aerodrom novaDestinacija;
    @JoinColumn(name = "odredisni_aer", referencedColumnName = "iata")
    @ManyToOne(optional = false)
    private Aerodrom odredisniAer;
    @JoinColumn(name = "polazni_aer", referencedColumnName = "iata")
    @ManyToOne(optional = false)
    private Aerodrom polazniAer;

    public Let() {
    }

    public Let(Integer idLet) {
        this.idLet = idLet;
    }

    public Let(Integer idLet, String duzinaLeta, Date datumPoletanja, String vremePlanirano, String vremeOcekivano, int status, int idKompan, int idKapetan, int idKopilot, String gejtOdlaz, String gejtDolaz, int brSlobodnihMesta, int carter) {
        this.idLet = idLet;
        this.duzinaLeta = duzinaLeta;
        this.datumPoletanja = datumPoletanja;
        this.vremePlanirano = vremePlanirano;
        this.vremeOcekivano = vremeOcekivano;
        this.status = status;
        this.idKompan = idKompan;
        this.idKapetan = idKapetan;
        this.idKopilot = idKopilot;
        this.gejtOdlaz = gejtOdlaz;
        this.gejtDolaz = gejtDolaz;
        this.brSlobodnihMesta = brSlobodnihMesta;
        this.carter = carter;
    }

    public Let(Integer idLet, String duzinaLeta, Date datumPoletanja, String vremePlanirano, String vremeOcekivano, int status, int idKompan, int idKapetan, int idKopilot, String gejtOdlaz, String gejtDolaz, int brSlobodnihMesta, int carter, Avion idAvion, Aerodrom odredisniAer, Aerodrom polazniAer) {
        this.idLet = idLet;
        this.duzinaLeta = duzinaLeta;
        this.datumPoletanja = datumPoletanja;
        this.vremePlanirano = vremePlanirano;
        this.vremeOcekivano = vremeOcekivano;
        this.status = status;
        this.idKompan = idKompan;
        this.idKapetan = idKapetan;
        this.idKopilot = idKopilot;
        this.gejtOdlaz = gejtOdlaz;
        this.gejtDolaz = gejtDolaz;
        this.brSlobodnihMesta = brSlobodnihMesta;
        this.carter = carter;
        this.idAvion = idAvion;
        this.odredisniAer = odredisniAer;
        this.polazniAer = polazniAer;
    }

    public Integer getIdLet() {
        return idLet;
    }

    public void setIdLet(Integer idLet) {
        this.idLet = idLet;
    }

    public String getDuzinaLeta() {
        return duzinaLeta;
    }

    public void setDuzinaLeta(String duzinaLeta) {
        this.duzinaLeta = duzinaLeta;
    }

    public Date getDatumPoletanja() {
        return datumPoletanja;
    }

    public void setDatumPoletanja(Date datumPoletanja) {
        this.datumPoletanja = datumPoletanja;
    }

    public String getVremePlanirano() {
        return vremePlanirano;
    }

    public void setVremePlanirano(String vremePlanirano) {
        this.vremePlanirano = vremePlanirano;
    }

    public String getVremeOcekivano() {
        return vremeOcekivano;
    }

    public void setVremeOcekivano(String vremeOcekivano) {
        this.vremeOcekivano = vremeOcekivano;
    }

    public String getVremeSletanja() {
        return vremeSletanja;
    }

    public void setVremeSletanja(String vremeSletanja) {
        this.vremeSletanja = vremeSletanja;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdKompan() {
        return idKompan;
    }

    public void setIdKompan(int idKompan) {
        this.idKompan = idKompan;
    }

    public int getIdKapetan() {
        return idKapetan;
    }

    public void setIdKapetan(int idKapetan) {
        this.idKapetan = idKapetan;
    }

    public int getIdKopilot() {
        return idKopilot;
    }

    public void setIdKopilot(int idKopilot) {
        this.idKopilot = idKopilot;
    }

    public String getGejtOdlaz() {
        return gejtOdlaz;
    }

    public void setGejtOdlaz(String gejtOdlaz) {
        this.gejtOdlaz = gejtOdlaz;
    }

    public String getGejtDolaz() {
        return gejtDolaz;
    }

    public void setGejtDolaz(String gejtDolaz) {
        this.gejtDolaz = gejtDolaz;
    }

    public int getBrSlobodnihMesta() {
        return brSlobodnihMesta;
    }

    public void setBrSlobodnihMesta(int brSlobodnihMesta) {
        this.brSlobodnihMesta = brSlobodnihMesta;
    }

    public int getCarter() {
        return carter;
    }

    public void setCarter(int carter) {
        this.carter = carter;
    }

    public String getIzmene() {
        return izmene;
    }

    public void setIzmene(String izmene) {
        this.izmene = izmene;
    }

    public Avion getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(Avion idAvion) {
        this.idAvion = idAvion;
    }

    public Aerodrom getNovaDestinacija() {
        return novaDestinacija;
    }

    public void setNovaDestinacija(Aerodrom novaDestinacija) {
        this.novaDestinacija = novaDestinacija;
    }

    public Aerodrom getOdredisniAer() {
        return odredisniAer;
    }

    public void setOdredisniAer(Aerodrom odredisniAer) {
        this.odredisniAer = odredisniAer;
    }

    public Aerodrom getPolazniAer() {
        return polazniAer;
    }

    public void setPolazniAer(Aerodrom polazniAer) {
        this.polazniAer = polazniAer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLet != null ? idLet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Let)) {
            return false;
        }
        Let other = (Let) object;
        if ((this.idLet == null && other.idLet != null) || (this.idLet != null && !this.idLet.equals(other.idLet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hibernate.Let[ idLet=" + idLet + " ]";
    }

    @Override
    public int compareTo(Let o) {
        return getDatumPoletanja().compareTo(o.getDatumPoletanja());
    }
    
}
