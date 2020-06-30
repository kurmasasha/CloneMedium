package ru.javamentor.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javamentor.dao.NotificationsDaoImpl;
import ru.javamentor.model.Notification;

import javax.persistence.TransactionRequiredException;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificationServiceImplTest extends Mockito {

    @Autowired
    private NotificationServiceImpl notificationService;

    @MockBean
    NotificationsDaoImpl notificationsDao;

    @Test
    public void getAllNotes() {
        Mockito.doReturn(new ArrayList<Notification>())
                .when(notificationsDao)
                .getAllNotes();

        Assert.assertNotNull(notificationService.getAllNotes());

        Mockito.verify(notificationsDao, Mockito.times(1)).getAllNotes();
    }

    @Test(expected = TransactionRequiredException.class)
    public void failTestGetAllNotes(){
        Mockito.doThrow(new TransactionRequiredException())
                .when(notificationsDao)
                .getAllNotes();

        Assert.assertNull(notificationService.getAllNotes());

        Mockito.verify(notificationsDao).getAllNotes();
    }


    @Test
    public void getById() {
        Mockito.doReturn(new Notification())
                .when(notificationsDao)
                .getOne(ArgumentMatchers.anyLong());

        Assert.assertNotNull(notificationService.getById(ArgumentMatchers.anyLong()));

        Mockito.verify(notificationsDao, Mockito.times(1)).getOne(ArgumentMatchers.anyLong());
    }

    @Test(expected = TransactionRequiredException.class)
    public void failTestGetById(){
        Mockito.doThrow(new TransactionRequiredException())
                .when(notificationsDao)
                .getOne(ArgumentMatchers.anyLong());

        Assert.assertNull(notificationService.getById(ArgumentMatchers.anyLong()));

        Mockito.verify(notificationsDao).getOne(ArgumentMatchers.anyLong());
    }

    @Test
    public void getByTitle() {
        Mockito.doReturn(new Notification())
                .when(notificationsDao)
                .getByTitle(ArgumentMatchers.anyString());

        Assert.assertNotNull(notificationService.getByTitle(ArgumentMatchers.anyString()));

        Mockito.verify(notificationsDao, Mockito.times(1)).getByTitle(ArgumentMatchers.anyString());
    }

    @Test(expected = TransactionRequiredException.class)
    public void failTestGetByTitle(){
        Mockito.doThrow(new TransactionRequiredException())
                .when(notificationsDao)
                .getByTitle(ArgumentMatchers.anyString());

        Assert.assertNull(notificationService.getByTitle(ArgumentMatchers.anyString()));

        Mockito.verify(notificationsDao).getByTitle(ArgumentMatchers.anyString());
    }

    @Test
    public void updateNotification() {
        Notification notification = new Notification();
        Assert.assertTrue(notificationService.updateNotification(notification));

        Mockito.verify(notificationsDao, Mockito.times(1)).updateNotification(notification);
    }

    @Test(expected = Exception.class)
    public void failTestUpdateNotification(){
        Notification notification = new Notification();
        doThrow(new Exception())
                .when(notificationsDao)
                .updateNotification(notification);

        Mockito.verify(notificationsDao, Mockito.times(0)).updateNotification(notification);
    }

    @Test
    public void addNotification() {
        Notification notification = new Notification();
        Assert.assertTrue(notificationService.addNotification(notification));

        Mockito.verify(notificationsDao, Mockito.times(1)).addNotification(notification);
    }

    @Test(expected = Exception.class)
    public void failTestAddNotification(){
        Notification notification = new Notification();
        doThrow(new Exception())
                .when(notificationsDao)
                .addNotification(notification);

        Mockito.verify(notificationsDao, Mockito.times(0)).addNotification(notification);
    }

    @Test
    public void deleteNotification() {
        Notification notification = new Notification();
        Assert.assertTrue(notificationService.deleteNotification(notification));

        Mockito.verify(notificationsDao, Mockito.times(1)).deleteNotification(notification);
    }

    @Test(expected = Exception.class)
    public void failTestDeleteNotification(){
        Notification notification = new Notification();
        doThrow(new Exception())
                .when(notificationsDao)
                .addNotification(notification);
        Assert.assertFalse(notificationService.deleteNotification(notification));

        Mockito.verify(notificationsDao, Mockito.times(0)).addNotification(notification);
    }
}
