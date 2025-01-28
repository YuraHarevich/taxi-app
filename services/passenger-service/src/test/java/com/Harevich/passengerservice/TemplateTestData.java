package com.Harevich.passengerservice;

import com.Harevich.passengerservice.dto.PassengerRequest;

public class TemplateTestData {
    public static PassengerRequest createFirstPassengerRequest(){
        return new PassengerRequest(
                "Arsen",
                "Hydnitsky",
                "arsen@gmail.com",
                "+375447555799"
        );
    }
    public static PassengerRequest createUpdatePassengerRequest(){
        return new PassengerRequest(
                "Yura",
                "Harevich",
                "Nearsen@gmail.com",
                ""
        );
    }
    public static PassengerRequest createUpdatePassengerRequestWithConflict(){
        return  new PassengerRequest(
                "TochnoNeArsen",
                "TochnoNeHydnitsky",
                "TochnoNearsen@gmail.com",
                "+375447555799"
        );
    }
    public static PassengerRequest createSecondPassengerRequest(){
        return new PassengerRequest(
                "NeArsen",
                "NeHydnitsky",
                "Nearsen@gmail.com",
                "+375447555798"
        );
    }

}
