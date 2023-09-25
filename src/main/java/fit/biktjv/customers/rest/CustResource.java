package fit.biktjv.customers.rest;

import fit.biktjv.customers.domain.Cust;
import fit.biktjv.customers.integration.CustsDAO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/rest/customers")
public class CustResource {

    @Autowired
    CustsDAO custsDAO;// = CustomersDAOImpl.singleton;
    @Autowired
    HttpServletRequest req;

    Logger logger = LoggerFactory.getLogger(CustResource.class);

    @GetMapping
    public List<Cust.CustDTO> all() {
        return custsDAO.all().stream().map(c -> c.toDTO()).toList();
    }

    @GetMapping("{id}")
    public Cust.CustDTO findById(@PathVariable("id") Long id) {
        System.out.println(id);
        Optional<Cust> custO = custsDAO.findById(id);
        if (custO.isEmpty()) throw new CustomersException("unknown cust id");
        return custO.get().toDTO();
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Cust.CustDTO cust) {
        Pattern NAME_PATTERN = Pattern.compile("[A-Z][a-z]{1,98}");
        if (cust.name() != null && NAME_PATTERN.matcher(cust.name()).matches()) {
            Long custId = custsDAO.create(cust);
            URI customerURI = new DefaultUriBuilderFactory(
                    req.getRequestURI()).builder().path("/" + custId.toString()).build(); // uriString().build();
            logger.debug(customerURI.toString());
            return ResponseEntity.created(customerURI).build();
        } else {
            throw new CustomersException("invalid name");
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        custsDAO.delete(id);
    }

    @PutMapping("change_name")
    public void changeName(@RequestBody Cust.CustDTO cust) {
        Pattern NAME_PATTERN = Pattern.compile("[A-Z][a-z]{1,98}");
        if (cust.name() != null && NAME_PATTERN.matcher(cust.name()).matches()) {
            custsDAO.changeName(cust);
        } else {
            throw new CustomersException("invalid name");
        }
    }
}
