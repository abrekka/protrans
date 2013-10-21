/***********************************************************************************************/
USE [ProTrans]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[vegg_production_v]
AS
SELECT     dbo.Order_line.Order_line_id, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, dbo.Order_line.attribute_info, 
                      dbo.Order_line.Number_of_items, dbo.Transport.Loading_date, dbo.Order_line.produced, 
					CAST(dbo.Transport.Transport_Week AS nvarchar(2)) + '- ' + dbo.Transport.Transport_name AS transport_details, 
					dbo.Customer_order.Comment, isnull(dbo.Transport.Transport_Year,9999) as transport_year, 
                      dbo.Transport.Transport_Week
FROM         dbo.Order_line INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
WHERE     (dbo.Customer_order.Sent IS NULL) AND (dbo.Transport.Transport_name <> 'Historisk' or customer_order.transport_id is null) 
		AND (dbo.is_order_line_default(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.is_default) = 0) AND (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, 
                      dbo.Order_line.Article_type_id) = 'Vegg')
GO
/***********************************************************************************************/

USE [ProTrans]
GO
/****** Object:  UserDefinedFunction [dbo].[is_order_line_default]    Script Date: 05/03/2007 10:19:27 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[is_order_line_default] 
(
	@orderLineId int,
	@isDefault int
)
RETURNS int
AS
BEGIN
	DECLARE @resultDefault int
	declare @counter int

	if(@isDefault is not null)
		select @resultDefault = @isDefault
	else
	begin

	SELECT @counter = count(order_line_attribute_id) 
						from order_line_attribute,construction_type_article_attribute
						where order_line_id=@orderLineId
								and order_line_attribute.construction_type_article_attribute_id = construction_type_article_attribute.construction_type_article_attribute_id
								and order_line_attribute.order_line_attribute_value <> construction_type_article_attribute.construction_type_article_value

		if(@counter <> 0)
			select @resultDefault = 0
		else
			select @resultDefault = 1
	end

	-- Return the result of the function
	RETURN @resultDefault

END
