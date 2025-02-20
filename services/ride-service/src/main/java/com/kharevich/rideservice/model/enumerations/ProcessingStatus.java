package com.kharevich.rideservice.model.enumerations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum ProcessingStatus {

    NOT_PROCESSED(100),
    IN_PROCESS(200),
    PROCESSED(300);

    private final int processingStatus;

    public static Optional<ProcessingStatus> fromCode(int code) {
        return Arrays.stream(ProcessingStatus.values())
                .filter(queueElement -> queueElement.processingStatus == code)
                .findAny();
    }

}
