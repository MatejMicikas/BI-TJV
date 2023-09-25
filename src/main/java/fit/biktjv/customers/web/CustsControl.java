package fit.biktjv.customers.web;

import fit.biktjv.customers.domain.Cust;
import fit.biktjv.customers.integration.CustsDAO;
import fit.biktjv.customers.rest.CustomersException;
import jakarta.validation.constraints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustsControl {

    Logger logger = LoggerFactory.getLogger(CustsControl.class);
    @Autowired
    CustsDAO custsDAO;

    @GetMapping
    public String get(Model model) {
        // logger.debug(model.toString());
        model.addAttribute("customers", custsDAO.all());
        return "CustsTemplate";
    }

    @PostMapping("delete")
    public String delete(@RequestParam("custId") Long custId) {
        custsDAO.delete(custId);
        return "redirect:/customers";
    }

    static class CustForm {
        @Size(min = 2, max = 99)
        @Pattern(regexp = "[A-Z][a-z]*")
        String name;

        Long custId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getCustId() {
            return custId;
        }

        public void setCustId(Long custId) {
            this.custId = custId;
        }
    }

    @GetMapping("add")
    public String getAdd(CustForm custForm) {
        return "AddCustTemplate";
    }

    @PostMapping("add")
    public String postAdd(@Valid CustForm custForm, BindingResult br, RedirectAttributes redAttrs) {
        if (br.hasErrors())
            return "AddCustTemplate";
        custsDAO.create(new Cust.CustDTO(null, custForm.name));
        redAttrs.addFlashAttribute("message", "The new customer has been created");
        return "redirect:/customers";
    }

    @GetMapping("change_name")
    public String getChangeName(CustForm custForm, Model model, @RequestParam("custId") Optional<Long> custId) {
        Optional<Cust> cust = custsDAO.findById(custId.get());
        model.addAttribute("cust", cust.get());
        return "ChangeCustNameTemplate";
    }

    @PostMapping("change_name")
    public String postChangeName(@Valid CustForm custForm, BindingResult br, RedirectAttributes redAttrs, Model model) {
        Optional<Cust> cust = custsDAO.findById(custForm.getCustId());
        if (br.hasErrors()) {
            model.addAttribute("cust", cust.get());
            return "ChangeCustNameTemplate";
        }
        custsDAO.changeName(new Cust.CustDTO(cust.get().getId(), custForm.name));
        redAttrs.addFlashAttribute("message", "Customer's name has been changed");
        return "redirect:/customers";
    }

    @GetMapping("find")
    public String getFind(CustForm custForm) {
        return "FindCustTemplate";
    }

    @PostMapping("find")
    public String postFind(@Valid CustForm custForm, BindingResult br, RedirectAttributes redAttrs, Model model) {
        Optional<Cust> cust = custsDAO.findById(custForm.getCustId());
        if (br.hasErrors()) {
            return "FindCustTemplate";
        }
        if (!cust.isPresent()) {
            model.addAttribute("message", "Entered Id does not exist");
            return "FindCustTemplate";
        }
        model.addAttribute("cust", cust.get());
        return "FoundCustsTemplate";
    }

}
