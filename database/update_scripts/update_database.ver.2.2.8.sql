ALTER VIEW [dbo].[import_order_prob_100_v]
AS
SELECT     superoffice.crm5.sale.sale_id, superoffice.crm5.sale.number1, superoffice.crm5.sale.amount, superoffice.crm5.sale.saledate, 
                      superoffice.crm5.sale.userdef_id, superoffice.crm5.sale.registered, superoffice.crm5.sale.probability, superoffice.crm5.sale.contact_id, 
                      F0100.dbo.Ord.R1 AS product_area_nr, F0100.dbo.Ord.DelAd3 + ' ' + F0100.dbo.Ord.DelAd4 AS delivery_address, 
                      F0100.dbo.Ord.DelPNo AS postal_code, F0100.dbo.Ord.DelPArea AS postoffice, F0100.dbo.Ord.OurRef AS sales_man, 
                      F0100.dbo.Ord.Inf3 AS telephonenr_site
FROM         superoffice.crm5.sale INNER JOIN
                      F0100.dbo.Ord ON superoffice.crm5.sale.number1 = F0100.dbo.Ord.Inf6
WHERE     (F0100.dbo.Ord.R1 IN (select product_area_nr from product_area)) AND (superoffice.crm5.sale.saledate > 1257811200) AND (superoffice.crm5.sale.probability = 100) AND 
                      (superoffice.crm5.sale.number1 <> '') AND (NOT EXISTS
                          (SELECT     1 AS Expr1
                            FROM          dbo.Customer_order
                            WHERE      (Order_nr collate Danish_Norwegian_CI_AS = superoffice.crm5.sale.number1)))

/***************************************************************************************************************************************************************************************
CREATE FUNCTION [dbo].[GetISOWeekNumberFromDate] 
(
	-- Add the parameters for the function here
	@dtDate datetime
)
RETURNS int with RETURNS null on null INPUT
/*
** Return the ISO week of the year regardless of the DATEFIRST session setting.
*/
as
begin

declare @intISOWeekdayNumber int
declare @dtThisThursday datetime
declare @dtFirstOfThisThursdaysYear datetime
declare @intISOWeekdayNumberOfFirstOfThisThursdaysYear int
declare @dtFirstThursdayOfYear datetime
declare @intISOWeekNumber int

   -- Get the ISO week day number (Monday = 1) for our date.
   set @intISOWeekdayNumber = (((datepart(dw, @dtDate) - 1) + (@@datefirst - 1)) % 7) + 1

   -- Get the date of the Thursday in this ISO week.
   set @dtThisThursday = dateadd(d,(4 - @intISOWeekdayNumber),@dtDate)

   -- Get the date of the 1st January for 'this Thursdays' year.
   set @dtFirstOfThisThursdaysYear = cast(cast(year(@dtThisThursday) as char(4)) + '-01-01' as datetime)

   set @intISOWeekdayNumberOfFirstOfThisThursdaysYear = (((datepart(dw, @dtFirstOfThisThursdaysYear) - 1) + (@@datefirst - 1)) % 7) + 1

   -- Get the date of the first Thursday in 'this Thursdays' year.
   -- The year of which the ISO week is a part is the year of this date.     
   if (@intISOWeekdayNumberOfFirstOfThisThursdaysYear in (1,2,3,4))
      set @dtFirstThursdayOfYear = dateadd(d,(4 - @intISOWeekdayNumberOfFirstOfThisThursdaysYear),@dtFirstOfThisThursdaysYear)
   else
      set @dtFirstThursdayOfYear = dateadd(d,(4 - @intISOWeekdayNumberOfFirstOfThisThursdaysYear + 7),@dtFirstOfThisThursdaysYear)

   -- Work out how many weeks from the first Thursday to this Thursday.
   set @intISOWeekNumber = datediff(d,@dtFirstThursdayOfYear,@dtThisThursday)/7+1
   
   return @intISOWeekNumber

end

