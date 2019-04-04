# --- Sample dataset

# --- !Ups

insert into product (id) values (1);
insert into product (id) values (2);
insert into user (id, name) values (30, 'client name');
insert into product_user (product_id, user_id) values (1, 30);
insert into product_user (product_id, user_id) values (2, 30);


# --- !Downs

delete from product;
delete from user;
delete from product_user;
