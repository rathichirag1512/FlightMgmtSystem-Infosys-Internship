package com.agile.flightMgmtSystem.service;

import com.agile.flightMgmtSystem.bean.Flight;
import com.agile.flightMgmtSystem.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public List<Flight> addFlight(Flight flight, String returnDeparture, String returnArrival) {
        if (flightRepository.existsByCarrierNameAndFlightNumber(flight.getCarrierName(), flight.getFlightNumber())) {
            throw new IllegalArgumentException("Flight ID already exists for this carrier.");
        }
        Flight savedFlight = flightRepository.save(flight);
        Flight returnFlight = addReturnFlight(savedFlight, returnDeparture, returnArrival);
        return List.of(savedFlight, returnFlight);
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public List<Flight> getFlightsByCities(String departure, String arrival) {
        return flightRepository.findByDepartureAndArrival(departure, arrival);
    }

    public Flight addReturnFlight(Flight originalFlight, String returnDeparture, String returnArrival) {
        Flight returnFlight = new Flight();
        returnFlight.setFlightNumber(originalFlight.getFlightNumber() + 1);
        returnFlight.setCarrierName(originalFlight.getCarrierName());
        returnFlight.setSeatCapacity(originalFlight.getSeatCapacity());
        returnFlight.setRouteId(originalFlight.getRouteId() + 1);
        returnFlight.setDeparture(returnDeparture);
        returnFlight.setArrival(returnArrival);

        return flightRepository.save(returnFlight);
    }

    public List<Flight> findFlightsByRoute(String fromCity, String toCity) {
        return flightRepository.findByRoute(fromCity, toCity);
    }

    public List<Flight> getFlightsByRouteId(Long routeId) {
        return flightRepository.findByRouteId(routeId);
    }
    
    public Optional<Flight> getFlightByNumber(Long flightNumber) {
        return flightRepository.findById(flightNumber);
    }

    public void updateFlight(Flight flight) {
        flightRepository.save(flight);
    }
    
    public void cancelFlight(Long flightNumber) {
        Optional<Flight> flight = flightRepository.findById(flightNumber);
        if (flight.isPresent()) {
            flightRepository.delete(flight.get());
        } else {
            throw new IllegalArgumentException("Flight not found");
        }
    }
}
