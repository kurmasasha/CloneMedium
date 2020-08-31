# clone-medium

Ветка dev

### Docker

- загрузка и установка docker — 
    перейдите по [ссылке](https://www.docker.com/get-started) и выберете свою систему;
- запустите docker на своем компьютере;
- загрузка образа, настройка и установка контейнера выполняется одной командой 
`docker run --name cm_mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=jm_clone_medium -d -p 3307:3306 mysql`;
- остановка контейнера `docker stop cm_mysql`;
- запуск остановленного контейнера `docker start cm_mysql`;