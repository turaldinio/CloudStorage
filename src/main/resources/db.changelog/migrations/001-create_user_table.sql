create table user
(
    id         int primary key auto_increment,
    email      varchar(50),
    password   varchar(255),
    user_token varchar(255)
);

