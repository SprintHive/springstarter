package com.sprinthive.starter.customer;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Builder
@Value
public class Customer {
    @NotNull
    private String name;
}
