package com.stackroute.catalog.repository;

import com.stackroute.catalog.model.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepository extends MongoRepository<Photo, String> {
}