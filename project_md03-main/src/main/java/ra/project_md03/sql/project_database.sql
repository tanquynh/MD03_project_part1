CREATE DATABASE md03_project_database;
use md03_project_database;

CREATE TABLE category
(
    id        int primary key auto_increment,
    name      varchar(100) not null unique,
    status    bit(1) default 1,
    image varchar(255),
    parent_id int
);
alter table category
    modify name varchar(100) not null;

DELIMITER //
CREATE PROCEDURE PROC_CATEGORY_PAGINATION(IN limit_in int, IN current_page int, OUT total_page int)
begin
    DECLARE offset_page int;
    SET offset_page = (current_page - 1) * limit_in;
    SET total_page = CEIL((SELECT count(*) from category) / limit_in);
    SELECT * FROM category LIMIT limit_in offset offset_page;
end //

DELIMITER //
CREATE PROCEDURE PROC_CATEGORY_FIND_ALL()
begin
    SELECT *FROM category;
end //

DELIMITER //
CREATE PROCEDURE PROC_CATEGORY_FIND_PARENT()
begin
    SELECT *FROM category order by id limit 3;
end //

call PROC_CATEGORY_FIND_PARENT;

DELIMITER //
CREATE PROCEDURE PROC_CATEGORY_COUNT_BY_STATUS(IN status_in bit, OUT cat_count int)
begin
    IF (status_in != 0 and status_in != 1) THEN
        SET cat_count = (SELECT count(*) FROM category);
    ELSE
        SET cat_count = (SELECT count(*) FROM category WHERE status = status_in);
    END IF;
end //


DELIMITER //
CREATE PROCEDURE PROC_CATEGORY_ADD(IN name_in varchar(100), parent_id_in int,image_in varchar(255))
begin
    INSERT INTO category(name, parent_id,image) VALUES (name_in, parent_id_in,image_in);
end //

drop procedure PROC_CATEGORY_EDIT;
DELIMITER //
CREATE PROCEDURE PROC_CATEGORY_EDIT(IN name_in varchar(100), parent_id_in int,image_in varchar(255), id_in int)
begin
    UPDATE category SET name=name_in, parent_id=parent_id_in,image=image_in WHERE id = id_in;
end //

DELIMITER //
CREATE PROCEDURE PROC_CATEGORY_CHANGE_STATUS(IN id_in int)
begin
    UPDATE category SET status=status ^ 1 WHERE id = id_in;
end //

DELIMITER //
CREATE PROCEDURE PROC_CATEGORY_FIND_BY_ID(IN id_in int)
begin
    SELECT * FROM category WHERE id = id_in;
end //

DELIMITER //
CREATE PROCEDURE PROC_CATEGORY_FIND_BY_NAME(IN name_in varchar(100))
begin
    SELECT *
    FROM category
    WHERE LCASE(name) LIKE concat('%', LCASE(name_in), '%')
       or id LIKE concat('%', LCASE(name_in), '%');
end //


DELIMITER //
CREATE PROCEDURE PROC_CATEGORY_FIND(IN name_in varchar(100), OUT total_page int)
begin
    SELECT *
    FROM category
    WHERE LCASE(name) LIKE concat('%', LCASE(name_in), '%')
       or id LIKE concat('%', LCASE(name_in), '%');
    SET total_page = ceil((SELECT count(*)
                           FROM category
                           WHERE LCASE(name) LIKE concat('%', LCASE(name_in), '%')
                              or id LIKE concat('%', LCASE(name_in), '%')) / 5);
end //

drop procedure PROC_CATEGORY_FIND;

DELIMITER //
CREATE PROCEDURE PROC_CATEGORY_SORT_BY_ID(IN sort_order varchar(4))
begin
    IF sort_order = 'DESC' THEN
        SELECT * FROM category ORDER BY id desc ;
    ELSEIF sort_order = 'ASC' THEN
        SELECT * FROM category ORDER BY id ;
    ELSE
        SELECT * FROM category;
    END IF;
end
//

DELIMITER //
CREATE PROCEDURE PROC_CATEGORY_FIND_BY_PARENT_ID(IN parent_id_in int)
begin
    SELECT *FROM category WHERE parent_id=parent_id_in;
end //

