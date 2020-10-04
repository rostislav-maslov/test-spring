package com.rmaslov.springboot.user.api.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@ApiModel(value = "UserResponse", description = "User data")
public class UserResponse {
    protected String id;
    protected String firstName;
    protected String lastName;
    protected String email;
}
