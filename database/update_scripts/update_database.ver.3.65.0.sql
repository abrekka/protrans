alter table [dbo].[Customer_order] add estimated_time_k_dep numeric(10,2);


/*******************************************************************************************

ALTER VIEW [dbo].[productionoverview_v]
AS
SELECT dbo.Customer_order.Order_id, dbo.Customer_order.Order_nr,
dbo.Customer_order.packlist_ready,dbo.Transport.Transport_Year,
dbo.Transport.Transport_Week,dbo.Transport.Loading_date,
dbo.Transport.Transport_name,dbo.Customer_order.special_concern,
dbo.Customer_order.cached_comment as comment,-1 as post_shipment_id,
dbo.Customer.First_name,dbo.Customer.Last_name,
dbo.Customer_order.Postal_code,dbo.Customer_order.Post_office,
dbo.Construction_type.Name  as construction_type_name,
dbo.Customer_order.Info,dbo.Customer_order.packlist_done_by,
dbo.Customer_order.packlist_duration,dbo.product_area.product_area,
dbo.product_area_group.product_area_group_name,dbo.Customer_order.production_week,
dbo.Customer_order.estimated_time_wall,dbo.order_line.action_started as wall_action_started,
dbo.order_line.produced as wall_produced,dbo.order_line.real_production_hours as wall_real_production_hours,
dbo.Customer_order.estimated_time_gavl,order_line_gavl.action_started as gavl_action_started,
order_line_gavl.produced as gavl_produced,order_line_gavl.real_production_hours as gavl_real_production_hours,
dbo.Customer_order.estimated_time_pack,
dbo.Customer_order.status,
dbo.Customer_order.Do_Assembly,
dbo.Assembly.Assembly_week,
dbo.own_production_cost_v.sum_cost_amount as own_production,
dbo.own_production_cost_internal_v.sum_cost_amount as own_production_internal,
dbo.own_delivery_cost_v.sum_cost_amount as delivery_cost,
dbo.own_assembly_cost_v.sum_cost_amount as assembly_cost,
dbo.Customer_order.received_tross_drawing,
ord_takstol_kjop.deldt as tross_deldt,
ord_takstol_kjop.cfdeldt as tross_cfdeldt,
dbo.Customer_order.order_complete,
dbo.Customer_order.order_ready,
dbo.Customer_order.antall_standard_vegger,
dbo.Customer_order.estimated_time_k_dep
FROM     dbo.Customer_order left outer join
dbo.Transport on dbo.Customer_order.Transport_id=dbo.transport.Transport_id inner join
dbo.Customer on dbo.Customer_order.Customer_id=dbo.Customer.Customer_id inner join
dbo.Construction_type on dbo.Customer_order.Construction_type_id=dbo.Construction_type.Construction_type_id inner join
dbo.product_area on dbo.Customer_order.product_area_id=dbo.product_area.product_area_id inner join
dbo.product_area_group on dbo.product_area.product_area_group_id=dbo.product_area_group.product_area_group_id left outer join
dbo.order_line on dbo.Customer_order.Order_id=dbo.order_line.Order_id and dbo.order_line.article_path='Vegg' and dbo.order_line.Construction_type_article_id is not null left outer join
dbo.order_line order_line_gavl on dbo.Customer_order.Order_id=order_line_gavl.Order_id and order_line_gavl.article_path='Gavl' and order_line_gavl.Construction_type_article_id is not null left outer join
dbo.Assembly on dbo.Customer_order.Order_id=dbo.Assembly.Order_id left outer join
dbo.own_production_cost_v on dbo.Customer_order.Order_id=own_production_cost_v.order_id left outer join
dbo.own_production_cost_internal_v on dbo.Customer_order.Order_id=dbo.own_production_cost_internal_v.order_id left outer join
dbo.own_delivery_cost_v on dbo.Customer_order.Order_id=dbo.own_delivery_cost_v.order_id left outer join
dbo.own_assembly_cost_v on dbo.Customer_order.Order_id=dbo.own_assembly_cost_v.order_id left outer join
dbo.order_line order_line_takstol on dbo.Customer_order.Order_id=order_line_takstol.Order_id and order_line_takstol.article_path='Takstoler' and order_line_takstol.Number_of_items>2 left outer join
F0100.dbo.ordln as ordln_takstol_kunde on order_line_takstol.ord_no = ordln_takstol_kunde.ordno and order_line_takstol.ln_no=ordln_takstol_kunde.lnno left outer join
F0100.dbo.ord as ord_takstol_kjop on ordln_takstol_kunde.purcno=ord_takstol_kjop.ordno
WHERE  dbo.Customer_order.sent is null
union
select dbo.Post_shipment.order_id,
dbo.Customer_order.Order_nr,
null,
dbo.Transport.Transport_Year,
dbo.Transport.Transport_Week,
dbo.Transport.Loading_date,
dbo.Transport.Transport_name,
null,
convert(nvarchar(4000),dbo.Post_shipment.cached_comment) as comment,
dbo.Post_shipment.post_shipment_id as post_shipment_id,
dbo.Customer.First_name,
dbo.Customer.last_name,
dbo.Customer_order.Postal_code,
dbo.Customer_order.Post_office,
dbo.Construction_type.Name as construction_type_name,
dbo.Customer_order.Info,
null as packlist_done_by,
null as packlist_duration,
dbo.product_area.product_area,
dbo.product_area_group.product_area_group_name,
dbo.Customer_order.production_week,
dbo.Customer_order.estimated_time_wall,
null as wall_action_started,
null as wall_produced,
null as wall_real_production_hours,
null as estimated_time_gavl,
null as gavl_action_started,
null as gavl_produced,
null as gavl_real_production_hours,
null as estimated_time_pack,
null as status,
null as Do_Assembly,
null as Assembly_week,
null as own_production,
null as own_production_internal,
null as delivery_cost,
null as assembly_cost,
null as received_tross_drawing,
null as tross_deldt,
null as tross_cfdelt,
null as order_complete,
null as order_ready,
null as antall_standard_vegger,
null as estimated_time_k_dep
from dbo.Post_shipment inner join
Customer_order on dbo.Post_shipment.order_id=dbo.customer_order.Order_id left outer join
dbo.Transport on dbo.Post_shipment.Transport_id=dbo.transport.Transport_id inner join
dbo.Customer on dbo.Customer_order.Customer_id=dbo.Customer.Customer_id inner join
dbo.Construction_type on dbo.Customer_order.Construction_type_id=dbo.Construction_type.Construction_type_id inner join
dbo.product_area on dbo.Customer_order.product_area_id=dbo.product_area.product_area_id  inner join
dbo.product_area_group on dbo.product_area.product_area_group_id=dbo.product_area_group.product_area_group_id
where dbo.Post_shipment.sent is null
GO


