create table user_files
(
    id         int primary key auto_increment,
    file_name varchar(255),
    file_size bigint,
    file      mediumblob,
    user_id   int,
    foreign key (user_id) references user (id)
);
