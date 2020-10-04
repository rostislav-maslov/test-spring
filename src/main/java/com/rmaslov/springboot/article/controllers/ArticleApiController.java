package com.rmaslov.springboot.article.controllers;

import com.rmaslov.springboot.base.api.request.SearchRequest;
import com.rmaslov.springboot.base.api.response.OkResponse;
import com.rmaslov.springboot.base.api.response.SearchResponse;
import com.rmaslov.springboot.article.api.request.ArticleRequest;
import com.rmaslov.springboot.article.api.response.ArticleResponse;
import com.rmaslov.springboot.article.exception.ArticleNotExistException;
import com.rmaslov.springboot.article.mappings.ArticleMapping;
import com.rmaslov.springboot.article.routes.ArticleApiRoutes;
import com.rmaslov.springboot.article.services.ArticleApiService;
import com.rmaslov.springboot.user.exception.UserNotExistException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Article API")
public class ArticleApiController {
    private final ArticleApiService articleApiService;

    @GetMapping(ArticleApiRoutes.BY_ID)
    @ApiOperation(value = "Find article by ID", notes = "Use this when you need full info about Article")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Article not found")
    }            )
    public OkResponse<ArticleResponse> byId(
            @ApiParam(value = "Article ID") @PathVariable ObjectId id
    ) throws ChangeSetPersister.NotFoundException {
        return OkResponse.of(ArticleMapping.instance()
                .getResponse().convert(
                        articleApiService
                                .findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new)
                ));
    }

    @GetMapping(ArticleApiRoutes.ROOT)
    @ApiOperation(value = "Search articles", notes = "Use this when you need find article")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success") })
    public OkResponse<SearchResponse<ArticleResponse>> search(@ModelAttribute SearchRequest request) {
        return OkResponse.of(ArticleMapping.instance()
                .getSearch().convert(
                        articleApiService
                                .search(request)
                ));
    }

    @PostMapping(ArticleApiRoutes.ROOT)
    @ApiOperation(value = "Create", notes = "Use this when you need create new article")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Invalid ID"), })
    public OkResponse<ArticleResponse> create(@RequestBody ArticleRequest request) throws UserNotExistException {
        return OkResponse.of(ArticleMapping.instance()
                .getResponse().convert(
                        articleApiService
                                .create(request)
                ));
    }

    @PutMapping(ArticleApiRoutes.BY_ID)
    @ApiOperation(value = "Update", notes = "Use this when you need update")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Article not found") })
    public OkResponse<ArticleResponse> updateById(
            @ApiParam(value = "Article ID") @PathVariable ObjectId id,
            @RequestBody ArticleRequest request) throws ArticleNotExistException {
        return OkResponse.of(ArticleMapping.instance()
                .getResponse().convert(
                        articleApiService
                                .update(request)
                ));
    }

    @DeleteMapping(ArticleApiRoutes.BY_ID)
    @ApiOperation(value = "delete", notes = "Delete article")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success") })
    public OkResponse<String> delete(
            @ApiParam(value = "Article ID") @PathVariable ObjectId id
    ){
        articleApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
    }
}
