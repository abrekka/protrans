alter table customer_order alter column telephone_nr nvarchar(255);
/****************************************************************************************************/
alter table construction_type add villa integer;

/****************************************************************************************************/
alter table customer_order add villa integer;

/****************************************************************************************************/
update construction_type set villa=1 where lower(name) not like '%rekke%';

/****************************************************************************************************/
update customer_order set villa=1 
  where exists(select 1 
                 from construction_type 
                 where construction_type.construction_type_id=customer_order.construction_type_id and 
                       construction_type.villa=1);
                       
/****************************************************************************************************/
ALTER VIEW [dbo].[order_packlist_ready_v]
AS
SELECT     COUNT(Order_id) AS order_count, 
isnull(customer_order.villa,0) as villa,
		DATEPART(yyyy, packlist_ready) AS packlist_year, 
		DATEPART(ww, packlist_ready) AS packlist_week,
		sum(dbo.get_customer_cost_for_type(order_id,'Garasje')) as customer_cost
FROM         dbo.Customer_order
WHERE     (packlist_ready IS NOT NULL)
GROUP BY isnull(customer_order.villa,0),DATEPART(yyyy, packlist_ready), DATEPART(ww, packlist_ready)

/****************************************************************************************************/
ALTER VIEW [dbo].[sum_avvik_v]
AS
SELECT     COUNT(dbo.deviation.deviation_id) AS deviation_count, ISNULL(dbo.Customer_order.villa, 0) AS villa, ISNULL(dbo.job_function.job_function_name, '') 
                      AS job_function_name, DATEPART(yyyy, dbo.deviation.registration_date) AS registration_year, DATEPART(ww, dbo.deviation.registration_date) 
                      AS registration_week, SUM(ISNULL(dbo.Order_cost.Cost_Amount, 0)) AS internal_cost
FROM         dbo.Customer_order INNER JOIN
                      dbo.Order_cost ON dbo.Customer_order.Order_id = dbo.Order_cost.Order_id RIGHT OUTER JOIN
                      dbo.deviation LEFT OUTER JOIN
                      dbo.job_function ON dbo.deviation.deviation_function_id = dbo.job_function.job_function_id ON 
                      dbo.Order_cost.Order_cost_id = dbo.deviation.internal_cost_id
GROUP BY dbo.job_function.job_function_name, isnull(dbo.Customer_order.villa,0), DATEPART(yyyy, dbo.deviation.registration_date), DATEPART(ww, 
                      dbo.deviation.registration_date)
/****************************************************************************************************/
ALTER VIEW [dbo].[sum_avvik_drift_prosjektering_v]
AS
SELECT     deviation_count, 
		villa,
		job_function_name, 
		registration_year, 
		registration_week, 
		internal_cost
FROM         dbo.sum_avvik_v
WHERE     (job_function_name = N'Drift / Prosjektering')
/****************************************************************************************************/
ALTER VIEW [dbo].[nokkel_drift_prosjektering_v]
AS
SELECT     dbo.order_packlist_ready_v.order_count, ISNULL(dbo.order_packlist_ready_v.packlist_year, dbo.sum_avvik_drift_prosjektering_v.registration_year) 
                      AS packlist_year, ISNULL(dbo.order_packlist_ready_v.packlist_week, dbo.sum_avvik_drift_prosjektering_v.registration_week) AS packlist_week, 
                      dbo.order_packlist_ready_v.customer_cost, dbo.sum_avvik_drift_prosjektering_v.deviation_count, dbo.sum_avvik_drift_prosjektering_v.internal_cost, 
                      isnull(dbo.order_packlist_ready_v.villa,dbo.sum_avvik_drift_prosjektering_v.villa) as villa
FROM         dbo.order_packlist_ready_v FULL OUTER JOIN
                      dbo.sum_avvik_drift_prosjektering_v ON dbo.order_packlist_ready_v.villa = dbo.sum_avvik_drift_prosjektering_v.villa AND 
                      dbo.order_packlist_ready_v.packlist_year = dbo.sum_avvik_drift_prosjektering_v.registration_year AND 
                      dbo.order_packlist_ready_v.packlist_week = dbo.sum_avvik_drift_prosjektering_v.registration_week

/****************************************************************************************************/
ALTER VIEW [dbo].[sum_avvik_salg_v]
AS
SELECT     deviation_count, 
			villa,
			job_function_name, registration_year, registration_week, internal_cost
