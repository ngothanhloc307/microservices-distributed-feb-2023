package com.ngoloc.customer.controller;

import com.ngoloc.customer.dao.CustomerRegistationRequest;
import com.ngoloc.customer.entity.Customer;
import com.ngoloc.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> registerCustomer(@RequestBody CustomerRegistationRequest customerRegistationRequest){
        log.info("new customer registration {}", customerRegistationRequest);
       return new ResponseEntity<>(customerService.registerCustomer(customerRegistationRequest), HttpStatus.OK);
    }
}
