package com.rmaslov.springboot.article.mappings;

import com.rmaslov.springboot.base.api.response.SearchResponse;
import com.rmaslov.springboot.base.mapping.BaseMapping;
import com.rmaslov.springboot.article.api.request.ArticleRequest;
import com.rmaslov.springboot.article.api.response.ArticleResponse;
import com.rmaslov.springboot.article.model.ArticleDoc;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter
public class ArticleMapping {
    public static class RequestMapping extends BaseMapping<ArticleRequest, ArticleDoc> {
        @Override
        public ArticleDoc convert(ArticleRequest articleRequest) {
            return ArticleDoc.builder()

                    .id(articleRequest.getId())
                    .title(articleRequest.getTitle())
                    .body(articleRequest.getBody())
                    .ownerId(articleRequest.getOwnerId())

                    .build();
        }

        @Override
        public ArticleRequest unmapping(ArticleDoc articleDoc) {
            throw new RuntimeException("dont use this");
        }
    }

    public static class ResponseMapping extends BaseMapping<ArticleDoc, ArticleResponse> {
        @Override
        public ArticleResponse convert(ArticleDoc articleDoc) {
            return ArticleResponse.builder()

                    .id(articleDoc.getId().toString())
                    .title(articleDoc.getTitle())
                    .body(articleDoc.getBody())
                    .ownerId(articleDoc.getOwnerId().toString())

                    .build();
        }

        @Override
        public ArticleDoc unmapping(ArticleResponse articleResponse) {
            throw new RuntimeException("dont use this");
        }
    }

    public static class SearchMapping extends BaseMapping<SearchResponse<ArticleDoc>, SearchResponse<ArticleResponse>> {
        private ResponseMapping responseMapping = new ResponseMapping();

        @Override
        public SearchResponse<ArticleResponse> convert(SearchResponse<ArticleDoc> articleDocSearchResponse) {
            return SearchResponse.of(
                    articleDocSearchResponse
                            .getList().stream()
                            .map(responseMapping::convert)
                            .collect(Collectors.toList()), articleDocSearchResponse.getCount()
            );
        }

        @Override
        public SearchResponse<ArticleDoc> unmapping(SearchResponse<ArticleResponse> from) {
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
    private final SearchMapping search = new SearchMapping();

    public static ArticleMapping instance(){
        return new ArticleMapping();
    }
}
