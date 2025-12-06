package com.narendra.rider.controller;

import com.narendra.rider.dto.request.CreateRideRequest;
import com.narendra.rider.model.Ride;
import com.narendra.rider.service.RideService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RideController {

    @Autowired
    private RideService rideService;

    @PostMapping("/rides")
    public ResponseEntity<Ride> createRide(@Valid @RequestBody CreateRideRequest request) {
        return ResponseEntity.ok(rideService.createRide(request));
    }

    @PostMapping("/rides/{rideId}/complete")
    public ResponseEntity<Ride> completeRide(@PathVariable String rideId) {
        return ResponseEntity.ok(rideService.completeRide(rideId));
    }

    @GetMapping("/user/rides")
    public ResponseEntity<List<Ride>> getUserRides() {
        return ResponseEntity.ok(rideService.getUserRides());
    }
}
