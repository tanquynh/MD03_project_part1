use
    md03_project_database;
create table comment
(
    id        int primary key auto_increment,
    comment   text,
    productId int,
    rating    int,
    time      DATE not null,
    foreign key (productId) references product (id),
    userId    int      not null,
    foreign key (userId) references user (id)
);

DELIMITER //
create procedure PROC_COMMENT_ADD(IN comment_in text, IN rating_in int, product_id_in int, time_in DATE,
                                  IN user_id_in int)
begin
    insert into comment(comment, productId, rating, time, userId) value (comment_in, product_id_in, rating_in, time_in, user_id_in);
end //
DELIMITER ;

DELIMITER //
create procedure PROC_COMMENT_DELETE(IN id_in int)begin
    delete from WISHLIST where id = id_in;
end //
DELIMITER ;

DELIMITER
//
create procedure PROC_COMMENT_FIND_BY_PRODUCTID(IN id_in int)
begin
    select *
    from comment
    where productId = id_in;
end
//
DELIMITER ;
