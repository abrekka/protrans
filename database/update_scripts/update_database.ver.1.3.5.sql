ALTER VIEW [dbo].[own_production_v]
AS
SELECT     dbo.Customer_order.Order_id, dbo.Customer_order.packed_by, dbo.Transport.Transport_Year, dbo.Transport.Transport_Week, 
                      dbo.Customer.Customer_nr, dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_name, 
                      dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Garasje') AS garage_value, DATEPART(yyyy, dbo.Customer_order.order_ready) 
                      AS order_ready_year, DATEPART(ww, dbo.Customer_order.order_ready) AS order_ready_week, DATEPART(dw, dbo.Customer_order.order_ready) 
                      AS order_ready_day, dbo.Customer_order.packlist_ready, dbo.Customer_order.Invoice_date, dbo.product_area.product_area, 
                      dbo.Customer_order.Sent,customer_order.order_nr
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
/************************************************************************************************/
ALTER VIEW [dbo].[sum_avvik_v]
AS
SELECT     COUNT(dbo.deviation.deviation_id) AS deviation_count, 
		isnull(product_area.product_area,'Garasje villa') as product_area,
		ISNULL(dbo.job_function.job_function_name, 'Uspesifisert') AS job_function_name, 
		DATEPART(yyyy, dbo.deviation.registration_date) AS registration_year, 
		DATEPART(ww, dbo.deviation.registration_date) AS registration_week, 
		SUM(ISNULL(dbo.Order_cost.Cost_Amount, 0)) AS internal_cost,
		DATEPART(month, dbo.deviation.registration_date) AS registration_month,
CASE isnull(deviation.date_closed, 0) WHEN 0 THEN 0 ELSE 1 END AS closed 
FROM         dbo.Customer_order INNER JOIN
			product_area on customer_order.product_area_id = product_area.product_area_id inner join
			dbo.Order_cost ON dbo.Customer_order.Order_id = dbo.Order_cost.Order_id RIGHT OUTER JOIN
			dbo.deviation LEFT OUTER JOIN
			dbo.job_function ON dbo.deviation.deviation_function_id = dbo.job_function.job_function_id ON 
			dbo.Order_cost.deviation_id = dbo.deviation.deviation_id 
			and dbo.is_internal_cost(order_cost.order_cost_id)=1

GROUP BY dbo.job_function.job_function_name, 
isnull(product_area.product_area,'Garasje villa'), 
DATEPART(yyyy, dbo.deviation.registration_date), 
DATEPART(ww, dbo.deviation.registration_date),
DATEPART(month, dbo.deviation.registration_date),
CASE isnull(deviation.date_closed, 0) WHEN 0 THEN 0 ELSE 1 END


/************************************************************************************************/
alter table preventive_action add closed_date datetime;
/************************************************************************************************/
alter table preventive_action drop column project_closed;