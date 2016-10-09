ALTER VIEW [dbo].[takstol_basis_v] 
AS 
SELECT distinct dbo.Order_line.Order_line_id, 
	dbo.Customer.Customer_nr, 
dbo.Customer.First_name + ' ' + dbo.Customer.Last_name as customer_name,
	CAST(dbo.Customer.Customer_nr AS nvarchar(10)) + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, 
	dbo.Customer_order.Order_id, 
	dbo.Customer_order.Order_nr, 
	dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, 
	dbo.Customer_order.Info, 
customer_order.probability,
customer_order.packlist_ready,
	dbo.Construction_type.Name AS construction_type_name, 
	dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, 
	--dbo.Order_line.attribute_info AS Attribute_info,
	F0100.dbo.ordln.descr AS Attribute_info,
	dbo.Order_line.Number_of_items, 
	dbo.Transport.Loading_date, 
	dbo.Order_line.Colli_id, 
	dbo.Order_line.produced, 
	CAST(dbo.Transport.Transport_Week AS nvarchar(2)) + '- ' + dbo.Transport.Transport_name AS transport_details, 
	customer_order_comment_v.comment,
	ISNULL(dbo.Transport.Transport_Year, 9999) AS transport_year, 
	dbo.Transport.Transport_Week, 
	dbo.Transport.load_time, 
	dbo.Order_line.post_shipment_id, 
	dbo.product_area_group.product_area_group_name, 
	dbo.Order_line.action_started, 
	dbo.order_line_attribute_value_egenordre_v.order_line_attribute_value AS egenordre, 
	dbo.is_order_line_default(dbo.Order_line.Order_line_id, dbo.Order_line.is_default) AS is_default,
	customer_order.production_date,production_unit.production_unit_name,
	dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) as sent,
	dbo.Order_line.ord_no,
	dbo.Order_line.ln_no,
	dbo.Customer_order.tross_drawer,
dbo.order_line.cutting_started,
dbo.order_line.cutting_done,
dbo.Customer_order.production_week
FROM dbo.Order_line inner join
F0100.dbo.ordln on F0100.dbo.ordln.ordno=dbo.order_line.ord_no and dbo.order_line.ln_no=F0100.dbo.ordln.lnno inner join
customer_order_comment_v on dbo.Order_line.Order_id=customer_order_comment_v.order_id inner join
dbo.order_line_attribute_value_egenordre_v on dbo.Order_line.order_line_id=dbo.order_line_attribute_value_egenordre_v.order_line_id INNER JOIN 
dbo.order_attribute_info_gavl_v on dbo.Order_line.Order_id= dbo.order_attribute_info_gavl_v.order_id inner join
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


/***************************************************************************
ALTER VIEW [dbo].[takstol_v] 
AS 
select takstol_basis_v.*,
case article_name when 'Takstoler' then 1 else 2 end as default_article from takstol_basis_v where sent=0


/********************************************************************************

ALTER VIEW [dbo].[takstol_package_v]
AS
SELECT     Order_line_id, Customer_nr, customer_name, customer_details, Order_id, Order_nr, address, Info, probability, packlist_ready, 
                      construction_type_name, article_name, Attribute_info, Number_of_items, Loading_date, Colli_id, produced, transport_details, Comment, 
                      transport_year, Transport_Week, load_time, post_shipment_id, product_area_group_name, action_started, egenordre, is_default, production_date, 
                      production_unit_name, sent, ord_no, ln_no, tross_drawer, cutting_started, cutting_done, default_article,
					  ordlnkunde.purcno,ordlnkjop.NoInvoAb as rest,dbo.takstol_v.production_week
FROM         dbo.takstol_v left outer join
F0100.dbo.ordln as ordlnkunde on dbo.takstol_v.ord_no = ordlnkunde.ordno and dbo.takstol_v.ln_no=ordlnkunde.lnno left outer join
F0100.dbo.ordln as ordlnkjop on ordlnkunde.purcno=ordlnkjop.ordno and ordlnkunde.lnpurcno=ordlnkjop.lnno
