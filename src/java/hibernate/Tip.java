/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ja
 */
@Entity
@Table(name = "tip")
@NamedQueries({
    @NamedQuery(name = "Tip.findAll", query = "SELECT t FROM Tip t")})
public class Tip implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tip")
    private Integer idTip;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "licenca")
    private String licenca;
    @Basic(optional = false)
    @NotNull
    @Column(name = "max_sedista")
    private int maxSedista;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "brojLicence")
    private String brojLicence;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTtip")
    private Collection<Avion> avionCollection;

    public Tip() {
    }

    public Tip(Integer idTip) {
        this.idTip = idTip;
    }

    public Tip(Integer idTip, String naziv, String licenca, int maxSedista, String brojLicence) {
        this.idTip = idTip;
        this.naziv = naziv;
        this.licenca = licenca;
        this.maxSedista = maxSedista;
        this.brojLicence = brojLicence;
    }

    public Integer getIdTip() {
        return idTip;
    }

    public void setIdTip(Integer idTip) {
        this.idTip = idTip;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getLicenca() {
        return licenca;
    }

    public void setLicenca(String licenca) {
        this.licenca = licenca;
    }

    public int getMaxSedista() {
        return maxSedista;
    }

    public void setMaxSedista(int maxSedista) {
        this.maxSedista = maxSedista;
    }

    public String getBrojLicence() {
        return brojLicence;
    }

    public void setBrojLicence(String brojLicence) {
        this.brojLicence = brojLicence;
    }

    public Collection<Avion> getAvionCollection() {
        return avionCollection;
    }

    public void setAvionCollection(Collection<Avion> avionCollection) {
        this.avionCollection = avionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTip != null ? idTip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tip)) {
            return false;
        }
        Tip other = (Tip) object;
        if ((this.idTip == null && other.idTip != null) || (this.idTip != null && !this.idTip.equals(other.idTip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hibernate.Tip[ idTip=" + idTip + " ]";
    }
    
}
