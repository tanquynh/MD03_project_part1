CREATE TABLE orders
(
    id       int primary key auto_increment,
    user_id  int,
    foreign key (user_id) references user (id),
    total    double,
    address  text,
    note     text,
    payment  varchar(100),
    status   int(1) default 0,
    order_at date   default (DATE(NOW()))
);

DELIMITER //
CREATE PROCEDURE PROC_ORDERS_FIND_ALL()
begin
    SELECT * FROM orders;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_ORDERS_ADD(IN user_id_in int, total_in double, address_in text, note_in text,
                                 payment_in varchar(100), OUT order_id int)
begin
    INSERT INTO orders(user_id, total, address, note, payment)
    VALUES (user_id_in, total_in, address_in, note_in, payment_in);
    SET order_id = LAST_INSERT_ID();
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_ORDER_DETAIL_ADD(IN order_id_in int, product_id_in int, quantity_in int, price_in double)
begin
    INSERT INTO order_detail(order_id, product_id, quantity, price)
    VALUES (order_id_in, product_id_in, quantity_in, price_in);
end //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE PROC_ORDERS_CHANGE_STATUS(IN status_in int, order_id int)
begin
    UPDATE orders SET status=status_in WHERE id = order_id;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_ORDERS_FIND_BY_ID(IN id_in int)
begin
    SELECT * FROM orders WHERE id = id_in;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_ORDERS_FIND_BY_USER_ID(IN user_id_in int)
begin
    SELECT * FROM orders WHERE user_id = user_id_in;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_ORDER_DETAIL_BY_ORDER_ID(IN order_id_in int)
begin
    SELECT * FROM order_detail WHERE order_id = order_id_in;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_ORDER_PAGINATION(IN limit_in int, IN current_page int, OUT total_page int)
begin
    DECLARE offset_page int;
    SET offset_page = (current_page - 1) * limit_in;
    SET total_page = CEIL((SELECT count(*) from orders) / limit_in);
    SELECT * FROM orders LIMIT limit_in offset offset_page;
end //

DELIMITER //
CREATE PROCEDURE PROC_ORDERS_COUNT_BY_STATUS(IN status_in int, OUT order_count int)
begin
    SELECT *FROM orders WHERE status = status_in;
    SET order_count = (SELECT count(*) FROM orders WHERE status = status_in);
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_ORDERS_SEARCH_PAGED(
    IN name_in VARCHAR(100),
    IN limit_in INT,
    IN current_page INT)
BEGIN
    DECLARE offset_val INT;

    SET offset_val = (current_page - 1) * limit_in;
    SELECT *
    FROM orders
    WHERE user_id = name_in
       OR id = name_in
       OR status = name_in
    LIMIT limit_in OFFSET offset_val;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_ORDERS_SEARCH(IN name_in varchar(100))
begin
    SELECT *
    FROM orders
    WHERE user_id = name_in
       OR id = name_in
       OR status = name_in;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_ORDERS_COUNT_BY_STATUS(IN status_in int, OUT order_count int)
begin
    SELECT *FROM orders WHERE status = status_in;
    SET order_count = (SELECT count(*) FROM orders WHERE status = status_in);
end //
DELIMITER ;


