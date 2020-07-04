create table comments (
    id bigint not null auto_increment,
    date_created datetime,
    text varchar(2047),
    user_id bigint,
    topic_id bigint,
    primary key (id));

create table hashtags (
    id bigint not null auto_increment,
    name varchar(255),
    primary key (id));

create table hashtags_topics (
    topic_id bigint not null,
    hashtag_id bigint not null,
    primary key (hashtag_id, topic_id));

create table notifications (
    id bigint not null auto_increment,
    text varchar(2047) not null,
    title varchar(255) not null,
    user_id bigint, primary key (id));

create table roles (
    id bigint not null auto_increment,
    name varchar(255),
    primary key (id));

create table themes (
    id bigint not null auto_increment,
    name varchar(255) not null,
    primary key (id));

create table topics (
    id bigint not null auto_increment,
    completed bit not null,
    content varchar(2047) not null,
    date_created datetime,
    is_moderate bit not null,
    likes integer not null,
    title varchar(255) not null,
    primary key (id));

create table topics_themes (
    topic_id bigint not null,
    theme_id bigint not null,
    primary key (theme_id, topic_id));

create table users (
    id bigint not null auto_increment,
    activation_code varchar(2047),
    first_name varchar(255) not null,
    is_activated bit,
    last_name varchar(255) not null,
    password varchar(255) not null,
    username varchar(255) not null,
    role_id bigint, primary key (id));

create table users_themes (
    user_id bigint not null,
    theme_id bigint not null,
    primary key (user_id, theme_id));

create table users_topics (
    topic_id bigint not null,
    user_id bigint not null,
    primary key (topic_id, user_id));

alter table users
    add constraint usernames_unique
    unique (username);

alter table comments
    add constraint FK_user_id_in_comments
    foreign key (user_id) references users (id);

alter table comments
    add constraint FK_topic_id_in_comments
    foreign key (topic_id) references topics (id);

alter table hashtags_topics
    add constraint FK_hashtag_id_in_hashtags_topics
    foreign key (hashtag_id) references hashtags (id);

alter table hashtags_topics
    add constraint FK_topic_id_in_hashtags_topics
    foreign key (topic_id) references topics (id);

alter table notifications
    add constraint FK_user_id_in_notifications
    foreign key (user_id) references users (id);

alter table topics_themes
    add constraint FK_theme_id_in_topic_themes
    foreign key (theme_id) references themes (id);

alter table topics_themes
    add constraint FK_topic_id_in_topic_themes
    foreign key (topic_id) references topics (id);

alter table users
    add constraint FK_role_id_in_users
    foreign key (role_id) references roles (id);

alter table users_themes
    add constraint FK_theme_id_in_users_themes
    foreign key (theme_id) references themes (id);

alter table users_themes
    add constraint FK_user_id_in_users_themes
    foreign key (user_id) references users (id);

alter table users_topics
    add constraint FK_user_id_in_users_topics
    foreign key (user_id) references users (id);

alter table users_topics
    add constraint FK_topic_id_in_users_topics
    foreign key (topic_id) references topics (id);