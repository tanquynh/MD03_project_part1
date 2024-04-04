create
database md03_project_database;
use
md03_project_database;
create table category
(
    id        int primary key auto_increment,
    name      varchar(100) not null unique,
    status    bit(1) default 1,
    image     varchar(255),
    parent_id int
);
DELIMITER
//
create procedure PROC_CATEGORY_FIND_ALL()
begin
select *
from category;
end
//
DELIMITER ;

DELIMITER
//
create procedure PROC_CATEGORY_ADD(IN name_in varchar (100), parent_id_in int, image_in varchar (255))
begin
insert into category(name, parent_id, image)
values (name_in, parent_id_in, image_in);
end
//
DELIMITER ;

CREATE PROCEDURE PROC_CATEGORY_EDIT(IN name_in varchar (100), parent_id_in int, image_in varchar (255), id_in int)
begin
update category
set name      = name_in,
    parent_id = parent_id_in,
    image     = image_in
where id = id_in;
end;
DELIMITER ;

DELIMITER
//
create procedure PROC_FIND_CATEGORY_BY_ID(IN id_in int)
begin
select *
from category
where id = id_in;
end
//
DELIMITER ;


DELIMITER
//

CREATE PROCEDURE PROC_CATEGORY_FIND(IN name_in VARCHAR (100), OUT total_page INT)
BEGIN
SELECT *
FROM category
WHERE LCASE(name) LIKE CONCAT('%', LCASE(name_in), '%')
   OR id LIKE CONCAT('%', LCASE(name_in), '%');

SET
total_page = CEIL((SELECT COUNT(*)
                           FROM category
                           WHERE LCASE(name) LIKE CONCAT('%', LCASE(name_in), '%')
                              OR id LIKE CONCAT('%', LCASE(name_in), '%')) / 5);
END
//

DELIMITER ;


DELIMITER
//
create procedure PROC_CATEGORY_PAGINATION(IN limit_in int, IN current_page int, OUT total_page int)
begin
    DECLARE
offset_page int;
    SET
offset_page = (current_page - 1) * limit_in;
    SET
total_page = ceil((select count(*) from category) / limit_in);
SELECT *
FROM category LIMIT limit_in
offset offset_page;
end
//
DELIMITER ;

DELIMITER
//
create procedure PROC_CATEGORY_CHANGE_STATUS(id_in int)
begin
update category
set status = status ^ 1
where id = id_in;
end
//
DELIMITER ;



DELIMITER
//
create procedure PROC_CATEGORY_FIND_PARENT()
begin
select *
from category
order by id limit 3;
end
//
DELIMITER ;

DELIMITER
//

DELIMITER //

CREATE PROCEDURE PROC_CATEGORY_PARENT()
BEGIN
SELECT *
FROM category
WHERE parent_id IS NULL;
END
//

DELIMITER ;

DELIMITER
//
create procedure PROC_CATEGORY_PAGINATION(IN limit_in int, IN current_page int, OUT total_page int)
begin
    DECLARE
offset_page int;
    SET
offset_page = (current_page - 1) * limit_in;
    SET
total_page = CEIL((SELECT count(*) from category) / limit_in);
select *
from category LIMIT limit_in
offset offset_page;
end
//
DELIMITER ;
select *
from category;

DELIMITER
//
create procedure PROC_CATEGORY_COUNT_BY_STATUS(IN status_in bit, OUT cate_count int)
begin
    if
(status_in != 0 and status_in != 1) then
        set cate_count = (select count(*) from category);
else
        set cate_count = (select count(*) from category where status = status_in);
end if;

end
//
DELIMITER ;

DELIMITER
//
create procedure PROC_CATEGORY_FIND_BY_NAME(IN name_in varchar (100))
begin
select *
from category
where LCASE(name) like
      concat('%', LCASE(name_in), '%')
   or id like concat('%', LCASE(name_in), '%');
end
//
DELIMITER ;

DELIMITER
//
CREATE PROCEDURE PROC_CATEGORY_FIND_BY_PARENT_ID(IN parent_id_in int)
begin
SELECT *
FROM category
WHERE parent_id = parent_id_in;
end
//
DELIMITER ;