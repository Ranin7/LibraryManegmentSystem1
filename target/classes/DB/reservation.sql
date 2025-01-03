create table reservation
(
    id         int auto_increment
        primary key,
    bid        int          null,
    BookName   varchar(200) not null,
    ResDate    date         null,
    RetuenDate date         null,
    UName      varchar(200) null,
    AnotherB   tinyint(1)   null,
    uid        int          null,
    constraint reservation___fk
        foreign key (bid) references books (id),
    constraint reservation___fk_2u
        foreign key (uid) references users (user_id)
);

