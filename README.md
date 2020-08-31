# clone-medium
### Docker

- загрузка и установка docker — 
    перейдите по [ссылке](https://www.docker.com/get-started) и выберете свою систему;
- запустите docker на своем компьютере;
- загрузка образа, настройка и установка контейнера выполняется одной командой 
`docker run --name cm_mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=jm_clone_medium -d -p 3307:3306 mysql`;
- остановка контейнера `docker stop cm_mysql`;
- запуск остановленного контейнера `docker start cm_mysql`;

Процесс сборки: 

- открыть терминал, выполнить команду mvn clean install -Dmaven.test.skip=true

- Данная команда пропускает выполнение тестов, т.к тесты не все  были написаны, а некоторые неисправны

- Далее, в папке с /CloneMedium/target/ появится файл ClonMedium-0.1-beta.jar

- включащий в себя все зависимости(размер свыше 50мб) 

- !Important
  Обязательно запустить MySQL Server перед запуском

- для запуска приложение необходимо открыть CMD и выполнить команду

- java -jar path-to-file/name.jar 

- где path-to-file это путь к файлу

- name.jar - имя файла(по дефолту ClonMedium-0.1-beta.jar)

- для остановки приложения необходимо закрыть командную строку исполнения

 dev
