package fit.biktjv.customersclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CustomersclientApplication implements CommandLineRunner {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CustomersclientApplication.class).
                web(WebApplicationType.NONE)
                .run(args);
    }

    @Autowired
    Controller controller;

    @Override
    public void run(String... args) throws Exception {
        controller.run();
    }
}
