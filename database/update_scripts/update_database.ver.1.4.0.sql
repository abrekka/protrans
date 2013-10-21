ALTER VIEW [dbo].[fakturering_v]
AS
SELECT     dbo.Customer_order.Order_id, dbo.Customer_order.Order_nr, dbo.Customer_order.Invoice_date, dbo.Customer.Customer_nr, 
                      dbo.Customer.First_name, dbo.Customer.Last_name, dbo.Customer_order.Postal_code, dbo.Customer_order.Post_office, 
                      dbo.Construction_type.Name AS Construction_name, dbo.Customer_order.Sent, dbo.Get_customer_cost(dbo.Customer_order.Order_id) 
                      AS customer_cost, dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment, dbo.get_assemblied_date(dbo.Customer_order.Order_id, 
                      dbo.Customer_order.Do_Assembly) AS assemblied_date, dbo.product_area_group.product_area_group_name
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
WHERE     (dbo.Customer_order.Sent IS NOT NULL) AND (COALESCE (dbo.Customer_order.Do_Assembly, 0) = 0) AND 
                      (dbo.Transport.Transport_name <> 'Historisk') OR
                      (dbo.Customer_order.Sent IS NOT NULL) AND (dbo.Transport.Transport_name <> 'Historisk') AND (dbo.Customer_order.Do_Assembly = 1) AND 
                      EXISTS
                          (SELECT     1 AS Expr1
                            FROM          dbo.[Assembly]
                            WHERE      (Order_id = dbo.Customer_order.Order_id) AND (assemblied_date IS NOT NULL))
/********************************************************************************************/
ALTER VIEW [dbo].[packlist_v]
AS
SELECT     dbo.Customer_order.Order_id, dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.Transport.Loading_date, dbo.Customer_order.packlist_ready, 
                      dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment, dbo.get_number_of_article(dbo.Customer_order.Order_id, N'Takstoler') 
                      AS number_of_takstol, dbo.order_has_article(dbo.Customer_order.Order_id, N'Gulvspon') AS has_gulvspon, 
                      dbo.get_number_of_article(dbo.Customer_order.Order_id, N'Gulvspon') AS number_of_gulvspon, 
                      dbo.product_area_group.product_area_group_name
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
WHERE     (dbo.Customer_order.Sent IS NULL) AND (dbo.Transport.Transport_name <> 'Historisk') OR
                      (dbo.Customer_order.Sent IS NULL) AND (dbo.Customer_order.Transport_id IS NULL)
/********************************************************************************************/
ALTER VIEW [dbo].[paid_v]
AS
SELECT     dbo.Customer_order.Order_id, dbo.Customer_order.Order_nr, dbo.Customer_order.paid_date, dbo.Customer.Customer_nr, dbo.Customer.First_name, 
                      dbo.Customer.Last_name, dbo.Customer_order.Postal_code, dbo.Customer_order.Post_office, dbo.Construction_type.Name AS Construction_name, 
                      dbo.Customer_order.Sent, dbo.Get_customer_cost(dbo.Customer_order.Order_id) AS customer_cost, 
                      dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment, dbo.Customer_order.Do_Assembly, 
                      dbo.product_area_group.product_area_group_name
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id
WHERE     (dbo.Customer_order.Sent IS NULL)
/********************************************************************************************/
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
                      dbo.Customer_order.status AS order_status, dbo.Order_line.post_shipment_id, dbo.product_area_group.product_area_group_name
