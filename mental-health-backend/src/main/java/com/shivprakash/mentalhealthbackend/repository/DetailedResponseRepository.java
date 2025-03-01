package com.shivprakash.mentalhealthbackend.repository;

import com.shivprakash.mentalhealthbackend.model.DetailedResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DetailedResponseRepository extends MongoRepository<DetailedResponse, String> {

    Optional<DetailedResponse> findByUserId(Long userId);
}