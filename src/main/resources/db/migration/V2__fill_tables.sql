/* Заполнение таблицы ролей */
insert into roles(id, name)
    values
        (1,'ADMIN'),
        (2,'USER');

/* Заполнение таблицы пользователей */
insert into users(id, first_name, last_name, password, username, role_id, activation_code, is_activated)
    values
       (1,'Admin','Adminov','$2y$12$BuFhOIoesnUDalLvZGKPj.oQYczb72MIZ6jv1Z3kPSinNCte2ICsa','admin@mail.ru',1,NULL, true),
       (2,'User','Userov','$2y$12$QPVye//naiIK6MW57i5sfeN.npX8zvZ5liWpGO7muUkz0861R83TS','user@mail.ru',2,NULL, true),
       (3,'Fedor','Sumkins','$2y$12$bOXFgFAECDJ6tJoAm5D7T.GVcyowB.q4czONnJhlhcuXMVdZF9ihK','test1@mail.ru',2,NULL, true),
       (4,'Gendelf','Belyi','$2y$12$rVNZZS2jPy9WLzHhpiUMluqnM3n9ZWvdMFr.9bb.37TrQZp9DcUV2','test2@mail.ru',2,NULL, true),
       (5,'Lusya','Pelmenina','$2y$12$sa3Dr2wyn3kGRj8vUiYLUeIt1dwzMb1nYGN2JmUbTZFpm08XR1/36','test3@mail.ru',2,NULL, true),
       (6,'Ket','Sobakova','$2y$12$VvgfyPTt6AH19Y5x.JGml.gl80qBo9GnS65rLzeRfzm3/0UIbJxPy','test4@mail.ru',2,NULL, true),
       (7,'Garik','Potter','$2y$12$dMiUxcGh3CGqd41TVcLc4esHGW/vUcEHzuoZqr0CWOwNPuuLtYcTy','test5@mail.ru',2,NULL, true),
       (8,'Brus','Lee','$2y$12$bgqLWf.I3KmfDRo1NUv2Eu9zDavoy02f/aF5Pvy2UoiZtt8OfKZEu','test6@mail.ru',2,NULL, true),
       (9,'Semen','Buhlov','$2y$12$gvPFh4SsTBHjiDPvtRrC7.thATRCemb3xYMS8QbOYUbbqIfsvdqqW','test7@mail.ru',2,NULL, true),
       (10,'Covid','Karantinych','$2y$12$fLMtXnItPARgmIiNC/JayeYSLRuE6c1MyVH9jGqF43H.hTVIkg4qC','test8@mail.ru',2,NULL, true);

/* Заполнение таблицы уведомлений */
insert into notifications(id, text, title, user_id)
    values
        (1,'Уведомление 1','Уведомление', 1),
        (2,'Уведомление 2','Уведомление', 1),
        (3,'Уведомление 3','Уведомление', 1),
        (4,'Уведомление 4','Уведомление', 2),
        (5,'Уведомление 5','Уведомление', 2),
        (6,'Уведомление 6','Уведомление', 2),
        (7,'Уведомление 7','Уведомление', 3),
        (8,'Уведомление 8','Уведомление', 3),
        (9,'Уведомление 9','Уведомление', 3);

/* Заполнение таблицы тем */
insert into themes(id, name)
    values
       (1,'Тема_1'),
       (2,'Тема_2'),
       (3,'Тема_3'),
       (4,'Тема_4'),
       (5,'Тема_5'),
       (6,'Тема_6'),
       (7,'Тема_7'),
       (8,'Тема_8'),
       (9,'Тема_9'),
       (10,'Тема_10');

/* Заполнение таблиц хэштегов */
insert into hashtags(id, name)
    values
       (1, 'Хэштег_1'),
       (2, 'Хэштег_2'),
       (3, 'Хэштег_3'),
       (4, 'Хэштег_4'),
       (5, 'Хэштег_5'),
       (6, 'Хэштег_6'),
       (7, 'Хэштег_7'),
       (8, 'Хэштег_8'),
       (9, 'Хэштег_9'),
       (10, 'Хэштег_10');

