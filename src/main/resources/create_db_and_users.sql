drop database  if exists pet_store;
create database pet_store;
drop user if exists 'pet_store_admin'@'%';
drop role if exists RW_USERS@'%';

create role RW_USERS@'%';
grant all privileges on pet_store.* to RW_USERS@'%';

create user 'pet_store_admin'@'%' identified by 'store_admin';
grant 'RW_USERS'@'%' to 'pet_store_admin'@'%';
GRANT all privileges on pet_store.* TO 'pet_store_admin'@'%';
