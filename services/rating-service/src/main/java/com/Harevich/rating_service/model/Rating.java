package com.Harevich.rating_service.model;

import com.Harevich.rating_service.model.enumerations.VotingPerson;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.UUID;


@Document(collection = "ratings")
@Getter
@Setter
public class Rating {
    @Id
    private ObjectId id;

    @Field("ride_id")
    private UUID rideId;

    @Field("rateTime")
    @CreatedDate
    private LocalDateTime ratingTime;

    @Field("votingId")
    private UUID votingId;

    @Field("votableId")
    private UUID votableId;

    @Field("appraisal")
    private byte rating;

    @Field("whoVotes")
    private VotingPerson whoVotes;

    @Field("feedback")
    private String feedback;
}
