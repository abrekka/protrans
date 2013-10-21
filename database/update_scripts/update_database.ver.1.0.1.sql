CREATE VIEW [dbo].[own_production_v]
AS
SELECT     dbo.Customer_order.Order_id, dbo.Customer_order.packed_by, dbo.Transport.Transport_Year, dbo.Transport.Transport_Week, 
                      dbo.Customer.Customer_nr, dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_name, 
                      dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Garasje') AS garage_value, DATEPART(yyyy, dbo.Customer_order.order_ready) 
                      AS order_ready_year, DATEPART(ww, dbo.Customer_order.order_ready) AS order_ready_week, DATEPART(dw, dbo.Customer_order.order_ready) 
                      AS order_ready_day, dbo.Customer_order.packlist_ready, dbo.Customer_order.Invoice_date, ISNULL(dbo.Customer_order.villa, 0) AS villa
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
WHERE     (dbo.Transport.Transport_Year <> 9999)
