package ru.javamentor.service;

import ru.javamentor.model.Notification;

import java.util.List;

public interface NotificationService {

    List<Notification>  getAllNotes();

    Notification getById(Long id);

    Notification getByTitle(String title);

    boolean updateNotification(Notification notification);

    boolean addNotification(Notification notification);

    boolean deleteNotification(Notification notification);

}
