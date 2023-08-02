package com.lurodev.apisendemail.Services;

import com.lurodev.apisendemail.Model.Attachment;
import com.lurodev.apisendemail.Model.Email;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.codec.binary.Base64;

@Service
public class EmailService {
    private final Email email;

    public EmailService(Email email) {
        this.email = email;
    }

    public void sendEmail(String recipient, String subject, String message, List<Attachment> attachments) throws MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", email.getSmtpHost());
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email.getUsername(), email.getPassword());
            }
        });

        // Crear el mensaje
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(email.getUsername()));
        mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        mimeMessage.setSubject(subject);

        // Si hay adjuntos, agregarlos al mensaje
        if (attachments != null && !attachments.isEmpty()) {
            MimeMultipart multipart = new MimeMultipart();

            // Agregar el contenido del mensaje (texto)
            BodyPart messageBodyPart = new MimeBodyPart();
            if (isHtmlContent(message)) {
                messageBodyPart.setContent(message, "text/html");
            }
            else {
                messageBodyPart.setText(message);
            }
            multipart.addBodyPart(messageBodyPart);

            // Agregar los archivos adjuntos
            for (Attachment attachment : attachments) {
                MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                byte[] decodedAttachmentContent = Base64.decodeBase64(attachment.getContent());
                DataSource source = new ByteArrayDataSource(decodedAttachmentContent, "application/octet-stream");
                attachmentBodyPart.setDataHandler(new DataHandler(source));
                attachmentBodyPart.setFileName(attachment.getFilename());
                multipart.addBodyPart(attachmentBodyPart);
            }

            // Establecer el contenido del mensaje
            mimeMessage.setContent(multipart);
        }
        else {
            // Si no hay adjuntos, agregar solo el texto del mensaje
            if (isHtmlContent(message)) {
                mimeMessage.setContent(message, "text/html");
            }
            else {
                mimeMessage.setText(message);
            }
        }
        // Enviar el correo
        Transport.send(mimeMessage);
    }

    private boolean isHtmlContent(String content) {
        // Simple heurística para detectar si el contenido es HTML o texto plano
        return content.trim().toLowerCase().startsWith("<html");
    }
}
