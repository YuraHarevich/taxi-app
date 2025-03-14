package com.kharevich.rideservice.util.converter;

import com.kharevich.rideservice.exception.ProcessingStatusConvertionException;
import com.kharevich.rideservice.model.enumerations.ProcessingStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import static com.kharevich.rideservice.util.constants.RideServiceResponseConstants.ERROR_WHILE_CONVERTING_PROCESSING_STATUS;
import static com.kharevich.rideservice.util.constants.RideServiceResponseConstants.PROCESSING_STATUS_IS_ABSENT;

@Converter
public class ProcessingStatusConverter implements AttributeConverter<ProcessingStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ProcessingStatus processingStatus) {
        if (processingStatus == null) {
            throw new ProcessingStatusConvertionException(PROCESSING_STATUS_IS_ABSENT);
        }
        return processingStatus.getProcessingStatus();
    }

    @Override
    public ProcessingStatus convertToEntityAttribute(Integer code) {
        return ProcessingStatus.fromCode(code)
                .orElseThrow(() -> new ProcessingStatusConvertionException(ERROR_WHILE_CONVERTING_PROCESSING_STATUS));
    }

}
