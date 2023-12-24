drop database if exists pet_store;

create database if not exists pet_store;
use pet_store;

drop user if exists 'pet_store_admin'@'%';
drop role if exists RW_USERS@'%';

create user 'pet_store_admin'@'%' identified by 'store_admin';
-- Creating the user with the specified password
-- Assigning the role to the user
GRANT all privileges on pet_store.* TO 'pet_store_admin'@'%';

flush privileges;


