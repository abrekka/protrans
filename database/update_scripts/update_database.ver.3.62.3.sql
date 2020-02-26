ALTER VIEW [dbo].[not_invoiced_v]
AS
SELECT     dbo.Customer_order.Order_id, dbo.Customer_order.Order_nr + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS order_details, 
                      dbo.Customer_order.Sent, dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Egenproduksjon') AS garage_value, 
                      dbo.product_area.product_area,dbo.Customer_order.Order_nr
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id
WHERE     (dbo.Customer_order.Sent IS NOT NULL) AND (dbo.Customer_order.Invoice_date IS NULL)