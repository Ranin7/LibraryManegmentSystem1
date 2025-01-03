create table permissions
(
    permission_id   int auto_increment
        primary key,
    permission_name varchar(255) not null,
    description     text         null
);
