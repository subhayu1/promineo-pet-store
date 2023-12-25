drop database if exists pet_store;

create database if not exists pet_store;
use pet_store;

drop user if exists 'pet_store_admin'@'%';
drop role if exists RW_USERS@'%';
drop user if exists 'test_pet_store_user'@'%';
drop user if exists 'pet_store_user'@'%';

create user 'pet_store_admin'@'%' identified by 'store_admin';
-- Creating the user with the specified password
-- Assigning the role to the user
GRANT all privileges on pet_store.* TO 'pet_store_admin'@'%';

flush privileges;


