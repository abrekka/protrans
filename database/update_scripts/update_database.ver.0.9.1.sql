create table production_budget(
  budget_id int identity(1,1) primary key,
  budget_year int not null,
  budget_week int not null,
  budget_value int not null
);

/*******************************************************************************************************************************/

CREATE UNIQUE NONCLUSTERED INDEX [production_budget_uk] ON [dbo].[production_budget] 
(
	[budget_year] ASC,
	[budget_week] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]

/*******************************************************************************************************************************/
insert into window_access(window_name) values('Produksjonsbudsjett');

/*******************************************************************************************************************************/

ALTER VIEW [dbo].[nokkel_produksjon_v]
AS
SELECT     dbo.sum_order_ready_week_v.count_order_ready, dbo.sum_order_ready_week_v.package_sum_week, 
                      ISNULL(dbo.sum_order_ready_week_v.order_ready_year, dbo.sum_avvik_produksjon_v.registration_year) AS order_ready_year, 
                      ISNULL(dbo.sum_order_ready_week_v.order_ready_week, dbo.sum_avvik_produksjon_v.registration_week) AS order_ready_week, 
                      dbo.sum_avvik_produksjon_v.deviation_count, dbo.sum_avvik_produksjon_v.internal_cost, isnull(dbo.production_budget.budget_value,0) as budget_value,sum_order_ready_week_v.package_sum_week-isnull(dbo.production_budget.budget_value,0) as budget_deviation,((sum_order_ready_week_v.package_sum_week-isnull(dbo.production_budget.budget_value,0))/sum_order_ready_week_v.package_sum_week)*100 as budget_deviation_proc
FROM         dbo.sum_order_ready_week_v FULL OUTER JOIN
                      dbo.production_budget ON dbo.sum_order_ready_week_v.order_ready_year = dbo.production_budget.budget_year AND 
                      dbo.sum_order_ready_week_v.order_ready_week = dbo.production_budget.budget_week FULL OUTER JOIN
                      dbo.sum_avvik_produksjon_v ON dbo.sum_order_ready_week_v.order_ready_year = dbo.sum_avvik_produksjon_v.registration_year AND 
                      dbo.sum_order_ready_week_v.order_ready_week = dbo.sum_avvik_produksjon_v.registration_week

/*******************************************************************************************************************************/
CREATE VIEW [dbo].[order_reserve_v]
AS
SELECT     COUNT(Order_id) AS order_count, CASE isnull(packlist_ready, 0) WHEN 0 THEN 'Nei' ELSE 'Ja' END AS is_packlist_ready, 
                      SUM(dbo.Get_customer_cost_for_type(Order_id,'Garasje')) AS customer_cost
FROM         dbo.Customer_order
WHERE     (Sent IS NULL) and order_ready is null
GROUP BY CASE isnull(packlist_ready, 0) WHEN 0 THEN 'Nei' ELSE 'Ja' END

/*******************************************************************************************************************************/
ALTER VIEW [dbo].[order_packlist_ready_v]
AS
SELECT     COUNT(Order_id) AS order_count, 
		DATEPART(yyyy, packlist_ready) AS packlist_year, 
		DATEPART(ww, packlist_ready) AS packlist_week,
		sum(dbo.get_customer_cost_for_type(order_id,'Garasje')) as customer_cost
FROM         dbo.Customer_order
WHERE     (packlist_ready IS NOT NULL)
GROUP BY DATEPART(yyyy, packlist_ready), DATEPART(ww, packlist_ready)

/*******************************************************************************************************************************/
ALTER VIEW [dbo].[nokkel_drift_prosjektering_v]
AS
SELECT     dbo.order_packlist_ready_v.order_count, 
	ISNULL(dbo.order_packlist_ready_v.packlist_year, dbo.sum_avvik_drift_prosjektering_v.registration_year) AS packlist_year, 
	ISNULL(dbo.order_packlist_ready_v.packlist_week, dbo.sum_avvik_drift_prosjektering_v.registration_week) AS packlist_week, 
        dbo.order_packlist_ready_v.customer_cost,
        dbo.sum_avvik_drift_prosjektering_v.deviation_count, dbo.sum_avvik_drift_prosjektering_v.internal_cost
FROM         dbo.order_packlist_ready_v FULL OUTER JOIN
                      dbo.sum_avvik_drift_prosjektering_v ON dbo.order_packlist_ready_v.packlist_year = dbo.sum_avvik_drift_prosjektering_v.registration_year AND 
                      dbo.order_packlist_ready_v.packlist_week = dbo.sum_avvik_drift_prosjektering_v.registration_week
                      
/*******************************************************************************************************************************/
CREATE VIEW [dbo].[assemblied_v]
AS
SELECT     COUNT(dbo.Customer_order.Order_id) AS order_count, DATEPART(yyyy, dbo.[Assembly].assemblied_date) AS assemblied_year, DATEPART(ww, 
                      dbo.[Assembly].assemblied_date) AS assemblied_week, SUM(dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Frakt')) 
                      AS delivery_cost, SUM(dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Montering')) AS assembly_cost, 
                      SUM(dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Garasje')) AS garage_cost
FROM         dbo.Customer_order INNER JOIN
                      dbo.[Assembly] ON dbo.Customer_order.Order_id = dbo.[Assembly].Order_id
WHERE     (dbo.[Assembly].assemblied_date IS NOT NULL)
GROUP BY DATEPART(yyyy, dbo.[Assembly].assemblied_date), DATEPART(ww, dbo.[Assembly].assemblied_date)

/*******************************************************************************************************************************/

CREATE VIEW [dbo].[sum_avvik_montering_v]
AS
SELECT     deviation_count, job_function_name, registration_year, registration_week, internal_cost
FROM         dbo.sum_avvik_v
WHERE     (job_function_name = N'Montering')

/*******************************************************************************************************************************/

CREATE VIEW [dbo].[nokkel_montering_v]
AS
SELECT     dbo.assemblied_v.order_count, ISNULL(dbo.assemblied_v.assemblied_year, dbo.sum_avvik_montering_v.registration_year) AS assemblied_year, 
                      ISNULL(dbo.assemblied_v.assemblied_week, dbo.sum_avvik_montering_v.registration_week) AS assemblied_week, dbo.assemblied_v.delivery_cost, 
                      dbo.assemblied_v.assembly_cost, dbo.assemblied_v.garage_cost, dbo.sum_avvik_montering_v.deviation_count, 
                      dbo.sum_avvik_montering_v.internal_cost
FROM         dbo.assemblied_v FULL OUTER JOIN
                      dbo.sum_avvik_montering_v ON dbo.assemblied_v.assemblied_year = dbo.sum_avvik_montering_v.registration_year AND 
                      dbo.assemblied_v.assemblied_week = dbo.sum_avvik_montering_v.registration_week