DELIMITER //
CREATE PROCEDURE PROC_CATEGORY_FIND_BY_NAME(IN name_in varchar(100))
begin
    SELECT * FROM category WHERE LCASE(name) LIKE concat('%', LCASE(name_in), '%');
end //

DELIMITER //
CREATE PROCEDURE PROC_CATEGORY_SORT_PRICE()
BEGIN
    #     DECLARE sort_order VARCHAR(4);
#     SET sort_order = 'ASC'; -- Thứ tự mặc định là tăng dần
#
#     IF EXISTS(SELECT * FROM category) THEN
#         SELECT MAX(id) INTO @max_id FROM category;
#         SELECT MIN(id) INTO @min_id FROM category;
#         IF @max_id > @min_id THEN
#             IF @min_id = 1 THEN
#                 SET sort_order = 'DESC'; -- Nếu id bắt đầu từ 1, sắp xếp giảm dần
#             END IF;
#         END IF;
#     END IF;
#
#     IF sort_order = 'DESC' THEN
#         SELECT * FROM category ORDER BY id DESC ;
#     ELSE
#         SELECT * FROM category ORDER BY id ASC ;
#     END IF;

END //



CREATE TABLE product
(
    id          int primary key auto_increment,
    name        varchar(100) not null unique,
    price       double       not null,
    description text         not null,
    brand       varchar(100),
    stock       int          not null,
    status      bit(1) default 1,
    image       text,
    category_id int          not null,
    foreign key (category_id) references category (id)
);



DELIMITER //
CREATE PROCEDURE PROC_PRODUCT_FIND_ALL()
begin
    SELECT *FROM product;
end //


DELIMITER //
CREATE PROCEDURE PROC_PRODUCT_FIND_ALL_ACTIVE_STATUS(IN status_in bit)
begin
    SELECT *FROM product WHERE status = status_in;
end //

DELIMITER //
CREATE PROCEDURE PROC_PRODUCT_PAGINATION(IN limit_in int, IN current_page int, OUT total_page int)
begin
    DECLARE offset_page int;
    SET offset_page = (current_page - 1) * limit_in;
    SET total_page = CEIL((SELECT count(*) from product) / limit_in);
    SELECT * FROM product LIMIT limit_in offset offset_page;
end //


DELIMITER //
CREATE PROCEDURE PROC_PRODUCT_COUNT_BY_STATUS(IN status_in bit, OUT pro_count int)
begin
    SELECT *FROM product WHERE status = status_in;
    SET pro_count = (SELECT count(*) FROM product WHERE status = status_in);
end //
drop procedure PROC_PRODUCT_FIND_ALL_ACTIVE_STATUS;

DELIMITER //
CREATE PROCEDURE PROC_PRODUCT_ADD(IN name_in varchar(100), price_in double, description_in text, brand_in varchar(100),
                                  stock_in int, image_in text,
                                  category_id_in int, OUT product_id_add int)
begin
    INSERT INTO product(name, price, description, brand, stock, image, category_id)
    VALUES (name_in, price_in, description_in, brand_in, stock_in, image_in, category_id_in);
    SELECT LAST_INSERT_ID() INTO product_id_add;
end //

call PROC_PRODUCT_ADD('uiiii', 10, 't', 't', 10, null, 1, @product_id_add);
select @product_id_add;
drop procedure PROC_PRODUCT_ADD;

DELIMITER //
CREATE PROCEDURE PROC_PRODUCT_EDIT(IN name_in varchar(100), price_in double, description_in text, brand_in varchar(100),
                                   stock_in int, image_in varchar(100),
                                   category_id_in int, id_in int)
begin
    UPDATE product
    SET name=name_in,
        price=price_in,
        description=description_in,
        brand=brand_in,
        stock=stock_in,
        image=image_in,
        category_id=category_id_in
    WHERE id = id_in;
end //

DELIMITER //
CREATE PROCEDURE PROC_LIST_BRAND()
BEGIN
    SELECT DISTINCT brand
    FROM product;
END //
DELIMITER ;
call PROC_LIST_BRAND;


DELIMITER //
CREATE PROCEDURE PROC_PRODUCT_FIND_BY_BRAND(IN brand_in varchar(100),OUT total_product int)
begin
    SELECT * FROM product WHERE brand = brand_in;
    SET total_product=(SELECT count(*)FROM product WHERE brand = brand_in);
