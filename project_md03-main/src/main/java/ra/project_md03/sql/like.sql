use
    md03_project_database;
create table LikeTable
(
    id        int primary key auto_increment,
    productId int,
    foreign key (productId) references product (id),
    userId    int not null,
    foreign key (userId) references user (id)
);
DELIMITER //

CREATE PROCEDURE GET_ALL_LIKE()
BEGIN
    SELECT * FROM LikeTable;
END //

DELIMITER ;
DELIMITER //

CREATE PROCEDURE AddNewLike(
    IN in_productId INT,
    IN in_userId INT
)
BEGIN
    INSERT INTO LikeTable (productId, userId)
    VALUES (in_productId, in_userId);

END //

DELIMITER ;
DELIMITER //

CREATE PROCEDURE DeleteLike(
    IN in_likeId INT
)
BEGIN
    DELETE FROM LikeTable WHERE id = in_likeId;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE CountLikesByProduct(
    IN in_productId INT,
    OUT out_likeCount INT
)
BEGIN

    SET out_likeCount = 0;
    SELECT COUNT(*)
    INTO out_likeCount
    FROM LikeTable
    WHERE productId = in_productId;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE FindLikeByUserAndProduct(
    IN in_userId INT,
    IN in_productId INT,
    OUT out_likeId INT
)
BEGIN
    -- Khởi tạo biến out_likeId để lưu trữ ID của lượt thích
    SET out_likeId = NULL;
    -- Tìm lượt thích dựa trên userId và productId
    SELECT id INTO out_likeId
    FROM LikeTable
    WHERE userId = in_userId AND productId = in_productId
    LIMIT 1;
END //

DELIMITER ;
