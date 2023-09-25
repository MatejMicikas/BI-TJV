package fit.biktjv.customers.integration.jpa;

import fit.biktjv.customers.domain.Cust;
import fit.biktjv.customers.integration.CustsDAO;
import fit.biktjv.customers.rest.CustomersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class CustsDAOJPA implements CustsDAO {

    @Autowired
    EntityManager em;

    @Override
    @Transactional
    public List<Cust> all() {
        TypedQuery<Cust> q = em.createNamedQuery("allCusts", Cust.class);
        return q.getResultList();
    }

    @Override
    @Transactional
    public Long create(Cust.CustDTO custDTO) {
        Cust cust = new Cust(custDTO);
        em.persist(cust);
        em.flush();
        return cust.getId();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Cust> cust = findById(id);
        if (cust.isEmpty()) throw new CustomersException("invalid custid");
        em.remove(cust.get());
    }

    @Override
    @Transactional
    public void changeName(Cust.CustDTO custDTO) {
        Optional<Cust> cust = findById(custDTO.id());
        if (cust.isEmpty()) throw new CustomersException("invalid custid");
        Cust custObject = em.find(Cust.class, cust.get().getId());
        custObject.setName(custDTO.name());
        em.merge(custObject);
    }

    @Override
    @Transactional
    public Optional<Cust> findById(Long id) {
        return Optional.ofNullable(em.find(Cust.class, id));
    }
}
