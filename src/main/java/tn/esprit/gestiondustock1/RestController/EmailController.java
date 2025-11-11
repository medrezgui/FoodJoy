package tn.esprit.gestiondustock1.RestController;

import org.springframework.web.bind.annotation.*;
import tn.esprit.gestiondustock1.Service.EmailService;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public String sendEmail(
            @RequestParam String templateId,
            @RequestParam String message) throws IOException {

        Map<String, Object> vars = new HashMap<>();
        vars.put("message", message);

        emailService.sendTemplateEmail(
                "aflisarra19@gmail.com",
                templateId,
                vars
        );

        return "Email envoyé ✅";
    }

    @PostMapping("/test-sendgrid")
    public String testSend() throws IOException {
        Map<String, Object> vars = new HashMap<>();
        vars.put("ingredient", "Test Ingredient");
        vars.put("quantite", 5);
        vars.put("datePeremption", "2025-12-01");

        emailService.sendTemplateEmail(
                "aflisarra19@gmail.com",
                "d-cc39b7ae9bf14245815d712fda163291", // ton template ID
                vars
        );

        return "Email test envoyé ✅";
    }
}
