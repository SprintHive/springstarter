package com.sprinthive.starter.customer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;

@Service
public class CustomerService {
    private final HashMap<String, Customer> custStorage = new HashMap<>();
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "customer")
    public void receive(ConsumerRecord<?, String> consumerRecord) {
        custStorage.put(consumerRecord.value(), Customer.builder().name(consumerRecord.value()).build());
    }

    public Customer findCustomerByName(String name) {
        Customer customer = custStorage.get(name);
        if (customer == null) {
            throw new CustomerNotFoundException("No customer found with the name of ".concat(name));
        }
        return customer;
    }

    public void createCustomer(String name) {
        kafkaTemplate.send("customer", name);
    }

}
