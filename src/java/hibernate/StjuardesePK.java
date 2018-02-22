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
public class StjuardesePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_lett")
    private int idLett;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_korisnikk")
    private int idKorisnikk;

    public StjuardesePK() {
    }

    public StjuardesePK(int idLett, int idKorisnikk) {
        this.idLett = idLett;
        this.idKorisnikk = idKorisnikk;
    }

    public int getIdLett() {
        return idLett;
    }

    public void setIdLett(int idLett) {
        this.idLett = idLett;
    }

    public int getIdKorisnikk() {
        return idKorisnikk;
    }

    public void setIdKorisnikk(int idKorisnikk) {
        this.idKorisnikk = idKorisnikk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idLett;
        hash += (int) idKorisnikk;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StjuardesePK)) {
            return false;
        }
        StjuardesePK other = (StjuardesePK) object;
        if (this.idLett != other.idLett) {
            return false;
        }
        if (this.idKorisnikk != other.idKorisnikk) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hibernate.StjuardesePK[ idLett=" + idLett + ", idKorisnikk=" + idKorisnikk + " ]";
    }
    
}
