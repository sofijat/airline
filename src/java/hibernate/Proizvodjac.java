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
@Table(name = "proizvodjac")
@NamedQueries({
    @NamedQuery(name = "Proizvodjac.findAll", query = "SELECT p FROM Proizvodjac p")})
public class Proizvodjac implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_proizvodjac")
    private Integer idProizvodjac;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "mesto")
    private String mesto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proizvodjac")
    private Collection<Proizvodi> proizvodiCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProizvodjac")
    private Collection<Avion> avionCollection;

    public Proizvodjac() {
    }

    public Proizvodjac(Integer idProizvodjac) {
        this.idProizvodjac = idProizvodjac;
    }

    public Proizvodjac(Integer idProizvodjac, String naziv, String mesto) {
        this.idProizvodjac = idProizvodjac;
        this.naziv = naziv;
        this.mesto = mesto;
    }

    public Integer getIdProizvodjac() {
        return idProizvodjac;
    }

    public void setIdProizvodjac(Integer idProizvodjac) {
        this.idProizvodjac = idProizvodjac;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public Collection<Proizvodi> getProizvodiCollection() {
        return proizvodiCollection;
    }

    public void setProizvodiCollection(Collection<Proizvodi> proizvodiCollection) {
        this.proizvodiCollection = proizvodiCollection;
    }

    public Collection<Avion> getAvionCollection() {
        return avionCollection;
    }

    public void setAvionCollection(Collection<Avion> avionCollection) {
        this.avionCollection = avionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProizvodjac != null ? idProizvodjac.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proizvodjac)) {
            return false;
        }
        Proizvodjac other = (Proizvodjac) object;
        if ((this.idProizvodjac == null && other.idProizvodjac != null) || (this.idProizvodjac != null && !this.idProizvodjac.equals(other.idProizvodjac))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hibernate.Proizvodjac[ idProizvodjac=" + idProizvodjac + " ]";
    }
    
}
