package com.Harevich.rating_service.repository;

import com.Harevich.rating_service.model.Rating;
import com.Harevich.rating_service.model.enumerations.RatingPerson;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface RatingRepository extends MongoRepository<Rating, ObjectId> {

    Page<Rating> findByRatedIdAndWhoIsRated(UUID ratedId, RatingPerson whoIsRated, Pageable pageable);

    Page<Rating> findByRatedIdAndWhoIsRatedOrderByRatingTimeDesc(UUID ratedId, RatingPerson whoIsRated, Pageable pageable);

    Optional<Rating> findByRideIdAndWhoIsRated(UUID rideId, RatingPerson whoIsRated);
}
