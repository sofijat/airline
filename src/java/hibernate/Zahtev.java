/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ja
 */
@Entity
@Table(name = "zahtev")
@NamedQueries({
    @NamedQuery(name = "Zahtev.findAll", query = "SELECT z FROM Zahtev z")})
public class Zahtev implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_zahtev")
    private Integer idZahtev;
    @JoinColumn(name = "id_kori", referencedColumnName = "id_korisnik")
    @ManyToOne(optional = false)
    private Korisnik idKori;

    public Zahtev() {
    }

    public Zahtev(Integer idZahtev, Korisnik idKori) {
        this.idZahtev = idZahtev;
        this.idKori = idKori;
    }

    public Zahtev(Integer idZahtev) {
        this.idZahtev = idZahtev;
    }

    public Integer getIdZahtev() {
        return idZahtev;
    }

    public void setIdZahtev(Integer idZahtev) {
        this.idZahtev = idZahtev;
    }

    public Korisnik getIdKori() {
        return idKori;
    }

    public void setIdKori(Korisnik idKori) {
        this.idKori = idKori;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idZahtev != null ? idZahtev.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zahtev)) {
            return false;
        }
        Zahtev other = (Zahtev) object;
        if ((this.idZahtev == null && other.idZahtev != null) || (this.idZahtev != null && !this.idZahtev.equals(other.idZahtev))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hibernate.Zahtev[ idZahtev=" + idZahtev + " ]";
    }
    
}
