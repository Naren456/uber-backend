package com.narendra.rider.service;

import com.narendra.rider.dto.request.CreateRideRequest;
import com.narendra.rider.exception.BadRequestException;
import com.narendra.rider.exception.NotFoundException;
import com.narendra.rider.model.Ride;
import com.narendra.rider.model.User;
import com.narendra.rider.repository.RideRepository;
import com.narendra.rider.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public Ride createRide(CreateRideRequest request) {
        User user = getCurrentUser();
        Ride ride = new Ride(user.getId(), request.getPickupLocation(), request.getDropLocation(), "REQUESTED");
        return rideRepository.save(ride);
    }

    public List<Ride> getPendingRides() {
        return rideRepository.findByStatus("REQUESTED");
    }

    public Ride acceptRide(String rideId) {
        User driver = getCurrentUser();
        // Although Security configuration restricts the endpoint to drivers, good to check/ensure?
        // Relying on security config for role check.

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!"REQUESTED".equals(ride.getStatus())) {
            throw new BadRequestException("Ride is not in REQUESTED status");
        }

        ride.setDriverId(driver.getId());
        ride.setStatus("ACCEPTED");
        return rideRepository.save(ride);
    }

    public Ride completeRide(String rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!"ACCEPTED".equals(ride.getStatus())) {
            throw new BadRequestException("Ride is not in ACCEPTED status");
        }

        ride.setStatus("COMPLETED");
        return rideRepository.save(ride);
    }

    public List<Ride> getUserRides() {
        User user = getCurrentUser();
        return rideRepository.findByUserId(user.getId());
    }
}
