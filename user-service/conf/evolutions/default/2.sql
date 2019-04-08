# --- Sample dataset

# --- !Ups

insert into reference (id) values (1);
insert into reference (id) values (2);
insert into reference (id) values (3);
insert into reference (id) values (4);
insert into reference (id) values (5);
insert into user (id, name) values (30, 'client name');
insert into reference_user (reference_id, user_id) values (1, 30);
insert into reference_user (reference_id, user_id) values (2, 30);


# --- !Downs

delete from reference;
delete from user;
delete from reference_user;