end //
drop procedure PROC_LIST_BRAND;

DELIMITER //
CREATE PROCEDURE PROC_PRODUCT_FIND_BY_ID(IN id_in int)
begin
    SELECT * FROM product WHERE id = id_in;
end //

DELIMITER //
CREATE PROCEDURE PROC_PRODUCT_FIND_BY_CATEGORY_ID(IN id_in int)
begin
    SELECT * FROM product WHERE category_id = id_in;
end //

DELIMITER //
CREATE PROCEDURE PROC_PRODUCT_CHANGE_STATUS(IN id_in int)
begin
    UPDATE product SET status=status ^ 1 WHERE id = id_in;
end //

DELIMITER //
CREATE PROCEDURE PROC_PRODUCT_FIND_BY_NAME(IN name_in varchar(100))
begin
    SELECT *
    FROM product
    WHERE LCASE(name) = (LCASE(name_in));
end //
drop procedure PROC_PRODUCT_FIND_COUNT;
call PROC_PRODUCT_FIND_BY_NAME('Mu len nnnn');

DELIMITER //
CREATE PROCEDURE PROC_PRODUCT_FIND_COUNT(IN name_in varchar(100))
begin
    SELECT *
    FROM product
    WHERE LCASE(name) LIKE CONCAT('%', LCASE(name_in), '%')
       OR id = name_in
       OR LCASE(description) LIKE CONCAT('%', LCASE(name_in), '%');

end //

DELIMITER //
CREATE PROCEDURE PROC_PRODUCT_FIND_NUMBER(IN name_in varchar(100))
begin
    SELECT count(*)
    FROM product
    WHERE LCASE(name) LIKE CONCAT('%', LCASE(name_in), '%')
       OR id = name_in
       OR LCASE(description) LIKE CONCAT('%', LCASE(name_in), '%');

end //

call PROC_PRODUCT_FIND_COUNT('mu len', @total_page);
select @total_page;

drop procedure PROC_PRODUCT_FIND;
call PROC_PRODUCT_FIND_PAGED('mu len', 5, 1);
call PROC_PRODUCT_TOTAL_FIND_PAGED('mu len', 5, 1);
select @total_page;

DELIMITER //
CREATE PROCEDURE PROC_PRODUCT_FIND_PAGED(
    IN name_in VARCHAR(100),
    IN limit_in INT,
    IN current_page INT)
BEGIN
    DECLARE offset_val INT;

    SET offset_val = (current_page - 1) * limit_in;
    SELECT *
    FROM product
    WHERE LCASE(name) LIKE CONCAT('%', LCASE(name_in), '%')
       OR id = name_in
       OR LCASE(description) LIKE CONCAT('%', LCASE(name_in), '%')
    LIMIT limit_in OFFSET offset_val;
END //
DELIMITER ;

# thu tuc de hien thi Product giao dien nguoi dung (status true)
DELIMITER //
CREATE PROCEDURE PROC_PRODUCT_ACTIVE_PAGINATION(IN limit_in int, IN current_page int, OUT total_page int)
begin
    DECLARE offset_page int;
    SET offset_page = (current_page - 1) * limit_in;
    SET total_page = CEIL((SELECT count(*) from product) / limit_in);
    SELECT *
    FROM product p
             JOIN category c on p.category_id = c.id
    WHERE p.status = true
      and c.status = true
    LIMIT limit_in offset offset_page;
end //

DELIMITER //
CREATE PROCEDURE PROC_PRODUCT_ACTIVE_FIND_PAGED(
    IN name_in VARCHAR(100),
    IN limit_in INT,
    IN current_page INT)
BEGIN
    DECLARE offset_val INT;

    SET offset_val = (current_page - 1) * limit_in;
    SELECT *
    FROM product p
             JOIN category c on p.category_id = c.id
    WHERE (LCASE(p.name) LIKE CONCAT('%', LCASE(name_in), '%')
        OR p.id = name_in
        OR LCASE(p.description) LIKE CONCAT('%', LCASE(name_in), '%'))
      and p.status = true
      and c.status = true
    LIMIT limit_in OFFSET offset_val;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_PRODUCT_ACTIVE_FIND(IN name_in varchar(100))
