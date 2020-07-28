/* Таблица комментариев */
create table comments (
    id bigint not null auto_increment,
    date_created datetime,
    text varchar(2047),
    user_id bigint,
    topic_id bigint,
    primary key (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/* Таблица хэштегов */
create table hashtags (
    id bigint not null auto_increment,
    name varchar(255),
    primary key (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/* Таблица связи хэштегов с топиками */
create table hashtags_topics (
    topic_id bigint not null,
    hashtag_id bigint not null,
    primary key (hashtag_id, topic_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/* Таблица уведомлений */
create table notifications (
    id bigint not null auto_increment,
    text varchar(2047) not null,
    title varchar(255) not null,
    user_id bigint, primary key (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/* Таблица ролей */
create table roles (
    id bigint not null auto_increment,
    name varchar(255),
    primary key (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/* Таблица тем */
create table themes (
    id bigint not null auto_increment,
    name varchar(255) not null,
    primary key (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/* Таблица топиков */
create table topics (
    id bigint not null auto_increment,
    completed bit not null,
    content varchar(2047) not null,
    date_created datetime,
    is_moderate bit not null,
    likes integer not null,
    title varchar(255) not null,
    primary key (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/* Таблица связи топиков с темами */
create table topics_themes (
    topic_id bigint not null,
    theme_id bigint not null,
    primary key (theme_id, topic_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/* Таблица пользователей */
create table users (
    id bigint not null auto_increment,
    activation_code varchar(2047),
    first_name varchar(255) not null,
    is_activated bit,
    last_name varchar(255) not null,
    password varchar(255) not null,
    username varchar(255) not null,
    role_id bigint, primary key (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/* Таблица связи пользователей с темами (предпочтения) */
create table users_themes (
    user_id bigint not null,
    theme_id bigint not null,
    primary key (user_id, theme_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/* Таблица связи пользователей с топиками (авторство) */
create table users_topics (
    topic_id bigint not null,
    user_id bigint not null,
    primary key (topic_id, user_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/* Указание уникальности значений колонки 'username' таблицы пользователей */
alter table users
    add constraint usernames_unique
    unique (username);

/* Указание foreign key 'user_id' в таблице комментариев для связи с таблицей пользователей */
alter table comments
    add constraint FK_user_id_in_comments
    foreign key (user_id) references users (id);

/* Указание foreign key 'topic_id' в таблице комментариев для связи с таблицей топиков */
alter table comments
    add constraint FK_topic_id_in_comments
    foreign key (topic_id) references topics (id);

/* Указание foreign key 'hashtag_id' в таблице связи хэштегов с топиками */
alter table hashtags_topics
    add constraint FK_hashtag_id_in_hashtags_topics
    foreign key (hashtag_id) references hashtags (id);

/* Указание foreign key 'topic_id' в таблице связи хэштегов с топиками */
alter table hashtags_topics
    add constraint FK_topic_id_in_hashtags_topics
    foreign key (topic_id) references topics (id);

/* Указание foreign key 'user_id' в таблице уведомлений для связи с таблицей пользователей */
alter table notifications
    add constraint FK_user_id_in_notifications
    foreign key (user_id) references users (id);

/* Указание foreign key 'theme_id' в таблице связи топиков с темами */
alter table topics_themes
    add constraint FK_theme_id_in_topic_themes
    foreign key (theme_id) references themes (id);

/* Указание foreign key 'topic_id' в таблице связи топиков с темами */
alter table topics_themes
    add constraint FK_topic_id_in_topic_themes
    foreign key (topic_id) references topics (id);

/* Указание foreign key 'role_id' в таблице пользователей для свзязи с таблицей ролей */
alter table users
    add constraint FK_role_id_in_users
    foreign key (role_id) references roles (id);

/* Указание foreign key 'theme_id' в таблице связи пользователей с темами */
alter table users_themes
    add constraint FK_theme_id_in_users_themes
    foreign key (theme_id) references themes (id);

/* Указание foreign key 'user_id' в таблице связи пользователей с темами */
alter table users_themes
    add constraint FK_user_id_in_users_themes
    foreign key (user_id) references users (id);

/* Указание foreign key 'user_id' в таблице связи пользователей с топиками */
alter table users_topics
    add constraint FK_user_id_in_users_topics
    foreign key (user_id) references users (id);

/* Указание foreign key 'topic_id' в таблице связи пользователей с топиками */
alter table users_topics
    add constraint FK_topic_id_in_users_topics
    foreign key (topic_id) references topics (id);