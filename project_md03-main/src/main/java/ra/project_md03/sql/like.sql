use
md03_project_database;
create table LikeTable
(
    id        int primary key auto_increment,
    productId int,
    foreign key (productId) references product (id),
    userId    int not null,
    foreign key (userId) references user (id)
);


