CREATE TABLE order_detail
(
    order_id   int,
    foreign key (order_id) references orders (id),
    product_id int,
    foreign key (product_id) references product (id),
    quantity   int,
    price      decimal
);