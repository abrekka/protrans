ALTER VIEW [dbo].[order_customer_cost_v]
AS
SELECT customer_order.order_id,sum(cost_amount) as customer_cost_amount
	from customer_order left outer join 
	order_cost on customer_order.order_id=order_cost.order_id and exists(select 1 from cost_unit 
	where order_cost.cost_unit_id=cost_unit.cost_unit_id and lower(cost_unit.cost_unit_name)='kunde')
	group by customer_order.order_id

##############################################################################################################################				



alter VIEW [dbo].[own_production_cost_v]
AS
SELECT customer_order.order_id,sum(cost_amount) as sum_cost_amount 
				from customer_order right outer join order_cost on Customer_order.Order_id=Order_cost.order_id
				where exists(select 1 
								from cost_unit 
								where order_cost.cost_unit_id=cost_unit.cost_unit_id and 
										lower(cost_unit.cost_unit_name)='kunde') and
						exists(select 1 
								from cost_type
								where order_cost.cost_type_id=cost_type.cost_type_id and
										lower(cost_type.cost_type_name)=lower('egenproduksjon'))
										
										group by customer_order.order_id;

##############################################################################################################################				

ALTER VIEW [dbo].[own_delivery_cost_v]
AS
SELECT customer_order.order_id,sum(cost_amount) as sum_cost_amount 
				from customer_order right outer join order_cost on Customer_order.Order_id=Order_cost.order_id
				where exists(select 1 
								from cost_unit 
								where order_cost.cost_unit_id=cost_unit.cost_unit_id and 
										lower(cost_unit.cost_unit_name)='kunde') and
						exists(select 1 
								from cost_type
								where order_cost.cost_type_id=cost_type.cost_type_id and
										lower(cost_type.cost_type_name)=lower('frakt'))
										
										group by customer_order.order_id

##############################################################################################################################				

ALTER VIEW [dbo].[own_assembly_cost_v]
AS
SELECT customer_order.order_id,sum(cost_amount) as sum_cost_amount 
				from customer_order right outer join order_cost on Customer_order.Order_id=Order_cost.order_id
				where exists(select 1 
								from cost_unit 
								where order_cost.cost_unit_id=cost_unit.cost_unit_id and 
										lower(cost_unit.cost_unit_name)='kunde') and
						exists(select 1 
								from cost_type
								where order_cost.cost_type_id=cost_type.cost_type_id and
										lower(cost_type.cost_type_name)=lower('montering'))
										
										group by customer_order.order_id

##############################################################################################################################				

ALTER VIEW [dbo].[order_customer_cost_v]
AS
	SELECT customer_order.order_id,sum(cost_amount) as customer_cost_amount
	from customer_order right outer join order_cost on customer_order.order_id=order_cost.order_id where 
	exists(select 1 from cost_unit 
	where order_cost.cost_unit_id=cost_unit.cost_unit_id and lower(cost_unit.cost_unit_name)='kunde')
	group by customer_order.order_id

##############################################################################################################################				
ALTER VIEW [dbo].[order_assembly_date_v]
AS
      SELECT customer_order.order_id,assembly.assemblied_date
		from customer_order right outer join
		assembly on customer_order.order_id=assembly.order_id



##############################################################################################################################				
CREATE VIEW [dbo].[order_attribute_info_gavl_v] 
AS 
select customer_order.order_id,attribute_info from customer_order left outer join Order_line on customer_order.order_id=Order_line.Order_id and lower(article_path)='gavl'

##############################################################################################################################				

create VIEW [dbo].[order_line_attribute_value_egenordre_v] 
AS 
select order_line.order_line_id,order_line_attribute_value 
from Order_line left join 
Order_line_attribute on order_line.order_line_id=order_line_attribute.order_line_id and LOWER(order_line_attribute_name)='egenordre'

##############################################################################################################################				

create VIEW [dbo].[customer_order_comment_v] 
AS 
SELECT order_id,
   STUFF( (SELECT ';' + comment
                             FROM order_comment where order_comment.order_id=Customer_order.order_id
                             FOR XML PATH('')), 
                            1, 1, '') as comment from customer_order
                            
##############################################################################################################################				                            
                            
