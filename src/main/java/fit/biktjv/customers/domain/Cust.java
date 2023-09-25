package fit.biktjv.customers.domain;

import jakarta.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "customers")
@NamedQuery(name = "allCusts", query = "select c from Cust c")
public class Cust {
    public static record CustDTO(Long id, String name) {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    @OneToMany(mappedBy = "cust", orphanRemoval = true)
    Collection<Rcpt> rcpts;

    public Cust() {
    }

    public Cust(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CustDTO toDTO() {
        return new CustDTO(id, name);
    }

    public Cust(CustDTO custDTO) {
        this.name = custDTO.name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
