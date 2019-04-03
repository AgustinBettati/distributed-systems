# --- Sample dataset

# --- !Ups

insert into product (id) values ('112C8DD8-346B-426E-B06C-75BBA97DCD63');
insert into client (id, name) values ('112C8DD8-346B-426E-B06C-75BBA97DCD64', 'client name');
insert into product_client (product_id, client_id) values ('112C8DD8-346B-426E-B06C-75BBA97DCD63', '112C8DD8-346B-426E-B06C-75BBA97DCD64')


# --- !Downs

delete from product;
delete from client;
delete from product_client;
