package fit.biktjv.customers.integration.springdata;

import fit.biktjv.customers.domain.Cust;
import fit.biktjv.customers.integration.CustsDAO;
import fit.biktjv.customers.rest.CustomersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public class CustsDAOSpringData implements CustsDAO {

    @Autowired
    CustRep custRep;

    @Override
    @Transactional
    public List<Cust> all() {
        return custRep.findAll();
    }

    @Override
    @Transactional
    public Long create(Cust.CustDTO custDTO) {
        Cust cust = new Cust(custDTO);
        custRep.save(cust);
        return cust.getId();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (custRep.existsById(id)) {
            custRep.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "cust not found");
        }
    }

    @Override
    @Transactional
    public void changeName(Cust.CustDTO custDTO) {
        if (custRep.existsById(custDTO.id())) {
            Cust cust = custRep.findById(custDTO.id()).get();
            cust.setName(custDTO.name());
            custRep.save(cust);
        }
    }

    @Override
    public Optional<Cust> findById(Long id) {
        return custRep.findById(id);
    }
}
