package com.sprinthive.starter.customer;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder
public class Customer {

    @NotNull
    private String name;

    private String id;
}
