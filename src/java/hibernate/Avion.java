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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "avion")
@NamedQueries({
    @NamedQuery(name = "Avion.findAll", query = "SELECT a FROM Avion a")})
public class Avion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_avion")
    private Integer idAvion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_kompanija")
    private int idKompanija;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idavion")
    private Collection<Iznajmljivanje> iznajmljivanjeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAvion")
    private Collection<Let> letCollection;
    @JoinColumn(name = "id_proizvodjac", referencedColumnName = "id_proizvodjac")
    @ManyToOne(optional = false)
    private Proizvodjac idProizvodjac;
    @JoinColumn(name = "id_ttip", referencedColumnName = "id_tip")
    @ManyToOne(optional = false)
    private Tip idTtip;

    public Avion() {
    }

    public Avion(Integer idAvion) {
        this.idAvion = idAvion;
    }

    public Avion(Integer idAvion, String naziv, int idKompanija) {
        this.idAvion = idAvion;
        this.naziv = naziv;
        this.idKompanija = idKompanija;
    }

    public Avion(Integer idAvion, String naziv, int idKompanija, Proizvodjac idProizvodjac, Tip idTtip) {
        this.idAvion = idAvion;
        this.naziv = naziv;
        this.idKompanija = idKompanija;
        this.idProizvodjac = idProizvodjac;
        this.idTtip = idTtip;
    }

    public Integer getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(Integer idAvion) {
        this.idAvion = idAvion;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getIdKompanija() {
        return idKompanija;
    }

    public void setIdKompanija(int idKompanija) {
        this.idKompanija = idKompanija;
    }

    public Collection<Iznajmljivanje> getIznajmljivanjeCollection() {
        return iznajmljivanjeCollection;
    }

    public void setIznajmljivanjeCollection(Collection<Iznajmljivanje> iznajmljivanjeCollection) {
        this.iznajmljivanjeCollection = iznajmljivanjeCollection;
    }

    public Collection<Let> getLetCollection() {
        return letCollection;
    }

    public void setLetCollection(Collection<Let> letCollection) {
        this.letCollection = letCollection;
    }

    public Proizvodjac getIdProizvodjac() {
        return idProizvodjac;
    }

    public void setIdProizvodjac(Proizvodjac idProizvodjac) {
        this.idProizvodjac = idProizvodjac;
    }

    public Tip getIdTtip() {
        return idTtip;
    }

    public void setIdTtip(Tip idTtip) {
        this.idTtip = idTtip;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAvion != null ? idAvion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Avion)) {
            return false;
        }
        Avion other = (Avion) object;
        if ((this.idAvion == null && other.idAvion != null) || (this.idAvion != null && !this.idAvion.equals(other.idAvion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hibernate.Avion[ idAvion=" + idAvion + " ]";
    }
    
}
