
use md03_project_database;
create table COMPARE
(
    id         int primary key auto_increment,
    user_id    int,
    foreign key (user_id) references user (id),
    product_id int,
    foreign key (product_id) references product (id)
);

DELIMITER //
create procedure PROC_COMPARE_FIND_BY_USER_ID(IN user_id_in int)
begin
select * from COMPARE WHERE user_id = user_id_in ;
end //
DELIMITER ;

DELIMITER //
create procedure PROC_COMPARE_ADD(IN user_id_in int, product_id_in int)
begin
insert into COMPARE(user_id, product_id) values (user_id_in, product_id_in);
end //
DELIMITER ;

DELIMITER //
create procedure PROC_COMPARE_DELETE(IN product_id_in int)
begin
delete from COMPARE where product_id = product_id_in;
end //
DELIMITER ;

DELIMITER //
create procedure PROC_COMPARE_FIND_BY_PRODUCT_ID(IN product_id_in int, user_id_in int)
begin
select * from COMPARE where product_id = product_id_in and user_id = user_id_in;
end //