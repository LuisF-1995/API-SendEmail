package com.lurodev.apisendemail.Controller;

import com.lurodev.apisendemail.Model.Email;
import com.lurodev.apisendemail.Model.MailResponse;
import com.lurodev.apisendemail.Services.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<MailResponse> sendEmail(@RequestBody Email emailContent){
        MailResponse response = new MailResponse();
        try {
            // Verificar el servidor y establecer la configuración correspondiente para el envío
            String smtpHost;
            if ("hotmail".equalsIgnoreCase(emailContent.getServer()) || "outlook".equalsIgnoreCase(emailContent.getServer())) {
                smtpHost = "smtp.office365.com";
            }
            else if ("gmail".equalsIgnoreCase(emailContent.getServer())) {
                smtpHost = "smtp.gmail.com";
            }
            else {
                response.setReponse("Servidor de correo no válido. Debe ser 'hotmail', 'outlook' o 'gmail'.");
                response.setStatus(400);
                response.setSuccess(false);
                return ResponseEntity.badRequest().body(response);
            }

            emailContent.setSmtpHost(smtpHost);

            EmailService emailService = new EmailService(emailContent);
            emailService.sendEmail(emailContent.getRecipient(), emailContent.getSubject(), emailContent.getMessage(), emailContent.getAttachments());

            response.setReponse("Correo enviado correctamente.");
            response.setStatus(200);
            response.setSuccess(true);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            response.setReponse("Error al enviar el correo: " + e.getMessage());
            response.setStatus(500);
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
