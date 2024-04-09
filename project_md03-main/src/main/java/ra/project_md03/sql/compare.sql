
use md03_project_database;
CREATE TABLE COMPARE (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         user_id INT,
                         FOREIGN KEY (user_id) REFERENCES user(id),
                         product_id INT,
                         FOREIGN KEY (product_id) REFERENCES product(id)
);


DELIMITER //

CREATE PROCEDURE PROC_COMPARE_FIND_NEWEST_BY_USER_ID(IN user_id_in INT)
BEGIN
    SELECT *
    FROM (
             SELECT *
             FROM COMPARE
             WHERE user_id = user_id_in
             ORDER BY id DESC -- Sắp xếp theo id giảm dần
             LIMIT 3
         ) AS newest_compare;

END //
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