use
md03_project_database;
CREATE TABLE product
(
    id          int primary key auto_increment,
    name        varchar(100)   not null unique,
    price       decimal(10, 2) not null,
    description text           not null,
    brand_id       int            not null,
    foreign key (brand_id) references brand (id),
    stock       int            not null,
    status      bit(1) default 1,
    image       varchar(255),
    category_id int            not null,
    foreign key (category_id) references category (id)
);
DELIMITER
//
CREATE PROCEDURE PROC_PRODUCT_FIND_ALL()
begin
SELECT *
FROM product;
end
//
DELIMITER ;


DELIMITER
//
CREATE PROCEDURE PROC_PRODUCT_EDIT(IN name_in varchar (100), price_in decimal, description_in text,
                                   brand_in int,
                                   stock_in int, image_in varchar (255),
                                   category_id_in int, id_in int)
begin
UPDATE product
SET name=name_in,
    price=price_in,
    description=description_in,
    brand_id=brand_in,
    stock=stock_in,
    image=image_in,
    category_id=category_id_in
WHERE id = id_in;
end
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_PRODUCT_EDIT1(image_in varchar(100), id_in int)
BEGIN
    UPDATE product
    SET image = image_in
    WHERE id = id_in;
END //
DELIMITER ;

DELIMITER
//
CREATE PROCEDURE PROC_PRODUCT_FIND_BY_ID(IN id_in int)
begin
SELECT *
FROM product
WHERE id = id_in;
end
//
DELIMITER ;

DELIMITER
//
CREATE PROCEDURE PROC_PRODUCT_FIND_BY_NAME(IN name_in varchar (100))
begin
SELECT *
FROM product
WHERE LCASE(name) = (LCASE(name_in));
end
//
DELIMITER ;

DELIMITER
//
CREATE PROCEDURE PROC_PRODUCT_PAGINATION(IN limit_in int, IN current_page int, OUT total_page int)
begin
    DECLARE
offset_page int;
    SET
offset_page = (current_page - 1) * limit_in;
    SET
total_page = CEIL((SELECT count(*) from product) / limit_in);
SELECT *
FROM product LIMIT limit_in
offset offset_page;
end
//
DELIMITER ;

DELIMITER
//
CREATE PROCEDURE PROC_PRODUCT_CHANGE_STATUS(IN id_in int)
begin
UPDATE product
SET status=status ^ 1
WHERE id = id_in;
end
//
DELIMITER ;

DELIMITER
//
CREATE PROCEDURE PROC_PRODUCT_ADD(IN name_in varchar (100), price_in double, description_in text,
                                  brand_in int,
                                  stock_in int, image_in varchar(255),
                                  category_id_in int, OUT product_id_add int)
begin
INSERT INTO product(name, price, description, brand_id, stock, image, category_id)
VALUES (name_in, price_in, description_in, brand_in, stock_in, image_in, category_id_in);
SELECT LAST_INSERT_ID()
INTO product_id_add;
end
//
DELIMITER ;

DELIMITER
//
CREATE PROCEDURE PROC_PRODUCT_COUNT_BY_STATUS(IN status_in bit, OUT pro_count int)
begin
SELECT *
FROM product
WHERE status = status_in;
SET
pro_count = (SELECT count(*) FROM product WHERE status = status_in);
end
//
DELIMITER ;

DELIMITER
//
CREATE PROCEDURE PROC_PRODUCT_FIND_PAGED(
    IN name_in VARCHAR (100),
    IN limit_in INT,
    IN current_page INT)
BEGIN
    DECLARE
offset_val INT;

    SET
offset_val = (current_page - 1) * limit_in;
SELECT *
FROM product
WHERE LCASE(name) LIKE CONCAT('%', LCASE(name_in), '%')
   OR id = name_in
   OR LCASE(description) LIKE CONCAT('%', LCASE(name_in), '%') LIMIT limit_in
OFFSET offset_val;
END
//
DELIMITER ;

DELIMITER
//
CREATE PROCEDURE PROC_PRODUCT_FIND_COUNT(IN name_in varchar (100))
begin
SELECT *
FROM product
WHERE LCASE(name) LIKE CONCAT('%', LCASE(name_in), '%')
   OR id = name_in
   OR LCASE(description) LIKE CONCAT('%', LCASE(name_in), '%');

end
//
DELIMITER ;

DELIMITER
//
CREATE PROCEDURE PROC_PRODUCT_FIND_COUNT(IN name_in varchar (100))
begin
SELECT *
FROM product
WHERE LCASE(name) LIKE CONCAT('%', LCASE(name_in), '%')
   OR id = name_in
   OR LCASE(description) LIKE CONCAT('%', LCASE(name_in), '%');

end
//
DELIMITER ;

DELIMITER
//
CREATE PROCEDURE PROC_PRODUCT_ACTIVE_PAGINATION(IN limit_in int, IN current_page int, OUT total_page int)
begin
    DECLARE
offset_page int;
    SET
offset_page = (current_page - 1) * limit_in;
    SET
total_page = CEIL((SELECT count(*) from product) / limit_in);
SELECT *
FROM product p
         JOIN category c on p.category_id = c.id
WHERE p.status = true
  and c.status = true LIMIT limit_in
offset offset_page;
end
//
DELIMITER ;

DELIMITER
//
CREATE PROCEDURE PROC_PRODUCT_ACTIVE_FIND_PAGED(
    IN name_in VARCHAR (100),
    IN limit_in INT,
    IN current_page INT)
BEGIN
    DECLARE
