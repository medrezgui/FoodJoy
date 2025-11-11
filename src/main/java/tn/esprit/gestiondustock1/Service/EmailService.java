package tn.esprit.gestiondustock1.Service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class EmailService {

    private final String sendGridApiKey;

    public EmailService() {
        // Récupère la clé depuis la variable d'environnement
        this.sendGridApiKey = System.getenv("SENDGRID_API_KEY");

        if (this.sendGridApiKey == null || this.sendGridApiKey.isEmpty()) {
            throw new IllegalStateException("❌ La variable d'environnement SENDGRID_API_KEY n'est pas définie !");
        }

        System.out.println("Clé SendGrid actuelle : '" + sendGridApiKey + "'");

    }

    public void sendTemplateEmail(String toEmail, String templateId, Map<String, Object> dynamicData) throws IOException {
        // Création du mail
        Mail mail = new Mail();
        mail.setFrom(new Email("aflisarra19@gmail.com")); // Ton email vérifié sur SendGrid
        mail.setTemplateId(templateId);

        // Personnalisation
        Personalization personalization = new Personalization();
        personalization.addTo(new Email(toEmail));

        if (dynamicData != null) {
            dynamicData.forEach(personalization::addDynamicTemplateData);
        }

        mail.addPersonalization(personalization);

        // Envoi via SendGrid
        SendGrid sg = new SendGrid(this.sendGridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);
            System.out.println("✅ Email envoyé");
            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Body: " + response.getBody());
        } catch (IOException ex) {
            System.err.println("❌ Erreur lors de l'envoi de l'email : " + ex.getMessage());
            throw ex;
        }
    }
}
