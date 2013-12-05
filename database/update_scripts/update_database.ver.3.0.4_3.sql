ALTER VIEW [dbo].[takstol_all_v] 
AS 
SELECT takstol_basis_v.*,
case article_name when 'Takstoler' then 1 else 2 end as default_article,
 dbo.own_production_cost_v.sum_cost_amount as own_production,
isnull(dbo.own_takstol_intern_cost_v.sum_cost_amount,0) as own_internal_production,
dbo.own_delivery_cost_v.sum_cost_amount as delivery_cost,
F0100.dbo.ordln.price,
F0100.dbo.ordln.Dc1p
FROM takstol_basis_v inner join
own_takstol_intern_cost_v on takstol_basis_v.Order_id=own_takstol_intern_cost_v.order_id inner join
dbo.own_delivery_cost_v on takstol_basis_v.order_id=dbo.own_delivery_cost_v.order_id inner join
own_production_cost_v on takstol_basis_v.order_id=own_production_cost_v.order_id LEFT OUTER JOIN 
F0100.dbo.ordln on takstol_basis_v.ord_no=F0100.dbo.ordln.ordno and takstol_basis_v.ln_no=F0100.dbo.ordln.lnno

