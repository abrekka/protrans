ALTER VIEW [dbo].[front_production_v]
AS
SELECT     dbo.Order_line.Order_line_id, dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, dbo.Order_line.attribute_info, 
                      dbo.Order_line.Number_of_items, dbo.Transport.Loading_date, dbo.Order_line.produced, CAST(dbo.Transport.Transport_Week AS nvarchar(2)) 
                      + '- ' + dbo.Transport.Transport_name AS transport_details, dbo.get_order_comment(Customer_order.order_id) as Comment, ISNULL(dbo.Transport.Transport_Year, 9999) 
                      AS transport_year, dbo.Transport.Transport_Week,transport.load_time,customer_order.status as order_status,
order_line.post_shipment_id
FROM         dbo.Order_line INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.Order_line_attribute ON dbo.Order_line.Order_line_id = dbo.Order_line_attribute.Order_line_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id
WHERE     (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND (dbo.Transport.Transport_name <> 'Historisk') AND 
                      (dbo.Order_line_attribute.order_line_attribute_name = 'Lager') AND (LOWER(dbo.Order_line_attribute.Order_line_Attribute_value) = 'nei') AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Front') OR
                      (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND 
                      (dbo.Order_line_attribute.order_line_attribute_name = 'Lager') AND (LOWER(dbo.Order_line_attribute.Order_line_Attribute_value) = 'nei') AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Front') AND 
                      (dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) IS NULL)

/******************************************************************************************/
ALTER VIEW [dbo].[vegg_production_v]
AS
SELECT     dbo.Order_line.Order_line_id,dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, dbo.Order_line.attribute_info, 
                      dbo.Order_line.Number_of_items, dbo.Transport.Loading_date, dbo.Order_line.produced, 
					CAST(dbo.Transport.Transport_Week AS nvarchar(2)) + '- ' + dbo.Transport.Transport_name AS transport_details, 
					dbo.get_order_comment(Customer_order.order_id) as Comment, isnull(dbo.Transport.Transport_Year,9999) as transport_year, 
                      dbo.Transport.Transport_Week,transport.load_time,customer_order.status as order_status,
order_line.post_shipment_id
FROM         dbo.Order_line INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.Order_line_attribute ON dbo.Order_line.Order_line_id = dbo.Order_line_attribute.Order_line_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id
WHERE     (dbo.is_order_line_sent(dbo.Order_line.Order_id, 
                      dbo.Order_line.post_shipment_id) = 0) AND 
	(dbo.Transport.Transport_name <> 'Historisk' or 
	dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) is null) AND 
	dbo.order_line_attribute.order_line_attribute_name='Lager' and
	lower(dbo.order_line_attribute.order_line_attribute_value)='nei' and
	(dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id,dbo.Order_line.Article_type_id) = 'Vegg')

/******************************************************************************************/
ALTER VIEW [dbo].[gavl_production_v]
AS
SELECT     dbo.Order_line.Order_line_id,dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, dbo.Order_line.attribute_info, 
                      dbo.Order_line.Number_of_items, dbo.Transport.Loading_date, dbo.Order_line.produced, CAST(dbo.Transport.Transport_Week AS nvarchar(2)) 
                      + '- ' + dbo.Transport.Transport_name AS transport_details, dbo.get_order_comment(customer_order.order_id) as Comment, ISNULL(dbo.Transport.Transport_Year, 9999) 
                      AS transport_year, dbo.Transport.Transport_Week,transport.load_time,customer_order.status as order_status,
order_line.post_shipment_id
FROM         dbo.Order_line INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id
WHERE     ((dbo.is_order_line_sent(dbo.Order_line.Order_id, 
                      dbo.Order_line.post_shipment_id) = 0) AND 
			(dbo.Transport.Transport_name <> 'Historisk') OR
            (dbo.is_order_line_sent(dbo.Order_line.Order_id, 
                      dbo.Order_line.post_shipment_id) = 0) AND 
			(dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) IS NULL)) AND 
			(dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id,dbo.Order_line.Article_type_id) = 'Gavl')


/******************************************************************************************/
ALTER VIEW [dbo].[takstol_package_v]
AS
SELECT     dbo.Order_line.Order_line_id, 
dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, 
dbo.Customer_order.Order_nr, 
dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, 
dbo.Customer_order.Info, 
dbo.Construction_type.Name AS construction_type_name, 
dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, 
dbo.Order_line.attribute_info+dbo.get_attribute_info(order_line.order_id,'Gavl') as Attribute_info, 
dbo.Order_line.Number_of_items, 
dbo.Transport.Loading_date, 
dbo.Order_line.Colli_id, CAST(dbo.Transport.Transport_Week AS nvarchar(2)) + '- ' + dbo.Transport.Transport_name AS transport_details, 
dbo.get_order_comment(Customer_order.order_id) as Comment, 
ISNULL(dbo.Transport.Transport_Year, 9999) AS transport_year, 
dbo.Transport.Transport_Week,
transport.load_time,
order_line.post_shipment_id
FROM         dbo.Order_line INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id
WHERE     (dbo.is_order_line_default(dbo.Order_line.Order_line_id, dbo.Order_line.is_default) = 1) AND (dbo.is_order_line_sent(dbo.Order_line.Order_id, 
                      dbo.Order_line.post_shipment_id) = 0) AND (dbo.Transport.Transport_name <> 'Historisk') AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Takstoler') 
                      AND (dbo.order_line_has_article(dbo.Order_line.Order_line_id, dbo.Order_line.has_article, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id)) = 1) OR
                      (dbo.is_order_line_default(dbo.Order_line.Order_line_id, dbo.Order_line.is_default) = 1) AND (dbo.is_order_line_sent(dbo.Order_line.Order_id, 
                      dbo.Order_line.post_shipment_id) = 0) AND (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, 
                      dbo.Order_line.Article_type_id) = 'Takstoler') AND (dbo.order_line_has_article(dbo.Order_line.Order_line_id, dbo.Order_line.has_article, 
                      dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id)) = 1) AND 
                      dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) is null


