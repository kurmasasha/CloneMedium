/* Таблица связи пользователей с комментами для лайков */
create table users_comments_likes (
                                      user_id bigint not null,
                                      comment_id bigint not null,
                                      primary key (user_id, comment_id));

/* Указание foreign key 'user_id' в таблице связи пользователей с комментариями для лайков */
alter table users_comments_likes
    add constraint FK_user_id_in_users_comments_likes
        foreign key (user_id) references users (id);

/* Указание foreign key 'comment_id' в таблице связи пользователей с комментариями для лайков */
alter table users_comments_likes
    add constraint FK_topic_id_in_users_comments_likes
        foreign key (comment_id) references comments (id);



/* Таблица связи пользователей с комментами для дизлайков */
create table users_comments_dislikes (
                                      user_id bigint not null,
                                      comment_id bigint not null,
                                      primary key (user_id, comment_id));

/* Указание foreign key 'user_id' в таблице связи пользователей с комментариями для дизлайков */
alter table users_comments_dislikes
    add constraint FK_user_id_in_users_comments_dislikes
        foreign key (user_id) references users (id);

/* Указание foreign key 'comment_id' в таблице связи пользователей с комментариями для дизлайков */
alter table users_comments_dislikes
    add constraint FK_topic_id_in_users_comments_dislikes
        foreign key (comment_id) references comments (id);