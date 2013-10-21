CREATE VIEW [dbo].[not_invoiced_v]
AS
SELECT     dbo.Customer_order.Order_id, 
dbo.Customer_order.Order_nr+' '+dbo.Customer.First_name+' '+dbo.Customer.Last_name as order_details, dbo.Customer_order.Sent, 
                      dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Garasje') AS garage_value
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id
WHERE     (dbo.Customer_order.Sent IS NOT NULL) AND (dbo.Customer_order.Invoice_date IS NULL)
/*************************************************************************************************************************************/
CREATE VIEW [dbo].[order_status_not_sent_v]
AS
SELECT     Order_id, packlist_ready, order_ready, dbo.get_customer_cost_for_type(Order_id, N'Garasje') AS garage_value
FROM         dbo.Customer_order
WHERE     (Sent IS NULL)
