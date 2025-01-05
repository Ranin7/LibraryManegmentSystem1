create table reservation
(
    id         int auto_increment
        primary key,
    BookName   varchar(200) not null,
    ResDate    date         null,
    UName      varchar(200) null,
    uid        int          null,
    bid        int          null,
    AnotherB   varchar(200) null,
    ReturnDate date         null,
    available  varchar(200) null,
    constraint reservation___fk_2u
        foreign key (uid) references users (user_id),
    constraint reservation___fkb
        foreign key (bid) references books (id)
);