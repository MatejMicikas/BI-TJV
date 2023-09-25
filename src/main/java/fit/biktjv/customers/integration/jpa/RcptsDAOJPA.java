package fit.biktjv.customers.integration.jpa;

import fit.biktjv.customers.domain.Cust;
import fit.biktjv.customers.domain.Rcpt;
import fit.biktjv.customers.integration.RcptsDAO;
import fit.biktjv.customers.rest.CustomersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class RcptsDAOJPA implements RcptsDAO {

    @Autowired
    EntityManager em;

    @Override
    @Transactional
    public List<Rcpt> all() {
        TypedQuery<Rcpt> q = em.createNamedQuery("allRcpts", Rcpt.class);
        return q.getResultList();
    }

    @Override
    @Transactional
    public Long create(Rcpt.RcptDTO rcptDTO) {
        Cust cust = em.find(Cust.class, rcptDTO.custId());
        if (cust == null)
            throw new CustomersException("invalid custid");
        Rcpt rcpt = new Rcpt(rcptDTO, cust);
        em.persist(rcpt);
        em.flush();
        return rcpt.getId();
    }

    @Override
    public Optional<Rcpt> findById(Long id) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Cust cust = em.find(Cust.class, id);
        if (cust == null) throw new CustomersException("invalid custid");
        em.remove(cust);
    }

    @Override
    public List<Rcpt> findByCustId(Long custId) {
        TypedQuery<Rcpt> q = em.createNamedQuery("rcptsByCustId", Rcpt.class);
        return q.getResultList();
    }
}
