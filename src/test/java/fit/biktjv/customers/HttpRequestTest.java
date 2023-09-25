package fit.biktjv.customers;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class HttpRequestTest {

    @LocalServerPort
    private String port;

    Logger logger = LoggerFactory.getLogger(HttpRequestTest.class);

    @Autowired
    MockMvc mvc;

    @Test
    public void addCustTest() throws Exception {
        String custsUrl = "http://localhost:" + port + "/rest/customers";
        String newCust = "Bob";
        String newCustJson = "{ \"name\":\"" + newCust + "\" }";
        MockHttpServletRequestBuilder createCustReqBldr =
                MockMvcRequestBuilders.post(custsUrl)
                        .contentType(MediaType.APPLICATION_JSON).content(newCustJson);
        MockHttpServletResponse resp = mvc.perform(createCustReqBldr).andReturn().getResponse();
        assertEquals(HttpStatus.CREATED.value(), resp.getStatus());
        String custId = resp.getHeader("Location").substring(resp.getHeader("Location").lastIndexOf("/") + 1);
        logger.debug(resp.getHeader("Location"));
        String respAppCusts = mvc.perform(MockMvcRequestBuilders.get(custsUrl)).andReturn().getResponse().getContentAsString();
        assertTrue(respAppCusts.contains(custId));
    }

    @Test
    void addCustomerInvalidNameFieldTest() throws Exception {
        String custsUrl = "http://localhost:" + port + "/rest/customers";
        String newCustJson = "{ \"name\":\"" + 2 + "\" }";
        MockHttpServletRequestBuilder createCustReqBldr =
                MockMvcRequestBuilders.post(custsUrl)
                        .contentType(MediaType.APPLICATION_JSON).content(newCustJson);
        MockHttpServletResponse resp = mvc.perform(createCustReqBldr).andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatus());
    }

    @Test
    void deleteCustTest() throws Exception {
        // Create a new customer
        String custsUrl = "http://localhost:" + port + "/rest/customers";
        String newCust = "Bob";
        String newCustJson = "{ \"name\":\"" + newCust + "\" }";
        MockHttpServletRequestBuilder createCustReqBldr =
                MockMvcRequestBuilders.post(custsUrl)
                        .contentType(MediaType.APPLICATION_JSON).content(newCustJson);
        MockHttpServletResponse resp = mvc.perform(createCustReqBldr).andReturn().getResponse();
        assertEquals(HttpStatus.CREATED.value(), resp.getStatus());
        logger.debug(resp.getHeader("Location"));
        String respAppCusts = mvc.perform(MockMvcRequestBuilders.get(custsUrl)).andReturn().getResponse().getContentAsString();
        assertTrue(respAppCusts.contains(newCust));
        String custId = resp.getHeader("Location").substring(resp.getHeader("Location").lastIndexOf("/") + 1);

        // Delete the customer
        String deleteUrl = "http://localhost:" + port + "/rest/customers/" + custId;
        MockHttpServletRequestBuilder deleteReqBldr =
                MockMvcRequestBuilders.delete(deleteUrl);
        resp = mvc.perform(deleteReqBldr).andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), resp.getStatus());
        respAppCusts = mvc.perform(MockMvcRequestBuilders.get(custsUrl)).andReturn().getResponse().getContentAsString();
        assertFalse(respAppCusts.contains(custId));
    }

    @Test
    void deleteNonExistingCustTest() throws Exception {
        String deleteUrl = "http://localhost:" + port + "/rest/customers/10000";
        MockHttpServletRequestBuilder deleteReqBldr =
                MockMvcRequestBuilders.delete(deleteUrl);
        MockHttpServletResponse resp = mvc.perform(deleteReqBldr).andReturn().getResponse();
        int statusCode = resp.getStatus();
        assertTrue(statusCode == HttpStatus.NOT_FOUND.value() || statusCode == HttpStatus.BAD_REQUEST.value());
    }

}