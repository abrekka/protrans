create table transport_cost(
  transport_cost_id int identity(1,1) primary key,
  postal_code nvarchar(10) not null,
  place nvarchar(255),
  address nvarchar(255),
  description nvarchar(255),
  area_code nvarchar(100),
  area nvarchar(100),
  cost numeric not null,
  max_addition int,
  valid int
);
/***********************************************************************************************/
create table transport_cost_addition(
  transport_cost_addition_id int identity(1,1) primary key,
  description nvarchar(100) not null unique,
  basis nvarchar(100) not null,
  addition numeric not null,
  metric nvarchar(50) not null,
  transport_basis int not null,
  member_of_max_additions int not null
);
/***********************************************************************************************/
insert into cost_type(cost_type_name,description) values('Fraktkost','Beregnet fraktkostnad');
/***********************************************************************************************/
create table transport_cost_basis(
  transport_cost_basis_id int identity(1,1) primary key,
  supplier_id int not null references supplier(supplier_id),
  periode nvarchar(50) not null,
  invoice_nr nvarchar(50)
);
/***********************************************************************************************/
alter table order_cost add transport_cost_basis_id int references transport_cost_basis(transport_cost_basis_id);
/***********************************************************************************************/
alter table customer_order alter column cached_comment nvarchar(4000);
