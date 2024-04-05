use
md03_project_database;
create table like
(
    id        int primary key auto_increment,
    productId int,
    numLike int,
    foreign key (productId) references product (id),
    userId    int not null,
    foreign key (userId) references user (id)
);

DELIMITER
//
create procedure PROC_COMMENT_FIND_BY_PRODUCTID(IN id_in int)
begin
select *
from like
where id = id_in;
end
//
DELIMITER ;
