ALTER VIEW [dbo].[vegg_production_v]
AS
SELECT     dbo.Order_line.Order_line_id,dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, dbo.Order_line.attribute_info, 
                      dbo.Order_line.Number_of_items, dbo.Transport.Loading_date, dbo.Order_line.produced, 
					CAST(dbo.Transport.Transport_Week AS nvarchar(2)) + '- ' + dbo.Transport.Transport_name AS transport_details, 
					dbo.get_order_comment(Customer_order.order_id) as Comment, isnull(dbo.Transport.Transport_Year,9999) as transport_year, 
                      dbo.Transport.Transport_Week,transport.load_time,customer_order.status as order_status
FROM         dbo.Order_line INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.Order_line_attribute ON dbo.Order_line.Order_line_id = dbo.Order_line_attribute.Order_line_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id
WHERE     (dbo.is_order_line_sent(dbo.Order_line.Order_id, 
                      dbo.Order_line.post_shipment_id) = 0) AND 
	(dbo.Transport.Transport_name <> 'Historisk' or 
	dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) is null) AND 
	dbo.order_line_attribute.order_line_attribute_name='Lager' and
	lower(dbo.order_line_attribute.order_line_attribute_value)='nei' and
	(dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id,dbo.Order_line.Article_type_id) = 'Vegg')

/************************************************************************************************************/
ALTER VIEW [dbo].[front_production_v]
AS
SELECT     dbo.Order_line.Order_line_id, dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, dbo.Order_line.attribute_info, 
                      dbo.Order_line.Number_of_items, dbo.Transport.Loading_date, dbo.Order_line.produced, CAST(dbo.Transport.Transport_Week AS nvarchar(2)) 
                      + '- ' + dbo.Transport.Transport_name AS transport_details, dbo.get_order_comment(Customer_order.order_id) as Comment, ISNULL(dbo.Transport.Transport_Year, 9999) 
                      AS transport_year, dbo.Transport.Transport_Week,transport.load_time,customer_order.status as order_status
FROM         dbo.Order_line INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.Order_line_attribute ON dbo.Order_line.Order_line_id = dbo.Order_line_attribute.Order_line_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id
WHERE     (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND (dbo.Transport.Transport_name <> 'Historisk') AND 
                      (dbo.Order_line_attribute.order_line_attribute_name = 'Lager') AND (LOWER(dbo.Order_line_attribute.Order_line_Attribute_value) = 'nei') AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Front') OR
                      (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND 
                      (dbo.Order_line_attribute.order_line_attribute_name = 'Lager') AND (LOWER(dbo.Order_line_attribute.Order_line_Attribute_value) = 'nei') AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Front') AND 
                      (dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) IS NULL)

/************************************************************************************************************/

alter table assembly add inactive int;

/************************************************************************************************************/
update order_cost set cost_unit_id=(select intern_cost.cost_unit_id 
					from cost_unit intern_cost 
					where intern_cost.cost_unit_name='Intern')
	where exists(select 1 
			from cost_unit 
			where cost_unit.cost_unit_id=order_cost.cost_unit_id and 
				cost_unit.cost_unit_name='UI')
				
/************************************************************************************************************/
delete from cost_unit where cost_unit_name='UI';