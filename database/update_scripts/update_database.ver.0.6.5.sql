ALTER VIEW [dbo].[sum_order_ready_v]
AS
SELECT     count(customer_order.order_id) as order_count,CONVERT(nvarchar, dbo.Customer_order.order_ready, 102) AS order_ready, SUM(dbo.Order_cost.Cost_Amount) AS package_sum,datepart(yyyy,order_ready) as order_ready_year,datepart(ww,order_ready) as order_ready_week
FROM         dbo.Customer_order INNER JOIN
                      dbo.Order_cost ON dbo.Customer_order.Order_id = dbo.Order_cost.Order_id INNER JOIN
                      dbo.Cost_type ON dbo.Order_cost.Cost_type_id = dbo.Cost_type.Cost_type_id
WHERE     (dbo.Customer_order.order_ready IS NOT NULL) AND (dbo.Cost_type.Cost_type_name = 'Garasje')
GROUP BY CONVERT(nvarchar, dbo.Customer_order.order_ready, 102),datepart(yyyy,order_ready),datepart(ww,order_ready)

/***************************************************************************************/
ALTER VIEW [dbo].[sum_order_ready_week_v]
AS
SELECT     sum(order_count) as count_order_ready,order_ready_year,order_ready_week, SUM(package_sum) AS package_sum_week
FROM         dbo.sum_order_ready_v
GROUP BY order_ready_year,order_ready_week