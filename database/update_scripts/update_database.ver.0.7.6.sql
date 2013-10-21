/*****************************************************************************************/
CREATE FUNCTION [dbo].[get_number_of_article] 
(
	-- Add the parameters for the function here
	@orderId int,
	@articleName nvarchar(255)
)
RETURNS int
AS
BEGIN
	-- Declare the return variable here
	DECLARE @Result int

	-- Add the T-SQL statements to compute the return value here
	SELECT @Result = number_of_items 
				from order_line 
				where order_line.order_id=@orderId and 
					  dbo.order_line_article_name(order_line.order_line_id,order_line.construction_type_article_id,order_line.article_type_id)=@articleName

	-- Return the result of the function
	RETURN @Result

END
/*********************************************************************************************/
CREATE FUNCTION [dbo].[order_has_article] 
(
	-- Add the parameters for the function here
	@orderId int,
	@articleName nvarchar(255)
)
RETURNS int
AS
BEGIN
	-- Declare the return variable here
	DECLARE @Result int

	-- Add the T-SQL statements to compute the return value here
	SELECT @Result = sum(dbo.order_line_has_article(order_line.order_line_id,order_line.has_article,@articleName)) 
			from order_line 
			where order_line.order_id=@orderId and
					dbo.order_line_article_name(order_line.order_line_id,order_line.construction_type_article_id,order_line.article_type_id)='Gulvspon'

	-- Return the result of the function
	RETURN @Result

END


/*********************************************************************************************/
CREATE VIEW [dbo].[packlist_v]
AS
SELECT     dbo.Customer_order.Order_id, dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.Transport.Loading_date, dbo.Customer_order.packlist_ready, 
                      dbo.Customer_order.Comment,
			dbo.get_number_of_article(customer_order.order_id,'Takstoler') as number_of_takstol,
			dbo.order_has_article(customer_order.order_id,'Gulvspon') as has_gulvspon,
dbo.get_number_of_article(customer_order.order_id,'Gulvspon') as number_of_gulvspon
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
WHERE     (dbo.Customer_order.Sent IS NULL) AND (dbo.Transport.Transport_name <> 'Historisk') OR
                      (dbo.Customer_order.Sent IS NULL) AND (dbo.Customer_order.Transport_id IS NULL)
