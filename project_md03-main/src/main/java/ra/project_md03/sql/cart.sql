CREATE TABLE CART
(
    id      int primary key auto_increment,
    user_id int,
    foreign key (user_id) references user (id)
);

CREATE TABLE ITEM
(
    cart_id    int,
    foreign key (cart_id) references cart (id),
    product_id int,
    foreign key (product_id) references product (id),
    quantity   int
);

DELIMITER //
CREATE PROCEDURE PROC_GET_USER_CART_ID(IN user_id_in int, OUT cart_id int)
begin
    SELECT * FROM CART WHERE user_id = user_id_in;
    SET cart_id = (SELECT id FROM CART WHERE user_id = user_id_in);
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_CART_INSERT(IN user_id_in int, OUT cart_id int)
begin
    INSERT INTO CART(user_id) VALUES (user_id_in);
    SET cart_id = LAST_INSERT_ID();
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_GET_USER_CART_ITEM(IN user_id_in int)
begin
    SELECT c.id, c.user_id, i.product_id, i.quantity
    FROM CART c
             JOIN ITEM i ON c.id = i.cart_id
    WHERE user_id = user_id_in;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_ITEM_ADD(IN cart_id_in int, product_id_in int, quantity_in int)
begin
    INSERT INTO ITEM(cart_id, product_id, quantity) VALUES (cart_id_in, product_id_in, quantity_in);
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_DELETE_ITEM_BY_PRODUCT_ID(IN product_id_in int)
begin
    DELETE FROM ITEM WHERE product_id = product_id_in;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_ITEM_INCREASE_QUANTITY(IN product_id_in int)
begin
    UPDATE ITEM SET quantity=quantity + 1 WHERE product_id = product_id_in;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_ITEM_DECREASE_QUANTITY(IN product_id_in int)
begin
    UPDATE ITEM SET quantity=quantity - 1 WHERE product_id = product_id_in and quantity > 1;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_FIND_ITEM_BY_PRODUCT_ID(IN product_id_in int, cart_id_in int)
begin
    SELECT * FROM ITEM WHERE product_id = product_id_in and cart_id = cart_id_in;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_GET_USER_CART_ITEMS(IN user_cart_id int)
begin
    SELECT * FROM ITEM WHERE cart_id = user_cart_id;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_DELETE_USER_CART_ITEM(IN user_cart_id int)
begin
    DELETE FROM ITEM WHERE cart_id = user_cart_id;
end //
DELIMITER ;