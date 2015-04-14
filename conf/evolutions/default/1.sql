# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "EXPENSES" ("AMOUNT" DOUBLE PRECISION NOT NULL,"DATE" DATE NOT NULL,"DESCRIPTION" VARCHAR(254) NOT NULL,"ID" SERIAL NOT NULL PRIMARY KEY);
create table "USERS" ("EMAIL" VARCHAR(254) NOT NULL,"PASSWORD" VARCHAR(254) NOT NULL,"ID" SERIAL NOT NULL PRIMARY KEY);

# --- !Downs

drop table "USERS";
drop table "EXPENSES";

