ALTER VIEW [dbo].[own_production_v]
AS
SELECT     dbo.Customer_order.Order_id
   ,dbo.Customer_order.packed_by
   ,dbo.Transport.Transport_Year
   ,dbo.Transport.Transport_Week
   ,dbo.Customer.Customer_nr
   ,dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_name
   ,dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Egenproduksjon') AS garage_value
   ,DATEPART(yyyy, isnull(dbo.Customer_order.order_ready,dbo.Customer_order.order_complete)) AS order_ready_year
   ,dbo.GetISOWeekNumberFromDate(isnull(dbo.Customer_order.order_ready,dbo.Customer_order.order_complete)) AS order_ready_week
   ,DATEPART(dw, isnull(dbo.Customer_order.order_ready,dbo.Customer_order.order_complete)) AS order_ready_day
   ,dbo.Customer_order.packlist_ready
   ,dbo.Customer_order.Invoice_date
   ,dbo.product_area.product_area
   ,dbo.Customer_order.Sent
   ,dbo.Customer_order.Order_nr
   ,dbo.product_area_group.product_area_group_name
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
/*************************************************************************************************************************************************************
ALTER VIEW [dbo].[own_production_v]
AS
SELECT dbo.Customer_order.Order_id
,dbo.Customer_order.packed_by
,dbo.Transport.Transport_Year
,dbo.Transport.Transport_Week
,dbo.Customer.Customer_nr
,dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_name
,dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Egenproduksjon') AS garage_value
,DATEPART(yyyy, isnull(dbo.Customer_order.order_ready,dbo.Customer_order.order_complete)) AS order_ready_year
,dbo.GetISOWeekNumberFromDate(isnull(dbo.Customer_order.order_ready,dbo.Customer_order.order_complete)) AS order_ready_week
,DATEPART(dw, isnull(dbo.Customer_order.order_ready,dbo.Customer_order.order_complete)) AS order_ready_day
,dbo.Customer_order.packlist_ready
,dbo.Customer_order.Invoice_date
,dbo.product_area.product_area
,dbo.Customer_order.Sent
,dbo.Customer_order.Order_nr
,dbo.product_area_group.product_area_group_name
FROM dbo.Customer_order INNER JOIN
dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
/*************************************************************************************************************************************************************
