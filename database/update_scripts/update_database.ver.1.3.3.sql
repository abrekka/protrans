alter table colli add package_date datetime;
/***********************************************************************/
insert into application_param(param_name,param_value) values('takstol_kollnavn','Takstol');
/***********************************************************************/
ALTER VIEW [dbo].[own_production_v]
AS
SELECT     dbo.Customer_order.Order_id, 
		dbo.Customer_order.packed_by, 
		dbo.Transport.Transport_Year, 
		dbo.Transport.Transport_Week, 
        dbo.Customer.Customer_nr, 
		dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_name, 
        dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Garasje') AS garage_value, 
		DATEPART(yyyy, dbo.Customer_order.order_ready) AS order_ready_year, 
		DATEPART(ww, dbo.Customer_order.order_ready) AS order_ready_week, 
		DATEPART(dw, dbo.Customer_order.order_ready) AS order_ready_day, 
		dbo.Customer_order.packlist_ready, 
		dbo.Customer_order.Invoice_date, 
		product_area.product_area,
customer_order.sent
		--ISNULL(dbo.Customer_order.villa, 0) AS villa
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id inner join
					dbo.product_area on dbo.customer_order.product_area_id=dbo.product_area.product_area_id
WHERE     (dbo.Transport.Transport_Year <> 9999)
/***********************************************************************/
insert into application_param(param_name,param_value) values('default_avvik_kostnader','Funksjonærkost;Produksjon;Transport;Montering;Kraning;Varekjøp');
/***********************************************************************/
insert into cost_type(cost_type_name) values('Funksjonærkost');
/***********************************************************************/
insert into cost_type(cost_type_name) values('Produksjon');
/***********************************************************************/
insert into cost_type(cost_type_name) values('Transport');
/***********************************************************************/
insert into cost_type(cost_type_name) values('Varekjøp');