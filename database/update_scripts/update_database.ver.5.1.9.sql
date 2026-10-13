CREATE FUNCTION [dbo].[Get_tidsbruk] 
(
	@ordno nvarchar(255),
	@type nvarchar(255)
)
RETURNS int
AS
BEGIN
	DECLARE @Result numeric

	SELECT @Result = sum(noinvoab + nofin) from Protrans2.dbo.customer_order co inner join
		     F0100.dbo.ord o on o.inf6 = co.order_nr inner join
		     F0100.dbo.ordln ol on o.ordno = ol.ordno and ol.prodno = @type
	WHERE co.order_nr=@ordno

	RETURN @Result

END;





/***************************************************************************

ALTER VIEW [dbo].[productionoverview_v]
AS
SELECT        DISTINCT dbo.Customer_order.Order_id, dbo.Customer_order.Order_nr, dbo.Customer_order.packlist_ready, dbo.Transport.Transport_Year, dbo.Transport.Transport_Week, dbo.Transport.Loading_date, 
                         dbo.Transport.Transport_name, dbo.Customer_order.special_concern, dbo.Customer_order.cached_comment AS comment, - 1 AS post_shipment_id, dbo.Customer.First_name, dbo.Customer.Last_name, 
                         dbo.Customer_order.Postal_code, dbo.Customer_order.Post_office, dbo.Construction_type.Name AS construction_type_name, dbo.Customer_order.Info, dbo.Customer_order.packlist_done_by, 
                         dbo.Customer_order.packlist_duration, dbo.product_area.product_area, dbo.product_area_group.product_area_group_name, dbo.Customer_order.production_week, dbo.Customer_order.estimated_time_wall, 
                         dbo.order_line.action_started AS wall_action_started, dbo.order_line.produced AS wall_produced, dbo.order_line.real_production_hours AS wall_real_production_hours, dbo.Customer_order.estimated_time_gavl, 
                         order_line_gavl.action_started AS gavl_action_started, order_line_gavl.produced AS gavl_produced, order_line_gavl.real_production_hours AS gavl_real_production_hours, 
                         dbo.Customer_order.estimated_time_pack, dbo.Customer_order.status, dbo.Customer_order.Do_Assembly, dbo.Assembly.Assembly_week, dbo.own_production_cost_v.sum_cost_amount AS own_production, 
                         dbo.own_production_cost_internal_v.sum_cost_amount AS own_production_internal, dbo.own_delivery_cost_v.sum_cost_amount AS delivery_cost, 
                         dbo.own_assembly_cost_v.sum_cost_amount AS assembly_cost, dbo.Customer_order.received_tross_drawing, ord_takstol_kjop.deldt AS tross_deldt, ord_takstol_kjop.cfdeldt AS tross_cfdeldt, 
                         dbo.Customer_order.order_complete, dbo.Customer_order.order_ready, dbo.Customer_order.antall_standard_vegger, dbo.Customer_order.estimated_time_k_dep,
						 dbo.get_tidsbruk(dbo.Customer_order.order_nr,'PRODUKSJONSKOSTA') as A,
						 dbo.get_tidsbruk(dbo.Customer_order.order_nr,'PRODUKSJONSKOSTA+') as Apluss,
						 dbo.get_tidsbruk(dbo.Customer_order.order_nr,'PRODUKSJONSKOSTB') as B,
						 dbo.get_tidsbruk(dbo.Customer_order.order_nr,'PRODUKSJONSKOSTBF') as BF,
						 dbo.get_tidsbruk(dbo.Customer_order.order_nr,'PRODUKSJONSKOSTC') as C,
						 dbo.get_tidsbruk(dbo.Customer_order.order_nr,'PRODUKSJONSKOSTL') as L
FROM            dbo.Customer_order LEFT OUTER JOIN
                         dbo.Transport ON dbo.Customer_order.Transport_id = dbo.transport.Transport_id INNER JOIN
                         dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                         dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                         dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                         dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
                         dbo.order_line ON dbo.Customer_order.Order_id = dbo.order_line.Order_id AND dbo.order_line.article_path = 'Vegg' AND dbo.order_line.Construction_type_article_id IS NOT NULL LEFT OUTER JOIN
                         dbo.order_line order_line_gavl ON dbo.Customer_order.Order_id = order_line_gavl.Order_id AND order_line_gavl.article_path = 'Gavl' AND order_line_gavl.Construction_type_article_id IS NOT NULL 
                         LEFT OUTER JOIN
                         dbo.Assembly ON dbo.Customer_order.Order_id = dbo.Assembly.Order_id LEFT OUTER JOIN
                         dbo.own_production_cost_v ON dbo.Customer_order.Order_id = own_production_cost_v.order_id LEFT OUTER JOIN
                         dbo.own_production_cost_internal_v ON dbo.Customer_order.Order_id = dbo.own_production_cost_internal_v.order_id LEFT OUTER JOIN
                         dbo.own_delivery_cost_v ON dbo.Customer_order.Order_id = dbo.own_delivery_cost_v.order_id LEFT OUTER JOIN
                         dbo.own_assembly_cost_v ON dbo.Customer_order.Order_id = dbo.own_assembly_cost_v.order_id LEFT OUTER JOIN
                         dbo.order_line order_line_takstol ON dbo.Customer_order.Order_id = order_line_takstol.Order_id AND order_line_takstol.article_path = 'Takstoler' AND order_line_takstol.Number_of_items > 2 LEFT OUTER JOIN
                         F0100.dbo.ordln AS ordln_takstol_kunde ON order_line_takstol.ord_no = ordln_takstol_kunde.ordno AND order_line_takstol.ln_no = ordln_takstol_kunde.lnno LEFT OUTER JOIN
                         F0100.dbo.ord AS ord_takstol_kjop ON ordln_takstol_kunde.purcno = ord_takstol_kjop.ordno
WHERE        dbo.Customer_order.sent IS NULL
UNION
SELECT        dbo.Post_shipment.order_id, dbo.Customer_order.Order_nr, NULL, dbo.Transport.Transport_Year, dbo.Transport.Transport_Week, dbo.Transport.Loading_date, dbo.Transport.Transport_name, NULL, 
                         CONVERT(nvarchar(4000), dbo.Post_shipment.cached_comment) AS comment, dbo.Post_shipment.post_shipment_id AS post_shipment_id, dbo.Customer.First_name, dbo.Customer.last_name, 
                         dbo.Customer_order.Postal_code, dbo.Customer_order.Post_office, dbo.Construction_type.Name AS construction_type_name, dbo.Customer_order.Info, NULL AS packlist_done_by, NULL AS packlist_duration, 
                         dbo.product_area.product_area, dbo.product_area_group.product_area_group_name, dbo.Customer_order.production_week, dbo.Customer_order.estimated_time_wall, NULL AS wall_action_started, NULL 
                         AS wall_produced, NULL AS wall_real_production_hours, NULL AS estimated_time_gavl, NULL AS gavl_action_started, NULL AS gavl_produced, NULL AS gavl_real_production_hours, NULL 
                         AS estimated_time_pack, NULL AS status, NULL AS Do_Assembly, NULL AS Assembly_week, NULL AS own_production, NULL AS own_production_internal, NULL AS delivery_cost, NULL AS assembly_cost, NULL 
                         AS received_tross_drawing, NULL AS tross_deldt, NULL AS tross_cfdelt, NULL AS order_complete, NULL AS order_ready, NULL AS antall_standard_vegger, NULL AS estimated_time_k_dep,
						 null as A,
						 NULL as Apluss,
						 NULL as B,
						 NULL as BF,
						 NULL as C,
						 NULL as L
FROM            dbo.Post_shipment INNER JOIN
                         Customer_order ON dbo.Post_shipment.order_id = dbo.customer_order.Order_id LEFT OUTER JOIN
                         dbo.Transport ON dbo.Post_shipment.Transport_id = dbo.transport.Transport_id INNER JOIN
                         dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                         dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                         dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                         dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id
WHERE        dbo.Post_shipment.sent IS NULL

