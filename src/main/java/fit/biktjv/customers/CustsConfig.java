package fit.biktjv.customers;

import fit.biktjv.customers.integration.CustsDAO;
import fit.biktjv.customers.integration.RcptsDAO;
import fit.biktjv.customers.integration.jdbc.CustsDAOJDBC;
import fit.biktjv.customers.integration.jpa.CustsDAOJPA;
import fit.biktjv.customers.integration.jpa.RcptsDAOJPA;
import fit.biktjv.customers.integration.memory.CustsDAOMem;
import fit.biktjv.customers.integration.springdata.*;
import fit.biktjv.customers.web.CustsControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class CustsConfig {

    Logger logger = LoggerFactory.getLogger(CustsConfig.class);

    enum RepositoryClass {
        Mem, JDBC, JPA, SpringData;
    }

    String repKey = "fit.biktjv.customers.customersrep";
    @Autowired
    Environment env;

    @Bean
    public CustsDAO customersDAO() {
        String rep = env.getProperty(repKey, String.class, "JPA");
        CustsDAO bean = switch (RepositoryClass.valueOf(rep)) {
            case Mem -> CustsDAOMem.singleton;
            case JDBC -> new CustsDAOJDBC();
            case JPA -> new CustsDAOJPA();
            case SpringData -> new CustsDAOSpringData();
        };
        logger.debug(bean.getClass().getSimpleName());
        return bean;
    }

    @Bean
    public RcptsDAO receiptsDAO() {
        String rep = env.getProperty(repKey, String.class, "JPA");
        return switch (RepositoryClass.valueOf(rep)) {
            case JPA -> new RcptsDAOJPA();
            case SpringData -> new RcptsDAOSpringData();
            default -> throw new RuntimeException("invalid repository:" + rep);
        };
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("cz"));
        return slr;
    }

/*    @Bean
    public ResourceBundleMessageSource res() {
        ResourceBundleMessageSource rb = new ResourceBundleMessageSource();
        rb.setBasename("messages");
        rb.setDefaultEncoding("UTF-8");
        return rb;
    }*/
}
