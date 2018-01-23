package com.sprinthive.starter.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @RequestMapping(value="/customer/{name}")
    public Customer findCustomerByName(@PathVariable String name) {
        log.debug("findCustomerByName name: "+ name);
        Customer customer = customerService.findCustomerByName(name);
        return customer;
    }

    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public ResponseEntity createCustomer(@Valid @RequestBody Customer customer) {
        customerService.createCustomer(customer.getName());
        return ResponseEntity.created(URI.create("/customer/" + customer.getName())).build();

    }
}
