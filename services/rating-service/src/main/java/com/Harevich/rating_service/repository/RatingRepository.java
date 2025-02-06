package com.Harevich.rating_service.repository;

import com.Harevich.rating_service.model.Rating;
import com.Harevich.rating_service.model.enumerations.VotingPerson;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface RatingRepository extends MongoRepository<Rating, ObjectId> {

    Page<Rating> findByVotableIdAndWhoVotes(UUID votableId, VotingPerson whoVotes, Pageable pageable);

    Page<Rating> findByVotableIdAndWhoVotesOrderByRatingTimeDesc(UUID id, VotingPerson whoVotes, Pageable pageable);
}
