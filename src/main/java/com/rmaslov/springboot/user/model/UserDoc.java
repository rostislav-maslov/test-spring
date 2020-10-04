package com.rmaslov.springboot.user.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserDoc {
    @Id
    private ObjectId id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Address address = new Address();
    private Company company = new Company();
}
