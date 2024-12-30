create table users
(
    user_id  int auto_increment
        primary key,
    username varchar(255) not null,
    password varchar(255) not null,
    role_id  int          null,
    constraint users_ibfk_1
        foreign key (role_id) references roles (role_id)
);

create index role_id
    on users (role_id);