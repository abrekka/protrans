ALTER VIEW [dbo].[sales_v]
AS
select superoffice.crm5.sale.sale_id,
		superoffice.crm5.sale.number1 as order_nr,
		superoffice.crm5.sale.probability,
		superoffice.crm5.sale.group_idx,
		superoffice.crm5.sale.saledate,
		superoffice.crm5.sale.registered,
		superoffice.crm5.contact.number2 as customer_nr,
		superoffice.crm5.contact.name as customer_name,
		superoffice.crm5.address.address1 as delivery_address,
		superoffice.crm5.address.zipcode as postal_code,
		superoffice.crm5.address.city as post_office,
		superoffice.crm5.udsalesmall.double05 as own_production_cost,
		superoffice.crm5.udsalesmall.double06 as transport_cost,
		superoffice.crm5.udsalesmall.double07 as assembly_cost,
		superoffice.crm5.udsalesmall.double08 as yes_lines,
		superoffice.crm5.udsalesmall.double09 as contribution_margin,
		county.county_name,
		superoffice.crm5.person.firstname +' '+ superoffice.crm5.person.lastname as salesman
from superoffice.crm5.sale inner join
	superoffice.crm5.udsalesmall on superoffice.crm5.sale.userdef_id=
	superoffice.crm5.udsalesmall.udsalesmall_id left outer join 
	superoffice.crm5.address on superoffice.crm5.address.owner_id = superoffice.crm5.sale.contact_id left outer join
	superoffice.crm5.contact on superoffice.crm5.contact.contact_id=superoffice.crm5.sale.contact_id left outer join 
	superoffice.crm5.associate on superoffice.crm5.associate.associate_id=superoffice.crm5.contact.associate_id left outer join
	superoffice.crm5.person on superoffice.crm5.person.person_id = superoffice.crm5.associate.person_id left outer join 
	transport_cost on superoffice.crm5.address.zipcode = transport_cost.postal_code collate Danish_Norwegian_CI_AS left outer join 
	area on transport_cost.area_code = area.area_code left outer join 
	county on county.county_nr = area.county_nr 
where superoffice.crm5.address.atype_idx = 2

/***************************************************************************************
create table sales_data_snapshot(
  snapshot_id int identity(1,1) primary key,
  sale_id int,
  order_nr nvarchar(50),
  probability int,
  group_idx int,
  saledate int,
  registered int,
  customer_nr nvarchar(50),
  customer_name nvarchar(50),
  delivery_address nvarchar(50),
  postal_code nvarchar(50),
  post_office nvarchar(50),
  own_production_cost decimal(18,0),
  transport_cost decimal(18,0),
  assembly_cost decimal(18,0),
  yes_lines decimal(18,0),
  contribution_margin decimal(18,0),
  county_name nvarchar(100),
  salesman nvarchar(100)
);

/***************************************************************************************
CREATE PROCEDURE [dbo].[create_sales_v_snapshot] 
AS
BEGIN
  DECLARE @StartDateInt int
  DECLARE @EndDateInt int

  select @StartDateInt = datediff(ss,convert(datetime,'1970-01-01 01:00:00',20),DATEADD(wk,DATEDIFF(wk,0,GETDATE()),0))
  select @EndDateInt = datediff(ss,convert(datetime,'1970-01-01 01:00:00',20),DATEADD(wk,DATEDIFF(wk,0,GETDATE()),6))
--select @StartDateInt = datediff(ss,convert(datetime,'1970-01-01 01:00:00',20),DATEADD(wk,DATEDIFF(wk,0,convert(datetime,'2008-12-10 01:00:00',20)),0))
  --select @EndDateInt = datediff(ss,convert(datetime,'1970-01-01 01:00:00',20),DATEADD(wk,DATEDIFF(wk,0,convert(datetime,'2008-12-10 01:00:00',20)),6))
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	--SELECT , 
	insert into sales_data_snapshot(sale_id,order_nr,probability,group_idx,saledate,registered,customer_nr,customer_name,delivery_address,postal_code,post_office,own_production_cost,transport_cost,assembly_cost,yes_lines,contribution_margin,county_name,salesman) 
	(select sale_id,order_nr,probability,group_idx,saledate,registered,customer_nr,customer_name,delivery_address,postal_code,post_office,own_production_cost,transport_cost,assembly_cost,yes_lines,contribution_margin,county_name,salesman
	  from sales_v
	  where saledate between @StartDateInt and @EndDateInt)
END
/***************************************************************************************
alter table customer_order add registration_date datetime;