begin
    SELECT *
    FROM product p
             JOIN category c on p.category_id = c.id
    WHERE (LCASE(p.name) LIKE CONCAT('%', LCASE(name_in), '%')
        OR p.id = name_in
        OR LCASE(p.description) LIKE CONCAT('%', LCASE(name_in), '%'))
      and p.status = true
      and c.status = true;
end //

DELIMITER //
CREATE PROCEDURE PROC_PRODUCT_ACTIVE_FIND_NUMBER(IN name_in varchar(100))
begin
    SELECT count(*)
    FROM product p
             JOIN category c on p.category_id = c.id
    WHERE (LCASE(p.name) LIKE CONCAT('%', LCASE(name_in), '%')
        OR p.id = name_in
        OR LCASE(p.description) LIKE CONCAT('%', LCASE(name_in), '%'))
      and p.status = true
      and c.status = true;
end //

call PROC_PRODUCT_ACTIVE_FIND_PAGED('mu', 5, 1);

DELIMITER //
CREATE PROCEDURE PROC_PRODUCT_SORT_BY_PRICE(IN sort_type varchar(4))
begin
    SET @sort_query = CONCAT('SELECT * FROM product ORDER BY price ', sort_type);
    PREPARE stmt FROM @sort_query;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_PRODUCT_SORT_PAGINATION(IN limit_in INT, IN current_page INT, IN sort_type VARCHAR(4), OUT total_page INT)
BEGIN
    DECLARE offset_page INT;
    SET offset_page = (current_page - 1) * limit_in;

    -- Đếm tổng số trang
    SET total_page = CEIL((SELECT COUNT(*) FROM product p JOIN category c ON p.category_id = c.id WHERE p.status = true AND c.status = true) / limit_in);

    -- Lấy dữ liệu phân trang và sắp xếp
    IF sort_type = 'ASC' THEN
        SELECT *
        FROM product p
                 JOIN category c on p.category_id = c.id
        WHERE p.status = true
          and c.status = true
        ORDER BY p.price ASC
        LIMIT limit_in OFFSET offset_page;
    ELSE
        SELECT *
        FROM product p
                 JOIN category c on p.category_id = c.id
        WHERE p.status = true
          and c.status = true
        ORDER BY p.price DESC
        LIMIT limit_in OFFSET offset_page;
    END IF;
END //

DELIMITER //
CREATE PROCEDURE PROC_PRODUCT_SORT_PAGINATION_ALL(IN limit_in INT, IN current_page INT, IN sort_type VARCHAR(4), OUT total_page INT)
BEGIN
    DECLARE offset_page INT;
    SET offset_page = (current_page - 1) * limit_in;

    -- Đếm tổng số trang
    SET total_page = CEIL((SELECT COUNT(*) FROM product) / limit_in);

    -- Lấy dữ liệu phân trang và sắp xếp
    IF sort_type = 'ASC' THEN
        SELECT *
        FROM product
        ORDER BY price ASC
        LIMIT limit_in OFFSET offset_page;
    ELSE
        SELECT *
        FROM product
        ORDER BY price DESC
        LIMIT limit_in OFFSET offset_page;
    END IF;
END //

DELIMITER ;

CREATE TABLE USER
(
    id       int primary key auto_increment,
    name     varchar(100) not null,
    email    varchar(50)  not null unique,
    address  text         not null,
    phone    varchar(20)  not null,
    role     bit(1) default 1,
    status   bit(1) default 1,
    avatar   varchar(255),
    password varchar(255) not null
);


DELIMITER //
CREATE PROCEDURE PROC_USER_FIND_ALL()
begin
    SELECT *FROM user;
end //

DELIMITER //
CREATE PROCEDURE PROC_USER_COUNT_BY_STATUS(IN status_in bit, OUT count_user int)
begin
    IF (status_in != 0 and status_in != 1) THEN
        SET count_user = (SELECT count(*) FROM user);
    ELSE
        SET count_user = (SELECT count(*) FROM user WHERE status = status_in);
    END IF;
end //


DELIMITER //
CREATE PROCEDURE PROC_USER_CHANGE_STATUS(IN id_in int)
begin
    DECLARE user_role INT;
    DECLARE user_status INT;

    -- Lấy vai trò và trạng thái hiện tại của người dùng
    SELECT role, status INTO user_role, user_status FROM user WHERE id = id_in;

    -- Kiểm tra và thay đổi trạng thái
    IF user_role = 1 THEN
        UPDATE user SET status = status ^ 1 WHERE id = id_in;
    END IF;

