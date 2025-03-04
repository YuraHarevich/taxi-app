package com.kharevich.rideservice.schedule;

import com.kharevich.rideservice.model.DriverQueueElement;
import com.kharevich.rideservice.model.PassengerQueueElement;
import com.kharevich.rideservice.repository.DriverQueueRepository;
import com.kharevich.rideservice.repository.PassengerQueueRepository;
import com.kharevich.rideservice.service.RideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import static com.kharevich.rideservice.model.enumerations.ProcessingStatus.IN_PROCESS;
import static com.kharevich.rideservice.model.enumerations.ProcessingStatus.NOT_PROCESSED;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTask {

    @Value("${app.scheduling.time_of_queue_item_to_become_outdated_in_days}")
    private long timeOfQueueItemToBecomeUnnecessaryInDays;

    private final PassengerQueueRepository passengerQueueRepository;

    private final RideService rideService;

    private final DriverQueueRepository driverQueueRepository;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRateString = "${app.scheduling.period_of_performing_cleaning_of_outdated_data_in_millis}")
    public void clearingTheQueueOfOutdated() {
        log.info("ScheduledTask.clearingTheQueueOfOutdated.clearing the old data from queue {}", LocalDateTime.now());
        List<PassengerQueueElement> passengersOutdatedList = passengerQueueRepository.findByCreatedAtBefore(
                LocalDateTime
                        .now()
                        .minusDays(timeOfQueueItemToBecomeUnnecessaryInDays)
        );
        passengersOutdatedList.stream().forEach(passengerQueueElement -> passengerQueueRepository.delete(passengerQueueElement));

        List<DriverQueueElement> driversOutdatedList = driverQueueRepository.findByCreatedAtBefore(
                LocalDateTime
                        .now()
                        .minusDays(timeOfQueueItemToBecomeUnnecessaryInDays)
        );
        driversOutdatedList.stream().forEach(driverQueueElement -> driverQueueRepository.delete(driverQueueElement));

        if(!passengersOutdatedList.isEmpty() || !driversOutdatedList.isEmpty()){
            log.info("ScheduledTask.clearingTheQueueOfOutdated.successfully cleared {} entities", passengersOutdatedList.size() + driversOutdatedList.size());
        }
    }

    @Scheduled(fixedRateString = "${app.scheduling.period_of_performing_cleaning_stuck_orders_in_millis}")
    public void changingInProcessStatusIntoNotProcessed() {
        log.info("changing queue item statuses from IN_PROCESS into NOT_PROCESSED {}",  LocalDateTime.now());
        List<PassengerQueueElement> passengersInProcessList = passengerQueueRepository.findByProcessingStatus(IN_PROCESS);
        passengersInProcessList.stream().forEach(passengerQueueElement -> {
            passengerQueueElement.setProcessingStatus(NOT_PROCESSED);
            passengerQueueRepository.save(passengerQueueElement);
        });

        List<DriverQueueElement> driversInProcessList = driverQueueRepository.findByProcessingStatus(IN_PROCESS);
        driversInProcessList.stream().forEach(driverQueueElement -> {
            driverQueueElement.setProcessingStatus(NOT_PROCESSED);
            driverQueueRepository.save(driverQueueElement);
        });

        if(!passengersInProcessList.isEmpty() || !driversInProcessList.isEmpty()){
            log.info("Successfully modified {} entities", passengersInProcessList.size() + driversInProcessList.size());
        }

    }

    @Scheduled(fixedRateString = "${app.scheduling.period_of_processing_queue_items}")
    public void processingQueueItems() {
        log.info("ScheduledTask.processingQueueItems.starting processing queue items {}",  LocalDateTime.now());
        int i = 0;
        while(rideService.tryToCreatePairFromQueue()){
            log.info("ScheduledTask.processingQueueItems.processed queue {} item {}", ++i, LocalDateTime.now());
        }
    }

}
