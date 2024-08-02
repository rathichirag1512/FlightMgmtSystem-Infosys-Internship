package com.agile.flightMgmtSystem.controller;

import com.agile.flightMgmtSystem.bean.Flight;
import com.agile.flightMgmtSystem.bean.Route;
import com.agile.flightMgmtSystem.exception.RouteException;
import com.agile.flightMgmtSystem.repository.AirportRepository;
import com.agile.flightMgmtSystem.repository.FlightRepository;
import com.agile.flightMgmtSystem.repository.RouteRepository;
import com.agile.flightMgmtSystem.service.AirportService;
import com.agile.flightMgmtSystem.service.FlightService;
import com.agile.flightMgmtSystem.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private AirportService airportService;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private FlightRepository flightRepository;

    @GetMapping("/addFlight")
    public String showAddFlightForm(Model model) {
        model.addAttribute("flight", new Flight());
        model.addAttribute("routes", routeService.getAllRoutes());
        return "addFlight";
    }

    @PostMapping("/addFlight")
    public String addFlight(@ModelAttribute("flight") Flight flight,
                            @RequestParam("returnDeparture") String returnDeparture,
                            @RequestParam("returnArrival") String returnArrival,
                            Model model) {
        try {
            List<Flight> flights = flightService.addFlight(flight, returnDeparture, returnArrival);
            model.addAttribute("successMessage", "Flight added successfully.");
            model.addAttribute("flight", new Flight()); // Reset the form
            model.addAttribute("routes", routeService.getAllRoutes());
            model.addAttribute("flights", flights);
            return "addFlight";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("routes", routeService.getAllRoutes());
            return "addFlight";
        }
    }

    @GetMapping("/flights")
    public String getAllFlights(Model model) {
        model.addAttribute("flights", flightService.getAllFlights());
        return "flightList";
    }

    @GetMapping("/viewFlight")
    public String viewFlights(Model model) {
        List<Flight> flights = flightService.getAllFlights();
        model.addAttribute("flights", flights);
        return "viewFlight";
    }

    @GetMapping("/searchFlight")
    public String searchFlight(Model model) {
        model.addAttribute("airports", airportService.getAllAirports());
        return "searchFlight";
    }

    @PostMapping("/searchFlightResult")
    public String searchFlights(@RequestParam("fromCity") String fromCity,
                                @RequestParam("toCity") String toCity,
                                Model model) {
        try {
            String fromAirportCode = airportRepository.findAirportCodeByLocation(fromCity);
            String toAirportCode = airportRepository.findAirportCodeByLocation(toCity);

            if (fromAirportCode == null || toAirportCode == null) {
                throw new RouteException("Invalid airport location(s) provided.");
            }

            if (fromAirportCode.equalsIgnoreCase(toAirportCode)) {
                throw new RouteException("From-city & To-City cannot be the same.");
            }

            Optional<Route> optionalRoute = routeRepository.findBySourceAirportCodeAndDestinationAirportCode(fromAirportCode, toAirportCode);
            if (!optionalRoute.isPresent()) {
                throw new RouteException("Route not found between the specified cities.");
            }

            Route route = optionalRoute.get();
            List<Flight> flightList = flightRepository.findByRouteId(route.getRouteId());

            if (flightList.isEmpty()) {
                model.addAttribute("errorMessage", "No flights found for the selected route.");
                return "searchFlight";
            }

            model.addAttribute("flightList", flightList);
            model.addAttribute("fromAirport", fromCity);
            model.addAttribute("toAirport", toCity);
            model.addAttribute("fare", route.getFare());
            return "searchFlightResult";

        } catch (RouteException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("airports", airportService.getAllAirports());
            return "searchFlight";
        }
    }

    
    @GetMapping("/modifyFlight")
    public String showModifyFlightPage(@RequestParam("flightNumber") Long flightNumber, Model model) {
        Optional<Flight> flight = flightService.getFlightByNumber(flightNumber);
        if (flight.isPresent()) {
            model.addAttribute("flight", flight.get());
            return "modifyFlight";
        } else {
            return "redirect:/viewFlight";
        }
    }

    @PostMapping("/updateFlight")
    public String updateFlight(@RequestParam("flightNumber") Long flightNumber,
                               @RequestParam("departure") String departure,
                               @RequestParam("arrival") String arrival,
                               Model model) {
        Optional<Flight> flightOpt = flightService.getFlightByNumber(flightNumber);
        if (flightOpt.isPresent()) {
            Flight flight = flightOpt.get();
            flight.setDeparture(departure);
            flight.setArrival(arrival);
            flightService.updateFlight(flight);
            return "redirect:/viewFlight";
        } else {
            return "redirect:/viewFlight";
        }
    }
    
    @GetMapping("/cancelFlight")
    public String showCancelFlightForm(@RequestParam Long flightNumber, Model model) {
        model.addAttribute("flightNumber", flightNumber);
        return "cancelFlight";
    }

    @PostMapping("/cancelFlight")
    public String cancelFlight(@RequestParam Long flightNumber, Model model) {
        try {
            flightService.cancelFlight(flightNumber);
            model.addAttribute("message", "Flight canceled successfully");
            return "redirect:/viewFlight";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}