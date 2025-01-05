create table role_permissions
(
    role_id       int not null,
    permission_id int not null,
    primary key (role_id, permission_id),
    constraint role_permissions_ibfk_1
        foreign key (role_id) references roles (role_id) on delete cascade,
    constraint role_permissions_ibfk_2
        foreign key (permission_id) references permissions (permission_id) on delete cascade
);
