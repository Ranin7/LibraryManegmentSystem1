create table books
(
    BookName        varchar(200) null,
    id              int auto_increment
        primary key,
    Author          varchar(200) null,
    Genre           varchar(200) null,
    Available       varchar(200) null,
    PublicationYear date         null
);

