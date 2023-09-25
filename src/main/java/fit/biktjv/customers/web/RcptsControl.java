package fit.biktjv.customers.web;

import fit.biktjv.customers.domain.Cust;
import fit.biktjv.customers.domain.Rcpt;
import fit.biktjv.customers.integration.CustsDAO;
import fit.biktjv.customers.integration.RcptsDAO;
import fit.biktjv.customers.rest.CustomersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/receipts")
public class RcptsControl {
    @Autowired
    RcptsDAO rcptsDAO;
    @Autowired
    CustsDAO custsDAO;

    @GetMapping
    public String get(Model model, @RequestParam("custId") Optional<Long> custId) {
        if (custId.isPresent()) {
            Optional<Cust> cust = custsDAO.findById(custId.get());
            if (cust.isEmpty()) throw new CustomersException("invalid custId");
            model.addAttribute("receipts", rcptsDAO.findByCustId(custId.get()));
            model.addAttribute("cust", cust.get());
            return "RcptsForCustTemplate";
        } else {
            model.addAttribute("receipts", rcptsDAO.all());
            return "RcptsTemplate";
        }

    }


    static class RcptForm {
        String dsc;
        Long custId;

        public Long getCustId() {
            return custId;
        }

        public void setCustId(Long custId) {
            this.custId = custId;
        }

        public String getDsc() {
            return dsc;
        }

        public void setDsc(String dsc) {
            this.dsc = dsc;
        }

    }

    @GetMapping("add")
    public String getAdd(RcptForm rcptForm, Model model, @RequestParam("custId") Long custId) {
        Optional<Cust> cust = custsDAO.findById(custId);
        model.addAttribute("cust", cust.get());
        return "AddRcptTemplate";
    }

    @PostMapping("add")
    public String postAdd(@Valid RcptForm rcptForm, BindingResult br, Model model) {
        if (br.hasErrors()) {
            Optional<Cust> cust = custsDAO.findById(rcptForm.getCustId());
            model.addAttribute("cust", cust.get());
            return "AddRcptTemplate";
        }
        rcptsDAO.create(new Rcpt.RcptDTO(null, rcptForm.dsc, rcptForm.getCustId()));
        return "redirect:/customers";
    }

}
