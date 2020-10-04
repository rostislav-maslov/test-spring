package com.rmaslov.springboot.comment.controllers;

import com.rmaslov.springboot.article.exception.ArticleNotExistException;
import com.rmaslov.springboot.base.api.request.SearchRequest;
import com.rmaslov.springboot.base.api.response.OkResponse;
import com.rmaslov.springboot.base.api.response.SearchResponse;
import com.rmaslov.springboot.comment.api.request.CommentRequest;
import com.rmaslov.springboot.comment.api.response.CommentResponse;
import com.rmaslov.springboot.comment.exception.CommentNotExistException;
import com.rmaslov.springboot.comment.mappings.CommentMapping;
import com.rmaslov.springboot.comment.routes.CommentApiRoutes;
import com.rmaslov.springboot.comment.services.CommentApiService;
import com.rmaslov.springboot.user.exception.UserNotExistException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Comment API")
public class CommentApiController {
    private final CommentApiService commentApiService;

    @GetMapping(CommentApiRoutes.BY_ID)
    @ApiOperation(value = "Find comment by ID", notes = "Use this when you need full info about Comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Comment not found")
    }            )
    public OkResponse<CommentResponse> byId(
            @ApiParam(value = "Comment ID") @PathVariable ObjectId id
    ) throws ChangeSetPersister.NotFoundException {
        return OkResponse.of(CommentMapping.instance()
                .getResponse().convert(
                        commentApiService
                                .findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new)
                ));
    }

    @GetMapping(CommentApiRoutes.ROOT)
    @ApiOperation(value = "Search comments", notes = "Use this when you need find comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success") })
    public OkResponse<SearchResponse<CommentResponse>> search(@ModelAttribute SearchRequest request) {
        return OkResponse.of(CommentMapping.instance()
                .getSearch().convert(
                        commentApiService
                                .search(request)
                ));
    }

    @PostMapping(CommentApiRoutes.ROOT)
    @ApiOperation(value = "Create", notes = "Use this when you need create new comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Invalid ID"), })
    public OkResponse<CommentResponse> create(@RequestBody CommentRequest request) throws UserNotExistException, ArticleNotExistException {
        return OkResponse.of(CommentMapping.instance()
                .getResponse().convert(
                        commentApiService
                                .create(request)
                ));
    }

    @PutMapping(CommentApiRoutes.BY_ID)
    @ApiOperation(value = "Update", notes = "Use this when you need update")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Comment not found") })
    public OkResponse<CommentResponse> updateById(
            @ApiParam(value = "Comment ID") @PathVariable ObjectId id,
            @RequestBody CommentRequest request) throws CommentNotExistException {
        return OkResponse.of(CommentMapping.instance()
                .getResponse().convert(
                        commentApiService
                                .update(request)
                ));
    }

    @DeleteMapping(CommentApiRoutes.BY_ID)
    @ApiOperation(value = "delete", notes = "Delete comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success") })
    public OkResponse<String> delete(
            @ApiParam(value = "Comment ID") @PathVariable ObjectId id
    ){
        commentApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
    }
}
