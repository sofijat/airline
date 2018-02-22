/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ja
 */
@Embeddable
public class ImaTerminalPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "id_aerod")
    private String idAerod;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_termi")
    private int idTermi;

    public ImaTerminalPK() {
    }

    public ImaTerminalPK(String idAerod, int idTermi) {
        this.idAerod = idAerod;
        this.idTermi = idTermi;
    }

    public String getIdAerod() {
        return idAerod;
    }

    public void setIdAerod(String idAerod) {
        this.idAerod = idAerod;
    }

    public int getIdTermi() {
        return idTermi;
    }

    public void setIdTermi(int idTermi) {
        this.idTermi = idTermi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAerod != null ? idAerod.hashCode() : 0);
        hash += (int) idTermi;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImaTerminalPK)) {
            return false;
        }
        ImaTerminalPK other = (ImaTerminalPK) object;
        if ((this.idAerod == null && other.idAerod != null) || (this.idAerod != null && !this.idAerod.equals(other.idAerod))) {
            return false;
        }
        if (this.idTermi != other.idTermi) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hibernate.ImaTerminalPK[ idAerod=" + idAerod + ", idTermi=" + idTermi + " ]";
    }
    
}
