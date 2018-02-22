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
@Table(name = "stjuardese")
@NamedQueries({
    @NamedQuery(name = "Stjuardese.findAll", query = "SELECT s FROM Stjuardese s")})
public class Stjuardese implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StjuardesePK stjuardesePK;
    @JoinColumn(name = "id_lett", referencedColumnName = "id_let", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Let let;

    public Stjuardese() {
    }

    public Stjuardese(StjuardesePK stjuardesePK) {
        this.stjuardesePK = stjuardesePK;
    }

    public Stjuardese(int idLett, int idKorisnikk) {
        this.stjuardesePK = new StjuardesePK(idLett, idKorisnikk);
    }

    public StjuardesePK getStjuardesePK() {
        return stjuardesePK;
    }

    public void setStjuardesePK(StjuardesePK stjuardesePK) {
        this.stjuardesePK = stjuardesePK;
    }

    public Let getLet() {
        return let;
    }

    public void setLet(Let let) {
        this.let = let;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stjuardesePK != null ? stjuardesePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stjuardese)) {
            return false;
        }
        Stjuardese other = (Stjuardese) object;
        if ((this.stjuardesePK == null && other.stjuardesePK != null) || (this.stjuardesePK != null && !this.stjuardesePK.equals(other.stjuardesePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hibernate.Stjuardese[ stjuardesePK=" + stjuardesePK + " ]";
    }
    
}
