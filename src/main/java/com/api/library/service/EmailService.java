package com.api.library.service;

import java.util.HashMap;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String meuEmail;
    @Value("${spring.mail.password}")
    private String senha;

    private final Configuration config;

    public EmailService(Configuration config) {
        this.config = config;
    }

    public void enviarEmail(String assunto, String emailDestino, String mensagem) throws Exception {

        Session session = Session.getInstance(getProperties(), new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(meuEmail, senha);
            }
        });

        Address[] adressDestination = InternetAddress.parse(emailDestino);

        InternetAddress adressSender = new InternetAddress();
        adressSender.setPersonal("mySendName");
        adressSender.setAddress(meuEmail);

        Message message = new MimeMessage(session);
        String pageContent = convertTemplatePageIntoString();
        // Map<String, Object> model = new HashMap<>();
        // model.put("Name", "Anderson");

        message.setFrom(adressSender);
        message.addRecipients(Message.RecipientType.TO, adressDestination);
        message.setSubject(assunto);
        message.setContent(pageContent, "text/html; charset=utf-8");
        // message.setText(html);

        Transport.send(message);
    }

    private String convertTemplatePageIntoString() throws Exception {

        // Map<String, Object> model = new HashMap<>();
        // model.put("Name", "Anderson");

        Template pageTemplate = config.getTemplate("emailSend.html");
        String pageContent = FreeMarkerTemplateUtils.processTemplateIntoString(pageTemplate, new HashMap<>());

        return pageContent;
    }

    private Properties getProperties() {

        Properties properties = new Properties();

        properties.put("mail.smtp.ssl.trust", "*");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        return properties;
    }

}