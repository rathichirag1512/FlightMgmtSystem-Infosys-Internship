package com.agile.flightMgmtSystem.controller;

import com.agile.flightMgmtSystem.bean.Passenger;
import com.agile.flightMgmtSystem.bean.Ticket;
import com.agile.flightMgmtSystem.bean.TicketPassengerEmbed;
import com.agile.flightMgmtSystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookingController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/bookTicket")
    public String showBookingPage(Model model, @RequestParam Long flightNumber, @RequestParam String carrierName,
                                  @RequestParam String fromAirport, @RequestParam String toAirport, @RequestParam Double fare, @RequestParam Long routeId) {
        Ticket ticket = new Ticket();
        ticket.setTicketNumber(ticketService.generateTicketNumber());
        ticket.setFlightNumber(flightNumber);
        ticket.setCarrierName(carrierName);
        ticket.setRouteId(routeId);
        ticket.setTotalAmount(fare);  // Setting totalAmount correctly

        model.addAttribute("ticket", ticket);
        model.addAttribute("fromAirport", fromAirport);
        model.addAttribute("toAirport", toAirport);
        model.addAttribute("fare", fare);

        // Prepopulate passenger names and DOBs for the form
        model.addAttribute("passengerNames", new String[]{"", "", "", "", "", ""});
        model.addAttribute("passengerDOBs", new String[]{"", "", "", "", "", ""});

        return "bookingPage";
    }

    @PostMapping("/bookTicket")
    public String bookTicket(@ModelAttribute("ticket") Ticket ticket,
                             @RequestParam("name") String[] names,
                             @RequestParam("dob") String[] dobs,
                             @RequestParam("fromAirport") String fromAirport,
                             @RequestParam("toAirport") String toAirport,
                             Model model) {
        List<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            if (!names[i].isEmpty() && !dobs[i].isEmpty()) {  // Check if name and DOB are not empty
                Passenger passenger = new Passenger();
                TicketPassengerEmbed embeddedId = new TicketPassengerEmbed();
                embeddedId.setTicketNumber(ticket.getTicketNumber());
                embeddedId.setSerialNumber(i + 1);
                passenger.setEmbeddedId(embeddedId);
                passenger.setPassengerName(names[i]);
                passenger.setPassengerDOB(dobs[i]);
                passenger.setFare(ticketService.discountedFareCalculation(ticket.getTotalAmount(), dobs[i]));
                passengers.add(passenger);
            }
        }
        
        if (!ticketService.capacityCalculation(passengers.size(), ticket.getFlightNumber())) {
            model.addAttribute("error", "Seat not found, number of seats are less than the number of passengers.");
            model.addAttribute("ticket", ticket);
            model.addAttribute("fromAirport", fromAirport);
            model.addAttribute("toAirport", toAirport);
            return "bookingPage";
        }

        Ticket bookedTicket = ticketService.bookTicket(ticket, passengers);

        model.addAttribute("ticket", bookedTicket);
        model.addAttribute("passengers", passengers);
        model.addAttribute("fromAirport", fromAirport);
        model.addAttribute("toAirport", toAirport);

        return "bookingConfirmation";
    }


    
    @GetMapping("/passengerDetails")
    public String showPassengerDetails(@RequestParam(required = false) Long ticketNumber, Model model) {
        List<Passenger> passengers;
        if (ticketNumber != null) {
            passengers = ticketService.getPassengersByTicketNumber(ticketNumber);
        } else {
            passengers = ticketService.getAllPassengers();
        }
        model.addAttribute("passengers", passengers);
        return "passengerDetails";
    }
    
    
    
    @GetMapping("/ticketsBooked")
    public String showTicketsBooked(Model model) {
        List<Ticket> tickets = ticketService.getAllTickets();
        model.addAttribute("tickets", tickets);
        return "ticketsBooked";
    }
    
    @PostMapping("/cancelTicket")
    public String cancelTicket(@RequestParam("ticketNumber") Long ticketNumber, Model model) {
        boolean success = ticketService.cancelTicket(ticketNumber);
        if (success) {
            model.addAttribute("message", "Ticket cancelled successfully.");
        } else {
            model.addAttribute("message", "Ticket cancellation failed.");
        }
        return "cancellationResult";
    }
}