/***************************************************************************************************************************************************************************************
ALTER VIEW [dbo].[own_production_v]
AS
SELECT     dbo.Customer_order.Order_id, dbo.Customer_order.packed_by, dbo.Transport.Transport_Year, dbo.Transport.Transport_Week, 
                      dbo.Customer.Customer_nr, dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_name, 
                      dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Egenproduksjon') AS garage_value, DATEPART(yyyy, 
                      dbo.Customer_order.order_ready) AS order_ready_year, dbo.GetISOWeekNumberFromDate(dbo.Customer_order.order_ready) AS order_ready_week, DATEPART(dw, 
                      dbo.Customer_order.order_ready) AS order_ready_day, dbo.Customer_order.packlist_ready, dbo.Customer_order.Invoice_date, 
                      dbo.product_area.product_area, dbo.Customer_order.Sent, dbo.Customer_order.Order_nr, dbo.product_area_group.product_area_group_name
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
/***************************************************************************************************************************************************************************************
ALTER VIEW [dbo].[assemblied_v]
AS
SELECT     COUNT(dbo.Customer_order.Order_id) AS order_count, 
dbo.product_area.product_area, DATEPART(yyyy, dbo.[Assembly].assemblied_date) AS assemblied_year, 
dbo.GetISOWeekNumberFromDate(dbo.[Assembly].assemblied_date) AS assemblied_week, 
SUM(dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Frakt')) AS delivery_cost, 
SUM(dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Montering')) AS assembly_cost, 
SUM(dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Egenproduksjon')) AS garage_cost
FROM         dbo.Customer_order INNER JOIN
                      dbo.[Assembly] ON dbo.Customer_order.Order_id = dbo.[Assembly].Order_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id
WHERE     (dbo.[Assembly].assemblied_date IS NOT NULL)
GROUP BY dbo.product_area.product_area, DATEPART(yyyy, dbo.[Assembly].assemblied_date), dbo.GetISOWeekNumberFromDate(dbo.[Assembly].assemblied_date)
/***************************************************************************************************************************************************************************************
ALTER VIEW [dbo].[assembly_overdue_v]
AS
SELECT     distinct Assembly_year AS assembly_year, 
		Assembly_week AS assembly_week
FROM         dbo.[Assembly]
WHERE     CAST(Assembly_year AS nvarchar(10)) + right('00' + CAST(Assembly_week as varchar(2)), 2) 
			< CAST(DATEPART(yyyy, GETDATE()) AS nvarchar(10)) 
                      + right('00' + CAST(dbo.GetISOWeekNumberFromDate(GETDATE()) AS nvarchar(10)), 2) AND 
(assemblied_date IS NULL and 
isnull(assembly.inactive,0) =0)
and not exists(select 1 
				from customer_order 
				where customer_order.order_id=assembly.order_id and 
						(customer_order.do_assembly=0 or
						customer_order.do_assembly is null))
and not exists(select 1 
				from deviation 
				where deviation.deviation_id=assembly.deviation_id and 
						(deviation.do_assembly=0 or
						deviation.do_assembly is null))

/***************************************************************************************************************************************************************************************
ALTER VIEW [dbo].[count_agreement_v]
AS
SELECT     COUNT(Order_id) AS order_count, 
			product_area.product_area,
			DATEPART(yyyy, agreement_date) AS agreement_year, 
			dbo.GetISOWeekNumberFromDate(agreement_date) AS agreement_week
FROM         dbo.Customer_order,product_area,construction_type
where agreement_date is not null and
		customer_order.product_area_id=product_area.product_area_id and 
		customer_order.construction_type_id=construction_type.construction_type_id and lower(construction_type.name) not like '%tilleggsordre%'
GROUP BY 
product_area.product_area,
DATEPART(yyyy, agreement_date), dbo.GetISOWeekNumberFromDate(agreement_date)
/***************************************************************************************************************************************************************************************
ALTER VIEW [dbo].[deviation_sum_v]
AS
SELECT     dbo.job_function.job_function_name, dbo.function_category.function_category_name, dbo.deviation_status.deviation_status_name, dbo.GetISOWeekNumberFromDate( 
                      dbo.deviation.registration_date) AS registration_week, DATEPART(yyyy, dbo.deviation.registration_date) AS registration_year, 
                      COUNT(dbo.deviation.deviation_id) AS number_of_deviations, SUM(dbo.get_deviation_cost(dbo.deviation.deviation_id, 1)) AS internal_cost, 
                      SUM(dbo.get_deviation_cost(dbo.deviation.deviation_id, 0)) AS external_cost, dbo.product_area.product_area
FROM         dbo.product_area RIGHT OUTER JOIN
                      dbo.deviation ON dbo.product_area.product_area_id = dbo.deviation.product_area_id LEFT OUTER JOIN
                      dbo.deviation_status ON dbo.deviation.deviation_status_id = dbo.deviation_status.deviation_status_id LEFT OUTER JOIN
                      dbo.function_category ON dbo.deviation.function_category_id = dbo.function_category.function_category_id LEFT OUTER JOIN
                      dbo.job_function ON dbo.deviation.deviation_function_id = dbo.job_function.job_function_id
GROUP BY dbo.job_function.job_function_name, dbo.function_category.function_category_name, dbo.deviation_status.deviation_status_name,dbo.GetISOWeekNumberFromDate( 
                      dbo.deviation.registration_date), DATEPART(yyyy, dbo.deviation.registration_date), dbo.product_area.product_area
/***************************************************************************************************************************************************************************************
ALTER VIEW [dbo].[deviation_v]
AS
SELECT     dbo.deviation.deviation_id, dbo.deviation.registration_date, dbo.deviation.user_name, dbo.deviation.customer_nr, dbo.deviation.customer_name, 
                      dbo.deviation.order_nr, dbo.product_area.product_area, dbo.deviation.responsible, job_function_own.job_function_name AS own_function, 
                      job_function_deviation.job_function_name AS deviation_function, dbo.function_category.function_category_name, 
                      dbo.deviation_status.deviation_status_name, dbo.GetISOWeekNumberFromDate(dbo.deviation.registration_date) AS registration_week, DATEPART(yyyy, 
                      dbo.deviation.registration_date) AS registration_year, dbo.get_deviation_cost(dbo.deviation.deviation_id, 1) AS internal_cost, 
                      dbo.get_deviation_cost(dbo.deviation.deviation_id, 0) AS external_cost
FROM         dbo.product_area RIGHT OUTER JOIN
                      dbo.deviation ON dbo.product_area.product_area_id = dbo.deviation.product_area_id LEFT OUTER JOIN
                      dbo.deviation_status ON dbo.deviation.deviation_status_id = dbo.deviation_status.deviation_status_id LEFT OUTER JOIN
                      dbo.function_category ON dbo.deviation.function_category_id = dbo.function_category.function_category_id LEFT OUTER JOIN
                      dbo.job_function AS job_function_deviation ON dbo.deviation.deviation_function_id = job_function_deviation.job_function_id LEFT OUTER JOIN
                      dbo.job_function AS job_function_own ON dbo.deviation.own_function_id = job_function_own.job_function_id
/***************************************************************************************************************************************************************************************                      
ALTER VIEW [dbo].[order_invoiced_amount_v]
AS
SELECT     count(Order_id) as order_count,
			product_area.product_area,
DATEPART(yyyy, Invoice_date) AS invoice_year, 
dbo.GetISOWeekNumberFromDate( Invoice_date) AS invoice_week, 
sum(isnull(dbo.Get_customer_cost(Order_id),0))
                      AS invoiced_amount
FROM         dbo.Customer_order,product_area
WHERE     (Invoice_date IS NOT NULL) and customer_order.product_area_id=product_area.product_area_id
group by 
product_area.product_area,
DATEPART(yyyy, Invoice_date) , 
dbo.GetISOWeekNumberFromDate( Invoice_date)
/***************************************************************************************************************************************************************************************                      
ALTER VIEW [dbo].[order_packlist_ready_v]
AS
SELECT     COUNT(Order_id) AS order_count, 
		product_area.product_area,
		DATEPART(yyyy, customer_order.packlist_ready) AS packlist_year, 
		dbo.GetISOWeekNumberFromDate(customer_order.packlist_ready) AS packlist_week,
		sum(dbo.get_customer_cost_for_type(order_id,'Egenproduksjon')) as customer_cost
FROM         dbo.Customer_order,product_area
WHERE     (customer_order.packlist_ready IS NOT NULL) and
		customer_order.product_area_id=product_area.product_area_id
GROUP BY 
	product_area.product_area,
	DATEPART(yyyy, customer_order.packlist_ready), dbo.GetISOWeekNumberFromDate(customer_order.packlist_ready)
/***************************************************************************************************************************************************************************************                      
ALTER VIEW [dbo].[post_shipment_count_v]
AS
SELECT     COUNT(dbo.Post_shipment.post_shipment_id) AS post_shipment_count, DATEPART(yyyy, dbo.Post_shipment.sent) AS sent_year, dbo.GetISOWeekNumberFromDate( 
                      dbo.Post_shipment.sent) AS sent_week, ISNULL(dbo.job_function.job_function_name, 'Annet') AS job_function_name, 
                      dbo.product_area.product_area
FROM         dbo.Customer_order INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id RIGHT OUTER JOIN
                      dbo.Post_shipment ON dbo.Customer_order.Order_id = dbo.Post_shipment.order_id LEFT OUTER JOIN
                      dbo.deviation ON dbo.Post_shipment.deviation_id = dbo.deviation.deviation_id LEFT OUTER JOIN
                      dbo.job_function ON dbo.deviation.deviation_function_id = dbo.job_function.job_function_id
WHERE     (dbo.Post_shipment.sent IS NOT NULL)
GROUP BY DATEPART(yyyy, dbo.Post_shipment.sent), dbo.GetISOWeekNumberFromDate(dbo.Post_shipment.sent), dbo.job_function.job_function_name, 
                      dbo.product_area.product_area
/***************************************************************************************************************************************************************************************                      
ALTER VIEW [dbo].[sum_avvik_v]
AS
SELECT     COUNT(DISTINCT dbo.deviation.deviation_id) AS deviation_count, ISNULL(dbo.job_function.job_function_name, 'Uspesifisert') AS job_function_name, 
                      DATEPART(yyyy, dbo.deviation.registration_date) AS registration_year, dbo.GetISOWeekNumberFromDate(dbo.deviation.registration_date) AS registration_week, 
                      SUM(ISNULL(dbo.Order_cost.Cost_Amount, 0)) AS internal_cost, DATEPART(month, dbo.deviation.registration_date) AS registration_month, 
dbo.is_deviation_done(deviation.deviation_status_id) as closed,
						dbo.product_area.product_area, 
                      dbo.product_area_group.product_area_group_name
FROM         dbo.Customer_order INNER JOIN
                      dbo.Order_cost ON dbo.Customer_order.Order_id = dbo.Order_cost.Order_id RIGHT OUTER JOIN
                      dbo.deviation LEFT OUTER JOIN
                      dbo.product_area_group INNER JOIN
                      dbo.product_area ON dbo.product_area_group.product_area_group_id = dbo.product_area.product_area_group_id ON 
                      dbo.deviation.product_area_id = dbo.product_area.product_area_id ON dbo.Order_cost.deviation_id = dbo.deviation.deviation_id AND 
                      dbo.is_internal_cost(dbo.Order_cost.Order_cost_id) = 1 LEFT OUTER JOIN
                      dbo.job_function ON dbo.deviation.deviation_function_id = dbo.job_function.job_function_id
GROUP BY dbo.job_function.job_function_name, DATEPART(yyyy, dbo.deviation.registration_date), dbo.GetISOWeekNumberFromDate(dbo.deviation.registration_date), 
                      DATEPART(month, dbo.deviation.registration_date), 
dbo.is_deviation_done(deviation.deviation_status_id),
                      dbo.product_area.product_area, dbo.product_area_group.product_area_group_name
/***************************************************************************************************************************************************************************************                      
ALTER VIEW [dbo].[sum_order_ready_v]
AS
SELECT     COUNT(DISTINCT dbo.Customer_order.Order_id) AS order_count, 
dbo.product_area.product_area, 
CONVERT(nvarchar, isnull(dbo.Customer_order.order_ready,dbo.Customer_order.order_complete), 102) AS order_ready, 
SUM(dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Egenproduksjon')) AS package_sum, 
DATEPART(yyyy, isnull(dbo.Customer_order.order_ready,dbo.Customer_order.order_complete)) AS order_ready_year, 
dbo.GetISOWeekNumberFromDate(isnull(dbo.Customer_order.order_ready,dbo.Customer_order.order_complete)) AS order_ready_week, 
dbo.product_area_group.product_area_group_name
FROM         dbo.Customer_order INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id
WHERE     (dbo.Customer_order.order_ready IS NOT NULL or dbo.Customer_order.order_complete is not null) and
		not exists(select 1 from procent_done_order_v where procent_done_order_v.order_id=customer_order.order_id)
GROUP BY dbo.product_area.product_area, 
CONVERT(nvarchar, isnull(dbo.Customer_order.order_ready,dbo.Customer_order.order_complete), 102), 
DATEPART(yyyy, isnull(dbo.Customer_order.order_ready,dbo.Customer_order.order_complete)), 
dbo.GetISOWeekNumberFromDate(isnull(dbo.Customer_order.order_ready,dbo.Customer_order.order_complete)), 
dbo.product_area_group.product_area_group_name
union
select order_count,product_area,order_ready,production_sum,procent_done_year,procent_done_week,product_area_group_name from procent_done_v
/***************************************************************************************************************************************************************************************                      
