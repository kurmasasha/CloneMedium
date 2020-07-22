package ru.javamentor.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javamentor.dao.comment.CommentDAO;
import ru.javamentor.model.Comment;
import ru.javamentor.model.Role;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;
import ru.javamentor.service.comment.CommentService;

import javax.persistence.TransactionRequiredException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Тесты для класса CommentServiceImpl
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class CommentServiceImplTest {

    @Autowired
    CommentService commentService;

    @MockBean
    CommentDAO commentDAO;

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
        Mockito.doReturn(new Comment())
                .when(commentDAO)
                .getCommentById(ArgumentMatchers.anyLong());

        Comment comment = commentService.getCommentById(ArgumentMatchers.anyLong());
        Assert.assertNotNull("Проверка наличия возвращаемого объекта комментария", comment);
        Mockito.verify(commentDAO, Mockito.times(1)).getCommentById(ArgumentMatchers.anyLong());
    }

    /**
     * Проверка обработки ошибки при получении комментария по id
     */
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

    /**
     * Проверка обновления комментария в случае, если это пытаеся сделать не связанный с комментарием пользователь.
     */
    @Test
    void failUpdateComment() {
        User authorFromDAO = new User();
        authorFromDAO.setId(1L);
        Mockito.doReturn(authorFromDAO)
                .when(commentDAO)
                .getAuthorByCommentId(1L);
        Comment comment = new Comment();
        comment.setId(1L);
        User user = new User();
        user.setId(2L);
        boolean result = commentService.updateComment(comment, user);

        Assert.assertFalse("Проверка ответа метода updateComment()", result);
        Mockito.verify(commentDAO, Mockito.times(0)).updateComment(ArgumentMatchers.any(Comment.class));
    }

    /**
     * Проверка получения автора комментария по его id
     */
    @Test
    void getAuthorByCommentId() {
        Mockito.doReturn(new User())
                .when(commentDAO)
                .getAuthorByCommentId(ArgumentMatchers.anyLong());
        User user = commentService.getAuthorByCommentId(ArgumentMatchers.anyLong());
        Assert.assertNotNull("Проверка возвращаемого объекта", user);
        Mockito.verify(commentDAO, Mockito.times(1)).getAuthorByCommentId(ArgumentMatchers.anyLong());
    }

    /**
     * Проверка обработки ошибки при получении автора комментария по его id
     */
    @Test
    void failGetAuthorByCommentId() {
        Mockito.doThrow(new TransactionRequiredException())
                .when(commentDAO)
                .getAuthorByCommentId(ArgumentMatchers.anyLong());

        Assert.assertNull("Проверка возвращаемого объекта", commentService.getAuthorByCommentId(ArgumentMatchers.anyLong()));
        Mockito.verify(commentDAO, Mockito.times(1)).getAuthorByCommentId(ArgumentMatchers.anyLong());
    }

    /**
     * Проверка удаления комментария по его id
     */
    @Test
    void removeCommentById() {
        Assert.assertTrue("Проверка возвращаемого объекта",
                                commentService.removeCommentById(ArgumentMatchers.anyLong()));
        Mockito.verify(commentDAO, Mockito.times(1)).removeCommentById(ArgumentMatchers.anyLong());
    }

    /**
     * Проверка обработки ошибок при удалении комментария по его id
     */
    @Test
    void failRemoveCommentById() {
        Mockito.doThrow(new TransactionRequiredException())
                .when(commentDAO)
                .removeCommentById(ArgumentMatchers.anyLong());

        Assert.assertFalse("Проверка возвращаемого объекта",
                commentService.removeCommentById(ArgumentMatchers.anyLong()));
        Mockito.verify(commentDAO, Mockito.times(1)).removeCommentById(ArgumentMatchers.anyLong());
    }

    /**
     * Проверка получения списка комментариев к топику по его id
     */
    @Test
    void getAllCommentsByTopicId() {
        Mockito.doReturn(new ArrayList<Comment>())
                .when(commentDAO)
                .getAllCommentsByTopicId(ArgumentMatchers.anyLong());

        Assert.assertNotNull("Проверка возвращаемого объекта",
                commentService.getAllCommentsByTopicId(ArgumentMatchers.anyLong()));
        Mockito.verify(commentDAO, Mockito.times(1)).getAllCommentsByTopicId(ArgumentMatchers.anyLong());
    }

    /**
     * Проверка обработки ошибок при получении списка комментариев к топику по его id
     */
    @Test
    void failGetAllCommentsByTopicId() {
        Mockito.doThrow(new TransactionRequiredException())
                .when(commentDAO)
                .getAllCommentsByTopicId(ArgumentMatchers.anyLong());

        Assert.assertNull("Проверка возвращаемого объекта",
                commentService.getAllCommentsByTopicId(ArgumentMatchers.anyLong()));
        Mockito.verify(commentDAO, Mockito.times(1)).getAllCommentsByTopicId(ArgumentMatchers.anyLong());
    }
}