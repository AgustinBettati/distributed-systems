# --- Sample dataset

# --- !Ups

insert into product (id,name,description) values (  1,'un producto','Apple Inc.');
insert into product (id,name,description) values (  2,'un producto','Thinking Machines');
insert into product (id,name,description) values (  3,'un producto','RCA');
insert into product (id,name,description) values (  4,'un producto','Netronics');
insert into product (id,name,description) values (  5,'un producto','Tandy Corporation');

# --- !Downs

delete from product;
