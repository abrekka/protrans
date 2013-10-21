CREATE VIEW [dbo].[transport_sum_v]
AS
SELECT     COUNT(dbo.Customer_order.Order_id) AS order_count, dbo.Transport.Transport_Year AS transport_year, 
                      dbo.Transport.Transport_Week AS transport_week, SUM(dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'garasje')) 
                      AS garage_cost
FROM         dbo.Customer_order INNER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
GROUP BY dbo.Transport.Transport_Year, dbo.Transport.Transport_Week

/**********************************************************************************************/
insert into window_access(window_name) values('Transportoversikt');

/**********************************************************************************************/
alter table assembly drop column invoiced;