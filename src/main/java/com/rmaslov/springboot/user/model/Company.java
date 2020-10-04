package com.rmaslov.springboot.user.model;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "User.Company", description = "User company")
public class Company {
    private String name;
    private String address;
}