offset_val INT;

    SET
offset_val = (current_page - 1) * limit_in;
SELECT *
FROM product p
         JOIN category c on p.category_id = c.id
WHERE (LCASE(p.name) LIKE CONCAT('%', LCASE(name_in), '%')
    OR p.id = name_in
    OR LCASE(p.description) LIKE CONCAT('%', LCASE(name_in), '%'))
  and p.status = true
  and c.status = true LIMIT limit_in
OFFSET offset_val;
END
//
DELIMITER ;

DELIMITER
//
CREATE PROCEDURE PROC_PRODUCT_ACTIVE_FIND(IN name_in varchar (100))
begin
SELECT *
FROM product p
         JOIN category c on p.category_id = c.id
WHERE (LCASE(p.name) LIKE CONCAT('%', LCASE(name_in), '%')
    OR p.id = name_in
    OR LCASE(p.description) LIKE CONCAT('%', LCASE(name_in), '%'))
  and p.status = true
  and c.status = true;
end
//
DELIMITER ;

DELIMITER
//
CREATE PROCEDURE PROC_PRODUCT_ACTIVE_PAGINATION(IN limit_in int, IN current_page int, OUT total_page int)
begin
    DECLARE
offset_page int;
    SET
offset_page = (current_page - 1) * limit_in;
    SET
total_page = CEIL((SELECT count(*) from product) / limit_in);
SELECT *
FROM product p
         JOIN category c on p.category_id = c.id
WHERE p.status = true
  and c.status = true LIMIT limit_in
offset offset_page;
end
//
DELIMITER ;

DELIMITER
//
CREATE PROCEDURE PROC_PRODUCT_ACTIVE_FIND_NUMBER(IN name_in varchar (100))
begin
SELECT count(*)
FROM product p
         JOIN category c on p.category_id = c.id
WHERE (LCASE(p.name) LIKE CONCAT('%', LCASE(name_in), '%')
    OR p.id = name_in
    OR LCASE(p.description) LIKE CONCAT('%', LCASE(name_in), '%'))
  and p.status = true
  and c.status = true;
end
//
DELIMITER ;

DELIMITER
//
CREATE PROCEDURE PROC_PRODUCT_SORT_PAGINATION(IN limit_in INT, IN current_page INT, IN sort_type VARCHAR (4),
                                              OUT total_page INT)
BEGIN
    DECLARE
offset_page INT;
    SET
offset_page = (current_page - 1) * limit_in;

    -- Đếm tổng số trang
    SET
total_page = CEIL((SELECT COUNT(*)
                           FROM product p
                                    JOIN category c ON p.category_id = c.id
                           WHERE p.status = true
                             AND c.status = true) / limit_in);

    -- Lấy dữ liệu phân trang và sắp xếp
    IF
sort_type = 'ASC' THEN
SELECT *
FROM product p
         JOIN category c on p.category_id = c.id
WHERE p.status = true
  and c.status = true
ORDER BY p.price ASC LIMIT limit_in
OFFSET offset_page;
ELSE
SELECT *
FROM product p
         JOIN category c on p.category_id = c.id
WHERE p.status = true
  and c.status = true
ORDER BY p.price DESC LIMIT limit_in
OFFSET offset_page;
END IF;
END
//
DELIMITER ;

DELIMITER
//
CREATE PROCEDURE PROC_PRODUCT_FIND_BY_CATEGORY_ID(IN id_in int)
begin
SELECT *
FROM product
WHERE category_id = id_in;
end
//
DELIMITER ;

DELIMITER
//
CREATE PROCEDURE PROC_PRODUCT_FIND_BY_BRAND(IN brand_in int, OUT total_product int)
begin
SELECT *
FROM product
WHERE brand_id = brand_in;
SET
total_product = (SELECT count(*) FROM product WHERE brand_id = brand_in);
end
//
DELIMITER ;

DELIMITER
//
CREATE PROCEDURE PROC_PRODUCT_SORT_PAGINATION_ALL(IN limit_in INT, IN current_page INT, IN sort_type VARCHAR (4),
                                                  OUT total_page INT)
BEGIN
    DECLARE
offset_page INT;
    SET
offset_page = (current_page - 1) * limit_in;

    -- Đếm tổng số trang
    SET
total_page = CEIL((SELECT COUNT(*) FROM product) / limit_in);

    -- Lấy dữ liệu phân trang và sắp xếp
    IF
sort_type = 'ASC' THEN
SELECT *
FROM product
ORDER BY price ASC LIMIT limit_in
OFFSET offset_page;
ELSE
SELECT *
FROM product
ORDER BY price DESC LIMIT limit_in
OFFSET offset_page;
END IF;
END
//

DELIMITER ;

DELIMITER
//
CREATE PROCEDURE PROC_PRODUCT_SORT_PAGINATION_ALL(IN limit_in INT, IN current_page INT, IN sort_type VARCHAR (4),
                                                  OUT total_page INT)
BEGIN
    DECLARE
offset_page INT;
    SET
offset_page = (current_page - 1) * limit_in;

    -- Đếm tổng số trang
    SET
total_page = CEIL((SELECT COUNT(*) FROM product) / limit_in);

    -- Lấy dữ liệu phân trang và sắp xếp
    IF
sort_type = 'ASC' THEN
SELECT *
FROM product
ORDER BY price ASC LIMIT limit_in
OFFSET offset_page;
ELSE
SELECT *
FROM product
ORDER BY price DESC LIMIT limit_in
OFFSET offset_page;
END IF;
END
//

DELIMITER ;
