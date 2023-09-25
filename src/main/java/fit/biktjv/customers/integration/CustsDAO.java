package fit.biktjv.customers.integration;

import fit.biktjv.customers.domain.Cust;

import java.util.List;
import java.util.Optional;

public interface CustsDAO {

    List<Cust> all();

    Long create(Cust.CustDTO custDTO);


    void delete(Long id);

    void changeName(Cust.CustDTO custDTO);

    Optional<Cust> findById(Long aLong);
}
