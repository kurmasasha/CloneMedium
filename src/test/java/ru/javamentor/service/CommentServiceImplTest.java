package ru.javamentor.service;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javamentor.dao.comment.CommentDAO;
import ru.javamentor.dao.user.UserDAO;
import ru.javamentor.dto.CommentDTO;
import ru.javamentor.model.Comment;
import ru.javamentor.model.Role;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;
import ru.javamentor.service.comment.CommentService;
import ru.javamentor.service.comment.CommentServiceImpl;

import javax.persistence.TransactionRequiredException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Тесты для класса CommentServiceImpl
 *
 * @author Java Mentor
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest
class CommentServiceImplTest {

    @Autowired
    CommentService commentService;

    @MockBean
    CommentDAO commentDAO;

    @SpyBean
    UserDAO userDAO;

    /**
     * Проверка добавления комментария
     */
    @Test
    void addComment() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setText("Test text");

        Comment comment = commentService.addComment(commentDTO, new User());

        Assert.assertNotNull("Проверка создания объекта комментария", comment);
        Assert.assertNull("Проверка id комментария", comment.getId());
        Assert.assertEquals("Проверка текста комментария", "testText", comment.getText());
        Assert.assertNotNull("Проверка автора комментария", comment.getAuthor());
        Assert.assertNotNull("Проверка связанного топика комментария", comment.getTopic());
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
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setText("");
        commentDTO.setTopicId(1L);

