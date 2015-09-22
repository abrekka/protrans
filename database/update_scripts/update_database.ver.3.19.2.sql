ALTER VIEW [dbo].[packlist_v]
AS
SELECT    distinct dbo.Customer_order.Order_id
   ,dbo.Customer.Customer_nr
   ,dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details
   ,dbo.Customer_order.Order_nr
   ,dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address
   ,dbo.Customer_order.Info
   ,dbo.Construction_type.Name AS construction_type_name
   ,dbo.Transport.Loading_date
   ,dbo.Customer_order.packlist_ready
   ,dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment
   ,number_of_takstol_v.number_of_items as number_of_takstol
   ,dbo.order_has_gulvspon_v.has_gulvspon
   ,dbo.number_of_gulvspon_v.number_of_items as number_of_gulvspon
   ,dbo.product_area_group.product_area_group_name
   ,CAST(dbo.Transport.Transport_Week AS nvarchar(2)) + '- ' + dbo.Transport.Transport_name AS transport_details
   ,dbo.customer_order.tross_ready
   ,dbo.customer_order.tross_drawer
   ,dbo.transport.transport_year
   ,dbo.transport.transport_week,
dbo.Customer_order.status AS order_status
,dbo.Customer_order.production_basis
,dbo.Customer_order.packlist_duration
,dbo.Customer_order.packlist_done_by
,dbo.Customer_order.production_week,
dbo.customer_order.Do_Assembly
,dbo.Assembly.Assembly_week
FROM         dbo.Customer_order left outer join
number_of_takstol_v on Customer_order.Order_id=number_of_takstol_v.Order_id LEFT outer join
dbo.number_of_gulvspon_v on Customer_order.Order_id=dbo.number_of_gulvspon_v.order_id left outer join
dbo.order_has_gulvspon_v on customer_order.order_id=order_has_gulvspon_v.order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id left outer join
					  dbo.Assembly on dbo.Customer_order.order_id=dbo.Assembly.Order_id
WHERE     (dbo.Customer_order.Sent IS NULL) AND (dbo.Transport.Transport_name <> 'Historisk') OR
                      (dbo.Customer_order.Sent IS NULL) AND (dbo.Customer_order.Transport_id IS NULL)
GO


