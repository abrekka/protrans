drop table transport_cost;
/*****************************************************************************************/
create table county(
  county_nr nvarchar(2) primary key,
  county_name nvarchar(100) not null
);
/*****************************************************************************************/
create table area(
  area_code nvarchar(4) primary key,
  area_name nvarchar(100) not null,
  county_nr nvarchar(2) not null references county(county_nr)
);
/*****************************************************************************************/
create table transport_cost(
  transport_cost_id int identity(1,1) not null,
  postal_code nvarchar(20) not null unique,
  place nvarchar(50),
  area_code nvarchar(4) not null references area(area_code),
  cost numeric,
  addition numeric,
  max_addition int,
  valid int
);