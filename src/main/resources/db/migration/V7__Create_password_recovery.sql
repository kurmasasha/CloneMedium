create table password_recovery_token (
                          id bigint not null auto_increment,
                          hash_email varchar(250),
                          start_time datetime,
                          user_id bigint,
                          primary key (id));

alter table password_recovery_token
    add constraint FK_user_id_password_recovery_token
        foreign key (user_id) references users (id);