package ru.javamentor.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javamentor.dao.CommentDAOImpl;
import ru.javamentor.model.Comment;
import ru.javamentor.model.Role;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;

import javax.persistence.TransactionRequiredException;
import java.time.LocalDateTime;
import java.time.Month;

@RunWith(SpringRunner.class)
@SpringBootTest
class CommentServiceImplTest {

    @Autowired
    CommentServiceImpl commentService;

    @MockBean
    CommentDAOImpl commentDAO;

    /**
     * Проверка добавления комментария
     */
    @Test
    void addComment() {
        Comment comment = commentService.addComment("testText", new User(), new Topic());

        Assert.assertNotNull("Проверка создания объекта комментария", comment);
        Assert.assertNull("Проверка id комментария", comment.getId());
        Assert.assertEquals("Проверка текста комментария","testText", comment.getText());
        Assert.assertNotNull("Проверка автора комментария",comment.getAuthor());
        Assert.assertNotNull("Проверка связанного топика комментария",comment.getTopic());
        Assert.assertEquals("Проверка времени создания комментария",
                            comment.getDateCreated().getYear(), LocalDateTime.now().getYear());
        Assert.assertEquals("Проверка времени создания комментария",
                            comment.getDateCreated().getMonth(), LocalDateTime.now().getMonth());
        Assert.assertEquals("Проверка времени создания комментария",
                            comment.getDateCreated().getDayOfMonth(), LocalDateTime.now().getDayOfMonth());

        Mockito.verify(commentDAO, Mockito.times(1)).addComment(comment);
    }

    /**
     * Проверка обработки неудачного добавления комментария
     */
    @Test
    void failAddComment() {
        Mockito.doThrow(new TransactionRequiredException())
                .when(commentDAO)
                .addComment(ArgumentMatchers.any(Comment.class));

        Assert.assertNull("Проверка возвращаемого значения", commentService.addComment("", new User(), new Topic()));
        Mockito.verify(commentDAO, Mockito.times(1)).addComment(ArgumentMatchers.any(Comment.class));
    }

    /**
     * Проверка получения комментария по id
     */
    @Test
    void getCommentById() {
        Mockito.doReturn(new Comment("testText", new User(), new Topic(),
                                     LocalDateTime.of(2020, Month.JUNE, 26, 0, 0)))
                .when(commentDAO)
                .getCommentById(ArgumentMatchers.anyLong());

        Comment comment = commentService.getCommentById(ArgumentMatchers.anyLong());

        Assert.assertNotNull("Проверка наличия возвращаемого объекта комментария", comment);
        Assert.assertEquals("Проверка текста возвращаемого объекта комментария",
                            "testText", comment.getText());
        Assert.assertNotNull("Проверка автора возвращаемого объекта комментария",comment.getAuthor());
        Assert.assertNotNull("Проверка связанного топика возвращаемого объекта комментария",comment.getTopic());
        Assert.assertEquals("Проверка времени создания возвращаемого объекта комментария",
                            2020, comment.getDateCreated().getYear());
        Assert.assertEquals("Проверка времени создания возвращаемого объекта комментария",
                                    Month.JUNE, comment.getDateCreated().getMonth());
        Assert.assertEquals("Проверка времени создания возвращаемого объекта комментария",
                            0, comment.getDateCreated().getHour());
        Assert.assertEquals("Проверка времени создания возвращаемого объекта комментария",
                            0, comment.getDateCreated().getMinute());
        Mockito.verify(commentDAO, Mockito.times(1)).getCommentById(ArgumentMatchers.anyLong());
    }

    /**
     * Проверка обработки ошибки при получении комментария по id
     */
    /* Не пройдет, т.к. в методе не прописана обработка исключений */
    @Test
    void failGetCommentById() {
        Mockito.doThrow(new TransactionRequiredException())
                .when(commentDAO)
                .getCommentById(ArgumentMatchers.anyLong());
        Comment comment = commentService.getCommentById(ArgumentMatchers.anyLong());

        Assert.assertNull("Проверка наличия возвращаемого объекта комментария", comment);
        Mockito.verify(commentDAO, Mockito.times(1)).getCommentById(ArgumentMatchers.anyLong());
    }

    /**
     * Проверка обновления комментария
     */
    /* Не пройдет, т.к. в методе используется метод equals для сравнения юзеров.
       А у класса User не переопределен метод equals */
    @Test
    void updateComment() {
        User authorFromDAO = new User("firstName", "lastName", "username",
                "password", new Role("ADMIN"));
        authorFromDAO.setId(1L);
        Mockito.doReturn(authorFromDAO)
                .when(commentDAO)
                .getAuthorByCommentId(1L);
        Comment comment = new Comment();
        comment.setId(1L);
        User user = new User("firstName", "lastName", "username",
                "password", new Role("ADMIN"));
        user.setId(1L);
        boolean result = commentService.updateComment(comment, user);

        Assert.assertTrue("Проверка ответа метода updateComment()", result);
        Mockito.verify(commentDAO, Mockito.times(1)).updateComment(ArgumentMatchers.any(Comment.class));
    }

    @Test
    void failUpdateComment() {
        User authorFromDAO = new User("firstName", "lastName", "username",
                "password", new Role("ADMIN"));
        authorFromDAO.setId(1L);
        Mockito.doReturn(authorFromDAO)
                .when(commentDAO)
                .getAuthorByCommentId(1L);
        Comment comment = new Comment();
        comment.setId(1L);
        User user = new User("anotherFirstName", "anotherLastName", "anotherUsername",
                "password", new Role("ADMIN"));
        user.setId(1L);
        boolean result = commentService.updateComment(comment, user);

        Assert.assertFalse("Проверка ответа метода updateComment()", result);
        Mockito.verify(commentDAO, Mockito.times(0)).updateComment(ArgumentMatchers.any(Comment.class));
    }

    @Test
    void getAuthorByCommentId() {
        Mockito.doReturn(new User("firstName", "lastName", "username",
                                "password", new Role("ADMIN")))
                .when(commentDAO)
                .getAuthorByCommentId(1L);
        User user = commentService.getAuthorByCommentId(1L);
        Assert.assertNotNull("Проверка возвращаемого объекта", user);
        Assert.assertEquals("Проверка имени возвращаемого пользователя", "firstName", user.getFirstName());
        Assert.assertEquals("Проверка фамилии возвращаемого пользователя", "lastName", user.getLastName());
        Assert.assertEquals("Проверка username возвращаемого пользователя", "username", user.getUsername());
        Assert.assertEquals("Проверка пароля возвращаемого пользователя", "password", user.getPassword());
        Assert.assertEquals("Проверка роли возвращаемого пользователя", "ADMIN", user.getRole().getName());
        Mockito.verify(commentDAO, Mockito.times(1)).getAuthorByCommentId(ArgumentMatchers.anyLong());
    }

    /* Не пройдет, т.к. в методе не прописана обработка исключений */
    @Test
    void failGetAuthorByCommentId() {
        Mockito.doThrow(new TransactionRequiredException())
                .when(commentDAO)
                .getAuthorByCommentId(ArgumentMatchers.anyLong());

        Assert.assertNull("Проверка возвращаемого объекта", commentService.getAuthorByCommentId(1L));
        Mockito.verify(commentDAO, Mockito.times(1)).getAuthorByCommentId(ArgumentMatchers.anyLong());
    }

    @Test
    void removeCommentById() {

    }

    @Test
    void getAllCommentsByTopicId() {
    }
}