/* Заполнение таблицы топиков */
insert into topics(id, content, title, date_created, is_moderate, likes, dislikes, completed)
    values
       (1,'Бегемоту в грязной луже Никакой курорт не нужен. Он в грязи купаться может. Грязь ему смягчает кожу, Заменяет крем и мазь. Замечательная грязь!','Бегемот','2020-06-02 17:44:32', true,1,1, true),
       (2,'Тихо. Тихо. Тишина. Кукла бедная больна. Кукла бедная больна, Просит музыки она. Спойте, что ей нравится, И она поправится.','Больная кукла','2020-06-02 17:44:34', true,1,1, true),
       (3,'В лужице хрюшку увидела хрюшка: — Это, конечно, не я, а подружка! Ну и грязнуля подружка моя! Просто прекрасно, что это не я!  ***','Хрюшкина подружка','2020-06-02 17:44:39', true,1,1, true),
       (4,'Этот пёс сторожевой. Он может лаять, Как живой. Но он не лает потому, Что ты понравился ему.','Плюшевый пес','2020-06-02 17:44:41', true,1,1, true),
       (5,'Не ленись, моя лопатка, Будет вскопанная грядка. Грядку граблями пригладим, Все комочки разобьём, А потом цветы посадим, А потом водой польём. Лейка, лейка! Лей, лей! Грядка, грядка! Пей, пей!','Грядка','2020-06-02 17:44:42', true,1,1, true),
       (6,'Яблочко румяное Есть одна не стану я, Половинку яблочка Дам любимой мамочке.','Яблоко','2020-06-02 17:44:44', true,1,1, true),
       (7,'Холодильник хоть и близко — Не видать сметаны киске, Не открыть стальную дверь. Что же делать ей теперь? Так и быть, уж я сметану Для любимицы достану.','Киска','2020-06-02 17:44:45', true,1,1, true),
       (8,'Дождик, дождик, капелька, Водяная сабелька. Лужу резал, лужу резал, Резал, резал, не разрезал, И устал, И перестал.','Дождик','2020-06-02 17:44:46', true,1,1, true),
       (9,'Мою ручки, мою ножки, Мою спинку нашей кошке. Кошка очень рассердилась — Я сама уже умылась.','Кошка','2020-06-02 17:44:47', true,1,1, true),
       (10,'На ладони папиной Страшная царапина. Ой, какой же папа смелый, Я б давно уже ревела.','Царапина','2020-06-02 17:44:49', true,1,1, true),
       (11,'Кошка ходит без одежки, Не справляется с застежкой — Вместо рук у бедной кошки Только лишние две ножки.','Кошка','2020-06-02 17:44:50', true,1,1, true),
       (12,'Кран, откройся! Нос, умойся! Глаз, купайся! Грязь, смывайся!','Утренний Приказ','2020-06-02 17:44:51', true,1,1, true),
       (13,'— Но! – сказали мы лошадке И помчались без оглядки. Вьётся грива на ветру. Вот и дом. Лошадка, тпру-у-у!','Лошадка','2020-06-02 17:44:52', true,1,1, true),
       (14,'Коровушка; коровушка, Рогатая головушка! Малых деток не бодай, Молока им лучше дай!','Коровушка','2020-06-02 17:44:54', true,1,1, true),
       (15,'Маленький бычок, Жёлтенький бочок, Ножками ступает, Головой мотает. — Где же стадо? Му-у-у! Скучно одному-у-у!','Бычок','2020-06-02 17:44:55', true,1,1, true),
       (16,'Облепили лампу мошки, Греют тоненькие ножки. Осторожно, мошки! Обожжёте ножки!','Мошки','2020-06-02 17:44:56', true,1,1, true),
       (17,'Тучка с солнышком опять В прятки начали играть. Только солнце спрячется, Тучка вся расплачется. А как солнышко найдётся, Сразу радуга смеётся.','Тучка','2020-06-02 17:44:57', true,1,1, true),
       (18,'Петушки распетушились, Но подраться не решились. Если очень петушиться, Можно пёрышек лишиться. Если пёрышек лишиться, Нечем будет петушиться.','Петушки','2020-06-02 17:44:58', true,1,1, true),
       (19,'Медведица ласково сына качает. Малыш веселится. Малыш не скучает. Он думает, это смешная игра, Не зная, что спать медвежатам пора.','Медвежонок','2020-06-02 17:44:59', true,1,1, true),
       (20,'Белые носочки На лапках у щенка. Видно, перешёл он Речку молока.','Щенок','2020-06-02 17:45:00', true,1,1, true),
       (21,'— Что ты, кошка, сторожишь? — Сторожу у норки мышь. Выйдет мышка невзначай, Приглашу её на чай.','Кошка','2020-06-02 17:45:01', true,1,1, true),
       (22,'Комары-комарики, Рыжие сударики! Всюду вьются и толкутся. И откуда же берутся? А из лесу тёмного, Из болота сонного, Из-под ивушки лохматой, Из-под кочки бородатой.','Комары','2020-06-02 17:45:02', true,1,1, true),
       (23,'Серенькая кошечка Села у окошечка, Хвостиком виляла, Деток поджидала: «Детки мои, детки, Детки-непоседки, Хватит вам резвиться, Спать пора ложиться!»','Кошечка','2020-06-02 17:45:04', true,1,1, true),
       (24,'На комод забрался ёжик. У него не видно ножек. У него, такого злючки, Не причёсаны колючки, И никак не разберёшь – Щётка это или ёж?','Ёжик','2020-06-02 17:45:04', true,1,1, true),
       (25,'Знаю точно я, что птицы В перелетах мастерицы. Все по-разному летают — Кто парит, а кто порхает.  Над землею — посмотри! Осы, бабочки, шмели. На цветке пчела жужжит, Раз — и в небо улетит.','Кто летает в небе','2020-06-02 17:45:05', true,11,1, true),
       (26,'Я глазам своим не верю — Полетели даже звери! Мышь летучая, а рядом Белка рыжая — летяга.  Вот воздушные шары Для забавы детворы. Самолетик, змей воздушный Тоже ввысь летят послушно.','Кто летает в небе2','2020-06-02 17:45:06', true,1,1, true),
       (27,'А большие самолеты В небесах ведут пилоты. Дельтаплан, аэростат В небо нас легко умчат.','Кто летает в небе 3','2020-06-02 17:45:09', true,1,1, true),
       (28,'Ворона однажды на свой день рождения Из вишен садовых сварила варенье Сорока воровка в кладовку прокралась — Гостям только банка пустая досталась.','Ворона','2020-06-02 17:45:10', true,1,1, true),
       (29,'Горло заболело у грача, Пригласил он голубя – врача. Врач сказал: «У вас температура, Вот горчичники и горькая микстура! Каждому грачонку надо знать, Что опасно под дождем гулять».','Горло','2020-06-02 17:45:11', true,11,1, true),
       (30,'Дятел дом решил построить. надо доски приготовить, Дверь повесить, сделать пол… Только начал – день прошел.','Дятел','2020-06-02 17:45:12', true,1,1, true),
       (31,'Енот наступил на еловую шишку И начал вопить он и лапой махать. Енотиха – мама стыдила сынишку: «Ну разве же можно так громко кричать?»','Енот','2020-06-02 17:45:13', true,1,1, true),
       (32,'Жираф журнал о животных купил, Тут жеребенок ему позвонил. — Жираф, приходи, будем в жмурки играть! — Нет, жеребенок, я буду читать.','Жираф','2020-06-02 17:45:15', true,1,1, true),
       (33,'Зебру увидел в загоне зайчишка, Как хохотал длинноухий плутишка! — Зебра, скажи, ты тельняшку надела Или сквозь прутья так загорела?','Зебра','2020-06-02 17:45:16', true,1,1, true),
       (34,'Индюшка – мамаша на птичьем дворе Вязала носочки своей детворе: Одни – для сыночка, другие – для дочки. В гости пойдут они в новых носочках.','Индюшка','2020-06-02 17:45:17', true,1,1, true),
       (35,'Йог на гвозди как-то лег – Йод едва ему помог. Врач беднягу осмотрел, Йогурт есть ему велел.','Йог','2020-06-02 17:45:18', true,1,1, true),
       (36,'Львенок напиться к реке наклонился, Очень, увидев себя, удивился: «Какой я лохматый! Как некрасиво! Пора подстригать отросшую гриву».','Львенок','2020-06-02 17:45:20', true,1,1, true),
       (37,'Носорог понять не может: «Ведь у всех по паре рожек… У меня – один, и тот Прямо на носу растет!»','Носорог','2020-06-02 17:45:21', true,1,1, true),
       (38,'Овечка панамку с цветочком надела. Увидев цветочек, оса прилетела – Решила с панамки нектар собирать. Ей, видно, придется очки заказать.','Овечка','2020-06-02 17:45:23', true,1,1, true),
       (39,'Попугай дразнил павлина: «Что за странная картина! Где же видано в природе – Сам на ножках веер ходит?!»','Попугай','2020-06-02 17:45:24', true,1,1, true),
       (40,'Рысь нашла под елкой рыжик: «Что за гриб? Впервые вижу! Не боишься грибника? Виден ты из далека!»','Рысь','2020-06-02 17:45:25', true,1,1, true),
       (41,'Слон в жару не унывает, В хобот воду набирает. Не страшна слонятам сушь – Будет им прохладный душ!','Слон','2020-06-02 17:45:27', true,1,1, true),
       (42,'Улитка ужу закричала: «Постой! Скажи, ты удаву не брат ли родной? Ужасно похожи! но только, прости, Тебе б не мешало чуть-чуть подрасти!»','Улитка','2020-06-02 17:45:28', true,1,1, true),
       (43,'Мудрый филин до зари Зажигает фонари. Чтоб те, кому не спиться, Не боялись заблудиться.','Мудрый филин','2020-06-02 17:45:29', true,1,1, true),
       (44,'Хомяк холодильник огромный купил, До норки своей едва дотащил. Но холодильник – такая беда! – В двери застрял – ни туда ни сюда.','Хомяк','2020-06-02 17:45:30', true,1,1, true),
       (45,'Цыпленок требовал ответ: Родня мне цапля или нет? «Родня, — сказал петух, — но просто Большого вымахала роста».','Цыпленок','2020-06-02 17:45:31', true,1,1, true),
       (46,'В чехарду чижи играли И чирикать громко стали, Черепаху разбудили, Чем старушку рассердили.','В чехарду чижи играли','2020-06-02 17:45:32', true,1,1, true),
       (47,'Шакал за порядком в лесу наблюдал. Через поляну шмель пролетал. За то, что он скорость превысил в полете, Шакал его тотчас оштрафовал.','Шакал','2020-06-02 17:45:33', true,1,1, true),
       (48,'Щуку спрашивал щенок: — Щука, кто бы мне помог? Может, ты мне дашь совет, Как не знать с зубами бед? — Мой совет совсем простой: Щеткой пользуйся зубной!','Щука','2020-06-02 17:45:34', true,1,1, true),
       (49,'Эмус-страус – птица все же, Хоть летать совсем не может. А надумает бежать – Электричке не догнать.','Эмус-страус ','2020-06-02 17:45:35', true,2,1, true),
       (50,'Юбочку юла надела, Долго в зеркало глядела, Так вертелась, так крутилась, Даже на бочок свалилась.','Про юлу','2020-06-02 17:45:37', true,2,1, true),
       (51,'Ягуар увидел яхту И воскликнул: «Ух ты! Ах ты! Если б яхту я купил, Вот бы рыбы наловил!»','Ягуар увидел яхту','2020-06-02 17:45:38', true,2,1, true);

