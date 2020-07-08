/* Создание таблицы подписок */
create table subscribes (
    id bigint not null auto_increment,
    author_id bigint,
    subscriber_id bigint,
    primary key (id));

/* Создание ключей авторов */
alter table subscribes
    add constraint FK_author_id_in_subscibes
    foreign key (author_id)
    references users (id);

/* Создание ключей подписчиков */
alter table subscribes
    add constraint FK_subscriber_id_in_subscibes
    foreign key (subscriber_id)
    references users (id)