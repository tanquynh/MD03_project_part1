
use
    md03_project_database;
create table coupons
(
    id       int primary key auto_increment,
    title    varchar(100) not null ,
    code     varchar(100) not null ,
    discount varchar(100) not null ,
    startDay DATETIME     not null,
    endDay   DATETIME     not null,
    quantity int          not null,
    status   bit(1) default 1

);
DELIMITER
//
create procedure PROC_COUPONS_FIND_ALL()
begin
    select *
    from coupons;
end
//
DELIMITER ;

DELIMITER
//
create procedure PROC_COUPONS_ADD(IN title_in varchar(100), code_in varchar(100), discount_in varchar(100),
                                  start_in DATETIME, end_in DATETIME, quantity_in int)
begin
    insert into coupons(title, code, discount, startDay, endDay, quantity)
    VALUES (title_in,
            code_in,
            discount_in,
            start_in,
            end_in, quantity_in);
end
//
DELIMITER ;
DELIMITER //
CREATE PROCEDURE PROC_COUPONS_EDIT(IN title_in varchar(100), code_in varchar(100), discount_in varchar(100),  start_in DATETIME, end_in DATETIME, quantity_in int, id_in int)
begin
    update Coupons
    set title    = title_in,
        code     = code_in,
        discount = discount_in,
        startDay = start_in,
        endDay = end_in,
        quantity = quantity_in
    where id = id_in;
end //
DELIMITER ;

DELIMITER
//
create procedure PROC_FIND_BY_ID(IN id_in int)
begin
    select *
    from Coupons
    where id = id_in;
end
//
DELIMITER ;
select *
             from Coupons
             where id = 2;

DELIMITER
//

CREATE PROCEDURE PROC_COUPONS_FIND(IN title_in VARCHAR(100), OUT total_page INT)
BEGIN
    SELECT *
    FROM Coupons
    WHERE LCASE(title) LIKE CONCAT('%', LCASE(title_in), '%')
       OR id LIKE CONCAT('%', LCASE(title_in), '%');

    SET
        total_page = CEIL((SELECT COUNT(*)
                           FROM Coupons
                           WHERE LCASE(title) LIKE CONCAT('%', LCASE(title_in), '%')
                              OR id LIKE CONCAT('%', LCASE(title_in), '%')) / 5);
END
//

DELIMITER ;


DELIMITER
//
create procedure PROC_COUPONS_PAGINATION(IN limit_in int, IN current_page int, OUT total_page int)
begin
    DECLARE
        offset_page int;
    SET
        offset_page = (current_page - 1) * limit_in;
    SET
        total_page = ceil((select count(*) from Coupons) / limit_in);
    SELECT *
    FROM Coupons
    LIMIT limit_in offset offset_page;
end
//
DELIMITER ;

DELIMITER
//
create procedure PROC_COUPONS_CHANGE_STATUS(id_in int)
begin
    update Coupons
    set status = status ^ 1
    where id = id_in;
end
//
DELIMITER ;



DELIMITER
//
create procedure PROC_COUPONS_PAGINATION(IN limit_in int, IN current_page int, OUT total_page int)
begin
    DECLARE
        offset_page int;
    SET
        offset_page = (current_page - 1) * limit_in;
    SET
        total_page = CEIL((SELECT count(*) from Coupons) / limit_in);
    select *
    from Coupons
    LIMIT limit_in offset offset_page;
end
//
DELIMITER ;
select *
from Coupons;

DELIMITER
//
create procedure PROC_COUPONS_COUNT_BY_STATUS(IN status_in bit, OUT cou_count int)
begin
    if
        (status_in != 0 and status_in != 1) then
        set cou_count = (select count(*) from Coupons);
    else
        set cou_count = (select count(*) from Coupons where status = status_in);
    end if;

end
//
DELIMITER ;


DELIMITER
//
create procedure PROC_COUPONS_FIND_BY_NAME(IN title_in varchar(100))
begin
    select *
    from Coupons
    where LCASE(title) like
          concat('%', LCASE(title_in), '%')
       or id like concat('%', LCASE(title_in), '%');
end
//
DELIMITER ;

