# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table client (
  id                            uuid not null,
  name                          varchar(255),
  constraint pk_client primary key (id)
);


# --- !Downs

drop table if exists client;