FROM         dbo.sum_avvik_v
WHERE     (job_function_name = N'Salg')
/****************************************************************************************************/
ALTER VIEW [dbo].[count_agreement_v]
AS
SELECT     COUNT(Order_id) AS order_count, 
isnull(villa,0) as villa,DATEPART(yyyy, agreement_date) AS agreement_year, DATEPART(ww, agreement_date) AS agreement_week
FROM         dbo.Customer_order
where agreement_date is not null
GROUP BY isnull(villa,0),DATEPART(yyyy, agreement_date), DATEPART(ww, agreement_date)
/****************************************************************************************************/
ALTER VIEW [dbo].[nokkel_salg_v]
AS
SELECT     dbo.count_agreement_v.order_count, ISNULL(dbo.count_agreement_v.agreement_year, dbo.sum_avvik_salg_v.registration_year) AS agreement_year, 
                      ISNULL(dbo.count_agreement_v.agreement_week, dbo.sum_avvik_salg_v.registration_week) AS agreement_week, 
                      dbo.sum_avvik_salg_v.deviation_count, dbo.sum_avvik_salg_v.internal_cost, ISNULL(dbo.count_agreement_v.villa, dbo.sum_avvik_salg_v.villa) 
                      AS villa
FROM         dbo.count_agreement_v FULL OUTER JOIN
                      dbo.sum_avvik_salg_v ON dbo.count_agreement_v.villa = dbo.sum_avvik_salg_v.villa AND 
                      dbo.count_agreement_v.agreement_year = dbo.sum_avvik_salg_v.registration_year AND 
                      dbo.count_agreement_v.agreement_week = dbo.sum_avvik_salg_v.registration_week
/****************************************************************************************************/
ALTER VIEW [dbo].[sum_avvik_transport_v]
AS
SELECT     deviation_count, villa,job_function_name, registration_year, registration_week, internal_cost
FROM         dbo.sum_avvik_v
WHERE     (job_function_name = N'Transport')
/****************************************************************************************************/
ALTER VIEW [dbo].[transport_week_v]
AS
SELECT     COUNT(Order_id) AS order_count, 
isnull(customer_order.villa,0) as villa,
	   SUM(ISNULL(Do_Assembly, 0)) AS assemblied, 
	   transport.transport_year AS order_sent_year, 
	   transport.transport_week AS order_sent_week, 
	   SUM(dbo.get_customer_cost_for_type(Order_id, N'garasje')) AS garage_cost, 
	   SUM(dbo.get_customer_cost_for_type(Order_id,N'frakt')) AS transport_cost, 
	   SUM(dbo.get_customer_cost_for_type(Order_id, N'montering')) AS assembly_cost
FROM         dbo.Customer_order,transport
WHERE     (customer_order.Sent IS NOT NULL) and customer_order.transport_id=transport.transport_id
GROUP BY isnull(customer_order.villa,0),transport.transport_year, transport.transport_week
/****************************************************************************************************/
ALTER VIEW [dbo].[nokkel_transport_v]
AS
SELECT     dbo.transport_week_v.assemblied, dbo.transport_week_v.order_count - dbo.transport_week_v.assemblied AS not_assemblied, 
                      dbo.transport_week_v.garage_cost, dbo.transport_week_v.transport_cost, dbo.transport_week_v.assembly_cost, 
                      ISNULL(dbo.transport_week_v.order_sent_year, dbo.sum_avvik_transport_v.registration_year) AS order_sent_year, 
                      ISNULL(dbo.transport_week_v.order_sent_week, dbo.sum_avvik_transport_v.registration_week) AS order_sent_week, 
                      dbo.sum_avvik_transport_v.deviation_count, dbo.sum_avvik_transport_v.internal_cost, ISNULL(dbo.transport_week_v.villa, 
                      dbo.sum_avvik_transport_v.villa) AS villa
FROM         dbo.transport_week_v FULL OUTER JOIN
                      dbo.sum_avvik_transport_v ON dbo.transport_week_v.villa = dbo.sum_avvik_transport_v.villa AND 
                      dbo.transport_week_v.order_sent_year = dbo.sum_avvik_transport_v.registration_year AND 
                      dbo.transport_week_v.order_sent_week = dbo.sum_avvik_transport_v.registration_week
