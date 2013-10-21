ALTER VIEW [dbo].[deviation_v]
AS
SELECT     dbo.deviation.deviation_id, dbo.deviation.registration_date, dbo.deviation.user_name, dbo.deviation.customer_nr, dbo.deviation.customer_name, 
                      dbo.deviation.order_nr, dbo.product_area.product_area, dbo.deviation.responsible, job_function_own.job_function_name AS own_function, 
                      job_function_deviation.job_function_name AS deviation_function, dbo.function_category.function_category_name, dbo.deviation_status.deviation_status_name, 
                      dbo.GetISOWeekNumberFromDate(dbo.deviation.registration_date) AS registration_week, DATEPART(yyyy, dbo.deviation.registration_date) AS registration_year, 
                      dbo.get_deviation_cost(dbo.deviation.deviation_id, 1) AS internal_cost, dbo.get_deviation_cost(dbo.deviation.deviation_id, 0) AS external_cost, 
                      dbo.product_area_group.product_area_group_name,dbo.deviation.initiated_by
FROM         dbo.product_area LEFT OUTER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id RIGHT OUTER JOIN
                      dbo.deviation ON dbo.product_area.product_area_id = dbo.deviation.product_area_id LEFT OUTER JOIN
                      dbo.deviation_status ON dbo.deviation.deviation_status_id = dbo.deviation_status.deviation_status_id LEFT OUTER JOIN
                      dbo.function_category ON dbo.deviation.function_category_id = dbo.function_category.function_category_id LEFT OUTER JOIN
                      dbo.job_function AS job_function_deviation ON dbo.deviation.deviation_function_id = job_function_deviation.job_function_id LEFT OUTER JOIN
                      dbo.job_function AS job_function_own ON dbo.deviation.own_function_id = job_function_own.job_function_id
