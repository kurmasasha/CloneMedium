package ru.javamentor.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

/**
 * Тесты для класса MailSenderImpl
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class MailSenderImplTest {

    @Autowired
    MailSender mailSender;

    @MockBean
    JavaMailSender javaMailSender;

    /**
     * Тестирование отправки сообщения
     */
    @Test
    void send() {
        ArgumentCaptor<SimpleMailMessage> mailCapture = ArgumentCaptor.forClass(SimpleMailMessage.class);
        Mockito.doNothing()
                .when(javaMailSender)
                .send(mailCapture.capture());
        mailSender.send("emailTo", "subject", "message");

        Mockito.verify(javaMailSender, Mockito.times(1)).send(ArgumentMatchers.any(SimpleMailMessage.class));
        Assert.assertEquals("emailTo", Objects.requireNonNull(mailCapture.getValue().getTo())[0]);
        Assert.assertEquals("subject", mailCapture.getValue().getSubject());
        Assert.assertEquals("message", mailCapture.getValue().getText());
    }
}