package com.rmaslov.springboot.comment.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@Builder
@ApiModel(value = "CommentRequest", description = "Model for update commentRequest data")
public class CommentRequest {
    private ObjectId id;
    private ObjectId userId;
    private String message;
    private ObjectId articleId;
}
