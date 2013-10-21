ALTER VIEW [dbo].[takstol_all_v] 
AS 
SELECT takstol_basis_v.*,
case article_name when 'Takstoler' then 1 else 2 end as default_article,
	dbo.get_customer_cost_for_type(order_id,'Egenproduksjon') as own_production,
dbo.get_cost_for_type(order_id,'Takstoler','Intern') as own_internal_production,
F0100.dbo.ordln.price,
F0100.dbo.ordln.Dc1p
FROM takstol_basis_v LEFT OUTER JOIN 
F0100.dbo.ordln on takstol_basis_v.ord_no=F0100.dbo.ordln.ordno and takstol_basis_v.ln_no=F0100.dbo.ordln.lnno