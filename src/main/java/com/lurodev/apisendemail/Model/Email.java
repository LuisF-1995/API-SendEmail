package com.lurodev.apisendemail.Model;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.List;

@Component
public class Email {
    private String username;
    private String password;
    private String smtpHost;
    private String recipient;
    private String subject;
    private String message;
    private List<Attachment> attachments;
    private String server;

    public Email() {
    }
    public Email(String username, String password, String smtpHost, String recipient, String subject, String message, String server) {
        this.username = username;
        this.password = password;
        this.smtpHost = smtpHost;
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        this.server = server;
    }
    public Email(String username, String password, String smtpHost, String recipient, String subject, String message, List<Attachment> attachments, String server) {
        this.username = username;
        this.password = password;
        this.smtpHost = smtpHost;
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        this.attachments = attachments;
        this.server = server;
    }

    //GETTERS AND SETTERS
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
