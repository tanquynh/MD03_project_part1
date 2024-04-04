CREATE TABLE IMAGE
(
    id         int primary key auto_increment,
    product_id int          not null,
    foreign key (product_id) references product (id),
    src        varchar(255) not null
);

DELIMITER
//
CREATE PROCEDURE PROC_IMAGE_FIND_ALL()
begin
SELECT *
FROM image;
end
//
 DELIMITER ;


DELIMITER
//
CREATE PROCEDURE PROC_IMAGE_FIND_BY_PRODUCT_ID(IN product_id_in int)
begin
SELECT *
FROM image
WHERE product_id = product_id_in;
end
//
DELIMITER ;


DELIMITER
//
CREATE PROCEDURE PROC_IMAGE_ADD(IN product_id_in int, src_in varchar (255))
begin
INSERT INTO image (product_id, src)
VALUES (product_id_in, src_in);
end
//
DELIMITER ;

DELIMITER
//
CREATE PROCEDURE PROC_IMAGE_EDIT(IN src_in varchar (255), id_in int)
begin
UPDATE image
SET src=src_in
WHERE id = id_in;
end
//
DELIMITER ;
DELIMITER
//
CREATE PROCEDURE PROC_IMAGE_DELETE(IN id_in int)
begin
DELETE
FROM image
WHERE product_id = id_in;
end
//
    DELIMITER ;