alter table customer_order add packlist_ready datetime;

/*************************************************************************************/
alter table customer_order drop column invoiced;
/*************************************************************************************/
update customer_order set sent=getdate()-10000 where exists(select 1 from transport where transport.transport_id=customer_order.transport_id and transport.transport_name='Historisk')

/*************************************************************************************/

CREATE VIEW [dbo].[order_packlist_ready_v]
AS
SELECT     COUNT(Order_id) AS order_count, DATEPART(yyyy, packlist_ready) AS packlist_year, DATEPART(ww, packlist_ready) AS packlist_week
FROM         dbo.Customer_order
WHERE     (packlist_ready IS NOT NULL)
GROUP BY DATEPART(yyyy, packlist_ready), DATEPART(ww, packlist_ready)

/*************************************************************************************/
CREATE VIEW [dbo].[sum_avvik_drift_prosjektering_v]
AS
SELECT     deviation_count, job_function_name, registration_year, registration_week, internal_cost
FROM         dbo.sum_avvik_v
WHERE     (job_function_name = N'Drift / Prosjektering')
/*************************************************************************************/
CREATE VIEW [dbo].[nokkel_drift_prosjektering_v]
AS
SELECT     dbo.order_packlist_ready_v.order_count, ISNULL(dbo.order_packlist_ready_v.packlist_year, dbo.sum_avvik_drift_prosjektering_v.registration_year) 
                      AS packlist_year, ISNULL(dbo.order_packlist_ready_v.packlist_week, dbo.sum_avvik_drift_prosjektering_v.registration_week) AS packlist_week, 
                      dbo.sum_avvik_drift_prosjektering_v.deviation_count, dbo.sum_avvik_drift_prosjektering_v.internal_cost
FROM         dbo.order_packlist_ready_v FULL OUTER JOIN
                      dbo.sum_avvik_drift_prosjektering_v ON dbo.order_packlist_ready_v.packlist_year = dbo.sum_avvik_drift_prosjektering_v.registration_year AND 
                      dbo.order_packlist_ready_v.packlist_week = dbo.sum_avvik_drift_prosjektering_v.registration_week
                      
/*************************************************************************************/
insert into user_type(description,startup_window) values('Pakklister','no.ugland.utransprod.gui.PacklistWindow');

/*************************************************************************************/

CREATE VIEW [dbo].[vegg_front_not_ready_v]
AS
SELECT     Order_line_id, Order_id, Construction_type_article_id, Article_type_id, Order_line_ref, Number_of_items, produced, dialog_order, article_path, Colli_id, 
                      has_article, attribute_info, is_default, post_shipment_id
FROM         dbo.Order_line
WHERE     (LOWER(article_path) IN ('vegg', 'front')) AND (produced IS NULL) AND EXISTS
                          (SELECT     1 AS Expr1
                            FROM          dbo.Order_line_attribute
                            WHERE      (Order_line_id = dbo.Order_line.Order_line_id) AND (LOWER(order_line_attribute_name) = 'lager') AND 
                                                   (LOWER(Order_line_Attribute_value) = 'nei'))

/*************************************************************************************/

ALTER FUNCTION [dbo].[is_order_ready_for_package] 
(
	@orderid int
)
RETURNS int
AS
BEGIN
	DECLARE @ready int
	declare @numberOf int

	select @numberOf = count(*) 
        from dbo.vegg_front_not_ready_v
        where dbo.vegg_front_not_ready_v.order_id=@orderid 
	
    if(@numberOf=0)
      select @ready=1
	else
	  select @ready=0
	RETURN @ready

END

/*************************************************************************************/

ALTER VIEW [dbo].[main_package_v]
AS
SELECT     dbo.Customer_order.Order_id,
		dbo.Customer.Customer_nr, 
		CAST(dbo.Customer.Customer_nr AS nvarchar(10)) + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name as customer_details,
		customer_order.order_nr,
		customer_order.postal_code+' '+customer_order.post_office as address,
		Construction_type.Name as construction_type_name,customer_order.info, 
		CAST(dbo.Transport.Transport_Week AS nvarchar(2)) + '- ' + dbo.Transport.Transport_name AS transport_details, 
		dbo.is_order_ready_for_package(dbo.Customer_order.Order_id) AS package_ready, 
		dbo.customer_order.order_complete AS done_package,
		dbo.Customer_order.Comment,
		transport.transport_year,
		transport.transport_week,
		transport.loading_date
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
WHERE     (dbo.Customer_order.Transport_id IS NULL) AND (dbo.Customer_order.Sent IS NULL) OR
                      (dbo.Transport.Transport_name <> 'Historisk') AND (dbo.Customer_order.Sent IS NULL)