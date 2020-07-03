package ru.javamentor.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javamentor.dao.TopicDAOImpl;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;

import javax.persistence.TransactionRequiredException;
import java.time.LocalDateTime;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicServiceImplTest extends Mockito {

    @Autowired
    TopicServiceImpl topicService;

    @MockBean
    TopicDAOImpl topicDAO;

    @Test
    public void addTopic() {
        Set<User>users = new HashSet<>();
        Topic topic = topicService.addTopic("title", "content", users);

        //Проверка обращения к репозиторию
        Mockito.verify(topicDAO, Mockito.times(1)).addTopic(topic);

        //Проверка на null
        Assert.assertNotNull(topic);
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
        Assert.assertNull(topicService.addTopic(topic.getTitle(), topic.getContent(), topic.getAuthors()));

      //  Mockito.verify(topicDAO, Mockito.times(0)).addTopic(topic);
    }


    @Test
    public void getTopicById() {
        //Проверка возрващаемого типа
        Mockito.doReturn(new Topic())
                .when(topicDAO)
                .getTopicById(ArgumentMatchers.anyLong());

        //Проверка на наличие получаемого объекта
        Assert.assertNotNull(topicService.getTopicById(ArgumentMatchers.anyLong()));

        //Проверка обращения к дао
        Mockito.verify(topicDAO).getTopicById(Mockito.anyLong());
    }

    //Проверка на возвращаемые исключения при ошшибке
    @Test(expected = TransactionRequiredException.class)
    public void getTopicByIdFailTest() {
        Mockito.doThrow(new TransactionRequiredException())
                .when(topicDAO)
                .getTopicById(ArgumentMatchers.anyLong());
        Assert.assertNull( topicService.getTopicById(ArgumentMatchers.anyLong()));
        Mockito.verify(topicDAO, Mockito.times(1)).getTopicById(ArgumentMatchers.anyLong());
    }

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
        //Проверка на наличие получаемого объекта
        Assert.assertNotNull(topicService.getTotalListOfTopics());
        //Проверка на обращение  к дао
        Mockito.verify(topicDAO, Mockito.times(1)).getTotalListOfTopics();
    }

    @Test(expected = TransactionRequiredException.class)
    public void failGetTotalListOfTopics(){
        Mockito.doThrow(new TransactionRequiredException()).
                when(topicDAO)
                .getTotalListOfTopics();
        Assert.assertNotNull( topicService.getTotalListOfTopics());
    }

    @Test
    public void getTopicByTitle() {
        Mockito.doReturn(new Topic())
                .when(topicDAO)
                .getTopicByTitle(ArgumentMatchers.anyString());

        Assert.assertNotNull(topicService.getTopicByTitle(ArgumentMatchers.anyString()));
        Mockito.verify(topicDAO, Mockito.times(1)).getTopicByTitle(ArgumentMatchers.anyString());
    }

    @Test(expected = TransactionRequiredException.class)
    public void failTestGetTopicByTitle(){
        Mockito.doThrow(new TransactionRequiredException())
                .when(topicDAO)
                .getTopicByTitle(ArgumentMatchers.anyString());

        Assert.assertNull(topicService.getTopicByTitle(ArgumentMatchers.anyString()));
        Mockito.verify(topicDAO, Mockito.times(0)).getTopicByTitle(ArgumentMatchers.anyString());
    }


    @Test
    public void updateTopic() {
        //Проверка выполнения метода изменения
        Topic topic = new Topic();
        Assert.assertTrue(topicService.updateTopic(topic));

        //проверка обращения в дао
        Mockito.verify(topicDAO, Mockito.times(1)).updateTopic(topic);
    }

    @Test(expected = Exception.class)
    public void failTestUpdateTopic(){
        Mockito.doThrow(new Exception())
                .when(topicDAO)
                .updateTopic(new Topic());

        //Изменение несуществующего топика
        Topic topic = null;
        boolean updated = topicService.updateTopic(topic);
        Assert.assertFalse(updated);

        Mockito.verify(topicDAO, Mockito.times(0)).updateTopic(new Topic());
    }

    @Test
    public void removeTopicById() {
        //Проверка на возвращаемое значение при удалении топика
        Assert.assertTrue(topicService.removeTopicById(ArgumentMatchers.anyLong()));
        //Проверка обращения к дао
        Mockito.verify(topicDAO, Mockito.times(1)).removeTopicById(ArgumentMatchers.anyLong());
    }

    @Test(expected = Exception.class)
    public void failTestRemoveTopicById(){
        Mockito.doThrow(new Exception())
                .when(topicDAO)
                .removeTopicById(ArgumentMatchers.anyLong());
        Topic topic = null;
        doThrow(new NullPointerException()).when(topicService.removeTopicById(topic.getId()));

        Assert.assertFalse(!topicService.removeTopicById(ArgumentMatchers.anyLong()));
        Mockito.verify(topicDAO).removeTopicById(ArgumentMatchers.anyLong());
    }

    @Test
    public void getAllTopicsByUserId() {
        Mockito.doReturn(new ArrayList<Topic>())
                .when(topicDAO)
                .getAllTopicsByUserId(ArgumentMatchers.anyLong());

        Long id = 1l;
        List<Topic> topics = topicService.getAllTopicsByUserId(id);
        when(topicDAO.getAllTopicsByUserId(id)).thenReturn(topics);

        Assert.assertNotNull(topicService.getAllTopicsByUserId(ArgumentMatchers.anyLong()));

        Mockito.verify(topicDAO, Mockito.times(2)).getAllTopicsByUserId(ArgumentMatchers.anyLong());
    }

    @Test(expected = TransactionRequiredException.class)
    public void failTestGetAllTopicsByUserId(){
        Mockito.doThrow(new TransactionRequiredException())
                .when(topicDAO)
                .getAllTopicsByUserId(ArgumentMatchers.anyLong());
        Assert.assertNull(topicService.getAllTopicsByUserId(ArgumentMatchers.anyLong()));
        Mockito.verify(topicDAO, Mockito.times(0)).getAllTopicsByUserId(ArgumentMatchers.anyLong());
    }


    @Test
    public void getAllUsersByTopicId() {
        Mockito.doReturn(new ArrayList<User>())
                .when(topicDAO)
                .getAllUsersByTopicId(ArgumentMatchers.anyLong());

        Long id = 1l;
        List<User> users = topicService.getAllUsersByTopicId(id);
        when(topicDAO.getAllUsersByTopicId(id)).thenReturn(users);
        Assert.assertNotNull(topicService.getAllUsersByTopicId(ArgumentMatchers.anyLong()));
        Mockito.verify(topicDAO, Mockito.times(0)).getAllTopicsByUserId(ArgumentMatchers.anyLong());
    }

    @Test(expected = Exception.class)
    public void failTestGetAllUsersByTopicId(){
        Mockito.doThrow(new TransactionRequiredException())
                .when(topicDAO)
                .getAllUsersByTopicId(ArgumentMatchers.anyLong());
        Topic topic = null;
       when(topicService.getAllUsersByTopicId(topic.getId())).thenThrow(new NullPointerException());

        Assert.assertNull(topicService.getAllUsersByTopicId(ArgumentMatchers.anyLong()));

        Mockito.verify(topicDAO, Mockito.times(0)).getAllUsersByTopicId(ArgumentMatchers.anyLong());
    }

    @Test
    public void getAllTopicsByHashtag() {
        Mockito.doReturn(new ArrayList<Topic>())
                .when(topicDAO)
                .getAllTopicsByHashtag(ArgumentMatchers.anyString());

        String value = ArgumentMatchers.anyString();
        List<Topic> topics = topicService.getAllTopicsByHashtag(value);
        when(topicDAO.getAllTopicsByHashtag(value)).thenReturn(topics);
        Assert.assertNotNull(topicService.getAllTopicsByHashtag(ArgumentMatchers.anyString()));
        Mockito.verify(topicDAO, Mockito.times(2)).getAllTopicsByHashtag(ArgumentMatchers.anyString());
    }

    @Test(expected = Exception.class)
    public void failTestGetAllTopicsByHashtag(){
        Mockito.doThrow(new Exception())
                .when(topicDAO)
                .getAllTopicsByHashtag(ArgumentMatchers.anyString());
        String value = null;
        when(topicService.getAllTopicsByHashtag(value)).thenThrow(new NullPointerException());

        Assert.assertNull(topicService.getAllTopicsByHashtag(ArgumentMatchers.anyString()));
        Mockito.verify(topicDAO).getAllTopicsByHashtag(ArgumentMatchers.anyString());
    }

    @Test
    public void getAllTopicsOfUserByHashtag() {
        Mockito.doReturn(new ArrayList<Topic>())
                .when(topicDAO)
                .getAllTopicsOfUserByHashtag(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
        Assert.assertNotNull(topicService.getAllTopicsByUserId(ArgumentMatchers.anyLong()));

        Mockito.verify(topicDAO, Mockito.times(1)).getAllTopicsByUserId(ArgumentMatchers.anyLong());
    }

    @Test(expected = Exception.class)
    public void failTestGetAllTopicsOfUserByHashtag(){
        Mockito.doThrow(new Exception())
                .when(topicDAO)
                .getAllTopicsOfUserByHashtag(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
        String value = null;
        when(topicService.getAllTopicsOfUserByHashtag(ArgumentMatchers.anyLong(), value)).thenThrow(new Exception());
        Assert.assertNull(topicService.getAllTopicsOfUserByHashtag(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString()));
        Mockito.verify(topicDAO, Mockito.times(2)).getAllTopicsOfUserByHashtag(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
    }

    @Test
    public void getModeratedTopics() {
        Mockito.doReturn(new ArrayList<Topic>())
                .when(topicDAO)
                .getModeratedTopics();

        for (Topic topic : topicService.getModeratedTopics()){
            Assert.assertTrue(topic.isModerate());
        }
        Mockito.verify(topicDAO, Mockito.times(1)).getModeratedTopics();
    }

    @Test(expected = TransactionRequiredException.class)
    public void failTestGetModeratedTopics() {
        Mockito.doThrow(new TransactionRequiredException())
                .when(topicDAO)
                .getModeratedTopics();

        Assert.assertFalse(!topicService.getModeratedTopics().get(ArgumentMatchers.any()).isModerate());

        Assert.assertTrue(topicService.getModeratedTopics().isEmpty());

        Mockito.verify(topicDAO, Mockito.times(1)).getModeratedTopics();
    }

    @Test
    public void getNotModeratedTopics() {
        Mockito.doReturn(new ArrayList<Topic>())
                .when(topicDAO)
                .getNotModeratedTopics();

        for (Topic topic : topicService.getNotModeratedTopics()){
            Assert.assertTrue(!topic.isModerate());
        }

        Assert.assertNotNull(topicService.getNotModeratedTopics());

        Mockito.verify(topicDAO, Mockito.times(2)).getNotModeratedTopics();
    }

    @Test(expected = TransactionRequiredException.class)
    public void failTestGetNotModeratedTopics() {
        Mockito.doThrow(new TransactionRequiredException())
                .when(topicDAO)
                .getNotModeratedTopics();

        Assert.assertFalse(topicService.getNotModeratedTopics().get(ArgumentMatchers.any()).isModerate());

        Assert.assertTrue(topicService.getNotModeratedTopics().isEmpty());

        Mockito.verify(topicDAO, Mockito.times(1)).getNotModeratedTopics();
    }

    @Test
    public void getNotModeratedTopicsPage() {
        Mockito.doReturn(new ArrayList<Topic>())
                .when(topicDAO)
                .getNotModeratedTopicsPage(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt());

        for (Topic topic : topicService.getNotModeratedTopicsPage(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt())){
            Assert.assertTrue(!topic.isModerate());
        }

        Assert.assertNotNull(topicService.getNotModeratedTopicsPage(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()));

        Mockito.verify(topicDAO, Mockito.times(2)).getNotModeratedTopicsPage(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt());
    }

    @Test(expected = Exception.class)
    public void failTestGetNotModeratedTopicsPage() {
        Mockito.doThrow(new Exception())
                .when(topicDAO)
                .getNotModeratedTopicsPage(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt());

        Assert.assertFalse(topicService.getNotModeratedTopicsPage(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()).get(ArgumentMatchers.any()).isModerate());

        Assert.assertTrue(topicService.getNotModeratedTopicsPage(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()).isEmpty());

        Mockito.verify(topicDAO, Mockito.times(1)).getNotModeratedTopicsPage(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt());
    }

    @Test
    public void getNotModeratedTopicsCount() {
        List<Topic> topics = topicService.getTotalListOfTopics();
        Long len = (long) topics.size();
        Long count = 0l;
        for (int i = 0; i < topics.size(); i++) {
            if (!topics.get(i).isModerate()) {
                count++;
            }
        }
        Assert.assertEquals(topicDAO.getNotModeratedTopicsCount(), count);

        Mockito.verify(topicDAO, Mockito.times(1)).getNotModeratedTopicsCount();
    }

    @Test(expected = Exception.class)
    public void failTestGetNotModeratedTopicsCount(){
        Mockito.doThrow(new Exception())
                .when(topicDAO)
                .getNotModeratedTopicsCount();

        Mockito.verify(topicDAO, Mockito.times(1)).getNotModeratedTopicsCount();
    }

    @Test
    public void increaseTopicLikes() {
        Mockito.doReturn(new Topic())
                .when(topicDAO)
                .getTopicById(ArgumentMatchers.anyLong());

        Long id = Mockito.anyLong();
        Topic topic = topicService.getTopicById(id);
        //Проверка кол-ва лайков
        Assert.assertEquals(topicService.increaseTopicLikes(id), topic.getLikes() );
        //Проверка обращения к дао
        Mockito.verify(topicDAO, Mockito.times(2)).updateTopic(topicDAO.getTopicById(Mockito.anyLong()));
    }

    @Test(expected = Exception.class)
    public void failTestIncreaseTopicLikes() {
        Mockito.doThrow(new Exception())
                .when(topicService)
                .increaseTopicLikes(ArgumentMatchers.anyLong());
        Long id = ArgumentMatchers.anyLong();
        Mockito.verify(topicService, Mockito.times(0)).increaseTopicLikes(id);
    }
}
