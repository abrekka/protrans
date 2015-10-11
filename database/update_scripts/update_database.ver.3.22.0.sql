CREATE VIEW [dbo].[own_jalinjer_cost_v]
AS
SELECT customer_order.order_id,sum(cost_amount) as sum_cost_amount 
				from customer_order left outer join order_cost on Customer_order.Order_id=Order_cost.order_id
				and exists(select 1 
								from cost_unit 
								where order_cost.cost_unit_id=cost_unit.cost_unit_id and 
										lower(cost_unit.cost_unit_name)='kunde') and
						exists(select 1 
								from cost_type
								where order_cost.cost_type_id=cost_type.cost_type_id and
									lower(cost_type.cost_type_name)=lower('jalinjer'))
										group by customer_order.order_id


/*********************************************************************************************************************************************

ALTER VIEW [dbo].[order_segment_no_v]
AS
SELECT dbo.Customer_order.Order_id, 
dbo.Customer_order.Order_nr, 
dbo.Customer_order.agreement_date,
dbo.Customer_order.Order_date, 
dbo.Customer_order.Salesman,
dbo.Construction_type.Name as construction_type_name,
Intelle_Ordre.dbo.wOrd.SegmentNo,
dbo.own_assembly_cost_v.sum_cost_amount as assembly_cost,
dbo.Customer_order.Postal_code,
dbo.Customer.customer_nr,
dbo.Customer.First_name +' '+dbo.Customer.Last_name as customer_full_name,
dbo.own_production_cost_v.sum_cost_amount as own_production,
dbo.own_delivery_cost_v.sum_cost_amount as delivery_cost,
dbo.own_production_cost_internal_v.sum_cost_amount as own_production_internal,
dbo.own_jalinjer_cost_v.sum_cost_amount as jalinjer,
dbo.product_area.product_area_nr,
dbo.product_area.product_area as product_area_name,
(case when (dbo.own_production_cost_v.sum_cost_amount<>0 and dbo.own_production_cost_internal_v.sum_cost_amount<>0) then dbo.own_production_cost_v.sum_cost_amount-dbo.own_production_cost_internal_v.sum_cost_amount else 0 end) as contribution_margin,
(case when (dbo.own_production_cost_v.sum_cost_amount<>0 and dbo.own_production_cost_internal_v.sum_cost_amount<>0) then (dbo.own_production_cost_v.sum_cost_amount-dbo.own_production_cost_internal_v.sum_cost_amount)/dbo.own_production_cost_v.sum_cost_amount else 0 end) as contribution_rate
FROM     dbo.Customer_order LEFT OUTER JOIN
                  Intelle_Ordre.dbo.wOrd ON Intelle_Ordre.dbo.wOrd.SOSaleNo = dbo.Customer_order.Order_nr inner join
dbo.Construction_type on dbo.Customer_order.Construction_type_id=dbo.Construction_type.Construction_type_id inner join
dbo.Customer on dbo.Customer_order.Customer_id=dbo.Customer.Customer_id inner join
dbo.product_area on dbo.Customer_order.product_area_id=dbo.product_area.product_area_id left outer join
dbo.own_assembly_cost_v on dbo.Customer_order.Order_id=dbo.own_assembly_cost_v.order_id left outer join
dbo.own_production_cost_v on dbo.Customer_order.Order_id=own_production_cost_v.order_id left outer join
dbo.own_delivery_cost_v on dbo.Customer_order.Order_id=dbo.own_delivery_cost_v.order_id left outer join
dbo.own_production_cost_internal_v on dbo.Customer_order.Order_id=dbo.own_production_cost_internal_v.order_id left outer join
dbo.own_jalinjer_cost_v on dbo.Customer_order.Order_id=dbo.own_jalinjer_cost_v.order_id


