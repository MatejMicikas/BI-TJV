package fit.biktjv.customersclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Scanner;

@SpringBootApplication
public class Controller {

    @Autowired
    Proxy proxy;

    enum Cmd {
        AC("All Customers"), CC("Create Cust"), UC("Update Cust"), DC("Delete Cust"),
        GC("Get_Customer"),
        AR("All Rcpt"), CR("Create Rcpt"), UR("Update Rcpt"), DR("Delete Rcpt");

        Cmd(String help) {
            this.help = help;
        }

        public String getHelp() {
            return help;
        }

        private final String help;
    }

    static String hint = Arrays.stream(Cmd.values()).map(Cmd::getHelp).toList().toString();

    String input(String prompt) {
        System.out.printf("%s: ", prompt);
        return new Scanner(System.in).nextLine();
    }

    void prln(Object o) {
        System.out.println(o);
    }

    org.slf4j.Logger logger = LoggerFactory.getLogger(Controller.class);

    void run() {
        for (; ; ) {
            logger.info(hint);
            try {
                Cmd cmd = Cmd.valueOf(input("?").toUpperCase());
                switch (cmd) {
                    case AC -> prln(proxy.allCustomers());

                    case CC -> {
                        String name = input("name");
                        prln(proxy.createCustomer(new CustDTO(name)));
                    }

                    case GC -> {
                        long id = Integer.parseInt(input("id"));
                        prln(proxy.getCustomerById(id));
                    }

                    case DC -> {
                        long id = Integer.parseInt(input("id"));
                        proxy.deleteCustomer(id);
                    }
                    case UC -> {
                        long id = Integer.parseInt(input("id"));
                        String name = input("name");
                        proxy.updateCustomer(new CustDTO(id, name));
                    }

                    case AR -> prln(proxy.allReceipts());

                    case CR -> {
                        String dsc = input("description");
                        Long custId = Long.valueOf(input("customer id"));
                        RcptDTO r = new RcptDTO(dsc, custId);
                        prln(proxy.createReceipt(r));
                    }
                    case DR -> {
                        long id = Integer.parseInt(input("id"));
                        proxy.deleteReceipt(id);
                    }
                    default -> prln("unknown:" + cmd);
                }
            } catch (Exception ie) {
                prln(ie);
            }
        }
    }
}