FROM         dbo.Order_line INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.Order_line_attribute ON dbo.Order_line.Order_line_id = dbo.Order_line_attribute.Order_line_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id
WHERE     (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND (dbo.Transport.Transport_name <> 'Historisk') AND 
                      (dbo.Order_line_attribute.order_line_attribute_name = 'Lager') AND (LOWER(dbo.Order_line_attribute.Order_line_Attribute_value) = 'nei') AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Front') OR
                      (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND 
                      (dbo.Order_line_attribute.order_line_attribute_name = 'Lager') AND (LOWER(dbo.Order_line_attribute.Order_line_Attribute_value) = 'nei') AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Front') AND 
                      (dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) IS NULL)
/********************************************************************************************/
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
                      dbo.Customer_order.status AS order_status, dbo.Order_line.post_shipment_id, dbo.product_area_group.product_area_group_name
FROM         dbo.Order_line INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id
WHERE     (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND (dbo.Transport.Transport_name <> 'Historisk') AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Gavl') OR
                      (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Gavl') AND 
                      (dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) IS NULL)
/********************************************************************************************/
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
                      dbo.Customer_order.status AS order_status, dbo.Order_line.post_shipment_id, dbo.product_area_group.product_area_group_name
FROM         dbo.Order_line INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.Order_line_attribute ON dbo.Order_line.Order_line_id = dbo.Order_line_attribute.Order_line_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id
WHERE     (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND (dbo.Transport.Transport_name <> 'Historisk') AND 
                      (dbo.Order_line_attribute.order_line_attribute_name = 'Lager') AND (LOWER(dbo.Order_line_attribute.Order_line_Attribute_value) = 'nei') AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Vegg') OR
                      (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND 
                      (dbo.Order_line_attribute.order_line_attribute_name = 'Lager') AND (LOWER(dbo.Order_line_attribute.Order_line_Attribute_value) = 'nei') AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Vegg') AND 
                      (dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) IS NULL)
/********************************************************************************************/
ALTER VIEW [dbo].[takstol_production_v]
AS
SELECT     dbo.Order_line.Order_line_id, dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, dbo.Order_line.attribute_info, 
                      dbo.Order_line.Number_of_items, dbo.Transport.Loading_date, dbo.Order_line.produced, CAST(dbo.Transport.Transport_Week AS nvarchar(2)) 
                      + '- ' + dbo.Transport.Transport_name AS transport_details, dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment, 
                      ISNULL(dbo.Transport.Transport_Year, 9999) AS transport_year, dbo.Transport.Transport_Week, dbo.Transport.load_time, 
                      dbo.Order_line.post_shipment_id, dbo.product_area_group.product_area_group_name
FROM         dbo.Order_line INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
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
/********************************************************************************************/
ALTER VIEW [dbo].[takstol_package_v]
AS
SELECT     dbo.Order_line.Order_line_id, dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, 
                      dbo.Order_line.attribute_info + dbo.get_attribute_info(dbo.Order_line.Order_id, N'Gavl') AS Attribute_info, dbo.Order_line.Number_of_items, 
                      dbo.Transport.Loading_date, dbo.Order_line.Colli_id, CAST(dbo.Transport.Transport_Week AS nvarchar(2)) 
                      + '- ' + dbo.Transport.Transport_name AS transport_details, dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment, 
                      ISNULL(dbo.Transport.Transport_Year, 9999) AS transport_year, dbo.Transport.Transport_Week, dbo.Transport.load_time, 
                      dbo.Order_line.post_shipment_id, dbo.product_area_group.product_area_group_name
FROM         dbo.Order_line INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
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
                      (dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) IS NULL)
/********************************************************************************************/
ALTER VIEW [dbo].[takstein_skarpnes_v]
AS
SELECT     dbo.Order_line.Order_line_id, dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, 
                      dbo.Order_line.Article_type_id) AS article_name, dbo.Order_line.attribute_info, dbo.Order_line.Number_of_items, dbo.Order_line.produced, 
                      dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Transport.Loading_date, 
                      CAST(dbo.Transport.Transport_Week AS nvarchar(2)) + '- ' + dbo.Transport.Transport_name AS transport_details, 
                      ISNULL(dbo.Transport.Transport_Year, 9999) AS Transport_Year, dbo.Transport.Transport_Week, dbo.Transport.load_time, 
                      dbo.get_order_comment(dbo.Customer_order.Order_id) AS comment, dbo.product_area_group.product_area_group_name
FROM         dbo.Order_line_attribute INNER JOIN
                      dbo.Order_line ON dbo.Order_line_attribute.Order_line_id = dbo.Order_line.Order_line_id AND 
                      dbo.Order_line_attribute.Order_line_id = dbo.Order_line.Order_line_id INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id AND 
                      dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id AND 
                      dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id
WHERE     (dbo.Order_line_attribute.order_line_attribute_name = 'Taksteintype') AND (LOWER(dbo.Order_line_attribute.Order_line_Attribute_value) 
                      LIKE '%skarpnes%') AND (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0)
/********************************************************************************************/
ALTER VIEW [dbo].[gulvspon_package_v]
AS
SELECT     dbo.Order_line.Order_line_id, dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, dbo.Order_line.attribute_info, 
                      dbo.Order_line.Number_of_items, dbo.Transport.Loading_date, dbo.Order_line.Colli_id, CAST(dbo.Transport.Transport_Week AS nvarchar(2)) 
                      + '- ' + dbo.Transport.Transport_name AS transport_details, dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment, 
                      ISNULL(dbo.Transport.Transport_Year, 9999) AS transport_year, dbo.Transport.Transport_Week, dbo.Transport.load_time, 
                      dbo.Order_line.post_shipment_id, dbo.product_area_group.product_area_group_name
FROM         dbo.Order_line INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id
WHERE     (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND (dbo.Transport.Transport_name <> 'Historisk') AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Gulvspon') 
                      AND (dbo.order_line_has_article(dbo.Order_line.Order_line_id, dbo.Order_line.has_article, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id)) = 1) OR
                      (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Gulvspon') 
                      AND (dbo.order_line_has_article(dbo.Order_line.Order_line_id, dbo.Order_line.has_article, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id)) = 1) AND (dbo.get_transport_id(dbo.Order_line.Order_id, 
                      dbo.Order_line.post_shipment_id) IS NULL)
/********************************************************************************************/
ALTER VIEW [dbo].[main_package_v]
AS
SELECT     dbo.Customer_order.Order_id, dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Construction_type.Name AS construction_type_name, 
                      dbo.Customer_order.Info, CAST(dbo.Transport.Transport_Week AS nvarchar(2)) + '- ' + dbo.Transport.Transport_name AS transport_details, 
                      dbo.is_order_ready_for_package(dbo.Customer_order.Order_id) AS package_ready, dbo.Customer_order.order_complete AS done_package, 
                      dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment, dbo.Transport.Transport_Year, dbo.Transport.Transport_Week, 
                      dbo.Transport.Loading_date, dbo.product_area_group.product_area_group_name
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
WHERE     (dbo.Customer_order.Transport_id IS NULL) AND (dbo.Customer_order.Sent IS NULL) OR
                      (dbo.Customer_order.Sent IS NULL) AND (dbo.Transport.Transport_name <> 'Historisk')
/********************************************************************************************/
ALTER VIEW [dbo].[sum_order_ready_v]
AS
SELECT     COUNT(dbo.Customer_order.Order_id) AS order_count, dbo.product_area.product_area, CONVERT(nvarchar, dbo.Customer_order.order_ready, 102) 
                      AS order_ready, SUM(dbo.Order_cost.Cost_Amount) AS package_sum, DATEPART(yyyy, dbo.Customer_order.order_ready) AS order_ready_year, 
                      DATEPART(ww, dbo.Customer_order.order_ready) AS order_ready_week, dbo.product_area_group.product_area_group_name
FROM         dbo.Customer_order INNER JOIN
                      dbo.Order_cost ON dbo.Customer_order.Order_id = dbo.Order_cost.Order_id INNER JOIN
                      dbo.Cost_type ON dbo.Order_cost.Cost_type_id = dbo.Cost_type.Cost_type_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id
WHERE     (dbo.Customer_order.order_ready IS NOT NULL) AND (dbo.Cost_type.Cost_type_name = 'Garasje')
GROUP BY dbo.product_area.product_area, CONVERT(nvarchar, dbo.Customer_order.order_ready, 102), DATEPART(yyyy, dbo.Customer_order.order_ready), 
                      DATEPART(ww, dbo.Customer_order.order_ready), dbo.product_area_group.product_area_group_name
/********************************************************************************************/
ALTER VIEW [dbo].[transport_sum_v]
AS
SELECT     COUNT(dbo.Customer_order.Order_id) AS order_count, dbo.Transport.Transport_Year, dbo.Transport.Transport_Week, 
                      SUM(dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'garasje')) AS garage_cost, 
                      dbo.product_area_group.product_area_group_name
