package fit.biktjv.customers.integration.springdata;

import fit.biktjv.customers.domain.Cust;
import fit.biktjv.customers.domain.Rcpt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RcptRep extends JpaRepository<Rcpt, Long> {
    List<Rcpt> findByCustId(Long custId);
}
