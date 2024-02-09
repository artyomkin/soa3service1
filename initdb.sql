create table if not exists space_marines(
  id serial primary key,
  name varchar(255),
  coordinates_id int,
  creation_date date,
  creation_date_str varchar(255),
  health float,
  loyal boolean,
  height float,
  melee_weapon varchar(255),
  chapter_name varchar(255),
  starship_id int
);

create table if not exists chapters(
  name varchar(255) primary key,
  parent_legion varchar(255),
  world varchar(255)
);

create table if not exists coordinates(
  id serial primary key,
  x float,
  y int
);

create table if not exists starships(
  id int primary key,
  name varchar(255)
);
