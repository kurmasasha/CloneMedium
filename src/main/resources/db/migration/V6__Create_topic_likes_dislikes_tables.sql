/* Создание таблицы лайков для топика */
create table users_topics_likes (
        user_id bigint not null,
        topic_id bigint not null,
        primary key (user_id, topic_id));

/* Создание ключей пользователей для таблицы лайков */
alter table users_topics_likes
    add constraint FK_user_id_in_users_topics_likes
        foreign key (user_id) references users (id);

/* Создание ключей топика для таблицы лайков */
alter table users_topics_likes
    add constraint FK_topic_id_in_users_topics_likes
        foreign key (topic_id) references topics (id);

/* Создание таблицы дизлайков для топика */
create table users_topics_dislikes (
        user_id bigint not null,
        topic_id bigint not null,
        primary key (user_id, topic_id));

/* Создание ключей пользователей для таблицы дизлайков */
alter table users_topics_dislikes
    add constraint FK_user_id_in_users_topics_dislikes
        foreign key (user_id) references users (id);

/* Создание ключей топика для таблицы дизлайков */
alter table users_topics_dislikes
    add constraint FK_topic_id_in_users_topics_dislikes
        foreign key (topic_id) references topics (id);