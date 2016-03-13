CREATE VIEW [dbo].[igarasjen_package_v]
AS
SELECT [Protrans2].[dbo].[Order_line].Order_line_id, [Protrans2].[dbo].Customer.Customer_nr, CAST([Protrans2].[dbo].Customer.Customer_nr AS nvarchar(10)) 
+ ' ' + [Protrans2].[dbo].Customer.First_name + ' ' + [Protrans2].[dbo].Customer.Last_name AS customer_details, [Protrans2].[dbo].Customer_order.Order_nr, 
[Protrans2].[dbo].Customer_order.Postal_code + ' ' + [Protrans2].[dbo].Customer_order.Post_office AS address, [Protrans2].[dbo].Customer_order.Info, 
[Protrans2].[dbo].Order_line.attribute_info, 
[Protrans2].[dbo].Order_line.Number_of_items, 
[Protrans2].[dbo].Order_line.Colli_id, 
[Protrans2].[dbo].order_line.action_started,
[Protrans2].[dbo].Customer_order.production_week,
[Protrans2].[dbo].Customer_order.packlist_ready,
[Protrans2].[dbo].Customer_order.Do_Assembly,
CAST(dbo.Transport.Transport_Week AS nvarchar(2)) + '- ' + dbo.Transport.Transport_name AS transport_details, 
ISNULL(dbo.Transport.Transport_Year, 9999) AS transport_year, dbo.Transport.Transport_Week, dbo.Transport.load_time,
dbo.Transport.Loading_date,
dbo.product_area_group.product_area_group_name
  FROM [Protrans2].[dbo].[Order_line] INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id
  INNER JOIN
                      [Protrans2].[dbo].Customer_order ON [Protrans2].[dbo].Order_line.Order_id = [Protrans2].[dbo].Customer_order.Order_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id INNER JOIN
                      [Protrans2].[dbo].Customer ON [Protrans2].[dbo].Customer_order.Customer_id = [Protrans2].[dbo].Customer.Customer_id inner join 
  [F0100].[dbo].[OrdLn] on order_line.ord_no=[F0100].[dbo].[OrdLn].ordno and 
  order_line.ln_no=[F0100].[dbo].[OrdLn].lnno inner join 
  [F0100].[dbo].[Prod] on [F0100].[dbo].[OrdLn].ProdNo=[F0100].[dbo].[Prod].ProdNo and [F0100].[dbo].[Prod].PrCatNo in(1643 ,2007100)
  where
  dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0



/******************************************************************************

update application_param set param_value='Gulvspon;Epoxypakke' where param_name='not_package';


/*****************************************************************************

update article_type set top_level=1 where article_type_name='Igarasjen Epoxybelegg';