package com.ngoloc.customer.service;

import com.ngoloc.customer.repository.CustomerRepository;
import com.ngoloc.customer.dao.CustomerRegistationRequest;
import com.ngoloc.customer.entity.Customer;
import com.ngoloc.customer.response.FraudCheckResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final RestTemplate restTemplate;
    private final  CustomerRepository customerRepository;
    public Customer registerCustomer(CustomerRegistationRequest customerRegistationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistationRequest.firstName())
                .lastName(customerRegistationRequest.lastName())
                .email(customerRegistationRequest.email())
                .build();
        // todo: check uf email valid
        // todo: check if email not taken
       Customer saveCustomer = customerRepository.saveAndFlush(customer);
        // todo: check if fraudster
        // todo: store customer in db
        FraudCheckResponse fraudCheckResponse =  restTemplate.getForObject(
                "http://localhost:8081/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );
        if(fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("fraudster");
        }

       return  saveCustomer;
       // todo: send notification
    }
}
