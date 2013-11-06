CREATE VIEW [dbo].[order_customer_cost_v]
AS
	SELECT order_cost.order_id,sum(cost_amount) as customer_cost_amount
	from order_cost where 
	exists(select 1 from cost_unit 
	where order_cost.cost_unit_id=cost_unit.cost_unit_id and lower(cost_unit.cost_unit_name)='kunde')
	group by order_cost.order_id

###############################################################################################################################

ALTER VIEW [dbo].[order_invoiced_amount_v]
AS
SELECT     count(dbo.customer_order.Order_id) as order_count,
			product_area.product_area,
DATEPART(yyyy, Invoice_date) AS invoice_year, 
dbo.GetISOWeekNumberFromDate( Invoice_date) AS invoice_week, 
sum(dbo.order_customer_cost_v.customer_cost_amount) as invoiced_amount
FROM         dbo.Customer_order,product_area,dbo.order_customer_cost_v
WHERE     (Invoice_date IS NOT NULL) and customer_order.product_area_id=product_area.product_area_id and
 dbo.order_customer_cost_v.order_id=dbo.Customer_order.order_id
group by 
product_area.product_area,
DATEPART(yyyy, Invoice_date) , 
dbo.GetISOWeekNumberFromDate( Invoice_date)
###############################################################################################################################

CREATE VIEW [dbo].[own_assembly_cost_v]
AS
SELECT order_cost.order_id,sum(cost_amount) as sum_cost_amount 
				from order_cost 
				where exists(select 1 
								from cost_unit 
								where order_cost.cost_unit_id=cost_unit.cost_unit_id and 
										lower(cost_unit.cost_unit_name)='kunde') and
						exists(select 1 
								from cost_type
								where order_cost.cost_type_id=cost_type.cost_type_id and
										lower(cost_type.cost_type_name)=lower('montering'))
										
										group by Order_cost.order_id

###############################################################################################################################
CREATE VIEW [dbo].[own_delivery_cost_v]
AS
SELECT order_cost.order_id,sum(cost_amount) as sum_cost_amount 
				from order_cost 
				where exists(select 1 
								from cost_unit 
								where order_cost.cost_unit_id=cost_unit.cost_unit_id and 
										lower(cost_unit.cost_unit_name)='kunde') and
						exists(select 1 
								from cost_type
								where order_cost.cost_type_id=cost_type.cost_type_id and
										lower(cost_type.cost_type_name)=lower('frakt'))
										
										group by Order_cost.order_id


###############################################################################################################################

ALTER VIEW [dbo].[assemblied_v]
AS
SELECT     COUNT(dbo.Customer_order.Order_id) AS order_count, 
dbo.product_area.product_area, DATEPART(yyyy, dbo.[Assembly].assemblied_date) AS assemblied_year, 
dbo.GetISOWeekNumberFromDate(dbo.[Assembly].assemblied_date) AS assemblied_week, 
sum(dbo.own_delivery_cost_v.sum_cost_amount) as delivery_cost,
sum(dbo.own_assembly_cost_v.sum_cost_amount) as assembly_cost,
sum(dbo.own_production_cost_v.sum_cost_amount) as garage_cost
FROM         dbo.Customer_order INNER JOIN
dbo.own_delivery_cost_v on dbo.Customer_order.order_id=dbo.own_delivery_cost_v.order_id INNER JOIN
dbo.own_assembly_cost_v on dbo.Customer_order.order_id=dbo.own_assembly_cost_v.order_id INNER JOIN
dbo.own_production_cost_v on dbo.Customer_order.order_id=dbo.own_production_cost_v.order_id INNER JOIN
                      dbo.[Assembly] ON dbo.Customer_order.Order_id = dbo.[Assembly].Order_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id
WHERE     (dbo.[Assembly].assemblied_date IS NOT NULL)
GROUP BY dbo.product_area.product_area, DATEPART(yyyy, dbo.[Assembly].assemblied_date), dbo.GetISOWeekNumberFromDate(dbo.[Assembly].assemblied_date)

###############################################################################################################################

CREATE VIEW [dbo].[deviation_internal_cost_v]
AS
	SELECT deviation_id,sum(cost_amount) sum_cost_amount
						from order_cost,cost_unit
						where 
						order_cost.cost_unit_id=cost_unit.cost_unit_id and
								cost_unit.cost_unit_name in('Intern','UI')
							group by deviation_id
							
###############################################################################################################################							
							
