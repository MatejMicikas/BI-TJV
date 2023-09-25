package fit.biktjv.customers.integration.springdata;

import fit.biktjv.customers.domain.Cust;
import fit.biktjv.customers.domain.Rcpt;
import fit.biktjv.customers.integration.RcptsDAO;
import fit.biktjv.customers.rest.CustomersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
public class RcptsDAOSpringData implements RcptsDAO {

    @Autowired
    RcptRep rcptRep;
    @Autowired
    CustRep custRep;

    @Override
    @Transactional
    public List<Rcpt> all() {
        return rcptRep.findAll();
    }

    @Override
    @Transactional
    public Long create(Rcpt.RcptDTO rcptDTO) {
        Optional<Cust> cust = custRep.findById(rcptDTO.custId());
        if (cust.isEmpty()) throw new CustomersException("invalid custid in receipt");
        Rcpt rcpt = new Rcpt(rcptDTO, cust.get());
        rcptRep.save(rcpt);
        return rcpt.getId();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (rcptRep.existsById(id))
            rcptRep.deleteById(id);
    }

    @Override
    public List<Rcpt> findByCustId(Long custId) {
        return rcptRep.findByCustId(custId);
    }

    @Override
    public Optional<Rcpt> findById(Long id) {
        return rcptRep.findById(id);
    }
}
