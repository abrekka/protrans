ALTER VIEW [dbo].[deviation_v]
AS
SELECT     dbo.deviation.deviation_id, 
dbo.deviation.registration_date, 
dbo.deviation.user_name, 
dbo.deviation.customer_nr, 
dbo.deviation.customer_name, 
dbo.deviation.order_nr, 
dbo.product_area.product_area, 
dbo.deviation.responsible, 
job_function_own.job_function_name AS own_function, 
job_function_deviation.job_function_name AS deviation_function, 
dbo.function_category.function_category_name, 
dbo.deviation_status.deviation_status_name, 
dbo.GetISOWeekNumberFromDate(dbo.deviation.registration_date) AS registration_week, 
DATEPART(yyyy, dbo.deviation.registration_date) AS registration_year, 
dbo.deviation_internal_cost_v.sum_cost_amount AS internal_cost, 
dbo.deviation_external_cost_v.sum_cost_amount AS external_cost,  
dbo.product_area_group.product_area_group_name,dbo.deviation.initiated_by,
dbo.Customer_order.order_ready,
dbo.deviation.CS_ID,
dbo.preventive_action.preventive_action_id,
dbo.preventive_action.preventive_action_name,
dbo.deviation.date_closed,
dbo.deviation.last_changed,
dbo.post_shipment.post_shipment_id,
dbo.Transport.Transport_Year,
dbo.Transport.Transport_Week,
dbo.transport.Sent as transport_sent,
dbo.supplier.supplier_name as transportnavn,
dbo.deviation.do_assembly,
dbo.assembly.Assembly_year,
dbo.assembly.Assembly_week,
dbo.Assembly.assemblied_date,
supplier_assembly.supplier_name as montoernavn
FROM         dbo.product_area LEFT OUTER JOIN
dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id RIGHT OUTER JOIN
dbo.deviation ON dbo.product_area.product_area_id = dbo.deviation.product_area_id LEFT OUTER JOIN
dbo.Customer_order on dbo.deviation.order_id=dbo.Customer_order.Order_id left outer join
dbo.deviation_internal_cost_v ON dbo.deviation.deviation_id = dbo.deviation_internal_cost_v.deviation_id LEFT OUTER JOIN
dbo.deviation_external_cost_v ON dbo.deviation.deviation_id = dbo.deviation_external_cost_v.deviation_id LEFT OUTER JOIN
                      dbo.deviation_status ON dbo.deviation.deviation_status_id = dbo.deviation_status.deviation_status_id LEFT OUTER JOIN
                      dbo.function_category ON dbo.deviation.function_category_id = dbo.function_category.function_category_id LEFT OUTER JOIN
                      dbo.job_function AS job_function_deviation ON dbo.deviation.deviation_function_id = job_function_deviation.job_function_id LEFT OUTER JOIN
                      dbo.job_function AS job_function_own ON dbo.deviation.own_function_id = job_function_own.job_function_id left outer join
					  dbo.Assembly on dbo.Assembly.deviation_id=dbo.deviation.deviation_id left outer join
					  dbo.supplier as supplier_assembly on supplier_assembly.supplier_id=dbo.Assembly.supplier_id left outer join
					  dbo.preventive_action on dbo.preventive_action.preventive_action_id=dbo.deviation.preventive_action_id left outer join
					  dbo.post_shipment on dbo.post_shipment.deviation_id=dbo.deviation.deviation_id left outer join
					  dbo.Transport on dbo.transport.Transport_id=dbo.Post_shipment.transport_id left outer join
					  dbo.supplier on dbo.supplier.supplier_id=transport.supplier_id
