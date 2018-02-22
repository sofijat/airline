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
@Table(name = "imalicencu")
@NamedQueries({
    @NamedQuery(name = "Imalicencu.findAll", query = "SELECT i FROM Imalicencu i")})
public class Imalicencu implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ImalicencuPK imalicencuPK;
    @JoinColumn(name = "id_pilot", referencedColumnName = "id_korisnik", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Korisnik korisnik;

    public Imalicencu() {
    }

    public Imalicencu(ImalicencuPK imalicencuPK) {
        this.imalicencuPK = imalicencuPK;
    }

    public Imalicencu(int idPilot, String brojLicence) {
        this.imalicencuPK = new ImalicencuPK(idPilot, brojLicence);
    }

    public ImalicencuPK getImalicencuPK() {
        return imalicencuPK;
    }

    public void setImalicencuPK(ImalicencuPK imalicencuPK) {
        this.imalicencuPK = imalicencuPK;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (imalicencuPK != null ? imalicencuPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Imalicencu)) {
            return false;
        }
        Imalicencu other = (Imalicencu) object;
        if ((this.imalicencuPK == null && other.imalicencuPK != null) || (this.imalicencuPK != null && !this.imalicencuPK.equals(other.imalicencuPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hibernate.Imalicencu[ imalicencuPK=" + imalicencuPK + " ]";
    }
    
}
