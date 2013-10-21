ALTER VIEW [dbo].[sum_order_ready_v]
AS
SELECT     COUNT(DISTINCT dbo.Customer_order.Order_id) AS order_count, 
dbo.product_area.product_area, 
CONVERT(nvarchar, isnull(dbo.Customer_order.order_ready,dbo.Customer_order.order_complete), 102) AS order_ready, 
SUM(dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Egenproduksjon')) AS package_sum, 
DATEPART(yyyy, isnull(dbo.Customer_order.order_ready,dbo.Customer_order.order_complete)) AS order_ready_year, 
DATEPART(ww, isnull(dbo.Customer_order.order_ready,dbo.Customer_order.order_complete)) AS order_ready_week, 
dbo.product_area_group.product_area_group_name
FROM         dbo.Customer_order INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id
WHERE     (dbo.Customer_order.order_ready IS NOT NULL or dbo.Customer_order.order_complete is not null) and
		not exists(select 1 from procent_done_order_v where procent_done_order_v.order_id=customer_order.order_id)
GROUP BY dbo.product_area.product_area, 
CONVERT(nvarchar, isnull(dbo.Customer_order.order_ready,dbo.Customer_order.order_complete), 102), 
DATEPART(yyyy, isnull(dbo.Customer_order.order_ready,dbo.Customer_order.order_complete)), 
DATEPART(ww, isnull(dbo.Customer_order.order_ready,dbo.Customer_order.order_complete)), 
dbo.product_area_group.product_area_group_name
union
select order_count,product_area,order_ready,production_sum,procent_done_year,procent_done_week,product_area_group_name from procent_done_v
/*************************************************************************************************************