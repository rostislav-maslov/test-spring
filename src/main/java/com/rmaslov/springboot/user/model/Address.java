package com.rmaslov.springboot.user.model;

import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "User.Address", description = "User address")
public class Address {
    private String city;
    private String street;
    private String suite;
    private String zipcode;
    private Point point;
}
