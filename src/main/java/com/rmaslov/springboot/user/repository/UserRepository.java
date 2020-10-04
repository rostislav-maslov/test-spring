package com.rmaslov.springboot.user.repository;

import com.rmaslov.springboot.user.model.UserDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserDoc, ObjectId> {
    public Optional<UserDoc> findByEmail(String email);
}
