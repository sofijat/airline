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
@Table(name = "ima_gejt")
@NamedQueries({
    @NamedQuery(name = "ImaGejt.findAll", query = "SELECT i FROM ImaGejt i")})
public class ImaGejt implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ImaGejtPK imaGejtPK;
    @JoinColumn(name = "id_te", referencedColumnName = "id_terminal", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Terminal terminal;

    public ImaGejt() {
    }

    public ImaGejt(ImaGejtPK imaGejtPK) {
        this.imaGejtPK = imaGejtPK;
    }

    public ImaGejt(int idTe, String idGe) {
        this.imaGejtPK = new ImaGejtPK(idTe, idGe);
    }

    public ImaGejtPK getImaGejtPK() {
        return imaGejtPK;
    }

    public void setImaGejtPK(ImaGejtPK imaGejtPK) {
        this.imaGejtPK = imaGejtPK;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (imaGejtPK != null ? imaGejtPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImaGejt)) {
            return false;
        }
        ImaGejt other = (ImaGejt) object;
        if ((this.imaGejtPK == null && other.imaGejtPK != null) || (this.imaGejtPK != null && !this.imaGejtPK.equals(other.imaGejtPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hibernate.ImaGejt[ imaGejtPK=" + imaGejtPK + " ]";
    }
    
}
