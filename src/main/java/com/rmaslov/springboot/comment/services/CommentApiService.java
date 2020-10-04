package com.rmaslov.springboot.comment.services;

import com.rmaslov.springboot.article.exception.ArticleNotExistException;
import com.rmaslov.springboot.article.repository.ArticleRepository;
import com.rmaslov.springboot.base.api.request.SearchRequest;
import com.rmaslov.springboot.base.api.response.SearchResponse;
import com.rmaslov.springboot.comment.api.request.CommentRequest;
import com.rmaslov.springboot.comment.exception.CommentNotExistException;
import com.rmaslov.springboot.comment.mappings.CommentMapping;
import com.rmaslov.springboot.comment.model.CommentDoc;
import com.rmaslov.springboot.comment.repository.CommentRepository;
import com.rmaslov.springboot.user.exception.UserNotExistException;
import com.rmaslov.springboot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentApiService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    public SearchResponse<CommentDoc> search(SearchRequest request){
        Criteria criteria = new Criteria();
        if(request.getQuery() != null && request.getQuery() != ""){
            criteria = criteria.orOperator(
                    Criteria.where("message").regex(request.getQuery(), "i")
            );
        }

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, CommentDoc.class);

        query.limit(request.getSize().intValue());
        query.skip(request.getSkip());
        List<CommentDoc> commentDocs = mongoTemplate.find(query, CommentDoc.class);

        return SearchResponse.of(commentDocs, count);
    }

    public CommentDoc create(CommentRequest request) throws ArticleNotExistException, UserNotExistException {
        if(userRepository.findById(request.getUserId()).isPresent() == false){
            throw new UserNotExistException();
        }

        if(articleRepository.findById(request.getArticleId()).isPresent() == false){
            throw new ArticleNotExistException();
        }

        CommentDoc commentDoc = CommentMapping.instance().getRequest().convert(request);
        commentDoc = commentRepository.save(commentDoc);

        return commentDoc;
    }

    public CommentDoc update(CommentRequest request) throws CommentNotExistException {
        Optional<CommentDoc> optionalCommentDoc = commentRepository.findById(request.getId());
        if(optionalCommentDoc.isPresent() == false){
            throw new CommentNotExistException();
        }

        CommentDoc oldDoc = optionalCommentDoc.get();

        CommentDoc commentDoc = CommentMapping.instance().getRequest().convert(request);
        commentDoc.setId(oldDoc.getId());
        commentDoc.setArticleId(oldDoc.getArticleId());
        commentDoc.setUserId(oldDoc.getUserId());

        return commentDoc;
    }

    public Optional<CommentDoc> findById(ObjectId id){
        return commentRepository.findById(id);
    }

    public void delete(ObjectId id){
        commentRepository.deleteById(id);
    }

}
