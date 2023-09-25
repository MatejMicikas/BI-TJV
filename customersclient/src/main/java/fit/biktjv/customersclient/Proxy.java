package fit.biktjv.customersclient;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class Proxy {

    String customersUrl = "http://localhost:8080/rest/customers";
    String receptsUrl = "http://localhost:8080/rest/receipts";

    RestTemplate restTemplate = new RestTemplate();

    /*    public List<CustDTO> allCustomers() {
            ResponseEntity<List<CustDTO>> re =
                    restTemplate.exchange(customersUrl, HttpMethod.GET, null,
                            new ParameterizedTypeReference<List<CustDTO>>() {
                            });
            return re.getBody();
        }*/
    public List<CustDTO> allCustomers() {
        try {
            return restTemplate.getForObject(new URI(customersUrl), List.class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    public URI createCustomer(CustDTO customerDTO) {
        return restTemplate.postForLocation(customersUrl, customerDTO);
    }

    public List<RcptDTO> allReceipts() {
        ResponseEntity<List<RcptDTO>> re =
                restTemplate.exchange(receptsUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {
                        });
        return re.getBody();
    }

    public URI createReceipt(RcptDTO r) {
        return restTemplate.postForLocation(receptsUrl, r);
    }

    public CustDTO getCustomerById(long id) {
        URI custUri = new DefaultUriBuilderFactory(customersUrl).builder().pathSegment(Long.toString(id)).build();
        return restTemplate.getForObject(custUri, CustDTO.class);
     //   return restTemplate.getForObject(customersUrl + "/" + id, CustDTO.class);
    }

    public void deleteCustomer(long id) {
        restTemplate.delete(customersUrl + "/" + id);
    }

    public void updateCustomer(CustDTO customerDTO) {
    }

    public void deleteReceipt(long id) {
        restTemplate.delete(receptsUrl + "/" + id);
    }
}
