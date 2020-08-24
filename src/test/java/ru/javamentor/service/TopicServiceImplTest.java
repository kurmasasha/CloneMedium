package ru.javamentor.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javamentor.dao.topic.TopicDAOImpl;
import ru.javamentor.dao.user.UserDAO;
import ru.javamentor.dto.TopicDto;
import ru.javamentor.model.Role;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;
import ru.javamentor.service.topic.TopicServiceImpl;

import javax.persistence.TransactionRequiredException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * JUnit-тестирование методов класса TopicServiceImpl
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicServiceImplTest extends Mockito {

    @Autowired
    TopicServiceImpl topicService;

    @MockBean
    TopicDAOImpl topicDAO;

    @SpyBean
    UserDAO userDAO;

    /**
     * Проверка метода добавления нового топика
     */
    @Test
    public void addTopic() {
        Set<User> users = new HashSet<>();
        Topic topic = topicService.addTopic("title", "content", true, "image.png", users);

        //Проверка обращения к репозиторию
        Mockito.verify(topicDAO, Mockito.times(1)).addTopic(topic);

        Assert.assertNotNull("Проверка на null", topic);
        //Проверка признака модерирования
        Assert.assertFalse(topic.isModerate());
        //Тест на дату создания топика
        Assert.assertEquals(topic.getDateCreated().getYear(), LocalDateTime.now().getYear());
        Assert.assertEquals(topic.getDateCreated().getMonth(), LocalDateTime.now().getMonth());
        Assert.assertEquals(topic.getDateCreated().getDayOfYear(), LocalDateTime.now().getDayOfYear());
        //Проверка названия
        Assert.assertEquals("title", topic.getTitle());
        //Проверка контента
        Assert.assertEquals("content", topic.getContent());
        //Проверка списка связанных пользователей
        Assert.assertEquals(users, topic.getAuthors());
    }

    @Test(expected = Exception.class)
    public void addTopicFailTest() {
        //проверка на выбрасываемое исключение
        Mockito.doThrow(new Exception())
                .when(topicDAO)
                .addTopic(ArgumentMatchers.any(Topic.class));
        //Создание  топика с пустыми полями
        Topic topic = new Topic();
        topic.setTitle(null);
        topic.setContent(null);
        topic.setAuthors(null);

        //проверка возвращаемого значения при добавлении пустого топика
        Assert.assertNull(topicService.addTopic(topic.getTitle(), topic.getContent(), true, topic.getImg(), topic.getAuthors()));

        //  Mockito.verify(topicDAO, Mockito.times(0)).addTopic(topic);
    }

    /**
     * Проверка метода получения топика по ID
     */
    @Test
    public void getTopicById() {
        //Проверка возрващаемого типа
        Mockito.doReturn(new Topic())
                .when(topicDAO)
                .getTopicById(ArgumentMatchers.anyLong());

        Assert.assertNotNull("Проверка на наличие получаемого объекта", topicService.getTopicById(ArgumentMatchers.anyLong()));

        //Проверка обращения к дао
        Mockito.verify(topicDAO).getTopicById(Mockito.anyLong());
    }

    //Проверка на возвращаемые исключения при ошшибке
    @Test(expected = RuntimeException.class)
    public void getTopicByIdFailTest() {
        Mockito.doThrow(new TransactionRequiredException())
                .when(topicDAO)
                .getTopicById(ArgumentMatchers.anyLong());
        Assert.assertNull(topicService.getTopicById(ArgumentMatchers.anyLong()));
        Mockito.verify(topicDAO, Mockito.times(1)).getTopicById(ArgumentMatchers.anyLong());
    }

    /**
     * Тест метода для получения списка всех топиков
     */
    @Test
    public void getTotalListOfTopics() {
        //проверка возвращаемого типа
        Mockito.doReturn(new ArrayList<Topic>())
                .when(topicDAO)
                .getTotalListOfTopics();
        //Проверка метода на возврат листа топиков
        List<Topic> topics = new ArrayList<>();
        Topic topic1 = new Topic();
        Topic topic2 = new Topic();
        topics.add(topic1);
        topics.add(topic2);
        Mockito.when(topicService.getTotalListOfTopics()).thenReturn(topics);

        Assert.assertNotNull("Проверка на наличие получаемого объекта", topicService.getTotalListOfTopics());
        //Проверка на обращение  к дао
        Mockito.verify(topicDAO, Mockito.times(1)).getTotalListOfTopics();
    }

    @Test(expected = RuntimeException.class)
    public void failGetTotalListOfTopics() {
        Mockito.doThrow(new TransactionRequiredException()).
                when(topicDAO)
                .getTotalListOfTopics();
        Assert.assertNotNull("Проверка на null", topicService.getTotalListOfTopics());
    }

    /**
     * Тест метода получения топика по id
     */
    @Test
    public void getTopicByTitle() {
        Mockito.doReturn(new Topic())
                .when(topicDAO)
                .getTopicByTitle(ArgumentMatchers.anyString());

        Assert.assertNotNull("Проверка на null", topicService.getTopicByTitle(ArgumentMatchers.anyString()));
        Mockito.verify(topicDAO, Mockito.times(1)).getTopicByTitle(ArgumentMatchers.anyString());
    }

    @Test(expected = RuntimeException.class)
    public void failTestGetTopicByTitle() {
        Mockito.doThrow(new TransactionRequiredException())
                .when(topicDAO)
                .getTopicByTitle(ArgumentMatchers.anyString());

        Assert.assertNull("Проверка на null", topicService.getTopicByTitle(ArgumentMatchers.anyString()));
        Mockito.verify(topicDAO, Mockito.times(0)).getTopicByTitle(ArgumentMatchers.anyString());
    }

    /**
     * Тест метода обновления топика
     */
    @Test
    public void updateTopic() {
        //Проверка выполнения метода изменения
        Topic topic = new Topic();

        Assert.assertTrue("Проверка результата выполнения метода", topicService.updateTopic(topic));

        //проверка обращения в дао
        Mockito.verify(topicDAO, Mockito.times(1)).updateTopic(topic);
    }

    @Test(expected = Exception.class)
    public void failTestUpdateTopic() {
        Mockito.doThrow(new Exception())
                .when(topicDAO)
                .updateTopic(new Topic());

        //Изменение несуществующего топика
        Topic topic = null;
        boolean updated = topicService.updateTopic(topic);
        Assert.assertFalse("Проверка результата выполнения метода", updated);

        Mockito.verify(topicDAO, Mockito.times(0)).updateTopic(new Topic());
    }

    /**
     * Проверка метода уваления топика
     * param id
     */
    @Test
    public void removeTopicById() {
        //Проверка на возвращаемое значение при удалении топика
        Assert.assertTrue("Проверка результата выполнения метода", topicService.removeTopicById(ArgumentMatchers.anyLong()));
        //Проверка обращения к дао
        Mockito.verify(topicDAO, Mockito.times(1)).removeTopicById(ArgumentMatchers.anyLong());
    }

    @Test(expected = Exception.class)
    public void failTestRemoveTopicById() {
        Mockito.doThrow(new Exception())
                .when(topicDAO)
                .removeTopicById(ArgumentMatchers.anyLong());
        Topic topic = null;
        doThrow(new NullPointerException()).when(topicService.removeTopicById(topic.getId()));

        Assert.assertFalse(!topicService.removeTopicById(ArgumentMatchers.anyLong()));
        Mockito.verify(topicDAO).removeTopicById(ArgumentMatchers.anyLong());
    }

    /**
     * Тест метода получения списка топиков у пользователя по его id
     */
    @Test
    public void getAllTopicsByUserId() {
        Mockito.doReturn(new ArrayList<Topic>())
                .when(topicDAO)
                .getAllTopicsByUserId(ArgumentMatchers.anyLong());


        Assert.assertNotNull("Проверка на null", topicService.getAllTopicsByUserId(ArgumentMatchers.anyLong()));

        Mockito.verify(topicDAO, Mockito.times(1)).getAllTopicsByUserId(ArgumentMatchers.anyLong());
    }

    @Test(expected = RuntimeException.class)
    public void failTestGetAllTopicsByUserId() {
        Mockito.doThrow(new TransactionRequiredException())
                .when(topicDAO)
                .getAllTopicsByUserId(ArgumentMatchers.anyLong());
        Assert.assertNull(topicService.getAllTopicsByUserId(ArgumentMatchers.anyLong()));
        Mockito.verify(topicDAO, Mockito.times(0)).getAllTopicsByUserId(ArgumentMatchers.anyLong());
    }

    /**
     * тест  метода получения авторов топика по id топика
     */
    @Test
    public void getAllUsersByTopicId() {
        Mockito.doReturn(new ArrayList<User>())
                .when(topicDAO)
                .getAllUsersByTopicId(ArgumentMatchers.anyLong());

        Long id = 1l;
        List<User> users = topicService.getAllUsersByTopicId(id);
        when(topicDAO.getAllUsersByTopicId(id)).thenReturn(users);
        Assert.assertNotNull("Проверка на null", topicService.getAllUsersByTopicId(ArgumentMatchers.anyLong()));
        Mockito.verify(topicDAO, Mockito.times(0)).getAllTopicsByUserId(ArgumentMatchers.anyLong());
    }

    @Test(expected = Exception.class)
    public void failTestGetAllUsersByTopicId() {
        Mockito.doThrow(new TransactionRequiredException())
                .when(topicDAO)
                .getAllUsersByTopicId(ArgumentMatchers.anyLong());

        Topic topic = null;
        when(topicService.getAllUsersByTopicId(topic.getId())).thenThrow(new NullPointerException());

        Assert.assertNull("Проверка на null", topicService.getAllUsersByTopicId(ArgumentMatchers.anyLong()));

        Mockito.verify(topicDAO, Mockito.times(0)).getAllUsersByTopicId(ArgumentMatchers.anyLong());
    }

    /**
     * Тест метода получения всех топиков по хэштегу
     */
    @Test
    public void getAllTopicsByHashtag() {
        Mockito.doReturn(new ArrayList<Topic>())
                .when(topicDAO)
                .getAllTopicsByHashtag(ArgumentMatchers.anyString());

        String value = ArgumentMatchers.anyString();
        List<Topic> topics = topicService.getAllTopicsByHashtag(value);
        when(topicDAO.getAllTopicsByHashtag(value)).thenReturn(topics);
        Assert.assertNotNull("Проверка на null", topicService.getAllTopicsByHashtag(ArgumentMatchers.anyString()));
        Mockito.verify(topicDAO, Mockito.times(2)).getAllTopicsByHashtag(ArgumentMatchers.anyString());
    }

    @Test(expected = Exception.class)
    public void failTestGetAllTopicsByHashtag() {
        Mockito.doThrow(new Exception())
                .when(topicDAO)
                .getAllTopicsByHashtag(ArgumentMatchers.anyString());
        String value = null;
        when(topicService.getAllTopicsByHashtag(value)).thenThrow(new NullPointerException());

        Assert.assertNull("Проверка на null", topicService.getAllTopicsByHashtag(ArgumentMatchers.anyString()));
        Mockito.verify(topicDAO).getAllTopicsByHashtag(ArgumentMatchers.anyString());
    }

    /**
     * Тест метода получения всех топиков конкретного пользователя по id пользователя и хэштегу
     */
    @Test
    public void getAllTopicsOfUserByHashtag() {
        Mockito.doReturn(new ArrayList<Topic>())
                .when(topicDAO)
                .getAllTopicsOfUserByHashtag(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());

        Assert.assertNotNull("Проверка на null", topicService.getAllTopicsByUserId(ArgumentMatchers.anyLong()));

        Mockito.verify(topicDAO, Mockito.times(1)).getAllTopicsByUserId(ArgumentMatchers.anyLong());
    }

    @Test(expected = Exception.class)
    public void failTestGetAllTopicsOfUserByHashtag() {
        Mockito.doThrow(new Exception())
                .when(topicDAO)
                .getAllTopicsOfUserByHashtag(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
        String value = null;
        when(topicService.getAllTopicsOfUserByHashtag(ArgumentMatchers.anyLong(), value)).thenThrow(new Exception());
        Assert.assertNull(topicService.getAllTopicsOfUserByHashtag(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString()));
        Mockito.verify(topicDAO, Mockito.times(2)).getAllTopicsOfUserByHashtag(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
    }

    /**
     * Тест метода получения отмодерированных топиков
     */
    @Test
    public void getModeratedTopics() {
        Mockito.doReturn(new ArrayList<Topic>())
                .when(topicDAO)
                .getModeratedTopics();

        for (Topic topic : topicService.getModeratedTopics()) {
            Assert.assertTrue("Проверка состояния топика", topic.isModerate());
        }

        Mockito.verify(topicDAO, Mockito.times(1)).getModeratedTopics();
    }

    @Test(expected = RuntimeException.class)
    public void failTestGetModeratedTopics() {
        Mockito.doThrow(new TransactionRequiredException())
                .when(topicDAO)
                .getModeratedTopics();

        Assert.assertFalse(!topicService.getModeratedTopics().get(ArgumentMatchers.any()).isModerate());

        Assert.assertTrue(topicService.getModeratedTopics().isEmpty());

        Mockito.verify(topicDAO, Mockito.times(1)).getModeratedTopics();
    }

    /**
     * Тест метода получения топиков, не прошедших модерацию
     */
    @Test
    public void getNotModeratedTopics() {
        Mockito.doReturn(new ArrayList<Topic>())
                .when(topicDAO)
                .getNotModeratedTopics();

        for (Topic topic : topicService.getNotModeratedTopics()) {
            Assert.assertTrue("Проверка состояния топика", !topic.isModerate());
        }

        Assert.assertNotNull("Проверка на null", topicService.getNotModeratedTopics());

        Mockito.verify(topicDAO, Mockito.times(2)).getNotModeratedTopics();
    }

    @Test(expected = RuntimeException.class)
    public void failTestGetNotModeratedTopics() {
        Mockito.doThrow(new TransactionRequiredException())
                .when(topicDAO)
                .getNotModeratedTopics();

        Assert.assertFalse(topicService.getNotModeratedTopics().get(ArgumentMatchers.any()).isModerate());

        Assert.assertTrue(topicService.getNotModeratedTopics().isEmpty());

        Mockito.verify(topicDAO, Mockito.times(1)).getNotModeratedTopics();
    }

    /**
     * Проверка метода получения немодерированных топиков по странице (номеру и размеру)
     */
    @Test
    public void getNotModeratedTopicsPage() {
        Mockito.doReturn(new ArrayList<Topic>())
                .when(topicDAO)
                .getNotModeratedTopicsPage(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt());

        for (Topic topic : topicService.getNotModeratedTopicsPage(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt())) {
            Assert.assertTrue("Проверка состояния топика", !topic.isModerate());
        }

        Assert.assertNotNull("Проверка на null", topicService.getNotModeratedTopicsPage(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()));

        Mockito.verify(topicDAO, Mockito.times(2)).getNotModeratedTopicsPage(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt());
    }

    @Test(expected = Exception.class)
    public void failTestGetNotModeratedTopicsPage() {
        Mockito.doThrow(new Exception())
                .when(topicDAO)
                .getNotModeratedTopicsPage(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt());

        Assert.assertFalse("Проверка состояния топика", topicService.getNotModeratedTopicsPage(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()).get(ArgumentMatchers.any()).isModerate());

        Assert.assertTrue("Проверка  на пустое значение", topicService.getNotModeratedTopicsPage(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()).isEmpty());

        Mockito.verify(topicDAO, Mockito.times(1)).getNotModeratedTopicsPage(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt());
    }

    /**
     * Тест метода получения кол-ва немодерированных топиков
     */
    @Test
    public void getNotModeratedTopicsCount() {
        List<Topic> topics = topicService.getTotalListOfTopics();
        Long count = 0l;
        for (int i = 0; i < topics.size(); i++) {
            if (!topics.get(i).isModerate()) {
                count++;
            }
        }
        Assert.assertEquals("Проверка на кол-во топиков, возвращаемое методом", topicDAO.getNotModeratedTopicsCount(), count);

        Mockito.verify(topicDAO, Mockito.times(1)).getNotModeratedTopicsCount();
    }

    @Test(expected = Exception.class)
    public void failTestGetNotModeratedTopicsCount() {
        Mockito.doThrow(new Exception())
                .when(topicDAO)
                .getNotModeratedTopicsCount();

        List<Topic> topics = topicService.getTotalListOfTopics();
        Long count = 1l;
        for (int i = 0; i < topics.size(); i++) {
            if (!topics.get(i).isModerate()) {
                count++;
            }
        }
        Assert.assertNotEquals("Проверка на кол-во топиков, возвращаемое методом", topicDAO.getNotModeratedTopicsCount(), count);

        Mockito.verify(topicDAO, Mockito.times(1)).getNotModeratedTopicsCount();
    }

    /**
     * Проверка метода поиска топиков по темам
     */
    @Test
    public void getModeratedTopicsByThemes() {
        Set<Long> themesIds = new HashSet<>();
        Mockito.doReturn(new ArrayList<>())
                .when(topicDAO)
                .getModeratedTopicsByTheme(themesIds);
        List<Topic> result = topicDAO.getModeratedTopicsByTheme(themesIds);
        Assert.assertNotNull("Проверка топиков, на возвращаемое значение", result);
        Mockito.verify(topicDAO, Mockito.times(1)).getModeratedTopicsByTheme(themesIds);

    }

    @Test
    public void failTestGetModeratedTopicsByThemes() {
        Set<Long> themesIds = new HashSet<>();
        Mockito.doThrow(new TransactionRequiredException())
                .when(topicDAO)
                .getModeratedTopicsByTheme(themesIds);
        List<?>[] list = new List[1];

        Assertions.assertThrows(RuntimeException.class, () -> {
            list[0] = topicDAO.getModeratedTopicsByTheme(themesIds);
        });

        Assert.assertNull("Проверка топиков, на возвращаемое значение", list[0]);
        Mockito.verify(topicDAO, Mockito.times(1)).getModeratedTopicsByTheme(themesIds);

    }

    /**
     * Проверка метода на добавление лайков
     */
    @Test
    public void addLikeToTopic() {
        User user = new User("firstName", "lastName", "username",
                "password", new Role("ADMIN"));
        user.setId(1L);
        Topic topic3 = new Topic();
        topic3.setId(1L);
        topic3.setLikedUsers(new HashSet<>());
        topic3.setDislikedUsers(new HashSet<>());
        Mockito.doReturn(topic3)
                .when(topicDAO)
                .getTopicById(1L);
        Mockito.doReturn(user)
                .when(userDAO)
                .getUserById(1L);
        Mockito.doNothing()
                .when(topicDAO)
                .updateTopic(ArgumentMatchers.any(Topic.class));

        Topic topic = topicService.addLikeToTopic(1L, user);

        Assert.assertEquals("Проверка на количество лайков", 1, (int) topic.getLikes());
        Assert.assertTrue("Проверка лайка юзера на топике", topic.getLikedUsers().contains(user));

        topic = topicService.addLikeToTopic(1L, user);

        Assert.assertEquals("Проверка на количество лайков", 0, (int) topic.getLikes());
        Assert.assertFalse("Проверка лайка юзера на топике", topic.getLikedUsers().contains(user));

        topic3.getDislikedUsers().add(user);
        topic3.setDislikes(1);

        topic = topicService.addLikeToTopic(1L, user);

        Assert.assertEquals("Проверка на количество лайков", 1, (int) topic.getLikes());
        Assert.assertEquals("Проверка на количество дизлайков", 0, (int) topic.getDislikes());
        Assert.assertTrue("Проверка лайка юзера на топике", topic.getLikedUsers().contains(user));
        Assert.assertFalse("Проверка дизлайка юзера на топике", topic.getDislikedUsers().contains(user));

    }

    @Test
    public void failTestAddLikeToTopic() {
        Mockito.doThrow(new TransactionRequiredException())
                .when(topicDAO)
                .getTopicById(1L);
        Assertions.assertThrows(RuntimeException.class, () -> {
            Topic topic = topicService.addLikeToTopic(1L, new User());
            Assert.assertNull("Проверка возвращаемого объекта", topic);
        });
        Mockito.verify(topicDAO, Mockito.times(1)).getTopicById(1L);
    }

    /**
     * Проверка метода на добавление дизлайков
     */
    @Test
    public void addDislikeToTopic() {
        User user = new User("firstName", "lastName", "username",
                "password", new Role("ADMIN"));
        user.setId(1L);
        Topic topic3 = new Topic();
        topic3.setId(1L);
        topic3.setLikedUsers(new HashSet<>());
        topic3.setDislikedUsers(new HashSet<>());
        Mockito.doReturn(topic3)
                .when(topicDAO)
                .getTopicById(1L);
        Mockito.doReturn(user)
                .when(userDAO)
                .getUserById(1L);
        Mockito.doNothing()
                .when(topicDAO)
                .updateTopic(ArgumentMatchers.any(Topic.class));

        Topic topic = topicService.addDislikeToTopic(1L, user);

        Assert.assertEquals("Проверка на количество дизлайков", 1, (int) topic.getDislikes());
        Assert.assertTrue("Проверка дизлайка юзера на топике", topic.getDislikedUsers().contains(user));

        topic = topicService.addDislikeToTopic(1L, user);

        Assert.assertEquals("Проверка на количество дизлайков", 0, (int) topic.getDislikes());
        Assert.assertFalse("Проверка дизлайка юзера на топике", topic.getDislikedUsers().contains(user));

        topic3.getLikedUsers().add(user);
        topic3.setLikes(1);

        topic = topicService.addDislikeToTopic(1L, user);

        Assert.assertEquals("Проверка на количество дизлайков", 1, (int) topic.getDislikes());
        Assert.assertEquals("Проверка на количество лайков", 0, (int) topic.getLikes());
        Assert.assertTrue("Проверка дизлайка юзера на топике", topic.getDislikedUsers().contains(user));
        Assert.assertFalse("Проверка лайка юзера на топике", topic.getLikedUsers().contains(user));

    }

    @Test
    public void failTestAddDislikeToTopic() {
        Mockito.doThrow(new TransactionRequiredException())
                .when(topicDAO)
                .getTopicById(1L);
        Assertions.assertThrows(RuntimeException.class, () -> {
            Topic topic = topicService.addDislikeToTopic(1L, new User());
            Assert.assertNull("Проверка возвращаемого объекта", topic);
        });
        Mockito.verify(topicDAO, Mockito.times(1)).getTopicById(1L);
    }

    /**
     * Проверка метода получения списка TopicDto из списка Topic
     */
    @Test
    public void getTopicDtoListByTopicList() {
        List<Topic> topicList = new ArrayList<>();
        Topic topic = new Topic("Топик","Топик");
        topicList.add(topic);
        List<TopicDto> topicDtoList = topicService.getTopicDtoListByTopicList(topicList);

        Assert.assertNotNull("Проверка на null", topicDtoList);
        Assert.assertEquals("Проверка на кол-во топиков",1,topicDtoList.size());
    }
}
