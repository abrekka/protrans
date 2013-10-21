CREATE FUNCTION [dbo].[get_attribute_info] 
(
	-- Add the parameters for the function here
	@orderId int,
	@articlePath nvarchar(100)
)
RETURNS nvarchar(1000)
AS
BEGIN
	-- Declare the return variable here
	DECLARE @Result nvarchar(1000)

	-- Add the T-SQL statements to compute the return value here
	SELECT @Result = attribute_info from order_line where order_id=@orderId and lower(article_path)=lower(@articlePath)

	-- Return the result of the function
if(@Result is null)
      select @Result=''
	RETURN @Result

END
/*****************************************************************************************************************************/
ALTER VIEW [dbo].[takstol_package_v]
AS
SELECT     dbo.Order_line.Order_line_id, 
dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, 
dbo.Customer_order.Order_nr, 
dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, 
dbo.Customer_order.Info, 
dbo.Construction_type.Name AS construction_type_name, 
dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, 
dbo.Order_line.attribute_info+dbo.get_attribute_info(order_line.order_id,'Gavl') as Attribute_info, 
dbo.Order_line.Number_of_items, 
dbo.Transport.Loading_date, 
dbo.Order_line.Colli_id, CAST(dbo.Transport.Transport_Week AS nvarchar(2)) + '- ' + dbo.Transport.Transport_name AS transport_details, 
dbo.get_order_comment(Customer_order.order_id) as Comment, 
ISNULL(dbo.Transport.Transport_Year, 9999) AS transport_year, 
dbo.Transport.Transport_Week,
transport.load_time
FROM         dbo.Order_line INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id
WHERE     (dbo.is_order_line_default(dbo.Order_line.Order_line_id, dbo.Order_line.is_default) = 1) AND (dbo.is_order_line_sent(dbo.Order_line.Order_id, 
                      dbo.Order_line.post_shipment_id) = 0) AND (dbo.Transport.Transport_name <> 'Historisk') AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Takstoler') 
                      AND (dbo.order_line_has_article(dbo.Order_line.Order_line_id, dbo.Order_line.has_article, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id)) = 1) OR
                      (dbo.is_order_line_default(dbo.Order_line.Order_line_id, dbo.Order_line.is_default) = 1) AND (dbo.is_order_line_sent(dbo.Order_line.Order_id, 
                      dbo.Order_line.post_shipment_id) = 0) AND (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, 
                      dbo.Order_line.Article_type_id) = 'Takstoler') AND (dbo.order_line_has_article(dbo.Order_line.Order_line_id, dbo.Order_line.has_article, 
                      dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id)) = 1) AND 
                      dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) is null

/*****************************************************************************************************************************/
alter table deviation add do_assembly int;
/*****************************************************************************************************************************/
alter table assembly add deviation_id int references deviation(deviation_id);
/*****************************************************************************************************************************/
alter table assembly alter column order_id int;
/*****************************************************************************************************************************/
ALTER VIEW [dbo].[assembly_overdue_v]
AS
SELECT     distinct Assembly_year AS assembly_year, 
		Assembly_week AS assembly_week
FROM         dbo.[Assembly]
WHERE     CAST(Assembly_year AS nvarchar(10)) + right('00' + CAST(Assembly_week as varchar(2)), 2) 
			< CAST(DATEPART(yyyy, GETDATE()) AS nvarchar(10)) 
                      + right('00' + CAST(DATEPART(ww, GETDATE()) AS nvarchar(10)), 2) AND 
(assemblied_date IS NULL)
and not exists(select 1 
				from customer_order 
				where customer_order.order_id=assembly.order_id and 
						(customer_order.do_assembly=0 or
						customer_order.do_assembly is null))
and not exists(select 1 
				from deviation 
				where deviation.deviation_id=assembly.deviation_id and 
						(deviation.do_assembly=0 or
						deviation.do_assembly is null))
/*****************************************************************************************************************************/						
ALTER TABLE [dbo].[Assembly] DROP CONSTRAINT [order_id_uk]						