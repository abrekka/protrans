create VIEW [dbo].[monteringslinje_v]
AS
select order_id 
from order_line inner join article_type 
on order_line.Article_type_id=article_type.Article_type_id
where article_type.prod_cat_no=1330100 and article_type.prod_cat_no_2=0


/**************************************************************************

SELECT     dbo.Customer_order.Order_id, dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, 
dbo.Customer_order.Order_nr, 
dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, 
dbo.Construction_type.Name AS construction_type_name, 
dbo.Customer_order.Info, CAST(dbo.Transport.Transport_Week AS nvarchar(2)) + '- ' + dbo.Transport.Transport_name AS transport_details, 
dbo.order_ready_for_package_v.order_ready as package_ready,
dbo.Customer_order.order_complete AS done_package, 
dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment, 
dbo.Transport.Transport_Year, 
dbo.Transport.Transport_Week, 
dbo.Transport.Loading_date, 
dbo.product_area_group.product_area_group_name,
customer_order.probability,
customer_order.production_week
FROM         dbo.Customer_order INNER JOIN
dbo.order_ready_for_package_v on Customer_order.Order_id=dbo.order_ready_for_package_v.order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
WHERE     (dbo.Customer_order.Sent IS NULL) and
not exists(select 1 from monteringslinje_v where monteringslinje_v.order_id=customer_order.order_id)


