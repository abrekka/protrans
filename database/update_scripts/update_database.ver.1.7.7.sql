ALTER VIEW [dbo].[sales_v]
AS
SELECT superoffice.crm5.sale.sale_id, superoffice.crm5.sale.number1 AS order_nr, superoffice.crm5.sale.probability, 
ISNULL(Intelle_Ordre.dbo.word.deptno,F0100.DBO.ORD.R1) as product_area_nr, 
superoffice.crm5.sale.saledate, superoffice.crm5.sale.registered, superoffice.crm5.contact.number2 AS customer_nr, 
superoffice.crm5.contact.name AS customer_name, superoffice.crm5.address.address1 AS delivery_address, 
superoffice.crm5.address.zipcode AS postal_code, superoffice.crm5.address.city AS post_office, 
superoffice.crm5.udsalesmall.double05 AS own_production_cost, superoffice.crm5.udsalesmall.double06 AS transport_cost, 
superoffice.crm5.udsalesmall.double07 AS assembly_cost, superoffice.crm5.udsalesmall.double08 AS yes_lines, 
superoffice.crm5.udsalesmall.double09 AS contribution_margin, superoffice.crm5.udsalesmall.long03 AS order_date, dbo.county.county_name, 
ISNULL(Intelle_Ordre.dbo.word.OurRef,F0100.DBO.ORD.ourref) as salesman
FROM superoffice.crm5.sale INNER JOIN 
superoffice.crm5.udsalesmall ON superoffice.crm5.sale.userdef_id = superoffice.crm5.udsalesmall.udsalesmall_id LEFT OUTER JOIN 
Intelle_Ordre.dbo.Word on Intelle_Ordre.dbo.word.sosaleno = superoffice.crm5.sale.number1 LEFT OUTER JOIN 
F0100.DBO.ORD ON superoffice.crm5.sale.number1 = F0100.DBO.ORD.INF6 LEFT OUTER JOIN 
superoffice.crm5.address ON superoffice.crm5.address.owner_id = superoffice.crm5.sale.contact_id LEFT OUTER JOIN 
superoffice.crm5.contact ON superoffice.crm5.contact.contact_id = superoffice.crm5.sale.contact_id LEFT OUTER JOIN 
dbo.transport_cost ON superoffice.crm5.address.zipcode = dbo.transport_cost.postal_code collate Danish_Norwegian_CI_AS LEFT OUTER JOIN 
dbo.area ON dbo.transport_cost.area_code = dbo.area.area_code LEFT OUTER JOIN 
dbo.county ON dbo.county.county_nr = dbo.area.county_nr 
WHERE ( superoffice.crm5.address.atype_idx = 2) AND (superoffice.crm5.sale.probability > 0)
/******************************************************************************************************
alter table sales_data_snapshot add product_area_nr int;
/******************************************************************************************************
update sales_data_snapshot set product_area_nr=200 where group_idx in(2,15);
/******************************************************************************************************
update sales_data_snapshot set product_area_nr=100 where group_idx in(5);
/******************************************************************************************************
update sales_data_snapshot set product_area_nr=300 where group_idx in(4);
/******************************************************************************************************
update sales_data_snapshot set product_area_nr=400 where group_idx in(6);
/******************************************************************************************************
alter table sales_data_snapshot drop column group_idx;
/******************************************************************************************************
ALTER PROCEDURE [dbo].[create_sales_v_snapshot] 
AS
BEGIN
  DECLARE @StartDateInt int
  DECLARE @EndDateInt int

  select @StartDateInt = datediff(ss,convert(datetime,'1970-01-01 01:00:00',20),DATEADD(wk,DATEDIFF(wk,0,dateadd(dd,-1,GETDATE())),0))
  select @EndDateInt = datediff(ss,convert(datetime,'1970-01-01 01:00:00',20),dateadd(hh,23,DATEADD(wk,DATEDIFF(wk,0,dateadd(dd,-1,GETDATE())),6)))
--select @StartDateInt = datediff(ss,convert(datetime,'1970-01-01 01:00:00',20),DATEADD(wk,DATEDIFF(wk,0,convert(datetime,'2008-12-10 01:00:00',20)),0))
  --select @EndDateInt = datediff(ss,convert(datetime,'1970-01-01 01:00:00',20),DATEADD(wk,DATEDIFF(wk,0,convert(datetime,'2008-12-10 01:00:00',20)),6))
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	insert into application_log(log_date,log_type,log_msg) values(getdate(),'DEBUG','create_sales_v_snapshot startdate:'+cast(@StartDateInt as nvarchar)+' enddate:'+cast(@EndDateInt as nvarchar));

    -- Insert statements for procedure here
	--SELECT , 
	insert into sales_data_snapshot(sale_id,order_nr,probability,product_area_nr,saledate,registered,customer_nr,customer_name,delivery_address,postal_code,post_office,own_production_cost,transport_cost,assembly_cost,yes_lines,contribution_margin,county_name,salesman,order_date) 
	(select sale_id,order_nr,probability,product_area_nr,saledate,registered,customer_nr,customer_name,delivery_address,postal_code,post_office,own_production_cost,transport_cost,assembly_cost,yes_lines,contribution_margin,county_name,salesman,order_date
	  from sales_v
	  where saledate between @StartDateInt and @EndDateInt)
END
/******************************************************************************************************
alter table product_area add product_area_nr nvarchar(100);
/******************************************************************************************************
update product_area set product_area_nr='100' where product_area='Garasje rekke';
/******************************************************************************************************
update product_area set product_area_nr='200' where product_area='Garasje villa';
/******************************************************************************************************
update product_area set product_area_nr='300' where product_area='Takstol';
/******************************************************************************************************
update product_area set product_area_nr='400' where product_area='Byggelement';
/******************************************************************************************************
alter table product_area drop column group_idx;
/******************************************************************************************************
ALTER view [dbo].[drawer_v] 
as
Select 
superoffice.crm5.contact.number2 as customer_nr, 
	superoffice.crm5.sale.number1 as order_nr, 
	--superoffice.crm5.sale.group_idx, 
	ISNULL(Intelle_Ordre.dbo.word.deptno,F0100.DBO.ORD.R1) as product_area_nr,
	superoffice.crm5.contact.name as customer_name, 
	superoffice.crm5.doctmpl.name as category_name, 
	superoffice.crm5.appointment.registered, 
	superoffice.crm5.udsalesmall.double05 as own_production, 
	superoffice.crm5.associate.name as drawer_name,
	superoffice.crm5.document.document_id
from superoffice.crm5.appointment inner join 
	superoffice.crm5.doctmpl on superoffice.crm5.appointment.task_idx = superoffice.crm5.doctmpl.doctmpl_id inner join 
	superoffice.crm5.contact on superoffice.crm5.appointment.contact_id = superoffice.crm5.contact.contact_id inner join 
	superoffice.crm5.sale on superoffice.crm5.contact.contact_id = superoffice.crm5.sale.contact_id inner join 
	superoffice.crm5.document on superoffice.crm5.document.document_id = superoffice.crm5.appointment.document_id inner join 
	superoffice.crm5.relations on (superoffice.crm5.relations.source_record = superoffice.crm5.document.document_id and 
	superoffice.crm5.relations.destination_record = superoffice.crm5.sale.sale_id) inner join 
	superoffice.crm5.associate on superoffice.crm5.associate.associate_id = superoffice.crm5.appointment.associate_id inner join 
	superoffice.crm5.udsalesmall on superoffice.crm5.udsalesmall.udsalesmall_id = superoffice.crm5.sale.userdef_id LEFT OUTER JOIN 
Intelle_Ordre.dbo.Word on Intelle_Ordre.dbo.word.sosaleno = superoffice.crm5.sale.number1 LEFT OUTER JOIN 
F0100.DBO.ORD ON superoffice.crm5.sale.number1 = F0100.DBO.ORD.INF6 
where superoffice.crm5.doctmpl.doctmpl_id in (102,103,153,154) and 
	superoffice.crm5.relations.source_table = 10 and 
	superoffice.crm5.relations.destination_table = 13 ;
/******************************************************************************************************
ALTER view [dbo].[import_order_v] 
as
select superoffice.crm5.sale.sale_id,
		superoffice.crm5.sale.number1,
		superoffice.crm5.sale.amount,
		superoffice.crm5.sale.saledate,
		superoffice.crm5.sale.userdef_id,
		superoffice.crm5.sale.registered,
		superoffice.crm5.sale.probability,
		superoffice.crm5.sale.contact_id,
		ISNULL(Intelle_Ordre.dbo.word.deptno,F0100.DBO.ORD.R1) as product_area_nr
	from superoffice.crm5.sale LEFT OUTER JOIN 
Intelle_Ordre.dbo.Word on Intelle_Ordre.dbo.word.sosaleno = superoffice.crm5.sale.number1 LEFT OUTER JOIN 
F0100.DBO.ORD ON superoffice.crm5.sale.number1 = F0100.DBO.ORD.INF6 
	where superoffice.crm5.sale.saledate > 1230768000 and 
	      superoffice.crm5.sale.probability=100 and
	      not exists(select 1 
				from customer_order 
				where customer_order.order_nr=superoffice.crm5.sale.number1 collate Danish_Norwegian_CI_AS) and 
	      exists(select 1 
			from F0100.dbo.ord 
			where f0100.dbo.ord.inf6=superoffice.crm5.sale.number1)
/******************************************************************************************************
