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
@Table(name = "aerodrom")
@NamedQueries({
    @NamedQuery(name = "Aerodrom.findAll", query = "SELECT a FROM Aerodrom a")})
public class Aerodrom implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "iata")
    private String iata;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "zemlja")
    private String zemlja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "broj_pisti")
    private int brojPisti;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "grad")
    private String grad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aerodrom")
    private Collection<ImaTerminal> imaTerminalCollection;
    @OneToMany(mappedBy = "novaDestinacija")
    private Collection<Let> letCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "odredisniAer")
    private Collection<Let> letCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "polazniAer")
    private Collection<Let> letCollection2;

    public Aerodrom() {
    }

    public Aerodrom(String iata) {
        this.iata = iata;
    }

    public Aerodrom(String iata, String naziv, String zemlja, int brojPisti, String grad) {
        this.iata = iata;
        this.naziv = naziv;
        this.zemlja = zemlja;
        this.brojPisti = brojPisti;
        this.grad = grad;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getZemlja() {
        return zemlja;
    }

    public void setZemlja(String zemlja) {
        this.zemlja = zemlja;
    }

    public int getBrojPisti() {
        return brojPisti;
    }

    public void setBrojPisti(int brojPisti) {
        this.brojPisti = brojPisti;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public Collection<ImaTerminal> getImaTerminalCollection() {
        return imaTerminalCollection;
    }

    public void setImaTerminalCollection(Collection<ImaTerminal> imaTerminalCollection) {
        this.imaTerminalCollection = imaTerminalCollection;
    }

    public Collection<Let> getLetCollection() {
        return letCollection;
    }

    public void setLetCollection(Collection<Let> letCollection) {
        this.letCollection = letCollection;
    }

    public Collection<Let> getLetCollection1() {
        return letCollection1;
    }

    public void setLetCollection1(Collection<Let> letCollection1) {
        this.letCollection1 = letCollection1;
    }

    public Collection<Let> getLetCollection2() {
        return letCollection2;
    }

    public void setLetCollection2(Collection<Let> letCollection2) {
        this.letCollection2 = letCollection2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iata != null ? iata.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aerodrom)) {
            return false;
        }
        Aerodrom other = (Aerodrom) object;
        if ((this.iata == null && other.iata != null) || (this.iata != null && !this.iata.equals(other.iata))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hibernate.Aerodrom[ iata=" + iata + " ]";
    }
    
}
