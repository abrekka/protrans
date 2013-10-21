ALTER VIEW [dbo].[deviation_sum_v]
AS
SELECT     dbo.job_function.job_function_name, 
		dbo.function_category.function_category_name, 
		dbo.deviation_status.deviation_status_name, 
		DATEPART(ww, dbo.deviation.registration_date) AS registration_week, 
		DATEPART(yyyy, dbo.deviation.registration_date) AS registration_year, 
		count(deviation.deviation_id) as number_of_deviations,
        sum(dbo.get_deviation_cost(deviation.deviation_id,1)) as internal_cost,
		sum(dbo.get_deviation_cost(deviation.deviation_id,0)) as external_cost,
		product_area.product_area
FROM job_function,function_category,deviation_status,deviation,product_area
where deviation.deviation_function_id=job_function.job_function_id and
		deviation.function_category_id=function_category.function_category_id and
		deviation.deviation_status_id=deviation_status.deviation_status_id and
product_area.product_area_id=deviation.product_area_id
GROUP BY dbo.job_function.job_function_name, 
		dbo.function_category.function_category_name, 
		dbo.deviation_status.deviation_status_name, 
		DATEPART(ww,dbo.deviation.registration_date), 
		DATEPART(yyyy, dbo.deviation.registration_date),
product_area.product_area
/*************************************************************************************************
CREATE FUNCTION [dbo].[get_procent_value] 
(
	-- Add the parameters for the function here
	@orderId int,
	@yearWeek nvarchar(6)
)
RETURNS int
AS
BEGIN
	-- Declare the return variable here
	DECLARE @Result decimal
	declare @lastProcentYearWeek nvarchar(6)
	declare @lastProcent decimal
	declare @procentValue int

	select @procentValue = procent 
						from procent_done 
						where procent_done.order_id=@orderId and 
								ltrim(rtrim(str(procent_done.procent_done_year)+replace(str(procent_done.procent_done_week,2),' ','0')))=@yearWeek
								
	if(@procentValue is null)
      select @Result=0
	else
	begin
	  select @lastProcentYearWeek=max((ltrim(rtrim(str(procent_done.procent_done_year)+replace(str(procent_done.procent_done_week,2),' ','0')))))
				from procent_done
				where procent_done.order_id=@orderId and 
								ltrim(rtrim(str(procent_done.procent_done_year)+replace(str(procent_done.procent_done_week,2),' ','0')))<@yearWeek
	  if(@lastProcentYearWeek is null)
		select @lastProcent=0
	  else
	    select @lastProcent=procent_done.procent from procent_done where procent_done.order_id=@orderId and ltrim(rtrim(str(procent_done.procent_done_year)+replace(str(procent_done.procent_done_week,2),' ','0')))=@lastProcentYearWeek
	  
	  select @Result = @procentValue-@lastProcent
	end

	-- Return the result of the function
	RETURN @Result

END
/*************************************************************************************************
CREATE VIEW [dbo].[procent_done_order_v]
AS
SELECT     dbo.procent_done.order_id,procent_done.procent, dbo.product_area.product_area, CONVERT(nvarchar, dbo.Customer_order.order_ready, 102) AS order_ready, 
                      dbo.get_procent_value(dbo.procent_done.order_id, LTRIM(RTRIM(STR(dbo.procent_done.procent_done_year) 
                      + REPLACE(STR(dbo.procent_done.procent_done_week, 2), ' ', '0')))) * dbo.get_customer_cost_for_type(dbo.procent_done.order_id, N'Egenproduksjon') 
                      / 100 AS production_sum, dbo.procent_done.procent_done_year, dbo.procent_done.procent_done_week, 
                      dbo.product_area_group.product_area_group_name
FROM         dbo.procent_done INNER JOIN
                      dbo.Customer_order ON dbo.procent_done.order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id

/*************************************************************************************************
CREATE VIEW [dbo].[procent_done_v]
AS
SELECT     sum(case procent when 100 then 1 else 0 end) AS order_count, 
procent,
product_area,
order_ready, 
sum(production_sum) as production_sum,
procent_done_year, 
procent_done_week, 
product_area_group_name
FROM         dbo.procent_done_order_v
GROUP BY procent,product_area, 
order_ready, 
procent_done_year, 
procent_done_week, product_area_group_name
/*************************************************************************************************
ALTER VIEW [dbo].[sum_order_ready_v]
AS
SELECT     COUNT(DISTINCT dbo.Customer_order.Order_id) AS order_count, 
dbo.product_area.product_area, 
CONVERT(nvarchar, dbo.Customer_order.order_ready, 102) AS order_ready, 
SUM(dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Egenproduksjon')) AS package_sum, 
DATEPART(yyyy, dbo.Customer_order.order_ready) AS order_ready_year, 
DATEPART(ww, dbo.Customer_order.order_ready) AS order_ready_week, 
dbo.product_area_group.product_area_group_name
FROM         dbo.Customer_order INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id
WHERE     (dbo.Customer_order.order_ready IS NOT NULL) and
		not exists(select 1 from procent_done_order_v where procent_done_order_v.order_id=customer_order.order_id)
GROUP BY dbo.product_area.product_area, CONVERT(nvarchar, dbo.Customer_order.order_ready, 102), DATEPART(yyyy, dbo.Customer_order.order_ready), 
                      DATEPART(ww, dbo.Customer_order.order_ready), dbo.product_area_group.product_area_group_name
union
select order_count,product_area,order_ready,production_sum,procent_done_year,procent_done_week,product_area_group_name from procent_done_v
/*************************************************************************************************
alter table article_type add prod_cat_no int;
alter table article_type add prod_cat_no_2 int;
alter table article_type add constraint prod_cat_uk unique(prod_cat_no,prod_cat_no_2);
/*************************************************************************************************
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
	    superoffice.crm5.udsalesmall.long03 as order_date,
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
where superoffice.crm5.address.atype_idx = 2 and 
superoffice.crm5.sale.probability>0
/*************************************************************************************************
alter table order_line add ord_no int;
alter table order_line add ln_no int;