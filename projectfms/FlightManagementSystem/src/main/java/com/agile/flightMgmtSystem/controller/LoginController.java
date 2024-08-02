package com.agile.flightMgmtSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.agile.flightMgmtSystem.bean.FlightUser;
import com.agile.flightMgmtSystem.bean.Passenger;
import com.agile.flightMgmtSystem.bean.Ticket;
import com.agile.flightMgmtSystem.repository.UserRepository;
import com.agile.flightMgmtSystem.service.TicketService;
import com.agile.flightMgmtSystem.service.UserService;

import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TicketService ticketService;
    
    
/*
    @GetMapping("/index")
    public String showIndexPage() {
        return "redirect:/login";
    }
*/
    @GetMapping("/login")
    public String showLoginPage(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid email or password.");
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password,
                            HttpSession session, Model model) {
        System.out.println("Login attempt with email: " + email); // Debugging statement
        FlightUser user = userRepository.findByEmail(email);

        if (user != null) {
            System.out.println("User found: " + user.getUsername()); // Debugging statement

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            // Using BCrypt to check the password
            if (passwordEncoder.matches(password, user.getPassword())) {
                String userType = user.getUserType();
                System.out.println("User type: " + userType); // Debugging statement
                session.setAttribute("userType", userType); // Set userType in session
                if ("admin".equalsIgnoreCase(userType)) {
                    return "redirect:/aindex"; // Admin index page
                } else if ("customer".equalsIgnoreCase(userType)) {
                    return "redirect:/cindex"; // Customer index page
                } else {
                    return "redirect:/login?error=Unknown user type."; // Redirect with error
                }
            } else {
                return "redirect:/login?error=Invalid email or password."; // Redirect with error
            }
        } else {
            return "redirect:/login?error=Invalid email or password."; // Redirect with error
        }
    }

    @GetMapping("/newUser")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new FlightUser());
        return "newUser";
    }

    @PostMapping("/newUser")
    public String registerNewUser(@ModelAttribute("user") FlightUser user, Model model) {
        if (userService.emailExists(user.getEmail())) {
            model.addAttribute("errorMessage", "Email already exists.");
            return "newUser";
        }
        userService.save(user);
        return "redirect:/login"; // Redirect to login page after successful registration
    }
    
    @GetMapping("/cindex")
    public String showCustomerIndexPage() {
        return "cindex";
    }
    
    @GetMapping("/aindex")
    public String showAdminIndexPage() {
        return "aindex";
    }
    
    @GetMapping("/passengerDetailsC")
    public String showPassengerDetails(@RequestParam(required = false) Long ticketNumber, Model model) {
        List<Passenger> passengers;
        if (ticketNumber != null) {
            passengers = ticketService.getPassengersByTicketNumber(ticketNumber);
        } else {
            passengers = ticketService.getAllPassengers();
        }
        model.addAttribute("passengers", passengers);
        return "passengerDetailsC";
    }
    
    @GetMapping("/ticketsBookedA")
    public String showTicketsBooked(Model model) {
        List<Ticket> tickets = ticketService.getAllTickets();
        model.addAttribute("tickets", tickets);
        return "ticketsBookedA";
    }
    
    
    @PostMapping("/cancelTicketA")
    public String cancelTicket(@RequestParam("ticketNumber") Long ticketNumber, Model model) {
        boolean success = ticketService.cancelTicket(ticketNumber);
        if (success) {
            model.addAttribute("message", "Ticket cancelled successfully.");
        } else {
            model.addAttribute("message", "Ticket cancellation failed.");
        }
        return "cancellationResultA";
    }
}
