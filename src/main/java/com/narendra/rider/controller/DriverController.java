package com.narendra.rider.controller;

import com.narendra.rider.model.Ride;
import com.narendra.rider.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/driver/rides")
public class DriverController {

    @Autowired
    private RideService rideService;

    @GetMapping("/requests")
    public ResponseEntity<List<Ride>> getPendingRides() {
        return ResponseEntity.ok(rideService.getPendingRides());
    }

    @PostMapping("/{rideId}/accept")
    public ResponseEntity<Ride> acceptRide(@PathVariable String rideId) {
        return ResponseEntity.ok(rideService.acceptRide(rideId));
    }
}
