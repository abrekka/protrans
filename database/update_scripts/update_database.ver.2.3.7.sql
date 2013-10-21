create table budget(
  budget_id int identity(1,1) primary key,
  budget_year int not null,
  budget_week int null,
  budget_value int not null,
  budget_type int not null,
  product_area_id int not null references product_area(product_area_id)
);
/****************************************************************************************************************************************
insert into budget(budget_year,budget_week,budget_value,budget_type,product_area_id) (select budget_year,budget_week,budget_value,0,product_area_id from production_budget);
/****************************************************************************************************************************************
drop table production_budget;
/****************************************************************************************************************************************
ALTER VIEW [dbo].[nokkel_produksjon_v]
AS
SELECT     ISNULL(dbo.sum_order_ready_week_v.count_order_ready, 0) AS count_order_ready, dbo.sum_order_ready_week_v.package_sum_week, 
                      ISNULL(dbo.sum_order_ready_week_v.order_ready_year, ISNULL(dbo.sum_avvik_produksjon_v.registration_year, 
                      dbo.budget.budget_year)) AS order_ready_year, ISNULL(dbo.sum_order_ready_week_v.order_ready_week, 
                      ISNULL(dbo.sum_avvik_produksjon_v.registration_week, dbo.budget.budget_week)) AS order_ready_week, 
                      dbo.sum_avvik_produksjon_v.deviation_count, dbo.sum_avvik_produksjon_v.internal_cost, ISNULL(dbo.product_area.product_area, 
                      ISNULL(dbo.sum_order_ready_week_v.product_area, dbo.sum_avvik_produksjon_v.product_area)) AS product_area, 
                      dbo.budget.budget_value, ISNULL(dbo.sum_order_ready_week_v.package_sum_week, 0) - ISNULL(dbo.budget.budget_value, 0)
                       AS budget_deviation, (ISNULL(dbo.sum_order_ready_week_v.package_sum_week, 0) - ISNULL(dbo.budget.budget_value, 0))*100.0 
                      / CASE ISNULL(dbo.budget.budget_value, isnull(dbo.sum_order_ready_week_v.package_sum_week, 0)) 
                      WHEN 0 THEN - 1 ELSE ISNULL(dbo.budget.budget_value, dbo.sum_order_ready_week_v.package_sum_week) 
                      END AS budget_deviation_proc, ISNULL(dbo.product_area_group.product_area_group_name, 
                      ISNULL(dbo.sum_order_ready_week_v.product_area_group_name, dbo.sum_avvik_produksjon_v.product_area_group_name)) 
                      AS product_area_group_name
FROM         dbo.product_area_group INNER JOIN
                      dbo.product_area INNER JOIN
                      dbo.budget ON dbo.product_area.product_area_id = dbo.budget.product_area_id ON 
                      dbo.product_area_group.product_area_group_id = dbo.product_area.product_area_group_id FULL OUTER JOIN
                      dbo.sum_order_ready_week_v ON dbo.budget.budget_year = dbo.sum_order_ready_week_v.order_ready_year AND 
                      dbo.budget.budget_week = dbo.sum_order_ready_week_v.order_ready_week AND 
                      dbo.product_area.product_area = dbo.sum_order_ready_week_v.product_area FULL OUTER JOIN
                      dbo.sum_avvik_produksjon_v ON dbo.sum_order_ready_week_v.product_area = dbo.sum_avvik_produksjon_v.product_area AND 
                      dbo.sum_order_ready_week_v.order_ready_year = dbo.sum_avvik_produksjon_v.registration_year AND 
                      dbo.sum_order_ready_week_v.order_ready_week = dbo.sum_avvik_produksjon_v.registration_week
/****************************************************************************************************************************************
ALTER VIEW [dbo].[order_reserve_v]
AS
SELECT     COUNT(Order_id) AS order_count, 
product_area.product_area,
CASE isnull(customer_order.packlist_ready, 0) WHEN 0 THEN 'Nei' ELSE 'Ja' END AS is_packlist_ready, 
 SUM(dbo.Get_customer_cost_for_type(Order_id,'Egenproduksjon')) AS customer_cost,
SUM(dbo.Get_cost_for_type(Order_id,'Egenproduksjon','Intern')) AS own_production
FROM         dbo.Customer_order,product_area
WHERE     (Sent IS NULL) and order_ready is null and customer_order.product_area_id=product_area.product_area_id
GROUP BY --isnull(customer_order.villa,0),
product_area.product_area,
CASE isnull(customer_order.packlist_ready, 0) WHEN 0 THEN 'Nei' ELSE 'Ja' END
/****************************************************************************************************************************************
