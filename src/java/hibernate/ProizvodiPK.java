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

/**
 *
 * @author Ja
 */
@Embeddable
public class ProizvodiPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_proizvo")
    private int idProizvo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tipp")
    private int idTipp;

    public ProizvodiPK() {
    }

    public ProizvodiPK(int idProizvo, int idTipp) {
        this.idProizvo = idProizvo;
        this.idTipp = idTipp;
    }

    public int getIdProizvo() {
        return idProizvo;
    }

    public void setIdProizvo(int idProizvo) {
        this.idProizvo = idProizvo;
    }

    public int getIdTipp() {
        return idTipp;
    }

    public void setIdTipp(int idTipp) {
        this.idTipp = idTipp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idProizvo;
        hash += (int) idTipp;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProizvodiPK)) {
            return false;
        }
        ProizvodiPK other = (ProizvodiPK) object;
        if (this.idProizvo != other.idProizvo) {
            return false;
        }
        if (this.idTipp != other.idTipp) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hibernate.ProizvodiPK[ idProizvo=" + idProizvo + ", idTipp=" + idTipp + " ]";
    }
    
}
