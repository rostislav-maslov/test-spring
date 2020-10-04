package com.rmaslov.springboot.article.repository;

import com.rmaslov.springboot.article.model.ArticleDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends MongoRepository<ArticleDoc, ObjectId> {

}
