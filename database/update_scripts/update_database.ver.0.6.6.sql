alter table assembly add constraint order_id_uk unique(order_id);
/*****************************************************************************************************************/
alter table assembly add assemblied_date datetime;

/*****************************************************************************************************************/

CREATE FUNCTION [dbo].[get_assemblied_date] 
(
	-- Add the parameters for the function here
	@orderId int,
	@doAssembly int
)
RETURNS datetime
AS
BEGIN
	-- Declare the return variable here
	DECLARE @Result datetime

	if(COALESCE (@doAssembly, 0)=0)
      SELECT @Result = null
    else
	begin
      SELECT @Result = assembly.assemblied_date
		from assembly where assembly.order_id = @orderId
	end

	-- Return the result of the function
	RETURN @Result

END

/*****************************************************************************************************************/

ALTER VIEW [dbo].[fakturering_v]
AS
SELECT     dbo.Customer_order.Order_id,customer_order.order_nr,customer_order.invoice_date, dbo.Customer.Customer_nr, dbo.Customer.First_name, dbo.Customer.Last_name, dbo.Customer_order.Postal_code, 
                      dbo.Customer_order.Post_office, dbo.Construction_type.Name AS Construction_name, dbo.Customer_order.Sent, 
                      dbo.Get_customer_cost(dbo.Customer_order.Order_id) AS customer_cost,customer_order.comment,
dbo.get_assemblied_date(customer_order.order_id,customer_order.do_assembly) as assemblied_date
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id
WHERE     (dbo.Customer_order.Sent IS NOT NULL) AND (COALESCE (dbo.Customer_order.Do_Assembly, 0) = 0) OR
                      (dbo.Customer_order.Sent IS NOT NULL) AND (dbo.Customer_order.Do_Assembly = 1) AND EXISTS
                          (SELECT     1 AS Expr1
                            FROM          dbo.[Assembly]
                            WHERE      (Order_id = dbo.Customer_order.Order_id) AND (LOWER(Assemblied) = 'ja'))

