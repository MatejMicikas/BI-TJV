package fit.biktjv.customers.integration.memory;

import fit.biktjv.customers.domain.Cust;
import fit.biktjv.customers.integration.CustsDAO;

import java.util.*;

public class CustsDAOMem implements CustsDAO {
    public static CustsDAO singleton = new CustsDAOMem();

    private CustsDAOMem() {
    }

    private Map<Long, Cust> customers = new HashMap<>(Map.of(
            // 1L, new Cust(1L, "Tom"),
            // 2L, new Cust(2L, "Bob")
    ));

    @Override
    public List<Cust> all() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Long create(Cust.CustDTO custDTO) {
        Long id = customers.keySet().stream()
                .mapToLong(Long::longValue)
                .max().orElse(0) + 1;
//        long id = customers.keySet().stream()
//                .max((o1, o2) -> (int) (o1 - o2)).orElse(0L) + 1;
        Cust cust = new Cust(id, custDTO.name());
        customers.put(cust.getId(), cust);
        return cust.getId();
    }

    @Override
    public void delete(Long id) {
        customers.remove(id);
    }

    @Override
    public void changeName(Cust.CustDTO custDTO) {
        customers.get(custDTO.id()).setName(custDTO.name());
    }

    @Override
    public Optional<Cust> findById(Long id) {
        System.out.println(id);
        Cust c = customers.get(id);
        return Optional.ofNullable(c);
    }

}
