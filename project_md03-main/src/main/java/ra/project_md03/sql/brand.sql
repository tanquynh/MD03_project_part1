create
    database md03_project_database;
use
    md03_project_database;
create table brand
(
    id     int primary key auto_increment,
    name   varchar(100) not null unique,
    status bit(1) default 1
);
DELIMITER
//
create procedure PROC_BRAND_FIND_ALL()
begin
    select *
    from brand;
end
//
DELIMITER ;

DELIMITER
//
create procedure PROC_BRAND_ADD(IN name_in varchar(100))
begin
    insert into brand(name)
    values (name_in);
end
//
DELIMITER ;

DELIMITER
//
CREATE PROCEDURE PROC_BRAND_EDIT(IN name_in varchar(100), id_in int)
begin
    update brand
    set name = name_in
    where id = id_in;
end;
DELIMITER ;

DELIMITER
//
create procedure PROC_FIND_BY_ID(IN id_in int)
begin
    select *
    from brand
    where id = id_in;
end
//
DELIMITER ;


DELIMITER
//

CREATE PROCEDURE PROC_BRAND_FIND(IN name_in VARCHAR(100), OUT total_page INT)
BEGIN
    SELECT *
    FROM brand
    WHERE LCASE(name) LIKE CONCAT('%', LCASE(name_in), '%')
       OR id LIKE CONCAT('%', LCASE(name_in), '%');

    SET
        total_page = CEIL((SELECT COUNT(*)
                           FROM brand
                           WHERE LCASE(name) LIKE CONCAT('%', LCASE(name_in), '%')
                              OR id LIKE CONCAT('%', LCASE(name_in), '%')) / 5);
END
//

DELIMITER ;


DELIMITER
//
create procedure PROC_BRAND_PAGINATION(IN limit_in int, IN current_page int, OUT total_page int)
begin
    DECLARE
        offset_page int;
    SET
        offset_page = (current_page - 1) * limit_in;
    SET
        total_page = ceil((select count(*) from brand) / limit_in);
    SELECT *
    FROM brand
    LIMIT limit_in offset offset_page;
end
//
DELIMITER ;

DELIMITER
//
create procedure PROC_BRAND_CHANGE_STATUS(id_in int)
begin
    update BRAND
    set status = status ^ 1
    where id = id_in;
end
//
DELIMITER ;





DELIMITER
//
create procedure PROC_BRAND_COUNT_BY_STATUS(IN status_in bit, OUT cate_count int)
begin
    if
        (status_in != 0 and status_in != 1) then
        set cate_count = (select count(*) from BRAND);
    else
        set cate_count = (select count(*) from BRAND where status = status_in);
    end if;

end
//
DELIMITER ;

DELIMITER
//
create procedure PROC_BRAND_FIND_BY_NAME(IN name_in varchar(100))
begin
    select *
    from BRAND
    where LCASE(name) like
          concat('%', LCASE(name_in), '%')
       or id like concat('%', LCASE(name_in), '%');
end
//
DELIMITER ;