CREATE VIEW [dbo].[deviation_external_cost_v]
AS
	SELECT deviation_id,sum(cost_amount) sum_cost_amount
						from order_cost,cost_unit
						where 
						order_cost.cost_unit_id=cost_unit.cost_unit_id and
								cost_unit.cost_unit_name not in('Intern','UI')
							group by deviation_id							
							
###############################################################################################################################														
							
ALTER VIEW [dbo].[deviation_sum_v]
AS
SELECT     dbo.job_function.job_function_name, 
dbo.function_category.function_category_name, 
dbo.deviation_status.deviation_status_name, 
dbo.GetISOWeekNumberFromDate( dbo.deviation.registration_date) AS registration_week, 
DATEPART(yyyy, dbo.deviation.registration_date) AS registration_year, 
COUNT(dbo.deviation.deviation_id) AS number_of_deviations, 
SUM(dbo.deviation_internal_cost_v.sum_cost_amount) AS internal_cost, 
SUM(dbo.deviation_external_cost_v.sum_cost_amount) AS external_cost,  
dbo.product_area.product_area
FROM         dbo.product_area RIGHT OUTER JOIN
dbo.deviation ON dbo.product_area.product_area_id = dbo.deviation.product_area_id LEFT OUTER JOIN
dbo.deviation_internal_cost_v ON dbo.deviation.deviation_id = dbo.deviation_internal_cost_v.deviation_id LEFT OUTER JOIN
dbo.deviation_external_cost_v ON dbo.deviation.deviation_id = dbo.deviation_external_cost_v.deviation_id LEFT OUTER JOIN
dbo.deviation_status ON dbo.deviation.deviation_status_id = dbo.deviation_status.deviation_status_id LEFT OUTER JOIN
dbo.function_category ON dbo.deviation.function_category_id = dbo.function_category.function_category_id LEFT OUTER JOIN
dbo.job_function ON dbo.deviation.deviation_function_id = dbo.job_function.job_function_id
GROUP BY dbo.job_function.job_function_name, dbo.function_category.function_category_name, dbo.deviation_status.deviation_status_name,dbo.GetISOWeekNumberFromDate( 
                      dbo.deviation.registration_date), DATEPART(yyyy, dbo.deviation.registration_date), dbo.product_area.product_area
							
							
###############################################################################################################################																					
							
ALTER VIEW [dbo].[deviation_v]
AS
SELECT     dbo.deviation.deviation_id, 
dbo.deviation.registration_date, 
dbo.deviation.user_name, 
dbo.deviation.customer_nr, 
dbo.deviation.customer_name, 
dbo.deviation.order_nr, 
dbo.product_area.product_area, 
dbo.deviation.responsible, 
job_function_own.job_function_name AS own_function, 
job_function_deviation.job_function_name AS deviation_function, 
dbo.function_category.function_category_name, 
dbo.deviation_status.deviation_status_name, 
dbo.GetISOWeekNumberFromDate(dbo.deviation.registration_date) AS registration_week, 
DATEPART(yyyy, dbo.deviation.registration_date) AS registration_year, 
dbo.deviation_internal_cost_v.sum_cost_amount AS internal_cost, 
dbo.deviation_external_cost_v.sum_cost_amount AS external_cost,  
dbo.product_area_group.product_area_group_name,dbo.deviation.initiated_by
FROM         dbo.product_area LEFT OUTER JOIN
dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id RIGHT OUTER JOIN
dbo.deviation ON dbo.product_area.product_area_id = dbo.deviation.product_area_id LEFT OUTER JOIN
dbo.deviation_internal_cost_v ON dbo.deviation.deviation_id = dbo.deviation_internal_cost_v.deviation_id LEFT OUTER JOIN
dbo.deviation_external_cost_v ON dbo.deviation.deviation_id = dbo.deviation_external_cost_v.deviation_id LEFT OUTER JOIN
                      dbo.deviation_status ON dbo.deviation.deviation_status_id = dbo.deviation_status.deviation_status_id LEFT OUTER JOIN
                      dbo.function_category ON dbo.deviation.function_category_id = dbo.function_category.function_category_id LEFT OUTER JOIN
                      dbo.job_function AS job_function_deviation ON dbo.deviation.deviation_function_id = job_function_deviation.job_function_id LEFT OUTER JOIN
                      dbo.job_function AS job_function_own ON dbo.deviation.own_function_id = job_function_own.job_function_id
							
							
