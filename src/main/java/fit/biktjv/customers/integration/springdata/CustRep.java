package fit.biktjv.customers.integration.springdata;

import fit.biktjv.customers.domain.Cust;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustRep extends JpaRepository<Cust, Long> {
}