/******************************************************************************************/
ALTER VIEW [dbo].[gulvspon_package_v]
AS
SELECT     dbo.Order_line.Order_line_id,dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, dbo.Order_line.attribute_info, 
                      dbo.Order_line.Number_of_items, dbo.Transport.Loading_date, dbo.Order_line.colli_id, 
					CAST(dbo.Transport.Transport_Week AS nvarchar(2)) + '- ' + dbo.Transport.Transport_name AS transport_details, 
					dbo.get_Order_comment(dbo.Customer_order.order_id) as Comment, isnull(dbo.Transport.Transport_Year,9999) as transport_year, 
                      dbo.Transport.Transport_Week,transport.load_time,
order_line.post_shipment_id
FROM         dbo.Order_line INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id
WHERE     (dbo.is_order_line_sent(dbo.Order_line.Order_id, 
                      dbo.Order_line.post_shipment_id) = 0) AND 
			(dbo.Transport.Transport_name <> 'Historisk' or 
			dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) is null) AND 
			(dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Gulvspon') and 
			dbo.order_line_has_article(order_line.order_line_id,order_line.has_article,dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id,dbo.Order_line.Article_type_id))=1


/******************************************************************************************/
ALTER VIEW [dbo].[takstol_production_v]
AS
SELECT     dbo.Order_line.Order_line_id, dbo.Customer.Customer_nr, 
CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, dbo.Order_line.attribute_info, 
                      dbo.Order_line.Number_of_items, dbo.Transport.Loading_date, dbo.Order_line.produced, CAST(dbo.Transport.Transport_Week AS nvarchar(2)) 
                      + '- ' + dbo.Transport.Transport_name AS transport_details, dbo.get_order_comment(Customer_order.order_id) as Comment, ISNULL(dbo.Transport.Transport_Year, 9999) 
                      AS transport_year, dbo.Transport.Transport_Week, dbo.Transport.load_time,
order_line.post_shipment_id
FROM         dbo.Order_line INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id
WHERE     (dbo.is_order_line_default(dbo.Order_line.Order_line_id, dbo.Order_line.is_default) = 0) AND (dbo.is_order_line_sent(dbo.Order_line.Order_id, 
                      dbo.Order_line.post_shipment_id) = 0) AND (dbo.Transport.Transport_name <> 'Historisk') AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Takstoler') 
                      AND (dbo.order_line_has_article(dbo.Order_line.Order_line_id, dbo.Order_line.has_article, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id)) = 1) OR
                      (dbo.is_order_line_default(dbo.Order_line.Order_line_id, dbo.Order_line.is_default) = 0) AND (dbo.is_order_line_sent(dbo.Order_line.Order_id, 
                      dbo.Order_line.post_shipment_id) = 0) AND (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, 
                      dbo.Order_line.Article_type_id) = 'Takstoler') AND (dbo.order_line_has_article(dbo.Order_line.Order_line_id, dbo.Order_line.has_article, 
                      dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id)) = 1) AND 
                      (dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) IS NULL)

/******************************************************************************************/
create table product_area_group(
  product_area_group_id int primary key,
  product_area_group_name nvarchar(100) not null
);
/******************************************************************************************/
insert into product_area_group(product_area_group_id,product_area_group_name) values(1,'Garasje');
insert into product_area_group(product_area_group_id,product_area_group_name) values(2,'Byggelement');
insert into product_area_group(product_area_group_id,product_area_group_name) values(3,'Takstol');
/******************************************************************************************/
alter table product_area add product_area_group_id int references product_area_group(product_area_group_id);
/******************************************************************************************/
update product_area set product_area_group_id=1 where product_area='Garasje villa';
update product_area set product_area_group_id=1 where product_area='Garasje rekke';
update product_area set product_area_group_id=2 where product_area='Byggelement';
update product_area set product_area_group_id=3 where product_area='Takstol';
/******************************************************************************************/
insert into user_type(description,startup_window) values('Produksjonsansvarlig','no.ugland.utransprod.gui.ProductionOverviewWindow');
/******************************************************************************************/
insert into window_access(window_name) values('Produksjonsoversikt');
/******************************************************************************************/
insert into user_type_access(write_access,user_type_id,window_access_id) (select 1,user_type.user_type_id,window_accesss.window_access_id from user_type,window_access where user_type.description='Produksjonsansvarlig' and window_access.window_name='Produksjonsoversikt');