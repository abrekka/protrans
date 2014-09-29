CREATE VIEW [dbo].[assembly_v]
AS
SELECT dbo.[Assembly].Assembly_id,
    dbo.customer_order.order_id,
dbo.Customer_order.sent , 
dbo.[Assembly].assemblied_date, 
dbo.[Assembly].Assembly_week,
dbo.[Assembly].Assembly_year,
dbo.supplier.supplier_name,
dbo.Assembly.Planned,
dbo.Customer_order.packlist_ready,
dbo.Assembly.sent_base,
dbo.Customer_order.Order_nr,
dbo.Customer.First_name,
dbo.Customer.last_name,
dbo.Customer_order.Postal_code,
dbo.Customer_order.Post_office,
dbo.Customer_order.telephone_nr,
dbo.product_area.product_area,
dbo.Customer_order.production_week,
dbo.Transport.Transport_Year,
dbo.Transport.Transport_Week,
dbo.Transport.Transport_name,
dbo.Order_line.attribute_info as takstein_info,
dbo.own_assembly_cost_v.sum_cost_amount as assembly_cost,
dbo.Assembly.First_planned,
dbo.kraning_cost_v.sum_cost_amount as kraning_cost,
dbo.Customer_order.has_missing_collies
FROM         dbo.Customer_order INNER JOIN
dbo.product_area on dbo.Customer_order.product_area_id=dbo.product_area.product_area_id inner join
dbo.Customer on dbo.Customer_order.Customer_id=dbo.Customer.Customer_id inner join
dbo.own_assembly_cost_v on dbo.Customer_order.order_id=dbo.own_assembly_cost_v.order_id INNER JOIN
dbo.kraning_cost_v on dbo.Customer_order.order_id=dbo.kraning_cost_v.order_id INNER JOIN
                      dbo.[Assembly] ON dbo.Customer_order.Order_id = dbo.[Assembly].Order_id left outer JOIN
                      dbo.supplier on dbo.Assembly.supplier_id=dbo.supplier.supplier_id left outer join
					  dbo.Transport on dbo.Customer_order.Transport_id=dbo.Transport.Transport_id left outer join
					  dbo.Order_line on dbo.Customer_order.Order_id=dbo.Order_line.Order_id and dbo.order_line.article_path='Takstein' and dbo.order_line.attribute_info is not null

/***********************************************************************************************************************************************************************
ALTER VIEW [dbo].[packlist_v]
AS
SELECT     dbo.Customer_order.Order_id
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