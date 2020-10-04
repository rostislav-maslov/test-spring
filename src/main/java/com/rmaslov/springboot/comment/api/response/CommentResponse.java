package com.rmaslov.springboot.comment.api.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@ApiModel(value = "CommentResponse", description = "Comment data")
public class CommentResponse {
        private String id;
        private String userId;
        private String message;
        private String articleId;
}
