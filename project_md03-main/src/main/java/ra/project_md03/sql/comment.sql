use
    md03_project_database;
create table comment
(
    id        int primary key auto_increment,
    comment   text,
    productId int not null,
    rating    int,
    foreign key (productId) references product (id),
    userId    int not null,
    foreign key (userId) references user (id)
);

DELIMITER
//
create procedure PROC_COMMENT_FIND_BY_PRODUCTID(IN id_in int)
begin
    select *
    from comment
    where id = id_in;
end
//
DELIMITER ;
