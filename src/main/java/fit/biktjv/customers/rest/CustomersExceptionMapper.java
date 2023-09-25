package fit.biktjv.customers.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomersExceptionMapper {

    @ExceptionHandler(CustomersException.class)
    public ResponseEntity create(CustomersException ce) {
        return ResponseEntity.badRequest().body(ce.toString());
    }

}
