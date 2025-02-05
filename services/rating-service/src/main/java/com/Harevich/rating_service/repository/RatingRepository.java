package com.Harevich.rating_service.repository;

import com.Harevich.rating_service.model.Rating;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface RatingRepository extends MongoRepository<Rating, ObjectId> {
    Page<Rating> findByDriverId(UUID driverId, Pageable pageable);
    Page<Rating> findByPassengerId(UUID passengerId, Pageable pageable);
    Page<Rating> findByPassengerIdOrderByCreatedAtDesc(UUID id, Pageable pageable);
    Page<Rating> findByDriverIdOrderByCreatedAtDesc(UUID id, Pageable pageable);
}
