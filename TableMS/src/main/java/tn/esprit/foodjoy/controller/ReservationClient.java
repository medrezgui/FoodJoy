package tn.esprit.foodjoy.controller;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tn.esprit.foodjoy.dto.ReservationDto;
import tn.esprit.foodjoy.dto.ReservationEvent;

import java.util.List;

//TODO check with Service Reservation for correct PORT  & NAME (either in eureka or in app props)
@FeignClient(name="ReservationMS",url = "http://localhost:8083")
public interface ReservationClient {
    //TODO check with Service Reservation for correct URL
    @RequestMapping("/reservations/table/{tableId}")
    public List<ReservationEvent> getReservationsByTableId(@PathVariable("tableId") Long tableId);
    //TODO check with Service Reservation for correct URL
    @RequestMapping("/reservations/table/{tableId}/active")
    public ReservationEvent getActiveReservationByTableId(@PathVariable("tableId") Long tableId);
}
