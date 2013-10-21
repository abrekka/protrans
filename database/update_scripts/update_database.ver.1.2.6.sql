CREATE FUNCTION [dbo].[get_deviation_cost] 
(
	-- Add the parameters for the function here
	@deviationId int,
	@internal int
)
RETURNS int
AS
BEGIN
	-- Declare the return variable here
	DECLARE @Result int

	-- Add the T-SQL statements to compute the return value here
	SELECT @Result = sum(cost_amount) 
						from order_cost 
						where deviation_id=@deviationId and
							dbo.is_internal_cost(order_cost.order_cost_id)=@internal

	-- Return the result of the function
	RETURN @Result

END

/*********************************************************************************************************/

CREATE VIEW [dbo].[deviation_sum_v]
AS
SELECT     dbo.job_function.job_function_name, 
		dbo.function_category.function_category_name, 
		dbo.deviation_status.deviation_status_name, 
		DATEPART(ww, dbo.deviation.registration_date) AS registration_week, 
		DATEPART(yyyy, dbo.deviation.registration_date) AS registration_year, 
		count(deviation.deviation_id) as number_of_deviations,
        sum(dbo.get_deviation_cost(deviation.deviation_id,1)) as internal_cost,
		sum(dbo.get_deviation_cost(deviation.deviation_id,0)) as external_cost

FROM job_function,function_category,deviation_status,deviation
where deviation.deviation_function_id=job_function.job_function_id and
		deviation.function_category_id=function_category.function_category_id and
		deviation.deviation_status_id=deviation_status.deviation_status_id
GROUP BY dbo.job_function.job_function_name, 
		dbo.function_category.function_category_name, 
		dbo.deviation_status.deviation_status_name, 
		DATEPART(ww,dbo.deviation.registration_date), 
		DATEPART(yyyy, dbo.deviation.registration_date)

/*********************************************************************************************************/
alter table deviation add initiated_by nvarchar(1000);