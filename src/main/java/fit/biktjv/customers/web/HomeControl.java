package fit.biktjv.customers.web;

import fit.biktjv.customers.domain.Cust;
import fit.biktjv.customers.integration.CustsDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
@RequestMapping("/")
public class HomeControl {

    Logger logger = LoggerFactory.getLogger(HomeControl.class);

    @GetMapping
    public String get() {
        return "index";
    }
@Autowired
    LocaleResolver localeResolver;// = new SessionLocaleResolver();


    @Autowired
    HttpServletRequest req;
    HttpServletResponse resp;

    @PostMapping("locale")
    public String set(@RequestParam("locale") String locale) {
        logger.debug(locale);
        localeResolver.setLocale(req, null, new Locale(locale));
        return "redirect:/";
    }


}