        Mockito.doThrow(new TransactionRequiredException())
                .when(commentDAO)
                .addComment(ArgumentMatchers.any(Comment.class));
        Comment[] comments = new Comment[1];
        Assertions.assertThrows(RuntimeException.class, () -> {
            comments[0] = commentService.addComment(commentDTO, new User());
        });
        Assert.assertNull("Проверка наличия возвращаемого объекта комментария", comments[0]);
        Mockito.verify(commentDAO, Mockito.times(1))
                .addComment(ArgumentMatchers.any(Comment.class));
    }

    /**
     * Проверка получения комментария по id
     */
    @Test
    void getCommentById() {
        Mockito.doReturn(new Comment("", new User(), new Topic(), LocalDateTime.now()))
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
        Comment[] comments = new Comment[1];
        Assertions.assertThrows(RuntimeException.class, () -> {
            comments[0] = commentService.getCommentById(ArgumentMatchers.anyLong());
        });
        Assert.assertNull("Проверка наличия возвращаемого объекта комментария", comments[0]);
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
        User[] users = new User[1];
        Assertions.assertThrows(RuntimeException.class, () -> {
            users[0] = commentService.getAuthorByCommentId(ArgumentMatchers.anyLong());
        });
        Assert.assertNull("Проверка возвращаемого объекта", users[0]);
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
        List<?>[] lists = new List[1];
        Assertions.assertThrows(RuntimeException.class, () -> {
            lists[0] = commentService.getAllCommentsByTopicId(ArgumentMatchers.anyLong());
        });
        Assert.assertNull("Проверка возвращаемого объекта", lists[0]);
        Mockito.verify(commentDAO, Mockito.times(1)).getAllCommentsByTopicId(ArgumentMatchers.anyLong());
    }

    /**
     * Проверка добавления/удаления лайка к комментарию
     */
    @Test
    void putLikeToComment() {
        User authorOfComment = new User("firstName", "lastName", "username",
                "password", new Role("ADMIN"));
        authorOfComment.setId(1L);
        Comment commentFromDao = new Comment("comment", authorOfComment, new Topic(), LocalDateTime.now());
        commentFromDao.setId(1L);
        commentFromDao.setLikedUsers(new HashSet<>());
        commentFromDao.setDislikedUsers(new HashSet<>());
        Mockito.doReturn(commentFromDao)
                .when(commentDAO)
                .getCommentById(1L);
        Mockito.doReturn(authorOfComment)
                .when(userDAO)
                .getUserById(1L);
        Mockito.doNothing().
                when(commentDAO)
                .updateComment(ArgumentMatchers.any(Comment.class));

        commentFromDao = commentService.putLikeToComment(1L, authorOfComment);
        Assert.assertEquals("Проверка добавления лайка к комментарию", 1, (int) commentFromDao.getLikes());
        Assert.assertTrue("Проверка принадлежности лайка автору комментария", commentFromDao.getLikedUsers().contains(authorOfComment));

        commentFromDao = commentService.putLikeToComment(1L, authorOfComment);
        Assert.assertEquals("Проверка удаления лайка у комментария", 0, (int) commentFromDao.getLikes());
        Assert.assertFalse("Проверка удаления автора из списка лайкнувших", commentFromDao.getLikedUsers().contains(authorOfComment));

        commentFromDao.getDislikedUsers().add(authorOfComment);
        commentFromDao.setDislikes(1);
        commentFromDao = commentService.putLikeToComment(1L, authorOfComment);
        Assert.assertEquals("Проверка добавления лайка к комментарию (смена дизлайк/лайк)",
                1, (int) commentFromDao.getLikes());
        Assert.assertTrue("Проверка принадлежности лайка автору комментария (смена дизлайк/лайк)",
                commentFromDao.getLikedUsers().contains(authorOfComment));
        Assert.assertEquals("Проверка удаления дизлайка у комментария (смена дизлайк/лайк)",
                0, (int) commentFromDao.getDislikes());
        Assert.assertFalse("Проверка удаления автора из списка дизлайкнувших (смена дизлайк/лайк)",
                commentFromDao.getDislikedUsers().contains(authorOfComment));
    }

    /**
     * Проверка обработки ошибок при добавления/удаления лайка к комментарию
     */
    @Test
    void failPutLikeToComment() {
        Mockito.doThrow(new TransactionRequiredException())
                .when(commentDAO)
                .getCommentById(1L);
        Comment[] comments = new Comment[1];
        Assertions.assertThrows(RuntimeException.class, () -> {
            comments[0] = commentService.putLikeToComment(1L, new User());
        });
        Assert.assertNull("Проверка возвращаемого объекта", comments[0]);
        Mockito.verify(commentDAO, Mockito.times(1)).getCommentById(1L);
    }

    /**
     * Проверка добавления/удаления дизлайка к комментарию
     */
    @Test
    void putDislikeToComment() {
        User authorOfComment = new User("firstName", "lastName", "username",
                "password", new Role("ADMIN"));
        authorOfComment.setId(1L);
        Comment commentFromDao = new Comment("comment", authorOfComment, new Topic(), LocalDateTime.now());
        commentFromDao.setId(1L);
        commentFromDao.setLikedUsers(new HashSet<>());
        commentFromDao.setDislikedUsers(new HashSet<>());
        Mockito.doReturn(commentFromDao)
                .when(commentDAO)
                .getCommentById(1L);
        Mockito.doReturn(authorOfComment)
                .when(userDAO)
                .getUserById(1L);
        Mockito.doNothing().
                when(commentDAO)
                .updateComment(ArgumentMatchers.any(Comment.class));

        commentFromDao = commentService.putDislikeToComment(1L, authorOfComment);
        Assert.assertEquals("Проверка добавления дизлайка к комментарию", 1, (int) commentFromDao.getDislikes());
        Assert.assertTrue("Проверка принадлежности дизлайка автору комментария", commentFromDao.getDislikedUsers().contains(authorOfComment));

        commentFromDao = commentService.putDislikeToComment(1L, authorOfComment);
        Assert.assertEquals("Проверка удаления дизлайка у комментария", 0, (int) commentFromDao.getDislikes());
        Assert.assertFalse("Проверка удаления автора из списка дизлайкнувших", commentFromDao.getDislikedUsers().contains(authorOfComment));

        commentFromDao.getLikedUsers().add(authorOfComment);
        commentFromDao.setLikes(1);
        commentFromDao = commentService.putDislikeToComment(1L, authorOfComment);
        Assert.assertEquals("Проверка добавления дизлайка к комментарию (смена лайк/дизлайк)",
                1, (int) commentFromDao.getDislikes());
        Assert.assertTrue("Проверка принадлежности лайка автору комментария (смена лайк/дизлайк)",
                commentFromDao.getDislikedUsers().contains(authorOfComment));
        Assert.assertEquals("Проверка удаления лайка у комментария (смена лайк/дизлайк)",
                0, (int) commentFromDao.getLikes());
        Assert.assertFalse("Проверка удаления автора из списка лайкнувших (смена лайк/дизлайк)",
                commentFromDao.getLikedUsers().contains(authorOfComment));
    }

    /**
     * Проверка обработки ошибок при добавления/удаления дизлайка к комментарию
     */
    @Test
    void failPutDislikeToComment() {
        Mockito.doThrow(new TransactionRequiredException())
                .when(commentDAO)
                .getCommentById(1L);
        Comment[] comments = new Comment[1];
        Assertions.assertThrows(RuntimeException.class, () -> {
            comments[0] = commentService.putDislikeToComment(1L, new User());
        });
        Assert.assertNull("Проверка возвращаемого объекта", comments[0]);
        Mockito.verify(commentDAO, Mockito.times(1)).getCommentById(1L);
    }

    /**
     * Проверка на установление соответствия автора комментарию
     */
    @Test
    void isAuthorOfComment() {
        User authorFromDAO = new User();
        authorFromDAO.setId(1L);
        Mockito.doReturn(authorFromDAO)
                .when(commentDAO)
                .getAuthorByCommentId(ArgumentMatchers.anyLong());
        boolean result = commentService.isAuthorOfComment(1L, ArgumentMatchers.anyLong());
        Assert.assertTrue("Проверка ответа метода isAuthorOfComment()", result);
        Mockito.verify(commentDAO, Mockito.times(1)).getAuthorByCommentId(ArgumentMatchers.anyLong());
    }

    /**
     * Проверка на установление несоответствия автора комментарию
     */
    @Test
    void failIsAuthorOfComment() {
        User authorFromDAO = new User();
        authorFromDAO.setId(1L);
        Mockito.doReturn(authorFromDAO)
                .when(commentDAO)
                .getAuthorByCommentId(ArgumentMatchers.anyLong());
        boolean result = commentService.isAuthorOfComment(2L, ArgumentMatchers.anyLong());
        Assert.assertFalse("Проверка ответа метода isAuthorOfComment()", result);
        Mockito.verify(commentDAO, Mockito.times(1)).getAuthorByCommentId(ArgumentMatchers.anyLong());
    }

}