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
public class ImaGejtPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_te")
    private int idTe;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "id_ge")
    private String idGe;

    public ImaGejtPK() {
    }

    public ImaGejtPK(int idTe, String idGe) {
        this.idTe = idTe;
        this.idGe = idGe;
    }

    public int getIdTe() {
        return idTe;
    }

    public void setIdTe(int idTe) {
        this.idTe = idTe;
    }

    public String getIdGe() {
        return idGe;
    }

    public void setIdGe(String idGe) {
        this.idGe = idGe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idTe;
        hash += (idGe != null ? idGe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImaGejtPK)) {
            return false;
        }
        ImaGejtPK other = (ImaGejtPK) object;
        if (this.idTe != other.idTe) {
            return false;
        }
        if ((this.idGe == null && other.idGe != null) || (this.idGe != null && !this.idGe.equals(other.idGe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hibernate.ImaGejtPK[ idTe=" + idTe + ", idGe=" + idGe + " ]";
    }
    
}