end //

drop procedure PROC_USER_SET_ROLE;

DELIMITER //
CREATE PROCEDURE PROC_USER_SET_ROLE(IN id_in int)
begin
    DECLARE user_role INT;
    DECLARE user_status INT;

    -- Lấy vai trò và trạng thái hiện tại của người dùng
    SELECT role, status INTO user_role, user_status FROM user WHERE id = id_in;

    -- Kiểm tra và thay đổi trạng thái
    IF user_role = 1 THEN
        UPDATE user SET role = role ^ 1 WHERE id = id_in;
    END IF;
end //


DELIMITER //
CREATE PROCEDURE PROC_USER_ADD(IN name_in varchar(100), email_in varchar(50), address_in text, phone_in varchar(50),
                               password_in text)
begin
    INSERT INTO user(name, email, address, phone, password)
    VALUES (name_in, email_in, address_in, phone_in, password_in);
end //

drop procedure PROC_USER_ADD;


DELIMITER //
CREATE PROCEDURE PROC_USER_EDIT(IN name_in varchar(100), email_in varchar(50), address_in text, phone_in varchar(50),
                                avatar_in text, id_in int)
begin
    UPDATE user
    SET name=name_in,
        email=email_in,
        address=address_in,
        phone=phone_in,
        avatar=avatar_in
    WHERE id = id_in;
end //


DELIMITER //
CREATE PROCEDURE PROC_USER_CHANGE_PASSWORD(IN password_in varchar(255), id_in int)
begin
    UPDATE user SET password=password_in WHERE id = id_in;
end //

DELIMITER //
CREATE PROCEDURE PROC_USER_PAGINATION(IN limit_in int, IN current_page int, OUT total_page int)
begin
    DECLARE offset_page int;
    SET offset_page = (current_page - 1) * limit_in;
    SET total_page = ceil((select count(*) from user) / limit_in);
    SELECT * FROM user LIMIT limit_in offset offset_page;
end //

call PROC_USER_PAGINATION(2, 1, @total_page);
select @total_page;

DELIMITER //
CREATE PROCEDURE PROC_USER_FIND(IN name_in varchar(255), OUT total_user int, OUT total_page int)
begin
    SELECT *
    FROM user
    WHERE LCASE(name) LIKE concat('%', LCASE(name_in), '%')
       OR CAST(id AS CHAR) = name_in
       or LCASE(email) LIKE concat('%', LCASE(name_in), '%');
    SET total_user = (SELECT count(*)
                      FROM user
                      WHERE LCASE(name) LIKE concat('%', LCASE(name_in), '%')
                         OR CAST(id AS CHAR) = name_in
                         or LCASE(email) LIKE concat('%', LCASE(name_in), '%'));
    SET total_page = ceil(total_user / 5);
end //

call PROC_USER_FIND('tung', @total_page);
select @total_page;

drop procedure PROC_USER_FIND;


DELIMITER //
CREATE PROCEDURE PROC_USER_FIND_BY_STATUS(IN status_in bit)
begin
    SELECT * FROM user WHERE status = status_in;
end //

DELIMITER //
CREATE PROCEDURE PROC_USER_FIND_BY_ID(IN id_in int)
begin
    SELECT * FROM user WHERE id = id_in;
end //

DELIMITER //
CREATE PROCEDURE PROC_USER_FIND_BY_MAIL(IN mail varchar(50))
begin
    SELECT * FROM user WHERE email = mail;
end //

CREATE TABLE IMAGE
(
    id         int primary key auto_increment,
    product_id int          not null,
    foreign key (product_id) references product (id),
    src        varchar(255) not null
);

DELIMITER //
CREATE PROCEDURE PROC_IMAGE_FIND_ALL()
begin
    SELECT * FROM image;
end //

DELIMITER //
CREATE PROCEDURE PROC_IMAGE_FIND_BY_PRODUCT_ID(IN product_id_in int)
begin
    SELECT * FROM image WHERE product_id = product_id_in;
end //

call PROC_IMAGE_FIND_BY_PRODUCT_ID(52);

