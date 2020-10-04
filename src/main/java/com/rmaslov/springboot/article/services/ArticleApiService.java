package com.rmaslov.springboot.article.services;

import com.rmaslov.springboot.base.api.request.SearchRequest;
import com.rmaslov.springboot.base.api.response.SearchResponse;
import com.rmaslov.springboot.article.api.request.ArticleRequest;
import com.rmaslov.springboot.article.exception.ArticleNotExistException;
import com.rmaslov.springboot.article.mappings.ArticleMapping;
import com.rmaslov.springboot.article.model.ArticleDoc;
import com.rmaslov.springboot.article.repository.ArticleRepository;
import com.rmaslov.springboot.user.exception.UserNotExistException;
import com.rmaslov.springboot.user.model.UserDoc;
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
public class ArticleApiService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    public SearchResponse<ArticleDoc> search(SearchRequest request){
        Criteria criteria = new Criteria();
        if(request.getQuery() != null && request.getQuery() != ""){
            criteria = criteria.orOperator(
                    Criteria.where("title").regex(request.getQuery(), "i"),
                    Criteria.where("body").regex(request.getQuery(), "i")
            );
        }

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, ArticleDoc.class);

        query.limit(request.getSize().intValue());
        query.skip(request.getSkip());
        List<ArticleDoc> articleDocs = mongoTemplate.find(query, ArticleDoc.class);

        return SearchResponse.of(articleDocs, count);
    }

    public ArticleDoc create(ArticleRequest request) throws UserNotExistException {
        Optional<UserDoc> owner = userRepository.findById(request.getOwnerId());
        if(owner.isPresent() == false) throw new UserNotExistException();

        ArticleDoc articleDoc = ArticleMapping.instance().getRequest().convert(request);
        articleDoc = articleRepository.save(articleDoc);

        return articleDoc;
    }

    public ArticleDoc update(ArticleRequest request) throws ArticleNotExistException {
        Optional<ArticleDoc> optionalArticleDoc = articleRepository.findById(request.getId());
        if(optionalArticleDoc.isPresent() == false){
            throw new ArticleNotExistException();
        }

        ArticleDoc oldDoc = optionalArticleDoc.get();

        ArticleDoc articleDoc = ArticleMapping.instance().getRequest().convert(request);
        articleDoc.setId(oldDoc.getId());
        articleDoc.setOwnerId(oldDoc.getOwnerId());

        return articleDoc;
    }

    public Optional<ArticleDoc> findById(ObjectId id){
        return articleRepository.findById(id);
    }

    public void delete(ObjectId id){
        articleRepository.deleteById(id);
    }

}
