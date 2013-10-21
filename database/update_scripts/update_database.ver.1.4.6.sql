ALTER VIEW [dbo].[sum_order_ready_week_v]
AS
SELECT     SUM(order_count) AS count_order_ready, product_area, order_ready_year, order_ready_week, SUM(package_sum) AS package_sum_week, 
                      product_area_group_name
FROM         dbo.sum_order_ready_v
GROUP BY product_area, order_ready_year, order_ready_week, product_area_group_name
/*****************************************************************************************/
ALTER VIEW [dbo].[sum_avvik_v]
AS
SELECT     COUNT(DISTINCT dbo.deviation.deviation_id) AS deviation_count, ISNULL(dbo.job_function.job_function_name, 'Uspesifisert') AS job_function_name, 
                      DATEPART(yyyy, dbo.deviation.registration_date) AS registration_year, DATEPART(ww, dbo.deviation.registration_date) AS registration_week, 
                      SUM(ISNULL(dbo.Order_cost.Cost_Amount, 0)) AS internal_cost, DATEPART(month, dbo.deviation.registration_date) AS registration_month, 
                      CASE isnull(deviation.date_closed, 0) WHEN 0 THEN 0 ELSE 1 END AS closed, dbo.product_area.product_area, 
                      dbo.product_area_group.product_area_group_name
FROM         dbo.Customer_order INNER JOIN
                      dbo.Order_cost ON dbo.Customer_order.Order_id = dbo.Order_cost.Order_id RIGHT OUTER JOIN
                      dbo.deviation LEFT OUTER JOIN
                      dbo.product_area_group INNER JOIN
                      dbo.product_area ON dbo.product_area_group.product_area_group_id = dbo.product_area.product_area_group_id ON 
                      dbo.deviation.product_area_id = dbo.product_area.product_area_id ON dbo.Order_cost.deviation_id = dbo.deviation.deviation_id AND 
                      dbo.is_internal_cost(dbo.Order_cost.Order_cost_id) = 1 LEFT OUTER JOIN
                      dbo.job_function ON dbo.deviation.deviation_function_id = dbo.job_function.job_function_id
GROUP BY dbo.job_function.job_function_name, DATEPART(yyyy, dbo.deviation.registration_date), DATEPART(ww, dbo.deviation.registration_date), 
                      DATEPART(month, dbo.deviation.registration_date), CASE isnull(deviation.date_closed, 0) WHEN 0 THEN 0 ELSE 1 END, 
                      dbo.product_area.product_area, dbo.product_area_group.product_area_group_name
/*****************************************************************************************/
ALTER VIEW [dbo].[sum_avvik_produksjon_v]
AS
SELECT     SUM(deviation_count) AS deviation_count, product_area, job_function_name, registration_year, registration_week, SUM(internal_cost) AS internal_cost,
                       product_area_group_name
FROM         dbo.sum_avvik_v
WHERE     (job_function_name = N'Produksjon')
GROUP BY product_area, job_function_name, registration_year, registration_week, product_area_group_name
/*****************************************************************************************/
ALTER VIEW [dbo].[nokkel_produksjon_v]
AS
SELECT     ISNULL(dbo.sum_order_ready_week_v.count_order_ready, 0) AS count_order_ready, dbo.sum_order_ready_week_v.package_sum_week, 
                      ISNULL(dbo.sum_order_ready_week_v.order_ready_year, ISNULL(dbo.sum_avvik_produksjon_v.registration_year, 
                      dbo.production_budget.budget_year)) AS order_ready_year, ISNULL(dbo.sum_order_ready_week_v.order_ready_week, 
                      ISNULL(dbo.sum_avvik_produksjon_v.registration_week, dbo.production_budget.budget_week)) AS order_ready_week, 
                      dbo.sum_avvik_produksjon_v.deviation_count, dbo.sum_avvik_produksjon_v.internal_cost, ISNULL(dbo.product_area.product_area, 
                      ISNULL(dbo.sum_order_ready_week_v.product_area, dbo.sum_avvik_produksjon_v.product_area)) AS product_area, 
                      dbo.production_budget.budget_value, ISNULL(dbo.sum_order_ready_week_v.package_sum_week, 0) - ISNULL(dbo.production_budget.budget_value, 0)
                       AS budget_deviation, (ISNULL(dbo.sum_order_ready_week_v.package_sum_week, 0) - ISNULL(dbo.production_budget.budget_value, 0)) 
                      / CASE ISNULL(dbo.production_budget.budget_value, isnull(dbo.sum_order_ready_week_v.package_sum_week, 0)) 
                      WHEN 0 THEN - 1 ELSE ISNULL(dbo.production_budget.budget_value, dbo.sum_order_ready_week_v.package_sum_week) 
                      END * 100 AS budget_deviation_proc, ISNULL(dbo.product_area_group.product_area_group_name, 
                      ISNULL(dbo.sum_order_ready_week_v.product_area_group_name, dbo.sum_avvik_produksjon_v.product_area_group_name)) 
                      AS product_area_group_name
FROM         dbo.product_area_group INNER JOIN
                      dbo.product_area INNER JOIN
                      dbo.production_budget ON dbo.product_area.product_area_id = dbo.production_budget.product_area_id ON 
                      dbo.product_area_group.product_area_group_id = dbo.product_area.product_area_group_id FULL OUTER JOIN
                      dbo.sum_order_ready_week_v ON dbo.production_budget.budget_year = dbo.sum_order_ready_week_v.order_ready_year AND 
                      dbo.production_budget.budget_week = dbo.sum_order_ready_week_v.order_ready_week AND 
                      dbo.product_area.product_area = dbo.sum_order_ready_week_v.product_area FULL OUTER JOIN
                      dbo.sum_avvik_produksjon_v ON dbo.sum_order_ready_week_v.product_area = dbo.sum_avvik_produksjon_v.product_area AND 
                      dbo.sum_order_ready_week_v.order_ready_year = dbo.sum_avvik_produksjon_v.registration_year AND 
                      dbo.sum_order_ready_week_v.order_ready_week = dbo.sum_avvik_produksjon_v.registration_week
/*****************************************************************************************/
insert into window_access(window_name) values ('Produktivitet');
insert into window_access(window_name) values ('Ikke fakturert');
insert into window_access(window_name) values ('Status alle ordre ikke sendt');
insert into window_access(window_name) values ('Egenproduksjon');
insert into window_access(window_name) values ('Ettersendinger sendt');
insert into window_access(window_name) values ('Telleliste');
insert into window_access(window_name) values ('Transportert i periode');
insert into window_access(window_name) values ('Pakkliste ikke klar');
insert into window_access(window_name) values ('Avvik - Skjema gjennomgåelse');
/*****************************************************************************************/
insert into product_area_group(product_area_group_id,product_area_group_name) values(0,'Alle');