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
import ru.javamentor.dao.notification.NotificationDaoImpl;
import ru.javamentor.model.Notification;
import ru.javamentor.service.notification.NotificationServiceImpl;

import javax.persistence.TransactionRequiredException;
import java.util.ArrayList;

/**
 * Тесты для класса NotificationServiceImpl
 *
 * @version 1.0
 * @author Java Mentor
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificationServiceImplTest extends Mockito {

    @Autowired
    private NotificationServiceImpl notificationService;

    @MockBean
    NotificationDaoImpl notificationsDao;

    /**
     * Тест метода получения всех уведомлений
     */
    @Test
    public void getAllNotes() {
        Mockito.doReturn(new ArrayList<Notification>())
                .when(notificationsDao)
                .getAllNotes();

        Assert.assertNotNull("проверка на null", notificationService.getAllNotes());

        Mockito.verify(notificationsDao, Mockito.times(1)).getAllNotes();
    }

    @Test(expected = TransactionRequiredException.class)
    public void failTestGetAllNotes(){
        Mockito.doThrow(new TransactionRequiredException())
                .when(notificationsDao)
                .getAllNotes();

        Assert.assertNull("проверка на null", notificationService.getAllNotes());

        Mockito.verify(notificationsDao).getAllNotes();
    }

    /**
     * Тетс метода получения уведомления по id
     */
    @Test
    public void getById() {
        Mockito.doReturn(new Notification())
                .when(notificationsDao)
                .getOne(ArgumentMatchers.anyLong());

        Assert.assertNotNull("проверка на null", notificationService.getById(ArgumentMatchers.anyLong()));

        Mockito.verify(notificationsDao, Mockito.times(1)).getOne(ArgumentMatchers.anyLong());
    }

    @Test(expected = TransactionRequiredException.class)
    public void failTestGetById(){
        Mockito.doThrow(new TransactionRequiredException())
                .when(notificationsDao)
                .getOne(ArgumentMatchers.anyLong());

        Assert.assertNull("проверка на null", notificationService.getById(ArgumentMatchers.anyLong()));

        Mockito.verify(notificationsDao).getOne(ArgumentMatchers.anyLong());
    }

    /**
     * Тест метода получения уведомления по  названию
     */
    @Test
    public void getByTitle() {
        Mockito.doReturn(new Notification())
                .when(notificationsDao)
                .getByTitle(ArgumentMatchers.anyString());

        Assert.assertNotNull("проверка на null", notificationService.getByTitle(ArgumentMatchers.anyString()));

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

    /**
     * Тест метода обновления уведомления
     */
    @Test
    public void updateNotification() {
        Notification notification = new Notification();

        Assert.assertTrue("проверка на состояние выполнения метода",  notificationService.updateNotification(notification));

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

    /**
     * тест метода добавления нового уведомления
     */
    @Test
    public void addNotification() {
        Notification notification = new Notification();

        Assert.assertTrue("проверка на состояние выполнения метода", notificationService.addNotification(notification));

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

    /**
     * Тест метода удаления уведомления
     */
    @Test
    public void deleteNotification() {
        Notification notification = new Notification();

        Assert.assertTrue("проверка на состояние выполнения метода", notificationService.deleteNotification(notification));

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
