ALTER VIEW [dbo].[order_segment_no_v]
AS
SELECT        dbo.Customer_order.Order_id, dbo.Customer_order.Order_nr, dbo.Customer_order.agreement_date, dbo.Customer_order.Order_date, dbo.Customer_order.Salesman, 
                         dbo.Construction_type.Name AS construction_type_name, F0100.dbo.Ord.R4 AS SegmentNo, dbo.own_assembly_cost_v.sum_cost_amount AS assembly_cost, dbo.Customer_order.Postal_code, dbo.Customer.Customer_nr, 
                         dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_full_name, dbo.own_production_cost_v.sum_cost_amount AS own_production, 
                         dbo.own_delivery_cost_v.sum_cost_amount AS delivery_cost, dbo.own_production_cost_internal_v.sum_cost_amount AS own_production_internal, dbo.own_jalinjer_cost_v.sum_cost_amount AS jalinjer, 
                         dbo.product_area.product_area_nr, dbo.product_area.product_area AS product_area_name, (CASE WHEN (dbo.own_production_cost_v.sum_cost_amount <> 0 AND 
                         dbo.own_production_cost_internal_v.sum_cost_amount <> 0) THEN dbo.own_production_cost_v.sum_cost_amount - dbo.own_production_cost_internal_v.sum_cost_amount ELSE 0 END) AS contribution_margin, 
                         (CASE WHEN (dbo.own_production_cost_v.sum_cost_amount <> 0 AND dbo.own_production_cost_internal_v.sum_cost_amount <> 0) 
                         THEN (dbo.own_production_cost_v.sum_cost_amount - dbo.own_production_cost_internal_v.sum_cost_amount) / dbo.own_production_cost_v.sum_cost_amount ELSE 0 END) AS contribution_rate,
case when SuperofficeCRM7.crm7.COUNTRY.name = 'Norge' or SuperofficeCRM7.crm7.COUNTRY.name is null then dbo.county.county_name else SuperofficeCRM7.crm7.COUNTRY.name end as county_name
FROM            dbo.Customer_order LEFT OUTER JOIN
                         F0100.dbo.Ord ON CONVERT(Varchar(10), F0100.dbo.Ord.Inf6) = dbo.Customer_order.Order_nr INNER JOIN
                         dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                         dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                         dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id LEFT OUTER JOIN
                         dbo.own_assembly_cost_v ON dbo.Customer_order.Order_id = dbo.own_assembly_cost_v.order_id LEFT OUTER JOIN
                         dbo.own_production_cost_v ON dbo.Customer_order.Order_id = dbo.own_production_cost_v.order_id LEFT OUTER JOIN
                         dbo.own_delivery_cost_v ON dbo.Customer_order.Order_id = dbo.own_delivery_cost_v.order_id LEFT OUTER JOIN
                         dbo.own_production_cost_internal_v ON dbo.Customer_order.Order_id = dbo.own_production_cost_internal_v.order_id LEFT OUTER JOIN
                         dbo.own_jalinjer_cost_v ON dbo.Customer_order.Order_id = dbo.own_jalinjer_cost_v.order_id LEFT OUTER JOIN
                         dbo.transport_cost ON dbo.customer_order.Postal_code = dbo.transport_cost.postal_code LEFT OUTER JOIN
                         dbo.area ON dbo.transport_cost.area_code = dbo.area.area_code LEFT OUTER JOIN
                         dbo.county ON dbo.county.county_nr = dbo.area.county_nr left outer join
						 SuperofficeCRM7.crm7.SALE on SuperofficeCRM7.crm7.SALE.number1=dbo.Customer_order.Order_nr left OUTER JOIN
                         SuperofficeCRM7.crm7.CONTACT ON SuperofficeCRM7.crm7.CONTACT.contact_id = SuperofficeCRM7.crm7.SALE.contact_id left outer join
						 SuperofficeCRM7.crm7.COUNTRY on SuperofficeCRM7.crm7.CONTACT.country_id = SuperofficeCRM7.crm7.COUNTRY.country_id
