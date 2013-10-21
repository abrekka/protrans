/************************************************************************/
USE [ProTrans]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[sum_order_ready_v]
AS
SELECT     CONVERT(nvarchar, dbo.Customer_order.order_ready, 102) AS order_ready, SUM(dbo.Order_cost.Cost_Amount) AS package_sum,datepart(ww,order_ready) as order_ready_week
FROM         dbo.Customer_order INNER JOIN
                      dbo.Order_cost ON dbo.Customer_order.Order_id = dbo.Order_cost.Order_id INNER JOIN
                      dbo.Cost_type ON dbo.Order_cost.Cost_type_id = dbo.Cost_type.Cost_type_id
WHERE     (dbo.Customer_order.order_ready IS NOT NULL) AND (dbo.Cost_type.Cost_type_name = 'Garasje')
GROUP BY CONVERT(nvarchar, dbo.Customer_order.order_ready, 102),datepart(ww,order_ready)

GO
/************************************************************************/