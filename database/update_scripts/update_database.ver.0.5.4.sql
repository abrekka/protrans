CREATE FUNCTION [dbo].[Get_customer_cost] 
(
	@orderId int
)
RETURNS int
AS
BEGIN
	DECLARE @Result numeric

	SELECT @Result = sum(cost_amount) from order_cost where order_cost.order_id=@orderId and exists(select 1 from cost_unit where order_cost.cost_unit_id=cost_unit.cost_unit_id and lower(cost_unit.cost_unit_name)='kunde')

	RETURN @Result

END
*******************************************************************************************************
CREATE VIEW [dbo].[fakturering_v]
AS
SELECT     dbo.Customer_order.Order_id,customer_order.order_nr,customer_order.invoice_date, dbo.Customer.Customer_nr, dbo.Customer.First_name, dbo.Customer.Last_name, dbo.Customer_order.Postal_code, 
                      dbo.Customer_order.Post_office, dbo.Construction_type.Name AS Construction_name, dbo.Customer_order.Sent, 
                      dbo.Get_customer_cost(dbo.Customer_order.Order_id) AS customer_cost,customer_order.comment
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id
WHERE     (dbo.Customer_order.Sent IS NOT NULL) AND (COALESCE (dbo.Customer_order.Do_Assembly, 0) = 0) OR
                      (dbo.Customer_order.Sent IS NOT NULL) AND (dbo.Customer_order.Do_Assembly = 1) AND EXISTS
                          (SELECT     1 AS Expr1
                            FROM          dbo.[Assembly]
                            WHERE      (Order_id = dbo.Customer_order.Order_id) AND (LOWER(Assemblied) = 'ja'))
