CREATE TABLE production_unit(
	production_unit_id int IDENTITY(1,1) primary key,
	production_unit_name nvarchar(100) NOT NULL,
	article_type_id int REFERENCES Article_type (Article_type_id) NOT NULL
);
/**************************************************************************************************
CREATE TABLE production_unit_product_area_group(
	production_unit_product_area_group_id int IDENTITY(1,1) primary key,
	production_unit_id int REFERENCES production_unit (production_unit_id) NOT NULL,
	product_area_group_id int REFERENCES product_area_group (product_area_group_id) NOT NULL
);
/**************************************************************************************************
alter table order_line add production_unit_id int references production_unit(production_unit_id);
/**************************************************************************************************
insert into window_access(window_name) values('Produksjonsenhet');
/**************************************************************************************************
ALTER VIEW [dbo].[front_production_v]
AS
SELECT     dbo.Order_line.Order_line_id, dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, dbo.Order_line.attribute_info, 
                      dbo.Order_line.Number_of_items, dbo.Transport.Loading_date, dbo.Order_line.produced, CAST(dbo.Transport.Transport_Week AS nvarchar(2)) 
                      + '- ' + dbo.Transport.Transport_name AS transport_details, dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment, 
                      ISNULL(dbo.Transport.Transport_Year, 9999) AS transport_year, dbo.Transport.Transport_Week, dbo.Transport.load_time, 
                      dbo.Customer_order.status AS order_status, dbo.Order_line.post_shipment_id, dbo.product_area_group.product_area_group_name,order_line.action_started,
			customer_order.production_date,production_unit.production_unit_name
FROM         dbo.Order_line INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.Order_line_attribute ON dbo.Order_line.Order_line_id = dbo.Order_line_attribute.Order_line_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
					  dbo.production_unit ON dbo.Order_line.production_unit_id = dbo.production_unit.production_unit_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id
WHERE     (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND (dbo.Transport.Transport_name <> 'Historisk') AND 
                      (dbo.Order_line_attribute.order_line_attribute_name = 'Lager') AND (LOWER(dbo.Order_line_attribute.Order_line_Attribute_value) = 'nei') AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Front') OR
                      (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND 
                      (dbo.Order_line_attribute.order_line_attribute_name = 'Lager') AND (LOWER(dbo.Order_line_attribute.Order_line_Attribute_value) = 'nei') AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Front') AND 
                      (dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) IS NULL)
/**************************************************************************************************
ALTER VIEW [dbo].[gavl_production_v]
AS
SELECT     dbo.Order_line.Order_line_id, dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, dbo.Order_line.attribute_info, 
                      dbo.Order_line.Number_of_items, dbo.Transport.Loading_date, dbo.Order_line.produced, CAST(dbo.Transport.Transport_Week AS nvarchar(2)) 
                      + '- ' + dbo.Transport.Transport_name AS transport_details, dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment, 
                      ISNULL(dbo.Transport.Transport_Year, 9999) AS transport_year, dbo.Transport.Transport_Week, dbo.Transport.load_time, 
                      dbo.Customer_order.status AS order_status, dbo.Order_line.post_shipment_id, dbo.product_area_group.product_area_group_name,order_line.action_started,
						customer_order.production_date,production_unit.production_unit_name
FROM         dbo.Order_line INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
					  dbo.production_unit ON dbo.Order_line.production_unit_id = dbo.production_unit.production_unit_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id
WHERE     (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND (dbo.Transport.Transport_name <> 'Historisk') AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Gavl') OR
                      (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Gavl') AND 
                      (dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) IS NULL)
/**************************************************************************************************
ALTER VIEW [dbo].[takstol_v] 
AS 
SELECT dbo.Order_line.Order_line_id, 
	dbo.Customer.Customer_nr, 
	CAST(dbo.Customer.Customer_nr AS nvarchar(10)) + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, 
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
	customer_order.production_date,production_unit.production_unit_name
FROM dbo.Order_line INNER JOIN 
	dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN 
	dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN 
	dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN 
	dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN 
	dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN 
    dbo.production_unit ON dbo.Order_line.production_unit_id = dbo.production_unit.production_unit_id LEFT OUTER JOIN
	dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id 
WHERE (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND 
	(dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Takstoler') 
	AND (dbo.order_line_has_article(dbo.Order_line.Order_line_id, dbo.Order_line.has_article, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
	dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id)) = 1) 
/**************************************************************************************************
ALTER VIEW [dbo].[takstol_production_v]
AS
select * from takstol_v where is_default=0 or egenordre<>'Nei'
/**************************************************************************************************
ALTER VIEW [dbo].[takstol_package_v]
AS
select * from takstol_v where is_default=1 and egenordre='Nei'
/**************************************************************************************************
ALTER VIEW [dbo].[vegg_production_v]
AS
SELECT     dbo.Order_line.Order_line_id, dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, dbo.Order_line.attribute_info, 
                      dbo.Order_line.Number_of_items, dbo.Transport.Loading_date, dbo.Order_line.produced, CAST(dbo.Transport.Transport_Week AS nvarchar(2)) 
                      + '- ' + dbo.Transport.Transport_name AS transport_details, dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment, 
                      ISNULL(dbo.Transport.Transport_Year, 9999) AS transport_year, dbo.Transport.Transport_Week, dbo.Transport.load_time, 
                      dbo.Customer_order.status AS order_status, dbo.Order_line.post_shipment_id, dbo.product_area_group.product_area_group_name,order_line.action_started,
						customer_order.production_date,production_unit.production_unit_name
FROM         dbo.Order_line INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.Order_line_attribute ON dbo.Order_line.Order_line_id = dbo.Order_line_attribute.Order_line_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
					  dbo.production_unit ON dbo.Order_line.production_unit_id = dbo.production_unit.production_unit_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id
WHERE     (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND (dbo.Transport.Transport_name <> 'Historisk') AND 
                      (dbo.Order_line_attribute.order_line_attribute_name = 'Lager') AND (LOWER(dbo.Order_line_attribute.Order_line_Attribute_value) = 'nei') AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Vegg') OR
                      (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND 
                      (dbo.Order_line_attribute.order_line_attribute_name = 'Lager') AND (LOWER(dbo.Order_line_attribute.Order_line_Attribute_value) = 'nei') AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Vegg') AND 
                      (dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) IS NULL)
/**************************************************************************************************
ALTER VIEW [dbo].[sales_v]
AS
select superoffice.crm5.sale.sale_id,
		superoffice.crm5.sale.number1 as order_nr,
		superoffice.crm5.sale.probability,
		superoffice.crm5.sale.group_idx,
		superoffice.crm5.sale.saledate,
		superoffice.crm5.sale.registered,
		superoffice.crm5.contact.number2 as customer_nr,
		superoffice.crm5.contact.name as customer_name,
		superoffice.crm5.address.address1 as delivery_address,
		superoffice.crm5.address.zipcode as postal_code,
		superoffice.crm5.address.city as post_office,
		superoffice.crm5.udsalesmall.double05 as own_production_cost,
		superoffice.crm5.udsalesmall.double06 as transport_cost,
		superoffice.crm5.udsalesmall.double07 as assembly_cost,
		superoffice.crm5.udsalesmall.double08 as yes_lines,
		superoffice.crm5.udsalesmall.double09 as contribution_margin,
	    superoffice.crm5.udsalesmall.long03 as order_date,
		county.county_name,
		superoffice.crm5.person.firstname +' '+ superoffice.crm5.person.lastname as salesman
from superoffice.crm5.sale inner join
	superoffice.crm5.udsalesmall on superoffice.crm5.sale.userdef_id=
	superoffice.crm5.udsalesmall.udsalesmall_id left outer join 
	superoffice.crm5.address on superoffice.crm5.address.owner_id = superoffice.crm5.sale.contact_id left outer join
	superoffice.crm5.contact on superoffice.crm5.contact.contact_id=superoffice.crm5.sale.contact_id left outer join 
	superoffice.crm5.associate on superoffice.crm5.associate.associate_id=superoffice.crm5.contact.associate_id left outer join
	superoffice.crm5.person on superoffice.crm5.person.person_id = superoffice.crm5.associate.person_id left outer join 
	transport_cost on superoffice.crm5.address.zipcode = transport_cost.postal_code collate Danish_Norwegian_CI_AS left outer join 
	area on transport_cost.area_code = area.area_code left outer join 
	county on county.county_nr = area.county_nr 
where superoffice.crm5.address.atype_idx = 2
/**************************************************************************************************
alter table sales_data_snapshot add order_date int;
/**************************************************************************************************
ALTER PROCEDURE [dbo].[create_sales_v_snapshot] 
AS
BEGIN
  DECLARE @StartDateInt int
  DECLARE @EndDateInt int

  select @StartDateInt = datediff(ss,convert(datetime,'1970-01-01 01:00:00',20),DATEADD(wk,DATEDIFF(wk,0,dateadd(dd,-1,GETDATE())),0))
  select @EndDateInt = datediff(ss,convert(datetime,'1970-01-01 01:00:00',20),dateadd(hh,23,DATEADD(wk,DATEDIFF(wk,0,dateadd(dd,-1,GETDATE())),6)))
--select @StartDateInt = datediff(ss,convert(datetime,'1970-01-01 01:00:00',20),DATEADD(wk,DATEDIFF(wk,0,convert(datetime,'2008-12-10 01:00:00',20)),0))
  --select @EndDateInt = datediff(ss,convert(datetime,'1970-01-01 01:00:00',20),DATEADD(wk,DATEDIFF(wk,0,convert(datetime,'2008-12-10 01:00:00',20)),6))
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	insert into application_log(log_date,log_type,log_msg) values(getdate(),'DEBUG','create_sales_v_snapshot startdate:'+cast(@StartDateInt as nvarchar)+' enddate:'+cast(@EndDateInt as nvarchar));

    -- Insert statements for procedure here
	--SELECT , 
	insert into sales_data_snapshot(sale_id,order_nr,probability,group_idx,saledate,registered,customer_nr,customer_name,delivery_address,postal_code,post_office,own_production_cost,transport_cost,assembly_cost,yes_lines,contribution_margin,county_name,salesman,order_date) 
	(select sale_id,order_nr,probability,group_idx,saledate,registered,customer_nr,customer_name,delivery_address,postal_code,post_office,own_production_cost,transport_cost,assembly_cost,yes_lines,contribution_margin,county_name,salesman,order_date
	  from sales_v
	  where saledate between @StartDateInt and @EndDateInt)
END
/**************************************************************************************************
update sales_data_snapshot set order_date=saledate where probability=90;
/**************************************************************************************************