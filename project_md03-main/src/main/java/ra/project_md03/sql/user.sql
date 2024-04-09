create database md03_project_database;
use md03_project_database;
CREATE TABLE user (
                      id       INT PRIMARY KEY AUTO_INCREMENT,
                      name     VARCHAR(100) NOT NULL,
                      email    VARCHAR(50)  NOT NULL UNIQUE,
                      address  TEXT         NOT NULL,
                      phone    VARCHAR(20)  NOT NULL,
                      role     BIT(1) DEFAULT 1,
                      status   BIT(1) DEFAULT 1,
                      avatar   VARCHAR(255) DEFAULT 'https://firebasestorage.googleapis.com/v0/b/project-md03-javaweb.appspot.com/o/human.jpg?alt=media&token=9eb4c0d8-74fa-433e-894d-c152119c288c',
                      password VARCHAR(255) NOT NULL
);
DELIMITER //
create procedure PROC_USER_FIND_ALL()
BEGIN
    SELECT * FROM user;
end //
DELIMITER  ;
DELIMITER //
CREATE PROCEDURE PROC_DELETE_USER_CART_ITEM(IN user_cart_id int)
begin
    DELETE FROM ITEM WHERE cart_id = user_cart_id;
end //
DELIMITER //
create procedure PROC_USER_ADD(IN name_in varchar(100), email_in varchar(50), address_in text, phone_in varchar(50),
                               password_in text)
begin
    INSERT INTO user(name, email, address, phone, password)
    values (name_in, email_in, address_in, phone_in,
            password_in);
end //
DELIMITER ;
call PROC_USER_ADD('Hai', 'Hai@gmail.com', 'Da Nang', 0787646399, 'Hai12@');

DELIMITER //
create procedure PROC_USER_EDIT(IN name_in varchar(100), email_in varchar(50), address_in text, phone_in varchar(50),
                                avatar_in text, id_in int)
begin
    update user
    SET name    = name_in,
        email   = email_in,
        address = address_in,
        phone   = phone_in,
        avatar  = avatar_in
    where id = id_in;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE PROC_USER_FIND_BY_ID(IN id_in int)
begin
    SELECT * from user where id = id_in;
end //
DELIMITER ;

DELIMITER //
create procedure PROC_USER_PAGINATION(IN limit_in int, current_page int, OUT total_page int)
begin
    DECLARE offset_page int;
    set offset_page = (current_page - 1) * limit_in;
    set total_page = ceil((select count(*) from user) / limit_in);
    select * from user limit limit_in offset offset_page;
end //
DELIMITER ;

call PROC_USER_PAGINATION(1, 1, @total_page);
select @total_page;

DELIMITER //
create procedure PROC_USER_CHANGE_STATUS(IN id_in int)
begin
    DECLARE user_role int;
    DECLARE user_status int;
    SELECT role, status INTO user_role, user_status from user where id = id_in;
    if user_role = 1 then
        update user set status = status ^ 1 where id = id_in;
    end if;

end //
DELIMITER ;

DELIMITER //
create procedure PROC_USER_SET_ROLE(IN id_in int)
begin
    DECLARE user_role int;
    DECLARE user_status int;
    SELECT role, status INTO user_role, user_status from user where id = id_in;
    if user_role = 1 then
        update user set role = role ^ 1 where id = id_in;
    end if;
end //
DELIMITER  ;

DELIMITER //
create procedure PROC_USER_FIND_BY_MAIL(IN mail varchar(50))
begin
    select *from user where email = mail;
end //
DELIMITER ;

DELIMITER //
create procedure PROC_USER_COUNT_BY_STATUS(IN status_in bit, OUT count_user int)
begin
    if (status_in != 0 and status_in != 1) then
        set count_user = (select count(*) from user);
    else
        set count_user = (select count(*) from user where status = status_in);
    end if;

end //
DELIMITER ;

DELIMITER //
create procedure PROC_USER_FIND(IN name_in varchar(255), OUT total_user int, OUT total_page int)
begin
    SELECT *
    from user
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

call PROC_USER_FIND('tu', @total_user, @total_page);
select @total_page;



DELIMITER //
CREATE PROCEDURE PROC_USER_CHANGE_PASSWORD(IN password_in varchar(255), id_in int)
begin
    UPDATE user set password = password_in where id = id_in;
end //
DELIMITER ;