DELIMITER //
CREATE PROCEDURE PROC_IMAGE_ADD(IN product_id_in int, src_in varchar(255))
begin
    INSERT INTO image (product_id, src) VALUES (product_id_in, src_in);
end //


DELIMITER //
CREATE PROCEDURE PROC_IMAGE_EDIT(IN src_in varchar(255), id_in int)
begin
    UPDATE image SET src=src_in WHERE id = id_in;
end //

DELIMITER //
CREATE PROCEDURE PROC_IMAGE_DELETE(IN id_in int)
begin
    DELETE FROM image WHERE product_id = id_in;
end //

# chua chay tao bang
CREATE TABLE `orders`
(
    id       int primary key auto_increment,
    user_id  int,
    foreign key (user_id) references user (id),
    total    double,
    address  text,
    note     text,
    status   int(1) default 0,
    order_at date   default (DATE(NOW()))
);

DELIMITER //
CREATE PROCEDURE PROC_ORDERS_FIND_ALL()
begin
    SELECT * FROM orders;
end //

call PROC_ORDERS_FIND_ALL;

DELIMITER //
CREATE PROCEDURE PROC_ORDERS_ADD(IN user_id_in int, total_in double, address_in text, note_in text,
                                 payment_in varchar(100), OUT order_id int)
begin
    INSERT INTO orders(user_id, total, address, note, payment)
    VALUES (user_id_in, total_in, address_in, note_in, payment_in);
    SET order_id = LAST_INSERT_ID();
end //

drop procedure PROC_ORDERS_ADD;

DELIMITER //
CREATE PROCEDURE PROC_ORDERS_FIND_BY_ID(IN id_in int)
begin
    SELECT * FROM orders WHERE id = id_in;
end //

DELIMITER //
CREATE PROCEDURE PROC_ORDERS_FIND_BY_STATUS(IN status_in int, OUT total_orders int)
begin
    SELECT * FROM orders WHERE status = status_in;
    SET total_orders = (SELECT * FROM orders WHERE status = status_in);
end //

DELIMITER //
CREATE PROCEDURE PROC_ORDERS_FIND_BY_USER_ID(IN user_id_in int)
begin
    SELECT * FROM orders WHERE user_id = user_id_in;
end //

call PROC_ORDERS_FIND_BY_USER_ID(9);

DELIMITER //
CREATE PROCEDURE PROC_ORDERS_CHANGE_STATUS(IN status_in int, order_id int)
begin
    UPDATE orders SET status=status_in WHERE id = order_id;
end //

CREATE TABLE order_detail
(
    order_id   int,
    foreign key (order_id) references orders (id),
    product_id int,
    foreign key (product_id) references product (id),
    quantity   int,
    price      double
);
DELIMITER //
CREATE PROCEDURE PROC_ORDER_DETAIL_BY_ORDER_ID(IN order_id_in int)
begin
    SELECT * FROM order_detail WHERE order_id = order_id_in;
end //

DELIMITER //
CREATE PROCEDURE PROC_ORDER_DETAIL_ADD(IN order_id_in int, product_id_in int, quantity_in int, price_in double)
begin
    INSERT INTO order_detail(order_id, product_id, quantity, price)
    VALUES (order_id_in, product_id_in, quantity_in, price_in);
end //

CREATE TABLE CART
(
    id      int primary key auto_increment,
    user_id int,
    foreign key (user_id) references user (id)
);
DELIMITER //
CREATE PROCEDURE PROC_GET_USER_CART_ID(IN user_id_in int, OUT cart_id int)
begin
    SELECT * FROM CART WHERE user_id = user_id_in;
    SET cart_id = (SELECT id FROM CART WHERE user_id = user_id_in);
end //


DELIMITER //
CREATE PROCEDURE PROC_CART_INSERT(IN user_id_in int, OUT cart_id int)
begin
    INSERT INTO CART(user_id) VALUES (user_id_in);
    SET cart_id = LAST_INSERT_ID();
end //

drop procedure PROC_CART_INSERT;


CREATE TABLE ITEM
(
    cart_id    int,
    foreign key (cart_id) references cart (id),
    product_id int,
    foreign key (product_id) references product (id),
    quantity   int
);

