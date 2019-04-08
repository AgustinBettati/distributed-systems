
# --- !Ups

create table reference (
  id                            bigint auto_increment not null,
  constraint pk_reference primary key (id)
);

create table reference_user (
  reference_id                    bigint not null,
  user_id                       bigint not null,
  constraint pk_reference_user primary key (reference_id,user_id)
);

create table user (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  constraint pk_user primary key (id)
);

create index ix_reference_user_reference on reference_user (reference_id);
alter table reference_user add constraint fk_reference_user_reference foreign key (reference_id) references reference (id) on delete restrict on update restrict;

create index ix_reference_user_user on reference_user (user_id);
alter table reference_user add constraint fk_reference_user_user foreign key (user_id) references user (id) on delete restrict on update restrict;


# --- !Downs

alter table reference_user drop constraint if exists fk_reference_user_reference;
drop index if exists ix_reference_user_reference;

alter table reference_user drop constraint if exists fk_reference_user_user;
drop index if exists ix_reference_user_user;

drop table if exists reference;

drop table if exists reference_user;

drop table if exists user;

