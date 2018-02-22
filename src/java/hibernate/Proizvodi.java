/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Ja
 */
@Entity
@Table(name = "proizvodi")
@NamedQueries({
    @NamedQuery(name = "Proizvodi.findAll", query = "SELECT p FROM Proizvodi p")})
public class Proizvodi implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProizvodiPK proizvodiPK;
    @JoinColumn(name = "id_proizvo", referencedColumnName = "id_proizvodjac", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proizvodjac proizvodjac;

    public Proizvodi() {
    }

    public Proizvodi(ProizvodiPK proizvodiPK) {
        this.proizvodiPK = proizvodiPK;
    }

    public Proizvodi(int idProizvo, int idTipp) {
        this.proizvodiPK = new ProizvodiPK(idProizvo, idTipp);
    }

    public ProizvodiPK getProizvodiPK() {
        return proizvodiPK;
    }

    public void setProizvodiPK(ProizvodiPK proizvodiPK) {
        this.proizvodiPK = proizvodiPK;
    }

    public Proizvodjac getProizvodjac() {
        return proizvodjac;
    }

    public void setProizvodjac(Proizvodjac proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proizvodiPK != null ? proizvodiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proizvodi)) {
            return false;
        }
        Proizvodi other = (Proizvodi) object;
        if ((this.proizvodiPK == null && other.proizvodiPK != null) || (this.proizvodiPK != null && !this.proizvodiPK.equals(other.proizvodiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hibernate.Proizvodi[ proizvodiPK=" + proizvodiPK + " ]";
    }
    
}
