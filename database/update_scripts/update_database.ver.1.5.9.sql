alter table deviation_status add deviation_done int;
/*****************************************************************/
alter table deviation add cached_comment nvarchar(4000);
/*****************************************************************/
ALTER VIEW [dbo].[sum_order_ready_v]
AS
SELECT     COUNT(DISTINCT dbo.Customer_order.Order_id) AS order_count, dbo.product_area.product_area, CONVERT(nvarchar, 
                      dbo.Customer_order.order_ready, 102) AS order_ready, SUM(dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Egenproduksjon')) 
                      AS package_sum, DATEPART(yyyy, dbo.Customer_order.order_ready) AS order_ready_year, DATEPART(ww, dbo.Customer_order.order_ready) 
                      AS order_ready_week, dbo.product_area_group.product_area_group_name
FROM         dbo.Customer_order INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id
WHERE     (dbo.Customer_order.order_ready IS NOT NULL)
GROUP BY dbo.product_area.product_area, CONVERT(nvarchar, dbo.Customer_order.order_ready, 102), DATEPART(yyyy, dbo.Customer_order.order_ready), 
                      DATEPART(ww, dbo.Customer_order.order_ready), dbo.product_area_group.product_area_group_name

/*****************************************************************/
CREATE FUNCTION [dbo].[get_attribute_value] 
(
	-- Add the parameters for the function here
	@orderLineId int,
	@attributeName nvarchar(255)
)
RETURNS nvarchar(255)
AS
BEGIN
	-- Declare the return variable here
	DECLARE @Result nvarchar(255)

	-- Add the T-SQL statements to compute the return value here
	SELECT @Result = order_line_attribute_value from order_line_attribute where order_line_id=@orderLineId and lower(order_line_attribute_name)=lower(@attributeName)

	-- Return the result of the function
	RETURN @Result

END
/*****************************************************************/
CREATE VIEW [dbo].[takstol_v]
AS
SELECT     dbo.Order_line.Order_line_id, dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, dbo.Order_line.attribute_info, 
                      dbo.Order_line.Number_of_items, dbo.Transport.Loading_date, dbo.Order_line.produced, CAST(dbo.Transport.Transport_Week AS nvarchar(2)) 
                      + '- ' + dbo.Transport.Transport_name AS transport_details, dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment, 
                      ISNULL(dbo.Transport.Transport_Year, 9999) AS transport_year, dbo.Transport.Transport_Week, dbo.Transport.load_time, 
                      dbo.Order_line.post_shipment_id, dbo.product_area_group.product_area_group_name, dbo.Order_line.action_started, 
                      dbo.get_attribute_value(dbo.Order_line.Order_line_id, N'Egenordre') AS egenordre, dbo.is_order_line_default(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.is_default) AS is_default
FROM         dbo.Order_line INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id
WHERE     (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Takstoler') 
                      AND (dbo.order_line_has_article(dbo.Order_line.Order_line_id, dbo.Order_line.has_article, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id)) = 1)
/*****************************************************************/
ALTER VIEW [dbo].[takstol_package_v]
AS
select * from takstol_v where is_default=1 and egenordre='Nei'
/*****************************************************************/
ALTER VIEW [dbo].[takstol_production_v]
AS
select * from takstol_v where is_default=0 or egenordre<>'Nei'
