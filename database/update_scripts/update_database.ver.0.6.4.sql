CREATE FUNCTION [dbo].[get_customer_cost_for_type] 
(
	-- Add the parameters for the function here
	@orderId int,
	@costTypeName nvarchar(255)
)
RETURNS int
AS
BEGIN
	DECLARE @Result numeric

	SELECT @Result = isnull(sum(cost_amount),0) 
				from order_cost 
				where order_cost.order_id=@orderId and 
						exists(select 1 
								from cost_unit 
								where order_cost.cost_unit_id=cost_unit.cost_unit_id and 
										lower(cost_unit.cost_unit_name)='kunde') and
						exists(select 1 
								from cost_type
								where order_cost.cost_type_id=cost_type.cost_type_id and
										lower(cost_type.cost_type_name)=lower(@costTypeName))

	RETURN @Result

END

/*************************************************************************************/
ALTER VIEW [dbo].[sum_order_ready_v]
AS
SELECT     CONVERT(nvarchar, dbo.Customer_order.order_ready, 102) AS order_ready, SUM(dbo.Order_cost.Cost_Amount) AS package_sum,datepart(yyyy,order_ready) as order_ready_year,datepart(ww,order_ready) as order_ready_week
FROM         dbo.Customer_order INNER JOIN
                      dbo.Order_cost ON dbo.Customer_order.Order_id = dbo.Order_cost.Order_id INNER JOIN
                      dbo.Cost_type ON dbo.Order_cost.Cost_type_id = dbo.Cost_type.Cost_type_id
WHERE     (dbo.Customer_order.order_ready IS NOT NULL) AND (dbo.Cost_type.Cost_type_name = 'Garasje')
GROUP BY CONVERT(nvarchar, dbo.Customer_order.order_ready, 102),datepart(yyyy,order_ready),datepart(ww,order_ready)


/*************************************************************************************/
CREATE VIEW [dbo].[count_agreement_v]
AS
SELECT     COUNT(Order_id) AS order_count, DATEPART(yyyy, agreement_date) AS agreement_year, DATEPART(ww, agreement_date) AS agreement_week
FROM         dbo.Customer_order
where agreement_date is not null
GROUP BY DATEPART(yyyy, agreement_date), DATEPART(ww, agreement_date)

/*************************************************************************************/
CREATE VIEW [dbo].[order_invoiced_amount_v]
AS
SELECT     count(Order_id) as order_count, 
DATEPART(yyyy, Invoice_date) AS invoice_year, 
DATEPART(ww, Invoice_date) AS invoice_week, 
sum(isnull(dbo.Get_customer_cost(Order_id),0))
                      AS invoiced_amount
FROM         dbo.Customer_order
WHERE     (Invoice_date IS NOT NULL)
group by DATEPART(yyyy, Invoice_date) , 
DATEPART(ww, Invoice_date)
/*************************************************************************************/
CREATE VIEW [dbo].[transport_week_v]
AS
SELECT     COUNT(Order_id) AS order_count, SUM(ISNULL(Do_Assembly, 0)) AS assemblied, DATEPART(yyyy, Sent) AS order_sent_year, DATEPART(ww, Sent) 
                      AS order_sent_week, SUM(dbo.get_customer_cost_for_type(Order_id, N'garasje')) AS garage_cost, SUM(dbo.get_customer_cost_for_type(Order_id, 
                      N'frakt')) AS transport_cost, SUM(dbo.get_customer_cost_for_type(Order_id, N'montering')) AS assembly_cost
FROM         dbo.Customer_order
WHERE     (Sent IS NOT NULL)
GROUP BY DATEPART(yyyy, Sent), DATEPART(ww, Sent)
/*************************************************************************************/
CREATE VIEW [dbo].[sum_order_ready_week_v]
AS
SELECT     count(order_ready) as count_order_ready,order_ready_year,order_ready_week, SUM(package_sum) AS package_sum_week
FROM         dbo.sum_order_ready_v
GROUP BY order_ready_year,order_ready_week

/*************************************************************************************/
CREATE VIEW [dbo].[sum_avvik_v]
AS
SELECT     COUNT(dbo.deviation.deviation_id) AS deviation_count, isnull(dbo.job_function.job_function_name,'') as job_function_name, DATEPART(yyyy, dbo.deviation.registration_date) as registration_year, DATEPART(ww, dbo.deviation.registration_date) 
                      AS registration_week, SUM(ISNULL(dbo.Order_cost.Cost_Amount, 0)) AS internal_cost
FROM         dbo.deviation LEFT OUTER JOIN
                      dbo.job_function ON dbo.deviation.deviation_function_id = dbo.job_function.job_function_id LEFT OUTER JOIN
                      dbo.Order_cost ON dbo.deviation.internal_cost_id = dbo.Order_cost.Order_cost_id
GROUP BY dbo.job_function.job_function_name,DATEPART(yyyy, dbo.deviation.registration_date), DATEPART(ww, dbo.deviation.registration_date)
/*************************************************************************************/
CREATE VIEW [dbo].[sum_avvik_okonomi_v]
AS
SELECT     deviation_count, job_function_name, registration_year, registration_week, internal_cost
FROM         dbo.sum_avvik_v
WHERE     (job_function_name = N'Økonomi')
/*************************************************************************************/
CREATE VIEW [dbo].[sum_avvik_produksjon_v]
AS
SELECT     deviation_count, job_function_name, registration_year, registration_week, internal_cost
FROM         dbo.sum_avvik_v
WHERE     (job_function_name = N'Produksjon')

