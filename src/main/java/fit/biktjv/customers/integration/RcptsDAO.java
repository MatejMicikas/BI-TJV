package fit.biktjv.customers.integration;

import fit.biktjv.customers.domain.Rcpt;

import java.util.List;
import java.util.Optional;

public interface RcptsDAO {

    List<Rcpt> all();

    public Long create(Rcpt.RcptDTO rcptDTO);

    Optional<Rcpt> findById(Long id);

    void delete(Long id);

    List<Rcpt> findByCustId(Long custId);
}
