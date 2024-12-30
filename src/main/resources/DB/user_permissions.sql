create table user_permissions
(
    user_id       int not null,
    permission_id int not null,
    primary key (user_id, permission_id),
    constraint user_permissions_ibfk_1
        foreign key (user_id) references users (user_id),
    constraint user_permissions_ibfk_2
        foreign key (permission_id) references permissions (permission_id)
);