ALTER VIEW [dbo].[takstol_basis_v] 
AS 
SELECT dbo.Order_line.Order_line_id, 
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
	dbo.Order_line.attribute_info + dbo.order_attribute_info_gavl_v.attribute_info AS Attribute_info,
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
dbo.order_line.cutting_done
FROM dbo.Order_line inner join
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

##############################################################################################################################				                            
                            
                            
ALTER VIEW [dbo].[fakturering_v]
AS
SELECT     dbo.Customer_order.Order_id, 
dbo.Customer_order.Order_nr, 
dbo.Customer_order.Invoice_date, 
dbo.Customer.Customer_nr, 
dbo.Customer.First_name, 
dbo.Customer.Last_name, 
dbo.Customer_order.Postal_code, 
dbo.Customer_order.Post_office, 
dbo.Construction_type.Name AS Construction_name, 
dbo.Customer_order.Sent, 
dbo.order_customer_cost_v.customer_cost_amount as customer_cost, 
customer_order_comment_v.comment,
dbo.order_assembly_date_v.assemblied_date, 
dbo.product_area_group.product_area_group_name
FROM         dbo.Customer_order inner JOIN
customer_order_comment_v on dbo.Customer_order.Order_id=customer_order_comment_v.order_id inner join
dbo.order_customer_cost_v on dbo.Customer_order.Order_id=dbo.order_customer_cost_v.Order_id INNER JOIN
dbo.order_assembly_date_v on dbo.Customer_order.Order_id=dbo.order_assembly_date_v.order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
WHERE     (dbo.Customer_order.Sent IS NOT NULL) AND (COALESCE (dbo.Customer_order.Do_Assembly, 0) = 0) AND 
(dbo.Transport.Transport_name <> 'Historisk') OR
(dbo.Customer_order.Sent IS NOT NULL) AND (dbo.Transport.Transport_name <> 'Historisk') AND (dbo.Customer_order.Do_Assembly = 1) 
AND 
                      EXISTS
                          (SELECT     1 AS Expr1
                            FROM          dbo.[Assembly]
                            WHERE      (Order_id = dbo.Customer_order.Order_id) AND (assemblied_date IS NOT NULL))

##############################################################################################################################				                                                        
                            
ALTER VIEW [dbo].[own_production_cost_v]
AS
SELECT customer_order.order_id,sum(cost_amount) as sum_cost_amount 
				from customer_order left outer join order_cost on Customer_order.Order_id=Order_cost.order_id
				and exists(select 1 
								from cost_unit 
								where order_cost.cost_unit_id=cost_unit.cost_unit_id and 
										lower(cost_unit.cost_unit_name)='kunde') and
						exists(select 1 
								from cost_type
								where order_cost.cost_type_id=cost_type.cost_type_id and
										lower(cost_type.cost_type_name)=lower('egenproduksjon'))
										
										group by customer_order.order_id;                            
										

######################################################################################################################										
										
ALTER VIEW [dbo].[own_delivery_cost_v]
AS
SELECT customer_order.order_id,sum(cost_amount) as sum_cost_amount 
				from customer_order left outer join order_cost on Customer_order.Order_id=Order_cost.order_id
				and exists(select 1 
								from cost_unit 
								where order_cost.cost_unit_id=cost_unit.cost_unit_id and 
										lower(cost_unit.cost_unit_name)='kunde') and
						exists(select 1 
								from cost_type
								where order_cost.cost_type_id=cost_type.cost_type_id and
										lower(cost_type.cost_type_name)=lower('frakt'))
										group by customer_order.order_id										
										

####################################################################################################################										
ALTER VIEW [dbo].[own_assembly_cost_v]
AS
SELECT customer_order.order_id,sum(cost_amount) as sum_cost_amount 
				from customer_order left outer join order_cost on Customer_order.Order_id=Order_cost.order_id
				and exists(select 1 
								from cost_unit 
								where order_cost.cost_unit_id=cost_unit.cost_unit_id and 
										lower(cost_unit.cost_unit_name)='kunde') and
						exists(select 1 
								from cost_type
								where order_cost.cost_type_id=cost_type.cost_type_id and
									lower(cost_type.cost_type_name)=lower('montering'))
										group by customer_order.order_id
										
										
####################################################################################################################																				
										
