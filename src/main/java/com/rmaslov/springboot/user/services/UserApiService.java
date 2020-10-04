package com.rmaslov.springboot.user.services;

import com.rmaslov.springboot.base.api.request.SearchRequest;
import com.rmaslov.springboot.base.api.response.SearchResponse;
import com.rmaslov.springboot.user.api.request.RegistrationRequest;
import com.rmaslov.springboot.user.api.request.UserRequest;
import com.rmaslov.springboot.user.exception.UserExistException;
import com.rmaslov.springboot.user.exception.UserNotExistException;
import com.rmaslov.springboot.user.mappings.UserMapping;
import com.rmaslov.springboot.user.model.UserDoc;
import com.rmaslov.springboot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserApiService {
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    public SearchResponse<UserDoc> search(SearchRequest request){
        Criteria criteria = new Criteria();
        if(request.getQuery() != null && request.getQuery() != ""){
            criteria = criteria.orOperator(
                    Criteria.where("firstName").regex(request.getQuery(), "i"),
                    Criteria.where("lastName").regex(request.getQuery(), "i"),
                    Criteria.where("email").regex(request.getQuery(), "i")
            );
        }

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, UserDoc.class);

        query.limit(request.getSize().intValue());
        query.skip(request.getSkip());
        List<UserDoc> userDocs = mongoTemplate.find(query, UserDoc.class);

        return SearchResponse.of(userDocs, count);
    }

    public UserDoc registration(RegistrationRequest registrationRequest) throws UserExistException {
        if(userRepository.findByEmail(registrationRequest.getEmail()).isPresent() == true){
            throw new UserExistException();
        }

        UserDoc userDoc = new UserDoc();
        userDoc.setEmail(registrationRequest.getEmail());
        userDoc.setPassword(DigestUtils.md5DigestAsHex(registrationRequest.getPassword().getBytes()));
        userDoc = userRepository.save(userDoc);

        return userDoc;
    }

    public UserDoc update(UserRequest request) throws UserNotExistException {
        Optional<UserDoc> optionalUserDoc = userRepository.findById(request.getId());
        if(optionalUserDoc.isPresent() == false){
            throw new UserNotExistException();
        }

        UserDoc userDoc = optionalUserDoc.get();

        userDoc.setFirstName(request.getFirstName());
        userDoc.setLastName(request.getLastName());
        userDoc.setAddress(request.getAddress());
        userDoc.setCompany(request.getCompany());
        userRepository.save(userDoc);

        return userDoc;
    }

    public Optional<UserDoc> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public void delete(ObjectId id){
        userRepository.deleteById(id);
    }

}
