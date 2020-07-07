/*
Заполнение таблицы подписок

Подписчики для admin@mail.ru:
- user@mail.ru
- test1@mail.ru
- test2@mail.ru
Подписчики для user@mail.ru:
- admin@mail.ru
- test1@mail.ru
   */

insert into subscribes(id, author_id, subscriber_id)
    values
        (1, 1, 2),
        (2, 1, 3),
        (3, 1, 4),
        (5, 2, 1),
        (6, 2, 3);