package com.stackroute.catalog.repository;

import com.stackroute.catalog.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByUserId(long userId);
    //boolean existsByCode(String code);
    //Product findByCode(String code);
}