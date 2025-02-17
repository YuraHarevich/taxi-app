package com.kharevich.rideservice.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PriceService {

    BigDecimal getPriceByTwoAddresses(String from,
                                      String to,
                                      LocalDateTime currentTime);

}
