ALTER VIEW [dbo].[packlist_v]
AS
SELECT     dbo.Customer_order.Order_id, dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.Transport.Loading_date, dbo.Customer_order.packlist_ready, 
                      dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment, dbo.get_number_of_article(dbo.Customer_order.Order_id, N'Takstoler') 
                      AS number_of_takstol, dbo.order_has_article(dbo.Customer_order.Order_id, N'Gulvspon') AS has_gulvspon, 
                      dbo.get_number_of_article(dbo.Customer_order.Order_id, N'Gulvspon') AS number_of_gulvspon, 
                      dbo.product_area_group.product_area_group_name,
CAST(dbo.Transport.Transport_Week AS nvarchar(2)) + '- ' + dbo.Transport.Transport_name AS transport_details,dbo.customer_order.tross_ready,dbo.customer_order.tross_drawer
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
WHERE     (dbo.Customer_order.Sent IS NULL) AND (dbo.Transport.Transport_name <> 'Historisk') OR
                      (dbo.Customer_order.Sent IS NULL) AND (dbo.Customer_order.Transport_id IS NULL)
/***************************************************************************************************************************************************************************************
ALTER VIEW [dbo].[deviation_v]
AS
SELECT     dbo.deviation.deviation_id, dbo.deviation.registration_date, dbo.deviation.user_name, dbo.deviation.customer_nr, dbo.deviation.customer_name, 
                      dbo.deviation.order_nr, dbo.product_area.product_area, dbo.deviation.responsible, job_function_own.job_function_name AS own_function, 
                      job_function_deviation.job_function_name AS deviation_function, dbo.function_category.function_category_name, dbo.deviation_status.deviation_status_name, 
                      dbo.GetISOWeekNumberFromDate(dbo.deviation.registration_date) AS registration_week, DATEPART(yyyy, dbo.deviation.registration_date) AS registration_year, 
                      dbo.get_deviation_cost(dbo.deviation.deviation_id, 1) AS internal_cost, dbo.get_deviation_cost(dbo.deviation.deviation_id, 0) AS external_cost, 
                      dbo.product_area_group.product_area_group_name
FROM         dbo.product_area LEFT OUTER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id RIGHT OUTER JOIN
                      dbo.deviation ON dbo.product_area.product_area_id = dbo.deviation.product_area_id LEFT OUTER JOIN
                      dbo.deviation_status ON dbo.deviation.deviation_status_id = dbo.deviation_status.deviation_status_id LEFT OUTER JOIN
                      dbo.function_category ON dbo.deviation.function_category_id = dbo.function_category.function_category_id LEFT OUTER JOIN
                      dbo.job_function AS job_function_deviation ON dbo.deviation.deviation_function_id = job_function_deviation.job_function_id LEFT OUTER JOIN
                      dbo.job_function AS job_function_own ON dbo.deviation.own_function_id = job_function_own.job_function_id
/***************************************************************************************************************************************************************************************
