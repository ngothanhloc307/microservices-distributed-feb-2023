package com.ngoloc.customer.service;

import com.ngoloc.clients.fraud.FraudCheckResponse;
import com.ngoloc.clients.fraud.FraudClients;
import com.ngoloc.clients.notification.NotificationClient;
import com.ngoloc.clients.notification.NotificationRequest;
import com.ngoloc.customer.repository.CustomerRepository;
import com.ngoloc.customer.dao.CustomerRegistationRequest;
import com.ngoloc.customer.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final RestTemplate restTemplate;
    private final CustomerRepository customerRepository;

    private final FraudClients fraudClients;
    private final NotificationClient notificationClient;


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
//        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
//                "http://FRAUD/api/v1/fraud-check/{customerId}",
//                FraudCheckResponse.class,
//                customer.getId()
//        );

        FraudCheckResponse fraudCheckResponse = fraudClients.isFraudster(customer.getId());
        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }


        // todo: send notification
        NotificationRequest notificationRequest = new NotificationRequest(
                saveCustomer.getId(),
                saveCustomer.getEmail(),
                String.format("Hi %s, welcome to Ngoloc...",
                        saveCustomer.getFirstName())
        );

        notificationClient.sendNotification(notificationRequest);

        return saveCustomer;
    }
}
