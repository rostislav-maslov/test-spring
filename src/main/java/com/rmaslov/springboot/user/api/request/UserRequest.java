package com.rmaslov.springboot.user.api.request;

import com.rmaslov.springboot.user.model.Address;
import com.rmaslov.springboot.user.model.Company;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@Builder
@ApiModel(value = "UserRequest", description = "Model for update user data")
public class UserRequest {
    private ObjectId id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address = new Address();
    private Company company = new Company();
}
