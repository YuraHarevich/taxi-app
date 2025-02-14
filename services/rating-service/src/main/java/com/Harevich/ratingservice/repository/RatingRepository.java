package com.Harevich.ratingservice.repository;

import com.Harevich.ratingservice.model.Rating;
import com.Harevich.ratingservice.model.enumerations.RatingPerson;
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