###############################################################################################################################							
							
							
CREATE VIEW [dbo].[order_assembly_date_v]
AS
      SELECT customer_order.order_id,assembly.assemblied_date
		from assembly right outer join
		Customer_order on assembly.order_id=Customer_order.order_id


###############################################################################################################################														
							
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
dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment, 
dbo.order_assembly_date_v.assemblied_date, 
dbo.product_area_group.product_area_group_name
FROM         dbo.Customer_order INNER JOIN
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
							
							
###############################################################################################################################																					
							
							
							
CREATE VIEW [dbo].[order_ready_for_package_v]
AS
	select customer_order.order_id,case when count(dbo.vegg_front_not_ready_v.Order_id)=0 then 1 else 0 end as order_ready
        from dbo.vegg_front_not_ready_v right outer join
        Customer_order on dbo.vegg_front_not_ready_v.Order_id=Customer_order.order_id
        group by customer_order.order_id
							
							
							
							
							
##############################################################################################################################

ALTER VIEW [dbo].[main_package_v]
AS
SELECT     dbo.Customer_order.Order_id, dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, 
dbo.Customer_order.Order_nr, 
dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, 
dbo.Construction_type.Name AS construction_type_name, 
dbo.Customer_order.Info, CAST(dbo.Transport.Transport_Week AS nvarchar(2)) + '- ' + dbo.Transport.Transport_name AS transport_details, 
dbo.order_ready_for_package_v.order_ready as package_ready,
dbo.Customer_order.order_complete AS done_package, 
dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment, 
dbo.Transport.Transport_Year, 
dbo.Transport.Transport_Week, 
dbo.Transport.Loading_date, 
dbo.product_area_group.product_area_group_name,
customer_order.probability
FROM         dbo.Customer_order INNER JOIN
dbo.order_ready_for_package_v on Customer_order.Order_id=dbo.order_ready_for_package_v.order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
WHERE     (dbo.Customer_order.Transport_id IS NULL) AND (dbo.Customer_order.Sent IS NULL) OR
                      (dbo.Customer_order.Sent IS NULL) AND (dbo.Transport.Transport_name <> 'Historisk')

##############################################################################################################################

ALTER VIEW [dbo].[order_packlist_ready_v]
AS
SELECT     COUNT(dbo.Customer_order.Order_id) AS order_count, 
product_area.product_area,
DATEPART(yyyy, customer_order.packlist_ready) AS packlist_year, 
dbo.GetISOWeekNumberFromDate(customer_order.packlist_ready) AS packlist_week,
sum(dbo.own_production_cost_v.sum_cost_amount) as customer_cost
FROM         dbo.Customer_order inner join
dbo.own_production_cost_v on dbo.Customer_order.Order_id=dbo.own_production_cost_v.order_id inner join
product_area on customer_order.product_area_id=product_area.product_area_id
WHERE     (customer_order.packlist_ready IS NOT NULL)
GROUP BY 
	product_area.product_area,
	DATEPART(yyyy, customer_order.packlist_ready), dbo.GetISOWeekNumberFromDate(customer_order.packlist_ready)

##############################################################################################################################


ALTER VIEW [dbo].[transport_week_v]
AS
SELECT     COUNT(Customer_order.Order_id) AS order_count, 
		--isnull(customer_order.villa,0) as villa,
		product_area.product_area,
	   SUM(ISNULL(Do_Assembly, 0)) AS assemblied, 
	   transport.transport_year AS order_sent_year, 
	   transport.transport_week AS order_sent_week, 
	   SUM(dbo.own_production_cost_v.sum_cost_amount) AS garage_cost, 
	   SUM(dbo.own_delivery_cost_v.sum_cost_amount) AS transport_cost, 
	   SUM(dbo.own_assembly_cost_v.sum_cost_amount) AS assembly_cost
FROM         dbo.Customer_order inner join
dbo.own_production_cost_v on dbo.Customer_order.Order_id=dbo.own_production_cost_v.order_id inner join
dbo.own_delivery_cost_v on dbo.Customer_order.Order_id=dbo.own_delivery_cost_v.order_id inner join
dbo.own_assembly_cost_v on dbo.Customer_order.Order_id=dbo.own_assembly_cost_v.order_id inner join
transport on dbo.Customer_order.transport_id=transport.transport_id inner join 
product_area on dbo.Customer_order.product_area_id=product_area.product_area_id
WHERE     customer_order.Sent IS NOT NULL
GROUP BY --isnull(customer_order.villa,0),
		product_area.product_area,
		transport.transport_year, transport.transport_week


