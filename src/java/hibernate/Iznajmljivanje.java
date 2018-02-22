/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ja
 */
@Entity
@Table(name = "iznajmljivanje")
@NamedQueries({
    @NamedQuery(name = "Iznajmljivanje.findAll", query = "SELECT i FROM Iznajmljivanje i")})
public class Iznajmljivanje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datumOd")
    @Temporal(TemporalType.DATE)
    private Date datumOd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datumDo")
    @Temporal(TemporalType.DATE)
    private Date datumDo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ponuda")
    private int ponuda;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prihvaceno")
    private int prihvaceno;
    @JoinColumn(name = "idavion", referencedColumnName = "id_avion")
    @ManyToOne(optional = false)
    private Avion idavion;
    @JoinColumn(name = "kompOd", referencedColumnName = "id_kompanija")
    @ManyToOne(optional = false)
    private AvioKompanija kompOd;
    @JoinColumn(name = "kompZa", referencedColumnName = "id_kompanija")
    @ManyToOne(optional = false)
    private AvioKompanija kompZa;

    public Iznajmljivanje() {
    }

    public Iznajmljivanje(Integer id) {
        this.id = id;
    }

    public Iznajmljivanje(Integer id, Date datumOd, Date datumDo, int ponuda, int prihvaceno) {
        this.id = id;
        this.datumOd = datumOd;
        this.datumDo = datumDo;
        this.ponuda = ponuda;
        this.prihvaceno = prihvaceno;
    }

    public Iznajmljivanje(Integer id, Date datumOd, Date datumDo, int ponuda, int prihvaceno, Avion idavion, AvioKompanija kompOd, AvioKompanija kompZa) {
        this.id = id;
        this.datumOd = datumOd;
        this.datumDo = datumDo;
        this.ponuda = ponuda;
        this.prihvaceno = prihvaceno;
        this.idavion = idavion;
        this.kompOd = kompOd;
        this.kompZa = kompZa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(Date datumOd) {
        this.datumOd = datumOd;
    }

    public Date getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(Date datumDo) {
        this.datumDo = datumDo;
    }

    public int getPonuda() {
        return ponuda;
    }

    public void setPonuda(int ponuda) {
        this.ponuda = ponuda;
    }

    public int getPrihvaceno() {
        return prihvaceno;
    }

    public void setPrihvaceno(int prihvaceno) {
        this.prihvaceno = prihvaceno;
    }

    public Avion getIdavion() {
        return idavion;
    }

    public void setIdavion(Avion idavion) {
        this.idavion = idavion;
    }

    public AvioKompanija getKompOd() {
        return kompOd;
    }

    public void setKompOd(AvioKompanija kompOd) {
        this.kompOd = kompOd;
    }

    public AvioKompanija getKompZa() {
        return kompZa;
    }

    public void setKompZa(AvioKompanija kompZa) {
        this.kompZa = kompZa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Iznajmljivanje)) {
            return false;
        }
        Iznajmljivanje other = (Iznajmljivanje) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hibernate.Iznajmljivanje[ id=" + id + " ]";
    }
    
}