/* Заполнение таблицы связи пользователей с топиками */
insert into users_topics(user_id, topic_id)
    values
       (1,1), (1,2), (1,12), (1,35),
       (2,1), (2,2),
       (3,3), (3,13), (3,20), (3,21), (3,22), (3,31), (3,32), (3,33), (3,34),
       (4,4), (4,23), (4,24), (4,36), (4,37), (4,38), (4,39), (4,40), (4,41),
       (5,5),
       (6,6), (6,15), (6,28), (6,29), (6,30),
       (7,7), (7,14),
       (8,8), (8,9), (8,16), (8,17), (8,18),
       (9,10), (9,19), (9,42), (9,43), (9,44), (9,45), (9,46), (9,47),
       (10,11), (10,25), (10,26), (10,27), (10,48), (10,49), (10,50), (10,51);

/* Заполнение таблицы свзяи хэштегов с топиками */
insert into hashtags_topics(topic_id, hashtag_id)
    values
       (1,1), (1,2), (1,3),
       (2,4), (2,5),
       (3,6),
       (4,7), (4,8), (4,9), (4,10),
       (5,1), (5,3), (5,5),
       (6,6), (6,8),
       (7,7),
       (8,8),
       (9,9);

/* Заполнение таблицы связи топиков с темами */
insert into topics_themes(topic_id, theme_id)
    values
       (1,1), (1,2), (1,3),
       (2,4), (2,5), (2,6),
       (3,7),
       (4,8),
       (5,9),
       (6,8), (6,9), (6,10),
       (7,2),
       (8,3);

/* Заполнение таблицы связи пользователей с темами */
insert into users_themes(user_id, theme_id)
    values
       (1,1), (1,2), (1,3),
       (2,2), (2,4),
       (3,1), (3,5);