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
@Table(name = "ima_terminal")
@NamedQueries({
    @NamedQuery(name = "ImaTerminal.findAll", query = "SELECT i FROM ImaTerminal i")})
public class ImaTerminal implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ImaTerminalPK imaTerminalPK;
    @JoinColumn(name = "id_aerod", referencedColumnName = "iata", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Aerodrom aerodrom;

    public ImaTerminal() {
    }

    public ImaTerminal(ImaTerminalPK imaTerminalPK) {
        this.imaTerminalPK = imaTerminalPK;
    }

    public ImaTerminal(String idAerod, int idTermi) {
        this.imaTerminalPK = new ImaTerminalPK(idAerod, idTermi);
    }

    public ImaTerminalPK getImaTerminalPK() {
        return imaTerminalPK;
    }

    public void setImaTerminalPK(ImaTerminalPK imaTerminalPK) {
        this.imaTerminalPK = imaTerminalPK;
    }

    public Aerodrom getAerodrom() {
        return aerodrom;
    }

    public void setAerodrom(Aerodrom aerodrom) {
        this.aerodrom = aerodrom;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (imaTerminalPK != null ? imaTerminalPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImaTerminal)) {
            return false;
        }
        ImaTerminal other = (ImaTerminal) object;
        if ((this.imaTerminalPK == null && other.imaTerminalPK != null) || (this.imaTerminalPK != null && !this.imaTerminalPK.equals(other.imaTerminalPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hibernate.ImaTerminal[ imaTerminalPK=" + imaTerminalPK + " ]";
    }
    
}
