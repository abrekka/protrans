alter table customer_order add tross_started datetime;
/****************************************************************************************************
CREATE VIEW [dbo].[deviation_sum_job_function_v]
AS
SELECT     registration_year, datepart(month,registration_date) as month,registration_week, deviation_function, function_category_name, COUNT(deviation_id) AS count_deviations
FROM         dbo.deviation_v
GROUP BY registration_year, datepart(month,registration_date),registration_week, deviation_function, function_category_name
