/*
-- DROP TABLES
drop table if exists account_connection;
drop table if exists  account_email;
drop table if exists  account_history;
drop table if exists  account_indiv;
drop table if exists  account_info;
drop table if exists  auth_key;
drop table if exists  numbering;
drop table if exists  flyway_schema_history;
*/

/*
-- DROP SCHEMA
drop schema if exists dev;

-- CREATE DATABASE
create schema dev;

SET SCHEMA dev;
*/

--------------------------------------------------------------------------------
-- Table : account_connection
create table account_connection (
  user_id character varying(255) not null
  , provider_id character varying(255) not null
  , provider_user_id character varying(255) not null
  , rank integer not null
  , display_name character varying(255)
  , profile_url character varying(512)
  , image_url character varying(512)
  , access_token character varying(1024) not null
  , secret character varying(255)
  , refresh_token character varying(255)
  , expire_time bigint
  , primary key (user_id, provider_id, provider_user_id)
);


create unique index account_connection_rank on account_connection(user_id,provider_id,rank);



--------------------------------------------------------------------------------
-- Table : account_email
create table account_email (
  account_id character(14) not null
  , email_addr character varying(60) not null
  , email_kb character(2) not null
  , email_type character(2) not null
  , create_dt timestamp(6) without time zone not null
  , update_dt timestamp(6) without time zone not null
  , primary key (account_id, email_kb)
);



--------------------------------------------------------------------------------
-- Table : account_history
create table account_history (
  account_id character(14) not null
  , useragent character varying(512) not null
  , ope character(3) not null
  , create_dt timestamp(6) without time zone not null
  , update_dt timestamp(6) without time zone not null
  , primary key (account_id)
);



--------------------------------------------------------------------------------
-- Table : account_indiv
create table account_indiv (
  account_id character(14) not null
  , fst_name character varying(15) not null
  , lst_name character varying(15) not null
  , create_dt timestamp(6) without time zone not null
  , update_dt timestamp(6) without time zone not null
  , primary key (account_id)
);



--------------------------------------------------------------------------------
-- Table : account_info
create table account_info (
  account_id character varying(14) not null
  , user_id character varying(10) not null
  , password character varying(256) not null
  , user_kb character(2) not null
  , enabled boolean not null
  , account_non_expired boolean not null
  , credentials_non_expired boolean not null
  , account_non_locked boolean not null
  , role_id character varying(128) not null
  , create_dt timestamp(6) without time zone not null
  , update_dt timestamp(6) without time zone not null
  , primary key (account_id)
);


create unique index account_info_user_id_key on account_info(user_id);



--------------------------------------------------------------------------------
-- Table : auth_key
create table auth_key (
  auth_key character varying(64) not null
  , account_id character(14) not null
  , auth_type character(3) not null
  , expire_dt timestamp(6) without time zone not null
  , create_dt timestamp(6) without time zone not null
  , update_dt timestamp(6) without time zone not null
  , primary key (auth_key)
);



--------------------------------------------------------------------------------
-- Table : numbering
create table numbering (
  num_key character(20) not null
  , num_issued bigint not null
  , num_max bigint not null
  , num_min bigint not null
  , num_digits smallint not null
  , prefix_char character(5) not null
  , create_dt timestamp(6) without time zone not null
  , update_dt timestamp(6) without time zone not null
  , primary key (num_key)
);


