alter table Customer_order add Change_date Datetime;

update Customer_order set Change_date = getDate();

alter table construction_type_article add number_of_items numeric(10,2);

alter table order_line add number_of_items numeric(10,2);

CREATE TABLE Cost_type(
  Cost_type_id int identity(1,1) primary key not null,
  Cost_type_name nvarchar(255) not null unique,
  Description nvarchar(255)
);

CREATE TABLE Cost_unit(
  Cost_unit_id int identity(1,1) primary key not null,
  Cost_unit_name nvarchar(255) not null,
  Description nvarchar(255)
);

CREATE TABLE Order_cost(
  Order_cost_id int identity(1,1) primary key not null,
  Order_id int not null references Customer_order(Order_id),
  Cost_type_id int not null references Cost_type(Cost_type_id),
  Cost_unit_id int references Cost_unit(Cost_unit_id),
  Cost_Amount numeric(10,2) not null,
  Incl_vat int
);