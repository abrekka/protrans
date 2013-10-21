create table procent_done(
  procent_done_id int identity(1,1) primary key,
  procent_done_year int not null,
  procent_done_week int not null,
  procent int not null,
  procent_done_comment nvarchar(1000),
  change_date datetime not null,
  user_name nvarchar(100) not null,
  order_id int references customer_order(order_id) not null
);
/*************************************************************************************************************
alter table procent_done add constraint procent_done_uq unique(order_id,procent_done_year,procent_done_week);
/*************************************************************************************************************
ALTER VIEW [dbo].[nokkel_produksjon_v]
AS
SELECT     ISNULL(dbo.sum_order_ready_week_v.count_order_ready, 0) AS count_order_ready, dbo.sum_order_ready_week_v.package_sum_week, 
                      ISNULL(dbo.sum_order_ready_week_v.order_ready_year, ISNULL(dbo.sum_avvik_produksjon_v.registration_year, 
                      dbo.production_budget.budget_year)) AS order_ready_year, ISNULL(dbo.sum_order_ready_week_v.order_ready_week, 
                      ISNULL(dbo.sum_avvik_produksjon_v.registration_week, dbo.production_budget.budget_week)) AS order_ready_week, 
                      dbo.sum_avvik_produksjon_v.deviation_count, dbo.sum_avvik_produksjon_v.internal_cost, ISNULL(dbo.product_area.product_area, 
                      ISNULL(dbo.sum_order_ready_week_v.product_area, dbo.sum_avvik_produksjon_v.product_area)) AS product_area, 
                      dbo.production_budget.budget_value, ISNULL(dbo.sum_order_ready_week_v.package_sum_week, 0) - ISNULL(dbo.production_budget.budget_value, 0)
                       AS budget_deviation, (ISNULL(dbo.sum_order_ready_week_v.package_sum_week, 0) - ISNULL(dbo.production_budget.budget_value, 0))*100.0 
                      / CASE ISNULL(dbo.production_budget.budget_value, isnull(dbo.sum_order_ready_week_v.package_sum_week, 0)) 
                      WHEN 0 THEN - 1 ELSE ISNULL(dbo.production_budget.budget_value, dbo.sum_order_ready_week_v.package_sum_week) 
                      END AS budget_deviation_proc, ISNULL(dbo.product_area_group.product_area_group_name, 
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
/*************************************************************************************************************
alter table window_access add table_names nvarchar(1000);
/*************************************************************************************************************
update window_access set table_names='TableOrders,TablePostShipments,TableTransportOrders' where window_name='Transport';
/*************************************************************************************************************
update window_access set table_names='TableProductionGavl' where window_name='Gavl';
/*************************************************************************************************************
update window_access set table_names='TableProductionTakstol' where window_name='Takstol';
/*************************************************************************************************************
update window_access set table_names='TableProductionVegg' where window_name='Vegg';
/*************************************************************************************************************
update window_access set table_names='TableProductionFront' where window_name='Front';
/*************************************************************************************************************
update window_access set table_names='TableProductionOverview' where window_name='Produksjonsoversikt';
/*************************************************************************************************************
update window_access set table_names='TablePackageTakstol' where window_name='Standard takstol';
/*************************************************************************************************************
update window_access set table_names='TablePackageGulvspon' where window_name='Gulvspon';
/*************************************************************************************************************
update window_access set table_names='TablePacklist' where window_name='Pakkliste';
/*************************************************************************************************************
update window_access set table_names='TableInvoice' where window_name='Fakturering';
/*************************************************************************************************************
update window_access set table_names='TablePaid' where window_name='Betaling';
/*************************************************************************************************************
update window_access set table_names='TableTakstein' where window_name='Takstein';
