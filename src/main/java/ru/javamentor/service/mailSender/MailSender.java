package ru.javamentor.service.mailSender;

/**
 * Интерфейс для отправки электронной почты
 *
 * @version 1.0
 * @author Java Mentor
 */
public interface MailSender {
    /**
     * метод для отправки письма
     *
     * @param emailTo - адрес электронной почты на который необходимо отправить письмо
     * @param subject - текст представляющий тему письма, например "Activation code"
     * @param message - сообщение для отправки
     * @return void
     */
    void send(String emailTo, String subject, String message);
}
