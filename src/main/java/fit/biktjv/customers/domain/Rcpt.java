package fit.biktjv.customers.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "receipts")
@NamedQuery(name = "allRcpts", query = "select rcpt from Rcpt rcpt")
@NamedQuery(name = "findByCusId", query = "select r from Rcpt r where r.cust.id = ?1")
public class Rcpt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String dsc;

    @ManyToOne(optional = false)
    Cust cust;

    public Rcpt() {
    }

    public Rcpt(RcptDTO rcptDTO, Cust cust) {
        id = rcptDTO.id();
        dsc = rcptDTO.dsc();
        this.cust = cust;
    }

    public record RcptDTO(Long id, String dsc, Long custId) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    public Cust getCust() {
        return cust;
    }

    public void setCust(Cust cust) {
        this.cust = cust;
    }


    public RcptDTO toDTO() {
        return new RcptDTO(id, dsc, cust.getId());
    }

}
