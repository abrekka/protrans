ALTER VIEW [dbo].[main_package_v]
AS
SELECT     dbo.Customer_order.Order_id, dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Construction_type.Name AS construction_type_name, 
                      dbo.Customer_order.Info, CAST(dbo.Transport.Transport_Week AS nvarchar(2)) + '- ' + dbo.Transport.Transport_name AS transport_details, 
                      dbo.is_order_ready_for_package(dbo.Customer_order.Order_id) AS package_ready, dbo.Customer_order.order_complete AS done_package, 
                      dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment, dbo.Transport.Transport_Year, dbo.Transport.Transport_Week, 
                      dbo.Transport.Loading_date, dbo.product_area_group.product_area_group_name,customer_order.probability
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
WHERE     (dbo.Customer_order.Transport_id IS NULL) AND (dbo.Customer_order.Sent IS NULL) OR
                      (dbo.Customer_order.Sent IS NULL) AND (dbo.Transport.Transport_name <> 'Historisk')
/******************************************************************************************************************************************