DELIMITER //
CREATE PROCEDURE PROC_GET_USER_CART_ITEM(IN user_id_in int)
begin
    SELECT c.id, c.user_id, i.product_id, i.quantity
    FROM CART c
             JOIN ITEM i ON c.id = i.cart_id
    WHERE user_id = user_id_in;
end //

drop procedure PROC_GET_USER_CART_ITEM;

DELIMITER //
CREATE PROCEDURE PROC_GET_USER_CART_ITEM(IN user_id_in INT)
BEGIN
    DECLARE cursor_result CURSOR FOR
        SELECT c.id, c.user_id, i.product_id, i.quantity
        FROM CART c
                 JOIN ITEM i ON c.id = i.cart_id
        WHERE user_id = user_id_in;
    SELECT * FROM CART WHERE 1 = 0; -- Dummy query to make the ResultSet scrollable
    OPEN cursor_result;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_GET_USER_CART_ITEMS(IN user_cart_id int)
begin
    SELECT * FROM ITEM WHERE cart_id = user_cart_id;
end //

call PROC_GET_USER_CART_ITEMS(1);

DELIMITER //
CREATE PROCEDURE PROC_DELETE_USER_CART_ITEM(IN user_cart_id int)
begin
    DELETE FROM ITEM WHERE cart_id = user_cart_id;
end //


DELIMITER //
CREATE PROCEDURE PROC_ITEM_ADD(IN cart_id_in int, product_id_in int, quantity_in int)
begin
    INSERT INTO ITEM(cart_id, product_id, quantity) VALUES (cart_id_in, product_id_in, quantity_in);
end //

DELIMITER //
CREATE PROCEDURE PROC_FIND_ITEM_BY_PRODUCT_ID(IN product_id_in int, cart_id_in int)
begin
    SELECT * FROM ITEM WHERE product_id = product_id_in and cart_id = cart_id_in;
end //

drop procedure PROC_FIND_ITEM_BY_PRODUCT_ID;

DELIMITER //
CREATE PROCEDURE PROC_DELETE_ITEM_BY_PRODUCT_ID(IN product_id_in int)
begin
    DELETE FROM ITEM WHERE product_id = product_id_in;
end //

DELIMITER //
CREATE PROCEDURE PROC_ITEM_INCREASE_QUANTITY(IN product_id_in int)
begin
    UPDATE ITEM SET quantity=quantity + 1 WHERE product_id = product_id_in;
end //

DELIMITER //
CREATE PROCEDURE PROC_ITEM_DECREASE_QUANTITY(IN product_id_in int)
begin
    UPDATE ITEM SET quantity=quantity - 1 WHERE product_id = product_id_in and quantity > 1;
end //

CREATE TABLE WISHLIST
(
    id         int primary key auto_increment,
    user_id    int,
    foreign key (user_id) references user (id),
    product_id int,
    foreign key (product_id) references product (id)
);

DELIMITER //
CREATE PROCEDURE PROC_WISHLIST_FIND_BY_USER_ID(IN user_id_in int)
begin
    SELECT*FROM WISHLIST WHERE user_id = user_id_in;
end //

DELIMITER //
CREATE PROCEDURE PROC_WISHLIST_ADD(IN user_id_in int, product_id_in int)
begin
    INSERT INTO WISHLIST(user_id, product_id) VALUES (user_id_in, product_id_in);
end //

DELIMITER //
CREATE PROCEDURE PROC_WISHLIST_DELETE(IN product_id_in int)
begin
    DELETE FROM WISHLIST WHERE product_id = product_id_in;
end //


DELIMITER //
CREATE PROCEDURE PROC_WISHLIST_FIND_BY_PRODUCT_ID(IN product_id_in INT, user_id_in INT)
BEGIN
    SELECT * FROM WISHLIST WHERE product_id = product_id_in AND user_id = user_id_in;
END //

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

DELIMITER //
CREATE PROCEDURE PROC_ORDERS_SEARCH(IN name_in varchar(100))
begin
    SELECT *
    FROM orders
    WHERE user_id = name_in
       OR id = name_in
       OR status = name_in;
end //

DELIMITER //
CREATE PROCEDURE PROC_ORDERS_SEARCH_NUMBER(IN name_in varchar(100))
begin
    SELECT count(*)
    FROM orders
    WHERE user_id = name_in
       OR id = name_in
       OR status = name_in;
end //

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