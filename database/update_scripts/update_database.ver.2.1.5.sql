CREATE VIEW [dbo].[takstol_basis_v] 
AS 
SELECT dbo.Order_line.Order_line_id, 
	dbo.Customer.Customer_nr, 
	CAST(dbo.Customer.Customer_nr AS nvarchar(10)) + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, 
	dbo.Customer_order.Order_id, 
	dbo.Customer_order.Order_nr, 
	dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, 
	dbo.Customer_order.Info, 
	dbo.Construction_type.Name AS construction_type_name, 
	dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, 
	dbo.Order_line.attribute_info + dbo.get_attribute_info(dbo.Order_line.Order_id, N'Gavl') AS Attribute_info, 
	dbo.Order_line.Number_of_items, 
	dbo.Transport.Loading_date, 
	dbo.Order_line.Colli_id, 
	dbo.Order_line.produced, 
	CAST(dbo.Transport.Transport_Week AS nvarchar(2)) + '- ' + dbo.Transport.Transport_name AS transport_details, 
	dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment, 
	ISNULL(dbo.Transport.Transport_Year, 9999) AS transport_year, 
	dbo.Transport.Transport_Week, 
	dbo.Transport.load_time, 
	dbo.Order_line.post_shipment_id, 
	dbo.product_area_group.product_area_group_name, 
	dbo.Order_line.action_started, 
	dbo.get_attribute_value(dbo.Order_line.Order_line_id, N'Egenordre') AS egenordre, 
	dbo.is_order_line_default(dbo.Order_line.Order_line_id, dbo.Order_line.is_default) AS is_default,
	customer_order.production_date,production_unit.production_unit_name,
	dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) as sent,
	dbo.Order_line.ord_no,
	dbo.Order_line.ln_no
FROM dbo.Order_line INNER JOIN 
	dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN 
	dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN 
	dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN 
	dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN 
	dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN 
    dbo.production_unit ON dbo.Order_line.production_unit_id = dbo.production_unit.production_unit_id LEFT OUTER JOIN
	dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id  --CROSS JOIN
                     --dbo.application_param
WHERE (dbo.Transport.Transport_Week is null or dbo.Transport.Transport_Week <> 0) and
exists(select 1 from application_param where application_param.param_name like 'takstol_artikkel%' AND application_param.param_value=order_line.article_path )
/**********************************************************************************************************************************************************************
CREATE VIEW [dbo].[takstol_all_v] 
AS 
SELECT takstol_basis_v.*,
case article_name when 'Takstoler' then 1 else 2 end as default_article,
	dbo.get_customer_cost_for_type(order_id,'Egenproduksjon') as own_production,
dbo.get_cost_for_type(order_id,'Takstoler','Intern') as own_internal_production,
F0100.dbo.ordln.price
FROM takstol_basis_v LEFT OUTER JOIN 
F0100.dbo.ordln on takstol_basis_v.ord_no=F0100.dbo.ordln.ordno and takstol_basis_v.ln_no=F0100.dbo.ordln.lnno
/**********************************************************************************************************************************************************************
ALTER VIEW [dbo].[takstol_v] 
AS 
select takstol_basis_v.*,case article_name when 'Takstoler' then 1 else 2 end as default_article from takstol_basis_v where sent=0
/**********************************************************************************************************************************************************************
ALTER VIEW [dbo].[takstol_production_v]
AS
select * from takstol_v where is_default=0
/**********************************************************************************************************************************************************************
ALTER VIEW [dbo].[takstol_package_v]
AS
select * from takstol_v where is_default=1
/**********************************************************************************************************************************************************************
CREATE FUNCTION [dbo].[get_cost_for_type] 
(
	-- Add the parameters for the function here
	@orderId int,
	@costTypeName nvarchar(255),
	@costUnitName nvarchar(255)
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
										lower(cost_unit.cost_unit_name)=lower(@costUnitName)) and
						exists(select 1 
								from cost_type
								where order_cost.cost_type_id=cost_type.cost_type_id and
										lower(cost_type.cost_type_name)=lower(@costTypeName))

	RETURN @Result

END

/**********************************************************************************************************************************************************************