##############################################################################################################################

create VIEW [dbo].[own_production_cost_internal_v]
AS
SELECT order_cost.order_id,sum(cost_amount) as sum_cost_amount 
				from order_cost 
				where exists(select 1 
								from cost_unit 
								where order_cost.cost_unit_id=cost_unit.cost_unit_id and 
										lower(cost_unit.cost_unit_name)='intern') and
						exists(select 1 
								from cost_type
								where order_cost.cost_type_id=cost_type.cost_type_id and
										lower(cost_type.cost_type_name)=lower('egenproduksjon'))
										
										group by Order_cost.order_id


##############################################################################################################################



ALTER VIEW [dbo].[order_reserve_v]
AS
SELECT     COUNT(dbo.Customer_order.Order_id) AS order_count, 
dbo.product_area.product_area, CASE isnull(customer_order.packlist_ready, 0) WHEN 0 THEN 'Nei' ELSE 'Ja' END AS is_packlist_ready, 
SUM(dbo.own_production_cost_v.sum_cost_amount)  AS own_production, 
SUM(dbo.own_production_cost_internal_v.sum_cost_amount) AS customer_cost
FROM         dbo.Customer_order INNER JOIN
dbo.own_production_cost_internal_v on dbo.Customer_order.order_id=dbo.own_production_cost_internal_v.order_id inner join
dbo.own_production_cost_v on dbo.Customer_order.order_id= dbo.own_production_cost_v.order_id inner join
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id
WHERE     (dbo.Customer_order.Sent IS NULL) AND (dbo.Customer_order.order_ready IS NULL)
GROUP BY dbo.product_area.product_area, CASE isnull(customer_order.packlist_ready, 0) WHEN 0 THEN 'Nei' ELSE 'Ja' END


##############################################################################################################################


ALTER VIEW [dbo].[order_status_not_sent_v]
AS
SELECT     dbo.Customer_order.Order_id, 
dbo.Customer_order.packlist_ready, 
dbo.Customer_order.order_ready, 
dbo.own_production_cost_v.sum_cost_amount AS garage_value, 
dbo.product_area.product_area
FROM         dbo.Customer_order INNER JOIN
dbo.own_production_cost_v on dbo.Customer_order.order_id=dbo.own_production_cost_v.order_id inner join
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id
WHERE     (dbo.Customer_order.Sent IS NULL)

##############################################################################################################################

create VIEW [dbo].[order_has_gulvspon_v]
AS
select customer_order.order_id,(case when Order_line_attribute.Order_line_Attribute_value='Ja' then 1 else 0 end) as has_gulvspon
from customer_order right outer join
order_line on customer_order.order_id=order_line.order_id inner join
Order_line_attribute on order_line.order_line_id=order_line_attribute.order_line_id
where lower(order_line_attribute.order_line_attribute_name)='har gulvspon'
and exists(select 1 from construction_type_article,article_type
			where construction_type_article.construction_type_article_id = order_line.Construction_type_article_id
				and construction_type_article.article_type_id = article_type.article_type_id and
				lower(Article_type.Article_type_name)='gulvspon') or
				exists(select 1 from Article_type where article_type.article_type_id=Order_line.Article_type_id and
				LOWER(article_type.article_type_name)='gulvspon')
				
				
##############################################################################################################################				
				
				
ALTER VIEW [dbo].[paid_v]
AS
SELECT     dbo.Customer_order.Order_id, 
dbo.Customer_order.Order_nr, 
dbo.Customer_order.paid_date, 
dbo.Customer.Customer_nr, 
dbo.Customer.First_name, 
dbo.Customer.Last_name, 
dbo.Customer_order.Postal_code, 
dbo.Customer_order.Post_office, 
dbo.Construction_type.Name AS Construction_name, 
dbo.Customer_order.Sent, 
dbo.order_customer_cost_v.customer_cost_amount AS customer_cost, 
dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment, dbo.Customer_order.Do_Assembly, 
dbo.product_area_group.product_area_group_name
FROM         dbo.Customer_order INNER JOIN
dbo.order_customer_cost_v on dbo.Customer_order.Order_id=dbo.order_customer_cost_v.order_id inner join
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id
WHERE     (dbo.Customer_order.Sent IS NULL)
				