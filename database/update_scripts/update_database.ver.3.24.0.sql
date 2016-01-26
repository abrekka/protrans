CREATE VIEW [dbo].[areal_v]
AS
SELECT OrdNo, RIGHT(OrdNo, LEN(OrdNo) - 3) AS order_nr, CAST(LUdef0 AS int) AS lengde, CAST(LUdef9 AS int) AS bredde, (CAST(LUdef0 AS float) / 100) * (CAST(LUdef9 AS float) 
                  / 100) AS areal
FROM     Intelle_Ordre.dbo.wOrdLn
WHERE  (ProdNo IN
                      (SELECT ProdNo
                       FROM      F0100.dbo.Prod
                       WHERE   (PrCatNo2 = 1)))


/*******************************************************************************************************************

ALTER VIEW [dbo].[sales_v]
AS
SELECT DISTINCT 
SuperofficeCRM7.crm7.SALE.sale_id, SuperofficeCRM7.crm7.SALE.number1 AS order_nr, 
SuperofficeCRM7.crm7.SALE.probability, ISNULL(Intelle_Ordre.dbo.wOrd.DeptNo, F0100.dbo.Ord.R1) AS product_area_nr, 
SuperofficeCRM7.crm7.SALE.saledate, SuperofficeCRM7.crm7.SALE.registered, SuperofficeCRM7.crm7.CONTACT.number2 AS customer_nr, 
SuperofficeCRM7.crm7.CONTACT.name AS customer_name, 
SuperofficeCRM7.crm7.ADDRESS.address1 AS delivery_address, Intelle_Ordre.dbo.wOrd.DelZip AS postal_code, 
SuperofficeCRM7.crm7.ADDRESS.city AS post_office, 
SuperofficeCRM7.crm7.UDSALESMALL.double05 AS own_production_cost, 
SuperofficeCRM7.crm7.UDSALESMALL.double06 AS transport_cost, 
SuperofficeCRM7.crm7.UDSALESMALL.double07 AS assembly_cost, 
SuperofficeCRM7.crm7.UDSALESMALL.double08 AS yes_lines, 
SuperofficeCRM7.crm7.UDSALESMALL.double09 AS contribution_margin, 
SuperofficeCRM7.crm7.UDSALESMALL.long03 AS order_date, 
case when SuperofficeCRM7.crm7.COUNTRY.name = 'Norge' then dbo.county.county_name else SuperofficeCRM7.crm7.COUNTRY.name end as county_name,
ISNULL(Intelle_Ordre.dbo.wOrd.OurRef, F0100.dbo.Ord.OurRef) AS salesman, Intelle_Ordre.dbo.wOrd.SegmentNo,
areal_v.lengde,
areal_v.bredde,
areal_v.areal
FROM            SuperofficeCRM7.crm7.SALE INNER JOIN
SuperofficeCRM7.crm7.UDSALESMALL ON SuperofficeCRM7.crm7.SALE.userdef_id = SuperofficeCRM7.crm7.UDSALESMALL.udsalesmall_id LEFT OUTER JOIN
Intelle_Ordre.dbo.wOrd ON Intelle_Ordre.dbo.wOrd.SOSaleNo = SuperofficeCRM7.crm7.SALE.number1 LEFT OUTER JOIN
F0100.dbo.Ord ON SuperofficeCRM7.crm7.SALE.number1 = F0100.dbo.Ord.Inf6 LEFT OUTER JOIN
SuperofficeCRM7.crm7.ADDRESS ON SuperofficeCRM7.crm7.ADDRESS.owner_id = SuperofficeCRM7.crm7.SALE.contact_id LEFT OUTER JOIN
SuperofficeCRM7.crm7.CONTACT ON SuperofficeCRM7.crm7.CONTACT.contact_id = SuperofficeCRM7.crm7.SALE.contact_id LEFT OUTER JOIN
dbo.transport_cost ON Intelle_Ordre.dbo.wOrd.DelZip = dbo.transport_cost.postal_code LEFT OUTER JOIN
dbo.area ON dbo.transport_cost.area_code = dbo.area.area_code LEFT OUTER JOIN
dbo.county ON dbo.county.county_nr = dbo.area.county_nr left outer join
SuperofficeCRM7.crm7.COUNTRY on SuperofficeCRM7.crm7.CONTACT.country_id = SuperofficeCRM7.crm7.COUNTRY.country_id left outer join
areal_v on SuperofficeCRM7.crm7.SALE.number1=areal_v.order_nr
WHERE        (SuperofficeCRM7.crm7.ADDRESS.atype_idx = 2) AND (SuperofficeCRM7.crm7.SALE.probability > 0) AND (SuperofficeCRM7.crm7.CONTACT.number2 <> '743796') AND EXISTS
                             (SELECT        1 AS Expr1
                               FROM            dbo.product_area
                               WHERE        (product_area_nr = ISNULL(Intelle_Ordre.dbo.wOrd.DeptNo, F0100.dbo.Ord.R1)) AND (ISNULL(SuperofficeCRM7.crm7.UDSALESMALL.double05, 0) > own_production_cost_limit)) AND 
                         (NOT EXISTS
                             (SELECT        1 AS Expr1
                               FROM            SuperofficeCRM7.crm7.SALE AS S LEFT OUTER JOIN
                                                         SuperofficeCRM7.crm7.UDSALESMALL AS udsalesmall_1 ON SuperofficeCRM7.crm7.sale.userdef_id = udsalesmall_1.udsalesmall_id
                               WHERE        (udsalesmall_1.long06 IN (175, 176)) AND (S.probability < 91) AND (S.number1 = SuperofficeCRM7.crm7.sale.number1)))



/*************************************************************************************************************************************************
alter table[Protrans2].[dbo].sales_data_snapshot add lengde decimal(18,0);
alter table[Protrans2].[dbo].sales_data_snapshot add bredde decimal(18,0);
alter table[Protrans2].[dbo].sales_data_snapshot add areal decimal(18,2);

/**************************************************************************************************************************************************

ALTER PROCEDURE [dbo].[create_sales_v_snapshot] 
AS
BEGIN
      DECLARE @StartDateInt datetime
      DECLARE @EndDateInt datetime

      select @StartDateInt = DATEADD(wk,DATEDIFF(wk,0,dateadd(dd,-1,GETDATE())),0)
      select @EndDateInt = dateadd(hh,23,DATEADD(wk,DATEDIFF(wk,0,dateadd(dd,-1,GETDATE())),6))
    	SET NOCOUNT ON;

	insert into application_log(log_date,log_type,log_msg) values(getdate(),'DEBUG','create_sales_v_snapshot startdate:'+cast(@StartDateInt as nvarchar)+' enddate:'+cast(@EndDateInt as nvarchar));

    -- Insert statements for procedure here
	--SELECT , 
	insert into sales_data_snapshot(sale_id,order_nr,probability,product_area_nr,saledate,registered,customer_nr,customer_name,delivery_address,postal_code,post_office,own_production_cost,transport_cost,assembly_cost,yes_lines,contribution_margin,county_name,salesman,order_date,segmentno,lengde,bredde,areal) 
	(select sale_id,order_nr,probability,product_area_nr,saledate,registered,customer_nr,customer_name,delivery_address,postal_code,post_office,own_production_cost,transport_cost,assembly_cost,yes_lines,contribution_margin,county_name,salesman,order_date,segmentno,lengde,bredde,areal
	  from sales_v
	  where saledate between @StartDateInt and @EndDateInt)
END

/******************************************************************************************************************************************
ALTER VIEW [dbo].[order_segment_no_v]
AS
SELECT        dbo.Customer_order.Order_id, dbo.Customer_order.Order_nr, dbo.Customer_order.agreement_date, dbo.Customer_order.Order_date, dbo.Customer_order.Salesman, 
dbo.Construction_type.Name AS construction_type_name, F0100.dbo.Ord.R4 AS SegmentNo, dbo.own_assembly_cost_v.sum_cost_amount AS assembly_cost, dbo.Customer_order.Postal_code, dbo.Customer.Customer_nr, 
dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_full_name, dbo.own_production_cost_v.sum_cost_amount AS own_production, 
dbo.own_delivery_cost_v.sum_cost_amount AS delivery_cost, dbo.own_production_cost_internal_v.sum_cost_amount AS own_production_internal, dbo.own_jalinjer_cost_v.sum_cost_amount AS jalinjer, 
dbo.product_area.product_area_nr, dbo.product_area.product_area AS product_area_name, (CASE WHEN (dbo.own_production_cost_v.sum_cost_amount <> 0 AND 
dbo.own_production_cost_internal_v.sum_cost_amount <> 0) THEN dbo.own_production_cost_v.sum_cost_amount - dbo.own_production_cost_internal_v.sum_cost_amount ELSE 0 END) AS contribution_margin, 
(CASE WHEN (dbo.own_production_cost_v.sum_cost_amount <> 0 AND dbo.own_production_cost_internal_v.sum_cost_amount <> 0) 
THEN (dbo.own_production_cost_v.sum_cost_amount - dbo.own_production_cost_internal_v.sum_cost_amount) / dbo.own_production_cost_v.sum_cost_amount ELSE 0 END) AS contribution_rate,
case when SuperofficeCRM7.crm7.COUNTRY.name = 'Norge' or SuperofficeCRM7.crm7.COUNTRY.name is null then dbo.county.county_name else SuperofficeCRM7.crm7.COUNTRY.name end as county_name,
Customer_order.info,
LEFT(Customer_order.info,3) AS bredde,
RIGHT(Customer_order.info,3) AS lengde,
(cast(LEFT(Customer_order.info,3)as float)/100)*(cast(RIGHT(Customer_order.info,3)as float))/100 as areal
FROM            dbo.Customer_order LEFT OUTER JOIN
F0100.dbo.Ord ON CONVERT(Varchar(10), F0100.dbo.Ord.Inf6) = dbo.Customer_order.Order_nr INNER JOIN
dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id LEFT OUTER JOIN
                         dbo.own_assembly_cost_v ON dbo.Customer_order.Order_id = dbo.own_assembly_cost_v.order_id LEFT OUTER JOIN
                         dbo.own_production_cost_v ON dbo.Customer_order.Order_id = dbo.own_production_cost_v.order_id LEFT OUTER JOIN
                         dbo.own_delivery_cost_v ON dbo.Customer_order.Order_id = dbo.own_delivery_cost_v.order_id LEFT OUTER JOIN
                         dbo.own_production_cost_internal_v ON dbo.Customer_order.Order_id = dbo.own_production_cost_internal_v.order_id LEFT OUTER JOIN
                         dbo.own_jalinjer_cost_v ON dbo.Customer_order.Order_id = dbo.own_jalinjer_cost_v.order_id LEFT OUTER JOIN
                         dbo.transport_cost ON dbo.customer_order.Postal_code = dbo.transport_cost.postal_code LEFT OUTER JOIN
                         dbo.area ON dbo.transport_cost.area_code = dbo.area.area_code LEFT OUTER JOIN
                         dbo.county ON dbo.county.county_nr = dbo.area.county_nr left outer join
						 SuperofficeCRM7.crm7.SALE on SuperofficeCRM7.crm7.SALE.number1=dbo.Customer_order.Order_nr left OUTER JOIN
                         SuperofficeCRM7.crm7.CONTACT ON SuperofficeCRM7.crm7.CONTACT.contact_id = SuperofficeCRM7.crm7.SALE.contact_id left outer join
						 SuperofficeCRM7.crm7.COUNTRY on SuperofficeCRM7.crm7.CONTACT.country_id = SuperofficeCRM7.crm7.COUNTRY.country_id


/**************************************************************************************************************************
update [Protrans2].[dbo].[sales_data_snapshot] set bredde=cast([Protrans2].[dbo].[order_segment_no_v].bredde as int), lengde=cast([Protrans2].[dbo].[order_segment_no_v].lengde as int), areal=[Protrans2].[dbo].[order_segment_no_v].areal
from [Protrans2].[dbo].[sales_data_snapshot] inner join
[Protrans2].[dbo].[order_segment_no_v] on [Protrans2].[dbo].[sales_data_snapshot].order_nr=[Protrans2].[dbo].[order_segment_no_v].order_nr

/********************************************************************************************************************************************

update [Protrans2].[dbo].[sales_data_snapshot] set bredde=cast([Protrans2].[dbo].[areal_v].bredde as int), lengde=cast([Protrans2].[dbo].[areal_v].lengde as int), areal=[Protrans2].[dbo].[areal_v].areal
from [Protrans2].[dbo].[sales_data_snapshot] inner join
[Protrans2].[dbo].[areal_v] on [Protrans2].[dbo].[sales_data_snapshot].order_nr=[Protrans2].[dbo].[areal_v].order_nr