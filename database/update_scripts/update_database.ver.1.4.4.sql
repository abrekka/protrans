ALTER VIEW [dbo].[not_invoiced_v]
AS
SELECT     dbo.Customer_order.Order_id, dbo.Customer_order.Order_nr + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS order_details, 
                      dbo.Customer_order.Sent, dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Egenproduksjon') AS garage_value, 
                      dbo.product_area.product_area
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id
WHERE     (dbo.Customer_order.Sent IS NOT NULL) AND (dbo.Customer_order.Invoice_date IS NULL)
/***************************************************************************************************/
ALTER VIEW [dbo].[order_status_not_sent_v]
AS
SELECT     dbo.Customer_order.Order_id, dbo.Customer_order.packlist_ready, dbo.Customer_order.order_ready, 
                      dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Egenproduksjon') AS garage_value, dbo.product_area.product_area
FROM         dbo.Customer_order INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id
WHERE     (dbo.Customer_order.Sent IS NULL)
/***************************************************************************************************/
ALTER VIEW [dbo].[own_production_v]
AS
SELECT     dbo.Customer_order.Order_id, dbo.Customer_order.packed_by, dbo.Transport.Transport_Year, dbo.Transport.Transport_Week, 
                      dbo.Customer.Customer_nr, dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_name, 
                      dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Egenproduksjon') AS garage_value, DATEPART(yyyy, 
                      dbo.Customer_order.order_ready) AS order_ready_year, DATEPART(ww, dbo.Customer_order.order_ready) AS order_ready_week, DATEPART(dw, 
                      dbo.Customer_order.order_ready) AS order_ready_day, dbo.Customer_order.packlist_ready, dbo.Customer_order.Invoice_date, 
                      dbo.product_area.product_area, dbo.Customer_order.Sent, dbo.Customer_order.Order_nr, dbo.product_area_group.product_area_group_name
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
/***************************************************************************************************/
ALTER VIEW [dbo].[post_shipment_count_v]
AS
SELECT     COUNT(dbo.Post_shipment.post_shipment_id) AS post_shipment_count, DATEPART(yyyy, dbo.Post_shipment.sent) AS sent_year, DATEPART(ww, 
                      dbo.Post_shipment.sent) AS sent_week, ISNULL(dbo.job_function.job_function_name, 'Annet') AS job_function_name, 
                      dbo.product_area.product_area
FROM         dbo.Customer_order INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id RIGHT OUTER JOIN
                      dbo.Post_shipment ON dbo.Customer_order.Order_id = dbo.Post_shipment.order_id LEFT OUTER JOIN
                      dbo.deviation ON dbo.Post_shipment.deviation_id = dbo.deviation.deviation_id LEFT OUTER JOIN
                      dbo.job_function ON dbo.deviation.deviation_function_id = dbo.job_function.job_function_id
WHERE     (dbo.Post_shipment.sent IS NOT NULL)
GROUP BY DATEPART(yyyy, dbo.Post_shipment.sent), DATEPART(ww, dbo.Post_shipment.sent), dbo.job_function.job_function_name, 
                      dbo.product_area.product_area
/***************************************************************************************************/
