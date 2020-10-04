package com.rmaslov.springboot.user.mappings;

import com.rmaslov.springboot.base.api.response.SearchResponse;
import com.rmaslov.springboot.base.mapping.BaseMapping;
import com.rmaslov.springboot.user.api.request.UserRequest;
import com.rmaslov.springboot.user.api.response.UserFullResponse;
import com.rmaslov.springboot.user.api.response.UserResponse;
import com.rmaslov.springboot.user.model.UserDoc;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter
public class UserMapping {
    public static class RequestMapping extends BaseMapping<UserRequest, UserDoc> {
        @Override
        public UserDoc convert(UserRequest userRequest) {
            return UserDoc.builder()
                    .id(userRequest.getId())
                    .email(userRequest.getEmail())
                    .firstName(userRequest.getFirstName())
                    .lastName(userRequest.getLastName())
                    .address(userRequest.getAddress())
                    .company(userRequest.getCompany())
                    .build();
        }

        @Override
        public UserRequest unmapping(UserDoc userDoc) {
            throw new RuntimeException("dont use this");
        }
    }

    public static class ResponseMapping extends BaseMapping<UserDoc, UserResponse> {
        @Override
        public UserResponse convert(UserDoc userDoc) {
            return UserResponse.builder()
                    .id(userDoc.getId().toString())
                    .email(userDoc.getEmail())
                    .firstName(userDoc.getFirstName())
                    .lastName(userDoc.getLastName())
                    .build();
        }

        @Override
        public UserDoc unmapping(UserResponse userResponse) {
            throw new RuntimeException("dont use this");
        }
    }

    public static class ResponseFullMapping extends BaseMapping<UserDoc, UserFullResponse> {
        @Override
        public UserFullResponse convert(UserDoc userDoc) {
            return UserFullResponse.builder()
                    .id(userDoc.getId().toString())
                    .email(userDoc.getEmail())
                    .firstName(userDoc.getFirstName())
                    .lastName(userDoc.getLastName())
                    .address(userDoc.getAddress())
                    .company(userDoc.getCompany())
                    .build();
        }

        @Override
        public UserDoc unmapping(UserFullResponse userFullResponse) {
            throw new RuntimeException("dont use this");
        }
    }

    public static class SearchMapping extends BaseMapping<SearchResponse<UserDoc>, SearchResponse<UserResponse>> {
        private ResponseMapping responseMapping = new ResponseMapping();

        @Override
        public SearchResponse<UserResponse> convert(SearchResponse<UserDoc> userDocSearchResponse) {
            return SearchResponse.of(
                    userDocSearchResponse
                            .getList().stream()
                            .map(responseMapping::convert)
                            .collect(Collectors.toList()), userDocSearchResponse.getCount()
            );
        }

        @Override
        public SearchResponse<UserDoc> unmapping(SearchResponse<UserResponse> from) {
            return SearchResponse.of(
                    from
                            .getList().stream()
                            .map(responseMapping::unmapping)
                            .collect(Collectors.toList()), from.getCount()
            );
        }
    }

    private final RequestMapping request = new RequestMapping();
    private final ResponseMapping response = new ResponseMapping();
    private final ResponseFullMapping fullResponse = new ResponseFullMapping();
    private final SearchMapping search = new SearchMapping();

    public static UserMapping instance(){
        return new UserMapping();
    }
}