alter VIEW [dbo].[own_production_cost_internal_v]
AS
SELECT customer_order.order_id,sum(cost_amount) as sum_cost_amount 
				from customer_order left outer join order_cost on Customer_order.Order_id=Order_cost.order_id
				and exists(select 1 
								from cost_unit 
								where order_cost.cost_unit_id=cost_unit.cost_unit_id and 
										lower(cost_unit.cost_unit_name)='intern') and
						exists(select 1 
								from cost_type
								where order_cost.cost_type_id=cost_type.cost_type_id and
										lower(cost_type.cost_type_name)=lower('egenproduksjon'))
										
										group by customer_order.order_id										
										
####################################################################################################################																														
										
create VIEW [dbo].[number_of_gulvspon_v]
AS
      select customer_order.order_id,sum(isnull(number_of_items,0)) as number_of_items
      from customer_order inner join
      order_line on customer_order.order_id=order_line.order_id and 
      (exists(select 1
			from construction_type_article,article_type
			where construction_type_article.construction_type_article_id = order_line.construction_Type_Article_Id
				and construction_type_article.article_type_id = article_type.article_type_id and
				lower(article_type.article_type_name)='gulvspon') or
				exists(select 1
			from article_type
			where article_type.article_type_id=order_line.article_Type_Id and
			lower(article_type.article_type_name)='gulvspon'))
      group by Customer_order.order_id
										
										
####################################################################################################################										
										
create VIEW [dbo].[number_of_takstol_v]
AS
      select customer_order.order_id,sum(isnull(number_of_items,0)) as number_of_items
      from customer_order inner join
      order_line on customer_order.order_id=order_line.order_id and 
      isnull(order_line.Number_of_items,0)<>2 and
      (exists(select 1
			from construction_type_article,article_type
			where construction_type_article.construction_type_article_id = order_line.construction_Type_Article_Id
				and construction_type_article.article_type_id = article_type.article_type_id and
				lower(article_type.article_type_name)='takstoler') or
				exists(select 1
			from article_type
			where article_type.article_type_id=order_line.article_Type_Id and
			lower(article_type.article_type_name)='takstoler'))
      group by Customer_order.order_id

####################################################################################################################																				
										
										
ALTER VIEW [dbo].[packlist_v]
AS
SELECT     dbo.Customer_order.Order_id
   ,dbo.Customer.Customer_nr
   ,CAST(dbo.Customer.Customer_nr AS nvarchar(10)) + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details
   ,dbo.Customer_order.Order_nr
   ,dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address
   ,dbo.Customer_order.Info
   ,dbo.Construction_type.Name AS construction_type_name
   ,dbo.Transport.Loading_date
   ,dbo.Customer_order.packlist_ready
   ,dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment
   ,number_of_takstol_v.number_of_items as number_of_takstol
   ,dbo.order_has_gulvspon_v.has_gulvspon
   ,dbo.number_of_gulvspon_v.number_of_items as number_of_gulvspon
   ,dbo.product_area_group.product_area_group_name
   ,CAST(dbo.Transport.Transport_Week AS nvarchar(2)) + '- ' + dbo.Transport.Transport_name AS transport_details
   ,dbo.customer_order.tross_ready
   ,dbo.customer_order.tross_drawer
   ,dbo.transport.transport_year
   ,dbo.transport.transport_week,
dbo.Customer_order.status AS order_status
,dbo.Customer_order.production_basis
FROM         dbo.Customer_order left outer join
number_of_takstol_v on Customer_order.Order_id=number_of_takstol_v.Order_id LEFT outer join
dbo.number_of_gulvspon_v on Customer_order.Order_id=dbo.number_of_gulvspon_v.order_id left outer join
dbo.order_has_gulvspon_v on customer_order.order_id=order_has_gulvspon_v.order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
WHERE     (dbo.Customer_order.Sent IS NULL) AND (dbo.Transport.Transport_name <> 'Historisk') OR
                      (dbo.Customer_order.Sent IS NULL) AND (dbo.Customer_order.Transport_id IS NULL)

										
####################################################################################################################																														
										
										
create VIEW [dbo].[own_takstol_intern_cost_v]
AS
SELECT customer_order.order_id,sum(cost_amount) as sum_cost_amount 
				from customer_order left outer join order_cost on Customer_order.Order_id=Order_cost.order_id
				and exists(select 1 
								from cost_unit 
								where order_cost.cost_unit_id=cost_unit.cost_unit_id and 
										lower(cost_unit.cost_unit_name)='intern') and
						exists(select 1 
								from cost_type
								where order_cost.cost_type_id=cost_type.cost_type_id and
										lower(cost_type.cost_type_name)=lower('takstoler'))
										group by customer_order.order_id										
										