FROM         dbo.Customer_order INNER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id
GROUP BY dbo.Transport.Transport_Year, dbo.Transport.Transport_Week, dbo.product_area_group.product_area_group_name
/********************************************************************************************/
update cost_type set cost_type_name='Egenproduksjon',description='Egenproduksjon' where cost_type_name='Garasje'
/********************************************************************************************/
ALTER VIEW [dbo].[assemblied_v]
AS
SELECT     COUNT(dbo.Customer_order.Order_id) AS order_count, 
dbo.product_area.product_area, DATEPART(yyyy, dbo.[Assembly].assemblied_date) AS assemblied_year, 
DATEPART(ww, dbo.[Assembly].assemblied_date) AS assemblied_week, 
SUM(dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Frakt')) AS delivery_cost, 
SUM(dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Montering')) AS assembly_cost, 
SUM(dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Egenproduksjon')) AS garage_cost
FROM         dbo.Customer_order INNER JOIN
                      dbo.[Assembly] ON dbo.Customer_order.Order_id = dbo.[Assembly].Order_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id
WHERE     (dbo.[Assembly].assemblied_date IS NOT NULL)
GROUP BY dbo.product_area.product_area, DATEPART(yyyy, dbo.[Assembly].assemblied_date), DATEPART(ww, dbo.[Assembly].assemblied_date)
/********************************************************************************************/
ALTER VIEW [dbo].[not_invoiced_v]
AS
SELECT     dbo.Customer_order.Order_id, 
dbo.Customer_order.Order_nr+' '+dbo.Customer.First_name+' '+dbo.Customer.Last_name as order_details, dbo.Customer_order.Sent, 
dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Egenproduksjon') AS garage_value
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id
WHERE     (dbo.Customer_order.Sent IS NOT NULL) AND (dbo.Customer_order.Invoice_date IS NULL)
/********************************************************************************************/
ALTER VIEW [dbo].[order_packlist_ready_v]
AS
SELECT     COUNT(Order_id) AS order_count, 
		--isnull(customer_order.villa,0) as villa,
		product_area.product_area,
		DATEPART(yyyy, packlist_ready) AS packlist_year, 
		DATEPART(ww, packlist_ready) AS packlist_week,
		sum(dbo.get_customer_cost_for_type(order_id,'Egenproduksjon')) as customer_cost
FROM         dbo.Customer_order,product_area
WHERE     (packlist_ready IS NOT NULL) and
		customer_order.product_area_id=product_area.product_area_id
GROUP BY --isnull(customer_order.villa,0),
	product_area.product_area,
	DATEPART(yyyy, packlist_ready), DATEPART(ww, packlist_ready)
/********************************************************************************************/
ALTER VIEW [dbo].[order_reserve_v]
AS
SELECT     COUNT(Order_id) AS order_count, 
--isnull(customer_order.villa,0) as villa,
product_area.product_area,
CASE isnull(packlist_ready, 0) WHEN 0 THEN 'Nei' ELSE 'Ja' END AS is_packlist_ready, 
 SUM(dbo.Get_customer_cost_for_type(Order_id,'Egenproduksjon')) AS customer_cost
FROM         dbo.Customer_order,product_area
WHERE     (Sent IS NULL) and order_ready is null and customer_order.product_area_id=product_area.product_area_id
GROUP BY --isnull(customer_order.villa,0),
product_area.product_area,
CASE isnull(packlist_ready, 0) WHEN 0 THEN 'Nei' ELSE 'Ja' END
/********************************************************************************************/
ALTER VIEW [dbo].[order_status_not_sent_v]
AS
SELECT     Order_id, packlist_ready, order_ready, dbo.get_customer_cost_for_type(Order_id, N'Egenproduksjon') AS garage_value
FROM         dbo.Customer_order
WHERE     (Sent IS NULL)

/********************************************************************************************/
ALTER VIEW [dbo].[own_production_v]
AS
SELECT     dbo.Customer_order.Order_id, dbo.Customer_order.packed_by, dbo.Transport.Transport_Year, dbo.Transport.Transport_Week, 
                      dbo.Customer.Customer_nr, dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_name, 
                      dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Egenproduksjon') AS garage_value, DATEPART(yyyy, dbo.Customer_order.order_ready) 
                      AS order_ready_year, DATEPART(ww, dbo.Customer_order.order_ready) AS order_ready_week, DATEPART(dw, dbo.Customer_order.order_ready) 
                      AS order_ready_day, dbo.Customer_order.packlist_ready, dbo.Customer_order.Invoice_date, dbo.product_area.product_area, 
                      dbo.Customer_order.Sent,customer_order.order_nr
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
/********************************************************************************************/
ALTER VIEW [dbo].[sum_order_ready_v]
AS
SELECT     COUNT(dbo.Customer_order.Order_id) AS order_count, dbo.product_area.product_area, CONVERT(nvarchar, dbo.Customer_order.order_ready, 102) 
                      AS order_ready, SUM(dbo.Order_cost.Cost_Amount) AS package_sum, DATEPART(yyyy, dbo.Customer_order.order_ready) AS order_ready_year, 
                      DATEPART(ww, dbo.Customer_order.order_ready) AS order_ready_week, dbo.product_area_group.product_area_group_name
FROM         dbo.Customer_order INNER JOIN
                      dbo.Order_cost ON dbo.Customer_order.Order_id = dbo.Order_cost.Order_id INNER JOIN
                      dbo.Cost_type ON dbo.Order_cost.Cost_type_id = dbo.Cost_type.Cost_type_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id
WHERE     (dbo.Customer_order.order_ready IS NOT NULL) AND (dbo.Cost_type.Cost_type_name = 'Egenproduksjon')
GROUP BY dbo.product_area.product_area, CONVERT(nvarchar, dbo.Customer_order.order_ready, 102), DATEPART(yyyy, dbo.Customer_order.order_ready), 
                      DATEPART(ww, dbo.Customer_order.order_ready), dbo.product_area_group.product_area_group_name
/********************************************************************************************/
ALTER VIEW [dbo].[transport_sum_v]
AS
SELECT     COUNT(dbo.Customer_order.Order_id) AS order_count, dbo.Transport.Transport_Year, dbo.Transport.Transport_Week, 
                      SUM(dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Egenproduksjon')) AS garage_cost, 
                      dbo.product_area_group.product_area_group_name
FROM         dbo.Customer_order INNER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id
GROUP BY dbo.Transport.Transport_Year, dbo.Transport.Transport_Week, dbo.product_area_group.product_area_group_name
/********************************************************************************************/
ALTER VIEW [dbo].[transport_week_v]
AS
SELECT     COUNT(Order_id) AS order_count, 
		--isnull(customer_order.villa,0) as villa,
		product_area.product_area,
	   SUM(ISNULL(Do_Assembly, 0)) AS assemblied, 
	   transport.transport_year AS order_sent_year, 
	   transport.transport_week AS order_sent_week, 
	   SUM(dbo.get_customer_cost_for_type(Order_id, N'Egenproduksjon')) AS garage_cost, 
	   SUM(dbo.get_customer_cost_for_type(Order_id,N'frakt')) AS transport_cost, 
	   SUM(dbo.get_customer_cost_for_type(Order_id, N'montering')) AS assembly_cost
FROM         dbo.Customer_order,transport,product_area
WHERE     customer_order.product_area_id=product_area.product_area_id and (customer_order.Sent IS NOT NULL) and customer_order.transport_id=transport.transport_id
GROUP BY --isnull(customer_order.villa,0),
		product_area.product_area,
		transport.transport_year, transport.transport_week
/********************************************************************************************/
ALTER VIEW [dbo].[sum_avvik_v]
AS
SELECT     COUNT(distinct dbo.deviation.deviation_id) AS deviation_count, 
		isnull(product_area.product_area,'Garasje villa') as product_area,
		ISNULL(dbo.job_function.job_function_name, 'Uspesifisert') AS job_function_name, 
		DATEPART(yyyy, dbo.deviation.registration_date) AS registration_year, 
		DATEPART(ww, dbo.deviation.registration_date) AS registration_week, 
		SUM(ISNULL(dbo.Order_cost.Cost_Amount, 0)) AS internal_cost,
		DATEPART(month, dbo.deviation.registration_date) AS registration_month,
CASE isnull(deviation.date_closed, 0) WHEN 0 THEN 0 ELSE 1 END AS closed 
FROM         dbo.Customer_order INNER JOIN
			product_area on customer_order.product_area_id = product_area.product_area_id inner join
			dbo.Order_cost ON dbo.Customer_order.Order_id = dbo.Order_cost.Order_id RIGHT OUTER JOIN
			dbo.deviation LEFT OUTER JOIN
			dbo.job_function ON dbo.deviation.deviation_function_id = dbo.job_function.job_function_id ON 
			dbo.Order_cost.deviation_id = dbo.deviation.deviation_id 
			and dbo.is_internal_cost(order_cost.order_cost_id)=1

GROUP BY dbo.job_function.job_function_name, 
isnull(product_area.product_area,'Garasje villa'), 
DATEPART(yyyy, dbo.deviation.registration_date), 
DATEPART(ww, dbo.deviation.registration_date),
DATEPART(month, dbo.deviation.registration_date),
CASE isnull(deviation.date_closed, 0) WHEN 0 THEN 0 ELSE 1 END
/********************************************************************************************/
