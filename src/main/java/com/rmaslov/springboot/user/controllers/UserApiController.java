package com.rmaslov.springboot.user.controllers;

import com.rmaslov.springboot.base.api.request.SearchRequest;
import com.rmaslov.springboot.base.api.response.OkResponse;
import com.rmaslov.springboot.base.api.response.SearchResponse;
import com.rmaslov.springboot.user.api.request.RegistrationRequest;
import com.rmaslov.springboot.user.api.request.UserRequest;
import com.rmaslov.springboot.user.api.response.UserFullResponse;
import com.rmaslov.springboot.user.exception.UserExistException;
import com.rmaslov.springboot.user.exception.UserNotExistException;
import com.rmaslov.springboot.user.mappings.UserMapping;
import com.rmaslov.springboot.user.routes.UserApiRoutes;
import com.rmaslov.springboot.user.services.UserApiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "User API")
public class UserApiController {
    private final UserApiService userApiService;

    @GetMapping(UserApiRoutes.BY_ID)
    @ApiOperation(value = "Find user by ID", notes = "Use this when you need full info about User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "User not found")
    }            )
    public OkResponse<UserFullResponse> byId(
            @ApiParam(value = "User ID") @PathVariable ObjectId id
    ) throws ChangeSetPersister.NotFoundException {
        return OkResponse.of(UserMapping.instance()
                .getFullResponse().convert(
                        userApiService
                                .findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new)
                ));
    }

    @GetMapping(UserApiRoutes.ROOT)
    @ApiOperation(value = "Search users", notes = "Use this when you need find user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success") })
    public OkResponse<SearchResponse<UserFullResponse>> search(@ModelAttribute SearchRequest request) {
        return OkResponse.of(UserMapping.instance()
                .getSearch().convert(
                        userApiService
                                .search(request)
                ));
    }

    @PostMapping(UserApiRoutes.ROOT)
    @ApiOperation(value = "Register", notes = "Use this when you need register and create new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Invalid ID"), })
    public OkResponse<UserFullResponse> register(@RequestBody RegistrationRequest registrationRequest) throws UserExistException {
        return OkResponse.of(UserMapping.instance()
                .getFullResponse().convert(
                        userApiService
                                .registration(registrationRequest)
                ));
    }

    @PutMapping(UserApiRoutes.BY_ID)
    @ApiOperation(value = "Update", notes = "Use this when you need update")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "User not found") })
    public OkResponse<UserFullResponse> updateById(
            @ApiParam(value = "User ID") @PathVariable ObjectId id,
            @RequestBody UserRequest request) throws UserNotExistException {
        return OkResponse.of(UserMapping.instance()
                .getFullResponse().convert(
                        userApiService
                                .update(request)
                ));
    }

    @DeleteMapping(UserApiRoutes.BY_ID)
    @ApiOperation(value = "delete", notes = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success") })
    public OkResponse<String> delete(
            @ApiParam(value = "User ID") @PathVariable ObjectId id
    ){
        userApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
    }
}
