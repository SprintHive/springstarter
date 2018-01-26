package com.sprinthive.starter.customer;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@EnableBinding(CustomerService.MessageChannels.class)
public class CustomerService {

    private final Map<String, Customer> customerStorage;
    private final KafkaTemplate<?, Customer> customerApplicationTopic;

    public CustomerService(@Autowired KafkaTemplate<?, Customer> customerApplicationTopic) {
        this.customerApplicationTopic = customerApplicationTopic;
        this.customerStorage = new ConcurrentHashMap<>();
    }

    @StreamListener("customerApplicationSubmitted")
    @SendTo("customerOutput")
    public KStream<?, Customer> processCustomerApplications(KStream<?, Customer> newCustomerStream) {
        return newCustomerStream.map((key, value) -> new KeyValue<>(null, Customer.builder()
                .id(UUID.randomUUID().toString())
                .name(value.getName())
                .build()));
    }

    @StreamListener("customerInput")
    public void processCustomerUpdate(KStream<?, Customer> customerUpdateStream) {
        customerUpdateStream.foreach((key, customer) -> customerStorage.put(customer.getName(), customer));
    }

    public Customer findCustomerByName(String name) {
        Customer customer = customerStorage.get(name);
        if (customer == null) {
            throw new CustomerNotFoundException("No customer found with the name of ".concat(name));
        }
        return customer;
    }

    public void submitCustomerApplication(String name) {
        customerApplicationTopic.send("customerApplicationSubmitted",
                Customer.builder().name(name).build());
    }

    interface MessageChannels {
        @Output KStream<?, Customer> customerOutput();
        @Input KStream<?, Customer> customerApplicationSubmitted();
        @Input KStream<?, Customer> customerInput();
    }
}
