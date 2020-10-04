package com.rmaslov.springboot.user.api.response;

import com.rmaslov.springboot.user.model.Address;
import com.rmaslov.springboot.user.model.Company;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ApiModel(value = "UserFullResponse", description = "User full data")
public class UserFullResponse extends UserResponse {
    private Address address ;
    private Company company;
}
