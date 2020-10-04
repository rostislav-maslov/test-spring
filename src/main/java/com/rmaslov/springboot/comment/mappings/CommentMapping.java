package com.rmaslov.springboot.comment.mappings;

import com.rmaslov.springboot.base.api.response.SearchResponse;
import com.rmaslov.springboot.base.mapping.BaseMapping;
import com.rmaslov.springboot.comment.api.request.CommentRequest;
import com.rmaslov.springboot.comment.api.response.CommentResponse;
import com.rmaslov.springboot.comment.model.CommentDoc;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter
public class CommentMapping {
    public static class RequestMapping extends BaseMapping<CommentRequest, CommentDoc> {
        @Override
        public CommentDoc convert(CommentRequest commentRequest) {
            return CommentDoc.builder()

                    .id(commentRequest.getId())
                    .userId(commentRequest.getUserId())
                    .message(commentRequest.getMessage())
                    .articleId(commentRequest.getArticleId())

                    .build();
        }

        @Override
        public CommentRequest unmapping(CommentDoc commentDoc) {
            throw new RuntimeException("dont use this");
        }
    }

    public static class ResponseMapping extends BaseMapping<CommentDoc, CommentResponse> {
        @Override
        public CommentResponse convert(CommentDoc commentDoc) {
            return CommentResponse.builder()

                    .id(commentDoc.getId().toString())
                    .userId(commentDoc.getUserId().toString())
                    .message(commentDoc.getMessage())
                    .articleId(commentDoc.getArticleId().toString())

                    .build();
        }

        @Override
        public CommentDoc unmapping(CommentResponse commentResponse) {
            throw new RuntimeException("dont use this");
        }
    }

    public static class SearchMapping extends BaseMapping<SearchResponse<CommentDoc>, SearchResponse<CommentResponse>> {
        private ResponseMapping responseMapping = new ResponseMapping();

        @Override
        public SearchResponse<CommentResponse> convert(SearchResponse<CommentDoc> commentDocSearchResponse) {
            return SearchResponse.of(
                    commentDocSearchResponse
                            .getList().stream()
                            .map(responseMapping::convert)
                            .collect(Collectors.toList()), commentDocSearchResponse.getCount()
            );
        }

        @Override
        public SearchResponse<CommentDoc> unmapping(SearchResponse<CommentResponse> from) {
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

    public static CommentMapping instance(){
        return new CommentMapping();
    }
}
