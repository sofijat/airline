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
public class ImalicencuPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_pilot")
    private int idPilot;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "brojLicence")
    private String brojLicence;

    public ImalicencuPK() {
    }

    public ImalicencuPK(int idPilot, String brojLicence) {
        this.idPilot = idPilot;
        this.brojLicence = brojLicence;
    }

    public int getIdPilot() {
        return idPilot;
    }

    public void setIdPilot(int idPilot) {
        this.idPilot = idPilot;
    }

    public String getBrojLicence() {
        return brojLicence;
    }

    public void setBrojLicence(String brojLicence) {
        this.brojLicence = brojLicence;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPilot;
        hash += (brojLicence != null ? brojLicence.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImalicencuPK)) {
            return false;
        }
        ImalicencuPK other = (ImalicencuPK) object;
        if (this.idPilot != other.idPilot) {
            return false;
        }
        if ((this.brojLicence == null && other.brojLicence != null) || (this.brojLicence != null && !this.brojLicence.equals(other.brojLicence))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hibernate.ImalicencuPK[ idPilot=" + idPilot + ", brojLicence=" + brojLicence + " ]";
    }
    
}
