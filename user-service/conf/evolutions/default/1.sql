# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table client (
  id                            uuid not null,
  name                          varchar(255),
  constraint pk_client primary key (id)
);

create table product (
  id                            uuid not null,
  constraint pk_product primary key (id)
);

create table product_client (
  product_id                    uuid not null,
  client_id                     uuid not null,
  constraint pk_product_client primary key (product_id,client_id)
);

create index ix_product_client_product on product_client (product_id);
alter table product_client add constraint fk_product_client_product foreign key (product_id) references product (id) on delete restrict on update restrict;

create index ix_product_client_client on product_client (client_id);
alter table product_client add constraint fk_product_client_client foreign key (client_id) references client (id) on delete restrict on update restrict;


# --- !Downs

alter table product_client drop constraint if exists fk_product_client_product;
drop index if exists ix_product_client_product;

alter table product_client drop constraint if exists fk_product_client_client;
drop index if exists ix_product_client_client;

drop table if exists client;

drop table if exists product;

drop table if exists product_client;

