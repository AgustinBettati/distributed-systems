
# --- !Ups

create table product (
  id                            bigint auto_increment not null,
  constraint pk_product primary key (id)
);

create table product_user (
  product_id                    bigint not null,
  user_id                       bigint not null,
  constraint pk_product_user primary key (product_id,user_id)
);

create table user (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  constraint pk_user primary key (id)
);

create index ix_product_user_product on product_user (product_id);
alter table product_user add constraint fk_product_user_product foreign key (product_id) references product (id) on delete restrict on update restrict;

create index ix_product_user_user on product_user (user_id);
alter table product_user add constraint fk_product_user_user foreign key (user_id) references user (id) on delete restrict on update restrict;


# --- !Downs

alter table product_user drop constraint if exists fk_product_user_product;
drop index if exists ix_product_user_product;

alter table product_user drop constraint if exists fk_product_user_user;
drop index if exists ix_product_user_user;

drop table if exists product;

drop table if exists product_user;

drop table if exists user;