####################################################################################################################
										
ALTER VIEW [dbo].[takstol_all_v] 
AS 
SELECT takstol_basis_v.*,
case article_name when 'Takstoler' then 1 else 2 end as default_article,
 dbo.own_production_cost_v.sum_cost_amount as own_production,
dbo.own_takstol_intern_cost_v.sum_cost_amount as own_internal_production,
dbo.own_delivery_cost_v.sum_cost_amount as delivery_cost,
F0100.dbo.ordln.price,
F0100.dbo.ordln.Dc1p
FROM takstol_basis_v inner join
own_takstol_intern_cost_v on takstol_basis_v.Order_id=own_takstol_intern_cost_v.order_id inner join
dbo.own_delivery_cost_v on takstol_basis_v.order_id=dbo.own_delivery_cost_v.order_id inner join
own_production_cost_v on takstol_basis_v.order_id=own_production_cost_v.order_id LEFT OUTER JOIN 
F0100.dbo.ordln on takstol_basis_v.ord_no=F0100.dbo.ordln.ordno and takstol_basis_v.ln_no=F0100.dbo.ordln.lnno


####################################################################################################################
										
ALTER view [dbo].[Takstoltegner_v] as
select customer_nr, first_name + ' ' + last_name as name
   ,order_nr
   ,postal_code
   ,dbo.own_production_cost_v.sum_cost_amount as cost_amount
   ,customer_order.packlist_ready
   ,salesman
   ,tross_ready
   ,tross_drawer
   ,max_tross_height
   ,probability
   ,Intelle_ordre.dbo.IPK_Ord.Tak_Prosjektering
   ,product_area_group.product_area_group_name
from Protrans.dbo.customer_order 
inner join own_production_cost_v on Customer_order.Order_id=own_production_cost_v.order_id
   inner join Protrans.dbo.customer on Protrans.dbo.customer.customer_id = Protrans.dbo.customer_order.customer_id 
   inner join product_area on product_area.product_area_id=customer_order.product_area_id
   inner join product_area_group on product_area_group.product_area_group_id=product_area.product_area_group_id
   left outer join Intelle_ordre.dbo.IPK_Ord on Intelle_ordre.dbo.IPK_Ord.ordno = 'SO-' + order_nr collate Danish_Norwegian_CI_AS 
where product_area_group.product_area_group_name = 'Takstol' 
   and tross_ready is not null
union 
select customer_nr
   ,first_name + ' ' + last_name as name
   ,order_nr
   ,postal_code
   ,dbo.own_takstol_intern_cost_v.sum_cost_amount as cost_amount
   ,customer_order.packlist_ready
   ,salesman
   ,tross_ready
   ,tross_drawer
   ,max_tross_height
   ,probability
   ,0
   ,product_area_group.product_area_group_name
from Protrans.dbo.customer_order 
inner join own_takstol_intern_cost_v on Customer_order.Order_id=own_takstol_intern_cost_v.order_id
   inner join Protrans.dbo.customer on Protrans.dbo.customer.customer_id = Protrans.dbo.customer_order.customer_id 
   inner join product_area on product_area.product_area_id=customer_order.product_area_id
   inner join product_area_group on product_area_group.product_area_group_id=product_area.product_area_group_id
where product_area_group.product_area_group_name <> 'Takstol' 
   and tross_ready is not null
										
										
####################################################################################################################										
										
ALTER VIEW [dbo].[transport_sum_v]
AS
SELECT     COUNT(dbo.Customer_order.Order_id) AS order_count, 
dbo.Transport.Transport_Year, 
dbo.Transport.Transport_Week, 
sum(own_production_cost_v.sum_cost_amount) as garage_cost,
dbo.product_area_group.product_area_group_name
FROM         dbo.Customer_order INNER JOIN
own_production_cost_v on Customer_order.Order_id=own_production_cost_v.order_id inner join
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id
GROUP BY dbo.Transport.Transport_Year, dbo.Transport.Transport_Week, dbo.product_area_group.product_area_group_name
										