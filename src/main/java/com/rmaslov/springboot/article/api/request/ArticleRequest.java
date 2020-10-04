package com.rmaslov.springboot.article.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@Builder
@ApiModel(value = "ArticleRequest", description = "Model for update articleRequest data")
public class ArticleRequest {
    private ObjectId id;
    private String title;
    private String body;
    private ObjectId ownerId;
}
