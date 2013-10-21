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
                      dbo.production_budget ON ISNULL(dbo.sum_order_ready_week_v.order_ready_year, dbo.sum_avvik_produksjon_v.registration_year) = dbo.production_budget.budget_year AND 
                      ISNULL(dbo.sum_order_ready_week_v.order_ready_week, 
                      dbo.sum_avvik_produksjon_v.registration_week) = dbo.production_budget.budget_week



alter table deviation add order_id int references customer_order(order_id);

update deviation set order_id = (select order_id from customer_order where customer_order.order_nr=deviation.order_nr);

alter table order_line add deviation_id int references deviation(deviation_id);