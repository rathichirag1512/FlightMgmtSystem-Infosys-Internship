package com.agile.flightMgmtSystem.service;

import com.agile.flightMgmtSystem.bean.Flight;
import com.agile.flightMgmtSystem.bean.Passenger;
import com.agile.flightMgmtSystem.bean.Ticket;
import com.agile.flightMgmtSystem.bean.TicketPassengerEmbed;
import com.agile.flightMgmtSystem.repository.FlightRepository;
import com.agile.flightMgmtSystem.repository.PassengerRepository;
import com.agile.flightMgmtSystem.repository.TicketRepository;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PassengerRepository passengerRepository;
    
    @Autowired
    private FlightRepository flightRepository;

    private static final Long INITIAL_TICKET_NUMBER = 10000001L;

    public Long generateTicketNumber() {
        Long maxTicketNumber = ticketRepository.findMaxTicketNumber();
        return (maxTicketNumber != null) ? maxTicketNumber + 1 : INITIAL_TICKET_NUMBER;
    }

    @Transactional
    public Ticket bookTicket(Ticket ticket, List<Passenger> passengers) {
        // Ensure total amount is set before saving the ticket
        double totalAmount = passengers.stream().mapToDouble(Passenger::getFare).sum();
        ticket.setTotalAmount(totalAmount);
        
        // Save the ticket
        ticket = ticketRepository.save(ticket);
        
        // Save passengers
        for (Passenger passenger : passengers) {
            passengerRepository.save(passenger);
        }

        return ticket;
    }

    public Ticket getTicketByNumber(Long ticketNumber) {
        return ticketRepository.findById(ticketNumber).orElse(null);
    }

    public List<Passenger> getPassengersByTicketNumber(Long ticketNumber) {
        return passengerRepository.findByEmbeddedIdTicketNumber(ticketNumber);
    }

    private int ageCalculation(String dob) {
    	 try {
             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
             LocalDate birthDate = LocalDate.parse(dob, formatter);
             return LocalDate.now().getYear() - birthDate.getYear();
         } catch (DateTimeParseException e) {
             throw new IllegalArgumentException("Invalid date format: " + dob, e);
         }
    }

    public Double discountedFareCalculation(Double fare, String dob) {
        int age = ageCalculation(dob);
        if (age <= 14) {
            return fare / 2;
        } else if (age >= 60) {
            return fare - fare * 0.30;
        } else {
            return fare;
        }
    }
    
    public boolean capacityCalculation(int numberOfSeatBooking, long flightNumber) {
        Flight flight = flightRepository.findById(flightNumber).orElse(null);
        if (flight == null) {
            return false;
        }
        
        Integer seatBooked = flight.getSeatBooked();
        int bookedSeat = (seatBooked == null ? 0 : seatBooked) + numberOfSeatBooking;
        int balance = flight.getSeatCapacity() - bookedSeat;
        
        if (balance < 0) {
            return false;
        } else {
            flight.setSeatBooked(bookedSeat);
            flightRepository.save(flight);
            return true;
        }
    }
    
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAllByOrderByEmbeddedId_TicketNumberAsc();
    }
    
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
    
    public boolean cancelTicket(Long ticketNumber) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(ticketNumber);
        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            
            // Fetch passengers associated with the ticket
            List<Passenger> passengers = passengerRepository.findByEmbeddedIdTicketNumber(ticketNumber);

            // Update seat capacity
            Flight flight = flightRepository.findById(ticket.getFlightNumber()).orElse(null);
            if (flight != null) {
                int seatsToFree = passengers.size();
                flight.setSeatBooked(flight.getSeatBooked() - seatsToFree);
                flightRepository.save(flight);
            }
            
            // Delete ticket and associated passengers
            passengerRepository.deleteAll(passengers);
            ticketRepository.delete(ticket);
            return true;
        }
        return false;
    }
}