/****************************************************************************************************/
ALTER VIEW [dbo].[sum_avvik_montering_v]
AS
SELECT     deviation_count,villa, job_function_name, registration_year, registration_week, internal_cost
FROM         dbo.sum_avvik_v
WHERE     (job_function_name = N'Montering')
/****************************************************************************************************/
ALTER VIEW [dbo].[assemblied_v]
AS
SELECT     COUNT(dbo.Customer_order.Order_id) AS order_count, 
isnull(customer_order.villa,0) as villa,
DATEPART(yyyy, dbo.[Assembly].assemblied_date) AS assemblied_year, DATEPART(ww, 
                      dbo.[Assembly].assemblied_date) AS assemblied_week, SUM(dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Frakt')) 
                      AS delivery_cost, SUM(dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Montering')) AS assembly_cost, 
                      SUM(dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Garasje')) AS garage_cost
FROM         dbo.Customer_order INNER JOIN
                      dbo.[Assembly] ON dbo.Customer_order.Order_id = dbo.[Assembly].Order_id
WHERE     (dbo.[Assembly].assemblied_date IS NOT NULL)
GROUP BY isnull(customer_order.villa,0),DATEPART(yyyy, dbo.[Assembly].assemblied_date), DATEPART(ww, dbo.[Assembly].assemblied_date)
/****************************************************************************************************/
ALTER VIEW [dbo].[nokkel_montering_v]
AS
SELECT     dbo.assemblied_v.order_count, ISNULL(dbo.assemblied_v.assemblied_year, dbo.sum_avvik_montering_v.registration_year) AS assemblied_year, 
                      ISNULL(dbo.assemblied_v.assemblied_week, dbo.sum_avvik_montering_v.registration_week) AS assemblied_week, dbo.assemblied_v.delivery_cost, 
                      dbo.assemblied_v.assembly_cost, dbo.assemblied_v.garage_cost, dbo.sum_avvik_montering_v.deviation_count, 
                      dbo.sum_avvik_montering_v.internal_cost, ISNULL(dbo.assemblied_v.villa, dbo.sum_avvik_montering_v.villa) AS villa
FROM         dbo.assemblied_v FULL OUTER JOIN
                      dbo.sum_avvik_montering_v ON dbo.assemblied_v.villa = dbo.sum_avvik_montering_v.villa AND 
                      dbo.assemblied_v.assemblied_year = dbo.sum_avvik_montering_v.registration_year AND 
                      dbo.assemblied_v.assemblied_week = dbo.sum_avvik_montering_v.registration_week
/****************************************************************************************************/
ALTER VIEW [dbo].[sum_avvik_okonomi_v]
AS
SELECT     deviation_count,villa, job_function_name, registration_year, registration_week, internal_cost
FROM         dbo.sum_avvik_v
WHERE     (job_function_name = N'Økonomi')
/****************************************************************************************************/
ALTER VIEW [dbo].[order_invoiced_amount_v]
AS
SELECT     count(Order_id) as order_count,
isnull(customer_order.villa,0) as villa,
DATEPART(yyyy, Invoice_date) AS invoice_year, 
DATEPART(ww, Invoice_date) AS invoice_week, 
sum(isnull(dbo.Get_customer_cost(Order_id),0))
                      AS invoiced_amount
FROM         dbo.Customer_order
WHERE     (Invoice_date IS NOT NULL)
group by isnull(customer_order.villa,0),DATEPART(yyyy, Invoice_date) , 
DATEPART(ww, Invoice_date)
/****************************************************************************************************/
ALTER VIEW [dbo].[nokkel_okonomi_v]
AS
SELECT     dbo.order_invoiced_amount_v.order_count, dbo.order_invoiced_amount_v.invoiced_amount, ISNULL(dbo.order_invoiced_amount_v.invoice_year, 
                      dbo.sum_avvik_okonomi_v.registration_year) AS invoice_year, ISNULL(dbo.order_invoiced_amount_v.invoice_week, 
                      dbo.sum_avvik_okonomi_v.registration_week) AS invoice_week, dbo.sum_avvik_okonomi_v.deviation_count, 
                      dbo.sum_avvik_okonomi_v.internal_cost, ISNULL(dbo.order_invoiced_amount_v.villa, dbo.sum_avvik_okonomi_v.villa) AS villa
FROM         dbo.order_invoiced_amount_v FULL OUTER JOIN
                      dbo.sum_avvik_okonomi_v ON dbo.order_invoiced_amount_v.villa = dbo.sum_avvik_okonomi_v.villa AND 
                      dbo.order_invoiced_amount_v.invoice_year = dbo.sum_avvik_okonomi_v.registration_year AND 
                      dbo.order_invoiced_amount_v.invoice_week = dbo.sum_avvik_okonomi_v.registration_week
/****************************************************************************************************/
ALTER VIEW [dbo].[order_reserve_v]
AS
SELECT     COUNT(Order_id) AS order_count, 
isnull(customer_order.villa,0) as villa,
CASE isnull(packlist_ready, 0) WHEN 0 THEN 'Nei' ELSE 'Ja' END AS is_packlist_ready, 
 SUM(dbo.Get_customer_cost_for_type(Order_id,'Garasje')) AS customer_cost
FROM         dbo.Customer_order
WHERE     (Sent IS NULL) and order_ready is null
GROUP BY isnull(customer_order.villa,0),
CASE isnull(packlist_ready, 0) WHEN 0 THEN 'Nei' ELSE 'Ja' END
/****************************************************************************************************/
ALTER VIEW [dbo].[sum_order_ready_v]
AS
SELECT     count(customer_order.order_id) as order_count,
isnull(customer_order.villa,0) as villa,
CONVERT(nvarchar, dbo.Customer_order.order_ready, 102) AS order_ready, SUM(dbo.Order_cost.Cost_Amount) AS package_sum,datepart(yyyy,order_ready) as order_ready_year,datepart(ww,order_ready) as order_ready_week
FROM         dbo.Customer_order INNER JOIN
                      dbo.Order_cost ON dbo.Customer_order.Order_id = dbo.Order_cost.Order_id INNER JOIN
                      dbo.Cost_type ON dbo.Order_cost.Cost_type_id = dbo.Cost_type.Cost_type_id
WHERE     (dbo.Customer_order.order_ready IS NOT NULL) AND (dbo.Cost_type.Cost_type_name = 'Garasje')
GROUP BY isnull(customer_order.villa,0),CONVERT(nvarchar, dbo.Customer_order.order_ready, 102),datepart(yyyy,order_ready),datepart(ww,order_ready)

/***************************************************************************************/

ALTER VIEW [dbo].[sum_order_ready_week_v]
AS
SELECT     sum(order_count) as count_order_ready,
villa,
order_ready_year,order_ready_week, SUM(package_sum) AS package_sum_week
FROM         dbo.sum_order_ready_v
GROUP BY villa,order_ready_year,order_ready_week
/***************************************************************************************/
ALTER VIEW [dbo].[sum_avvik_produksjon_v]
AS
SELECT     deviation_count,villa, job_function_name, registration_year, registration_week, internal_cost
FROM         dbo.sum_avvik_v
WHERE     (job_function_name = N'Produksjon')
/***************************************************************************************/
ALTER VIEW [dbo].[nokkel_produksjon_v]
AS
SELECT     ISNULL(dbo.sum_order_ready_week_v.count_order_ready, 0) AS count_order_ready, ISNULL(dbo.sum_order_ready_week_v.package_sum_week, 0) 
                      AS package_sum_week, ISNULL(ISNULL(dbo.sum_order_ready_week_v.order_ready_year, dbo.sum_avvik_produksjon_v.registration_year), 
                      dbo.production_budget.budget_year) AS order_ready_year, ISNULL(ISNULL(dbo.sum_order_ready_week_v.order_ready_week, 
                      dbo.sum_avvik_produksjon_v.registration_week), dbo.production_budget.budget_week) AS order_ready_week, 
                      dbo.sum_avvik_produksjon_v.deviation_count, dbo.sum_avvik_produksjon_v.internal_cost, ISNULL(dbo.production_budget.budget_value, 0) 
                      AS budget_value, 
ISNULL(dbo.sum_order_ready_week_v.package_sum_week, 0) - ISNULL(dbo.production_budget.budget_value, 0) AS budget_deviation, 
(ISNULL(dbo.sum_order_ready_week_v.package_sum_week, 0) - ISNULL(dbo.production_budget.budget_value, 0)) 
                      / CASE ISNULL(dbo.production_budget.budget_value, isnull(dbo.sum_order_ready_week_v.package_sum_week,0)) 
                      WHEN 0 THEN - 1 ELSE ISNULL(dbo.production_budget.budget_value, dbo.sum_order_ready_week_v.package_sum_week) 
                      END * 100 AS budget_deviation_proc, ISNULL(dbo.sum_order_ready_week_v.villa, dbo.sum_avvik_produksjon_v.villa) AS villa
FROM         dbo.sum_order_ready_week_v FULL OUTER JOIN
                      dbo.sum_avvik_produksjon_v ON dbo.sum_order_ready_week_v.villa = dbo.sum_avvik_produksjon_v.villa AND 
                      dbo.sum_order_ready_week_v.order_ready_year = dbo.sum_avvik_produksjon_v.registration_year AND 
                      dbo.sum_order_ready_week_v.order_ready_week = dbo.sum_avvik_produksjon_v.registration_week FULL OUTER JOIN
                      dbo.production_budget ON dbo.sum_order_ready_week_v.order_ready_year = dbo.production_budget.budget_year AND 
                      dbo.sum_order_ready_week_v.order_ready_week = dbo.production_budget.budget_week
/***************************************************************************************/