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

# DELIMITER //
# CREATE PROCEDURE PROC_GET_USER_CART_ID(IN user_id_in int, OUT cart_id int)
# begin
#     SELECT * FROM CART WHERE user_id = user_id_in;
#     SET cart_id = (SELECT id FROM CART WHERE user_id = user_id_in);
# end //
# DELIMITER ;

DELIMITER //

CREATE DEFINER = root@localhost PROCEDURE PROC_GET_USER_CART_ID(IN user_id_in INT, OUT cart_id INT)
BEGIN
    DECLARE cart_exists INT DEFAULT 0; -- Biến để kiểm tra xem giỏ hàng có tồn tại không

    -- Kiểm tra xem giỏ hàng tồn tại không
    SELECT COUNT(*) INTO cart_exists FROM CART WHERE user_id = user_id_in;

    IF cart_exists > 0 THEN
        -- Nếu giỏ hàng tồn tại, thực hiện lấy id của giỏ hàng
        SELECT id INTO cart_id FROM CART WHERE user_id = user_id_in;
    ELSE
        -- Nếu không tìm thấy giỏ hàng, gán cart_id = -1 hoặc một giá trị khác tuỳ bạn chọn
        SET cart_id = -1; -- hoặc SET cart_id = NULL; tùy theo yêu cầu của ứng dụng của bạn
    END IF;
END//

DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_CART_INSERT(IN user_id_in int, OUT cart_id int)
begin
    INSERT INTO CART(user_id) VALUES (user_id_in);
    SET cart_id = LAST_INSERT_ID();
end //
DELIMITER ;

DELIMITER //

DELIMITER //

CREATE PROCEDURE PROC_GET_USER_CART_ITEMS(IN cart_id_in INT)
BEGIN
    SELECT *
    FROM ITEM
    WHERE cart_id = cart_id_in;
END//
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

DELIMITER //

CREATE PROCEDURE CheckProductExistence(
    IN p_product_id INT,
    IN p_cart_id INT,
    OUT p_exists INT
)
BEGIN
    -- Khởi tạo giá trị mặc định cho biến kết quả
    SET p_exists = 0;

    -- Kiểm tra sự tồn tại của product_id trong bảng ITEM cho cart_id cụ thể
    SELECT COUNT(*) INTO p_exists
    FROM ITEM
    WHERE product_id = p_product_id AND cart_id = p_cart_id;

    -- Kết thúc stored procedure
END//

DELIMITER ;