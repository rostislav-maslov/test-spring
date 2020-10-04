package com.rmaslov.springboot.article.api.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@ApiModel(value = "ArticleResponse", description = "Article data")
public class ArticleResponse {
        private String id;
        private String title;
        private String body;
        private String ownerId;
}