/*************************************************************************************/
CREATE VIEW [dbo].[sum_avvik_salg_v]
AS
SELECT     deviation_count, job_function_name, registration_year, registration_week, internal_cost
FROM         dbo.sum_avvik_v
WHERE     (job_function_name = N'Salg')

/*************************************************************************************/
CREATE VIEW [dbo].[sum_avvik_transport_v]
AS
SELECT     deviation_count, job_function_name, registration_year, registration_week, internal_cost
FROM         dbo.sum_avvik_v
WHERE     (job_function_name = N'Transport')
/*************************************************************************************/

CREATE VIEW [dbo].[nokkel_produksjon_v]
AS
SELECT     dbo.sum_order_ready_week_v.count_order_ready,
dbo.sum_order_ready_week_v.package_sum_week,
 ISNULL(dbo.sum_order_ready_week_v.order_ready_year, 
                      dbo.sum_avvik_produksjon_v.registration_year) AS order_ready_year, ISNULL(dbo.sum_order_ready_week_v.order_ready_week, 
                      dbo.sum_avvik_produksjon_v.registration_week) AS order_ready_week, dbo.sum_avvik_produksjon_v.deviation_count, 
                      dbo.sum_avvik_produksjon_v.internal_cost
FROM         dbo.sum_order_ready_week_v FULL OUTER JOIN
                      dbo.sum_avvik_produksjon_v ON dbo.sum_order_ready_week_v.order_ready_year = dbo.sum_avvik_produksjon_v.registration_year AND 
                      dbo.sum_order_ready_week_v.order_ready_week = dbo.sum_avvik_produksjon_v.registration_week

/*************************************************************************************/
CREATE VIEW [dbo].[nokkel_salg_v]
AS
SELECT     dbo.count_agreement_v.order_count, ISNULL(dbo.count_agreement_v.agreement_year, dbo.sum_avvik_salg_v.registration_year) AS agreement_year, 
                      ISNULL(dbo.count_agreement_v.agreement_week, dbo.sum_avvik_salg_v.registration_week) AS agreement_week, 
                      dbo.sum_avvik_salg_v.deviation_count, dbo.sum_avvik_salg_v.internal_cost
FROM         dbo.count_agreement_v FULL OUTER JOIN
                      dbo.sum_avvik_salg_v ON dbo.count_agreement_v.agreement_year = dbo.sum_avvik_salg_v.registration_year AND 
                      dbo.count_agreement_v.agreement_week = dbo.sum_avvik_salg_v.registration_week

/*************************************************************************************/
CREATE VIEW [dbo].[nokkel_transport_v]
AS
SELECT     dbo.transport_week_v.assemblied, dbo.transport_week_v.order_count - dbo.transport_week_v.assemblied AS not_assemblied, 
                      dbo.transport_week_v.garage_cost, dbo.transport_week_v.transport_cost, dbo.transport_week_v.assembly_cost, 
                      ISNULL(dbo.transport_week_v.order_sent_year, dbo.sum_avvik_transport_v.registration_year) AS order_sent_year, 
                      ISNULL(dbo.transport_week_v.order_sent_week, dbo.sum_avvik_transport_v.registration_week) AS order_sent_week, 
                      dbo.sum_avvik_transport_v.deviation_count, dbo.sum_avvik_transport_v.internal_cost
FROM         dbo.transport_week_v FULL OUTER JOIN
                      dbo.sum_avvik_transport_v ON dbo.transport_week_v.order_sent_year = dbo.sum_avvik_transport_v.registration_year AND 
                      dbo.transport_week_v.order_sent_week = dbo.sum_avvik_transport_v.registration_week

/*************************************************************************************/
CREATE VIEW [dbo].[nokkel_okonomi_v]
AS
SELECT     dbo.order_invoiced_amount_v.order_count, dbo.order_invoiced_amount_v.invoiced_amount, ISNULL(dbo.order_invoiced_amount_v.invoice_year, 
                      dbo.sum_avvik_okonomi_v.registration_year) AS invoice_year, ISNULL(dbo.order_invoiced_amount_v.invoice_week, 
                      dbo.sum_avvik_okonomi_v.registration_week) AS invoice_week, dbo.sum_avvik_okonomi_v.deviation_count, 
                      dbo.sum_avvik_okonomi_v.internal_cost
FROM         dbo.order_invoiced_amount_v FULL OUTER JOIN
                      dbo.sum_avvik_okonomi_v ON dbo.order_invoiced_amount_v.invoice_year = dbo.sum_avvik_okonomi_v.registration_year AND 
                      dbo.order_invoiced_amount_v.invoice_week = dbo.sum_avvik_okonomi_v.registration_week

/*************************************************************************************/

ALTER VIEW [dbo].[main_package_v]
AS
SELECT     dbo.Customer_order.Order_id,dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name as customer_details,customer_order.order_nr,customer_order.postal_code+' '+customer_order.post_office as address,Construction_type.Name as construction_type_name,customer_order.info
                      , CAST(dbo.Transport.Transport_Week AS nvarchar(2)) 
                      + '- ' + dbo.Transport.Transport_name AS transport_details, dbo.is_order_ready_for_package(dbo.Customer_order.Order_id) AS package_ready, 
                      dbo.is_order_done_package(dbo.Customer_order.Order_id, dbo.Customer_order.Collies_done) AS done_package, dbo.Customer_order.Comment,transport.transport_year,transport.transport_week,transport.loading_date
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
WHERE     (dbo.Customer_order.Transport_id IS NULL) AND (dbo.Customer_order.Sent IS NULL) OR
                      (dbo.Transport.Transport_name <> 'Historisk') AND (dbo.Customer_order.Sent IS NULL)
