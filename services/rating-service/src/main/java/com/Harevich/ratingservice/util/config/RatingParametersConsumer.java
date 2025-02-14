package com.Harevich.ratingservice.util.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RatingParametersConsumer {

    @Value("${app.rating.number_to_evaluate:10}")
    private int numberOfRidesToEvaluateRating;

}
