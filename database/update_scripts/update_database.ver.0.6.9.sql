alter table assembly drop column assemblied;
/******************************************************************************************/
create table supplier_type(
  supplier_type_id int identity(1,1) not null primary key,
  supplier_type_name nvarchar(255) not null,
  description nvarchar(255)
);

/******************************************************************************************/
insert into supplier_type(supplier_type_name) values('Transport');
insert into supplier_type(supplier_type_name) values('Montering');
/******************************************************************************************/

alter table supplier add supplier_type_id int references supplier_type(supplier_type_id);
alter table supplier add address nvarchar(255);
alter table supplier add postal_code nvarchar(5);
alter table supplier add post_office nvarchar(255);
alter table supplier add fax nvarchar(255);

/******************************************************************************************/

create table employee_type(
  employee_type_id int identity(1,1) not null primary key,
  employee_type_name nvarchar(255) not null,
  employee_type_description nvarchar(255)
);
/******************************************************************************************/
create table employee(
  employee_id int identity(1,1) not null primary key,
  first_name nvarchar(255) not null,
  last_name nvarchar(255) not null,
  phone nvarchar(20),
  employee_type_id int references employee_type(employee_type_id),
  supplier_id int not null references supplier(supplier_id)
);
/******************************************************************************************/
alter table transport add supplier_id int references supplier(supplier_id);
alter table transport add employee_id int references employee(employee_id);
alter table transport add load_time nvarchar(10);

/****************************************************************************************************/
ALTER VIEW [dbo].[transport_week_v]
AS
SELECT     COUNT(Order_id) AS order_count, 
	   SUM(ISNULL(Do_Assembly, 0)) AS assemblied, 
	   transport.transport_year AS order_sent_year, 
	   transport.transport_week AS order_sent_week, 
	   SUM(dbo.get_customer_cost_for_type(Order_id, N'garasje')) AS garage_cost, 
	   SUM(dbo.get_customer_cost_for_type(Order_id,N'frakt')) AS transport_cost, 
	   SUM(dbo.get_customer_cost_for_type(Order_id, N'montering')) AS assembly_cost
FROM         dbo.Customer_order,transport
WHERE     (customer_order.Sent IS NOT NULL) and customer_order.transport_id=transport.transport_id
GROUP BY transport.transport_year, transport.transport_week