package fit.biktjv.customers.rest;

import fit.biktjv.customers.domain.Rcpt;
import fit.biktjv.customers.integration.RcptsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.DefaultUriBuilderFactory;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/rest/receipts")
public class RcptResource {

    @Autowired
    RcptsDAO receiptsDAO;// = CustomersDAOImpl.singleton;
    @Autowired
    HttpServletRequest req;

    @GetMapping
    public List<Rcpt.RcptDTO> get() {
        return receiptsDAO.all().stream().map(c->c.toDTO()).toList();
    }

    @PostMapping
    public ResponseEntity post(@RequestBody Rcpt.RcptDTO rcp) {
        Long rcptId = receiptsDAO.create(rcp);
        URI customerURI = new DefaultUriBuilderFactory(req.getRequestURI()).builder().path("/"+ rcptId.toString()).build(); // uriString().build();
          return ResponseEntity.created(customerURI).build();

    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        receiptsDAO.delete(id);

    }

}
