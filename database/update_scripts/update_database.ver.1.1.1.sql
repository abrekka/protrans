alter table order_cost add deviation_id integer references deviation(deviation_id);
/********************************************************************************************/
update order_cost set deviation_id=(select deviation_id from deviation where deviation.external_cost_id=order_cost.order_cost_id or deviation.internal_cost_id=order_cost.order_cost_id)
/********************************************************************************************/
CREATE FUNCTION [dbo].[is_internal_cost] 
(
	-- Add the parameters for the function here
	@orderCostId int
)
RETURNS int
AS
BEGIN
	-- Declare the return variable here
	DECLARE @Result int
declare @num int

	-- Add the T-SQL statements to compute the return value here
	SELECT @num = count(order_cost_id) 
						from order_cost,cost_unit 
						where order_cost.order_cost_id=@orderCostId and
								order_cost.cost_unit_id=cost_unit.cost_unit_id and
								cost_unit.cost_unit_name in('Intern','UI')

	-- Return the result of the function
	if(@num <> 0)
	  select @Result=1
	else
	  select @Result=0
	RETURN @Result

END;

/********************************************************************************************/
ALTER VIEW [dbo].[sum_avvik_v]
AS
SELECT     COUNT(dbo.deviation.deviation_id) AS deviation_count, 
ISNULL(dbo.Customer_order.villa, 0) AS villa, 
ISNULL(dbo.job_function.job_function_name, '') AS job_function_name, 
DATEPART(yyyy, dbo.deviation.registration_date) AS registration_year, 
DATEPART(ww, dbo.deviation.registration_date) AS registration_week, 
SUM(ISNULL(dbo.Order_cost.Cost_Amount, 0)) AS internal_cost
FROM         dbo.Customer_order INNER JOIN
dbo.Order_cost ON dbo.Customer_order.Order_id = dbo.Order_cost.Order_id RIGHT OUTER JOIN
dbo.deviation LEFT OUTER JOIN
dbo.job_function ON dbo.deviation.deviation_function_id = dbo.job_function.job_function_id ON 
dbo.Order_cost.deviation_id = dbo.deviation.deviation_id 
and dbo.is_internal_cost(order_cost.order_cost_id)=1

GROUP BY dbo.job_function.job_function_name, 
isnull(dbo.Customer_order.villa,0), 
DATEPART(yyyy, dbo.deviation.registration_date), 
DATEPART(ww, dbo.deviation.registration_date)
/********************************************************************************************/

insert into window_access(window_name) values ('Betaling');

/********************************************************************************************/
alter table application_user add product_area_id integer references product_area(product_area_id);