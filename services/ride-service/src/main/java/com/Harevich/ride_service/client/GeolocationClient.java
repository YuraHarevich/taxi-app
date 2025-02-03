package com.Harevich.ride_service.client;

import com.Harevich.ride_service.dto.response.geolocation.DirectionsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(
        name = "geolocation",
        url = "${app.urls.openrouteservice.base-url}"
)
public interface GeolocationClient {
//    @GetMapping("/{relational_path}")
//    Map<String,Object> getCoordinatesByAddress(@PathVariable("relational_path") String coordinates_relational_path,
//                                @RequestParam("api_key") String api_key,
//                                @RequestParam("text") String text,
//                                @RequestParam(value = "boundary.country") String country);
//    @GetMapping("/geocode/search")
//    Map<String,Object> getCoordinatesByAddress(@RequestParam("api_key") String api_key,
//                                           @RequestParam("text") String text,
//                                           @RequestParam(value = "boundary.country") String country);
    @GetMapping("${relative_path}?api_key=${api_key}&text=${text}&boundary.country=${country}")
    Map<String,Object> getCoordinatesByAddress(@PathVariable("relative_path") String relative_path,
                                               @PathVariable("api_key") String api_key,
                                               @PathVariable("text") String text,
                                               @PathVariable("country") String country);

    @GetMapping("/{relational_path}")
    DirectionsResponse getDirectionByAddresses(@PathVariable("relational_path") String relational_path);

}