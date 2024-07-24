package com.agile.flightMgmtSystem.controller;

import com.agile.flightMgmtSystem.bean.Feedback;
import com.agile.flightMgmtSystem.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;
    
    @GetMapping("/feedback")
    public String showFeedbackForm(Model model) {
        model.addAttribute("feedback", new Feedback());
        return "feedback";
    }

    @PostMapping("/submitFeedback")
    public String submitFeedback(@ModelAttribute Feedback feedback, @RequestParam("image") MultipartFile imageFile, Model model) {
        try {
            System.out.println("Received Feedback Name: " + feedback.getName());
            System.out.println("Received Feedback Email: " + feedback.getEmail());
            System.out.println("Received Feedback Content: " + feedback.getFeedback());

            if (!imageFile.isEmpty()) {
                byte[] imageBytes = imageFile.getBytes();
                feedback.setImage(imageBytes);
            }

            feedbackService.saveFeedback(feedback);

            model.addAttribute("feedback", feedback);
            return "feedSuccess";

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Failed to upload image");
            return "feedback";
        }
    }
}
