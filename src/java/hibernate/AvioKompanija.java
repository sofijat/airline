/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ja
 */
@Entity
@Table(name = "avio_kompanija")
@NamedQueries({
    @NamedQuery(name = "AvioKompanija.findAll", query = "SELECT a FROM AvioKompanija a")})
public class AvioKompanija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_kompanija")
    private Integer idKompanija;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "adresa")
    private String adresa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "zemlja")
    private String zemlja;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "sajt")
    private String sajt;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "email")
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kompOd")
    private Collection<Iznajmljivanje> iznajmljivanjeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kompZa")
    private Collection<Iznajmljivanje> iznajmljivanjeCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idKompani")
    private Collection<Korisnik> korisnikCollection;

    public AvioKompanija() {
    }

    public AvioKompanija(Integer idKompanija) {
        this.idKompanija = idKompanija;
    }

    public AvioKompanija(Integer idKompanija, String naziv, String adresa, String zemlja, String sajt, String email) {
        this.idKompanija = idKompanija;
        this.naziv = naziv;
        this.adresa = adresa;
        this.zemlja = zemlja;
        this.sajt = sajt;
        this.email = email;
    }

    public Integer getIdKompanija() {
        return idKompanija;
    }

    public void setIdKompanija(Integer idKompanija) {
        this.idKompanija = idKompanija;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getZemlja() {
        return zemlja;
    }

    public void setZemlja(String zemlja) {
        this.zemlja = zemlja;
    }

    public String getSajt() {
        return sajt;
    }

    public void setSajt(String sajt) {
        this.sajt = sajt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Iznajmljivanje> getIznajmljivanjeCollection() {
        return iznajmljivanjeCollection;
    }

    public void setIznajmljivanjeCollection(Collection<Iznajmljivanje> iznajmljivanjeCollection) {
        this.iznajmljivanjeCollection = iznajmljivanjeCollection;
    }

    public Collection<Iznajmljivanje> getIznajmljivanjeCollection1() {
        return iznajmljivanjeCollection1;
    }

    public void setIznajmljivanjeCollection1(Collection<Iznajmljivanje> iznajmljivanjeCollection1) {
        this.iznajmljivanjeCollection1 = iznajmljivanjeCollection1;
    }

    public Collection<Korisnik> getKorisnikCollection() {
        return korisnikCollection;
    }

    public void setKorisnikCollection(Collection<Korisnik> korisnikCollection) {
        this.korisnikCollection = korisnikCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKompanija != null ? idKompanija.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AvioKompanija)) {
            return false;
        }
        AvioKompanija other = (AvioKompanija) object;
        if ((this.idKompanija == null && other.idKompanija != null) || (this.idKompanija != null && !this.idKompanija.equals(other.idKompanija))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hibernate.AvioKompanija[ idKompanija=" + idKompanija + " ]";
    }
    
}
