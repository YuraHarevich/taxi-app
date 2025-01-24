package com.Harevich.passengerservice.util.additional.impl;

import com.Harevich.passengerservice.exceptions.UniqueException;
import com.Harevich.passengerservice.util.additional.PassengerValidation;
import com.Harevich.passengerservice.util.constants.PassengerServiceResponseConstants;
import org.springframework.stereotype.Service;

@Service
public class PassengerValidationImpl implements PassengerValidation {
    @Override
    public void handleUniqueExceptionByErrorMessage(String rootMessage) {
        if (rootMessage.contains("Key (email)")) {
            throw new UniqueException(PassengerServiceResponseConstants.REPEATED_EMAIL);
        } else if (rootMessage.contains("Key (number)")) {
            throw new UniqueException(PassengerServiceResponseConstants.REPEATED_PHONE_NUMBER);
        }
    }
}
