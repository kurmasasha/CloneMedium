package ru.javamentor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Реализация интерфейса для отправки электронной почты
 *
 * @version 1.0
 * @autor Java Mentor
 */
@Slf4j
@Service
public class MailSenderImpl implements MailSender {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    /**
     * метод для отправки письма
     *
     * @param emailTo - адрес электронной почты на который необходимо отправить письмо
     * @param subject - текст представляющий тему письма, например "Activation code"
     * @param message - сообщение для отправки
     * @return void
     */
    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        log.debug("IN send - send mail from: {} send mail to: {} subject mail: {} message: {}", username, emailTo, subject, message);
        mailSender.send(mailMessage);
    }
}
