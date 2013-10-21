create table order_comment(
  order_comment_id int identity(1,1) primary key,
  user_name nvarchar(100) not null,
  comment_date datetime not null,
  comment nvarchar(1000) not null,
  for_transport int,
  order_id int references customer_order(order_id),
  deviation_id int references deviation(deviation_id)
);
/**********************************************************************************************************************/
insert into order_comment(user_name,comment_date,comment,order_id) (select 'org',getdate(),comment,order_id from customer_order where comment is not null);
/**********************************************************************************************************************/
insert into order_comment(user_name,comment_date,comment,deviation_id) (select user_name,comment_date,comment,deviation_id from deviation_comment);
/**********************************************************************************************************************/
update order_comment set order_id = (select deviation.order_id from deviation where deviation.deviation_id=order_comment.deviation_id)
/**********************************************************************************************************************/
alter table colli add height integer;
/**********************************************************************************************************************/
CREATE VIEW [dbo].[takstein_skarpnes_v]
AS
SELECT     dbo.Order_line.Order_line_id, dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, 
                      dbo.Order_line.Article_type_id) AS article_name, dbo.Order_line.attribute_info, dbo.Order_line.Number_of_items, dbo.Order_line.produced, 
                      dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Transport.Loading_date, 
                      CAST(dbo.Transport.Transport_Week AS nvarchar(2)) + '- ' + dbo.Transport.Transport_name AS transport_details, ISNULL(dbo.Transport.Transport_Year, 9999) as Transport_Year, 
                      dbo.Transport.Transport_Week, dbo.Transport.load_time,
                      dbo.get_order_comment(customer_order.order_id) as comment
FROM         dbo.Order_line_attribute INNER JOIN
                      dbo.Order_line ON dbo.Order_line_attribute.Order_line_id = dbo.Order_line.Order_line_id AND 
                      dbo.Order_line_attribute.Order_line_id = dbo.Order_line.Order_line_id INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id AND 
                      dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id AND 
                      dbo.Customer_order.Customer_id = dbo.Customer.Customer_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id
WHERE     (dbo.Order_line_attribute.order_line_attribute_name = 'Taksteintype') AND (LOWER(dbo.Order_line_attribute.Order_line_Attribute_value) 
                      LIKE '%skarpnes%') AND (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0)
/**********************************************************************************************************************/
insert into window_access(window_name) values('Takstein');
/**********************************************************************************************************************/
CREATE FUNCTION [dbo].[get_order_comment] 
(
	-- Add the parameters for the function here
	@orderId int
)
RETURNS varchar(1000)
AS
BEGIN
	DECLARE @comment varchar(100),@allComments varchar(1000)

DECLARE comment_cursor CURSOR FOR
SELECT comment FROM order_comment
WHERE order_id =@orderId
ORDER BY comment_date 

select @allComments=''

OPEN comment_cursor

-- Perform the first fetch and store the values in variables.
-- Note: The variables are in the same order as the columns
-- in the SELECT statement. 

FETCH NEXT FROM comment_cursor
INTO @comment

-- Check @@FETCH_STATUS to see if there are any more rows to fetch.
WHILE @@FETCH_STATUS = 0
BEGIN

   -- Concatenate and display the current values in the variables.
   select @allComments = @allComments +@comment+ ';'
   FETCH NEXT FROM comment_cursor
   INTO @comment
END

CLOSE comment_cursor
DEALLOCATE comment_cursor
return @allComments
END

/**********************************************************************************************************************/
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
                      AS transport_year, dbo.Transport.Transport_Week, dbo.Transport.load_time
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

/**********************************************************************************************************************/
ALTER VIEW [dbo].[gavl_production_v]
AS
SELECT     dbo.Order_line.Order_line_id,dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, dbo.Order_line.attribute_info, 
                      dbo.Order_line.Number_of_items, dbo.Transport.Loading_date, dbo.Order_line.produced, CAST(dbo.Transport.Transport_Week AS nvarchar(2)) 
                      + '- ' + dbo.Transport.Transport_name AS transport_details, dbo.get_order_comment(customer_order.order_id) as Comment, ISNULL(dbo.Transport.Transport_Year, 9999) 
                      AS transport_year, dbo.Transport.Transport_Week,transport.load_time,customer_order.status as order_status
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


/**********************************************************************************************************************/
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
                      dbo.Transport.Transport_Week,transport.load_time
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

/**********************************************************************************************************************/
ALTER VIEW [dbo].[fakturering_v]
AS
SELECT     dbo.Customer_order.Order_id, dbo.Customer_order.Order_nr, dbo.Customer_order.Invoice_date, dbo.Customer.Customer_nr, 
                      dbo.Customer.First_name, dbo.Customer.Last_name, dbo.Customer_order.Postal_code, dbo.Customer_order.Post_office, 
                      dbo.Construction_type.Name AS Construction_name, dbo.Customer_order.Sent, dbo.Get_customer_cost(dbo.Customer_order.Order_id) 
                      AS customer_cost, dbo.get_order_comment(Customer_order.order_id) as Comment, dbo.get_assemblied_date(dbo.Customer_order.Order_id, dbo.Customer_order.Do_Assembly) 
                      AS assemblied_date
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
WHERE     (dbo.Customer_order.Sent IS NOT NULL) AND (COALESCE (dbo.Customer_order.Do_Assembly, 0) = 0) AND 
                      (dbo.Transport.Transport_name <> 'Historisk') OR
                      (dbo.Customer_order.Sent IS NOT NULL) AND (dbo.Customer_order.Do_Assembly = 1) AND (dbo.Transport.Transport_name <> 'Historisk') AND 
                      EXISTS
                          (SELECT     1 AS Expr1
                            FROM          dbo.[Assembly]
                            WHERE      (Order_id = dbo.Customer_order.Order_id) AND (assemblied_date IS NOT NULL))


/**********************************************************************************************************************/
ALTER VIEW [dbo].[front_production_v]
AS
SELECT     dbo.Order_line.Order_line_id, dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, dbo.Order_line.attribute_info, 
                      dbo.Order_line.Number_of_items, dbo.Transport.Loading_date, dbo.Order_line.produced, CAST(dbo.Transport.Transport_Week AS nvarchar(2)) 
                      + '- ' + dbo.Transport.Transport_name AS transport_details, dbo.get_order_comment(Customer_order.order_id) as Comment, ISNULL(dbo.Transport.Transport_Year, 9999) 
                      AS transport_year, dbo.Transport.Transport_Week,transport.load_time
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

/**********************************************************************************************************************/
ALTER VIEW [dbo].[packlist_v]
AS
SELECT     dbo.Customer_order.Order_id, dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.Transport.Loading_date, dbo.Customer_order.packlist_ready, 
                      dbo.get_order_comment(Customer_order.order_id) as Comment,
			dbo.get_number_of_article(customer_order.order_id,'Takstoler') as number_of_takstol,
			dbo.order_has_article(customer_order.order_id,'Gulvspon') as has_gulvspon,
dbo.get_number_of_article(customer_order.order_id,'Gulvspon') as number_of_gulvspon
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
WHERE     (dbo.Customer_order.Sent IS NULL) AND (dbo.Transport.Transport_name <> 'Historisk') OR
                      (dbo.Customer_order.Sent IS NULL) AND (dbo.Customer_order.Transport_id IS NULL)

/**********************************************************************************************************************/
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
		dbo.get_order_comment(Customer_order.order_id) as Comment,
		transport.transport_year,
		transport.transport_week,
		transport.loading_date
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
WHERE     (dbo.Customer_order.Transport_id IS NULL) AND (dbo.Customer_order.Sent IS NULL) OR
                      (dbo.Transport.Transport_name <> 'Historisk') AND (dbo.Customer_order.Sent IS NULL)
/**********************************************************************************************************************/
ALTER VIEW [dbo].[takstol_package_v]
AS
SELECT     dbo.Order_line.Order_line_id, dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, dbo.Order_line.attribute_info, 
                      dbo.Order_line.Number_of_items, dbo.Transport.Loading_date, dbo.Order_line.Colli_id, CAST(dbo.Transport.Transport_Week AS nvarchar(2)) 
                      + '- ' + dbo.Transport.Transport_name AS transport_details, dbo.get_order_comment(Customer_order.order_id) as Comment, ISNULL(dbo.Transport.Transport_Year, 9999) 
                      AS transport_year, dbo.Transport.Transport_Week,
transport.load_time
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

/**********************************************************************************************************************/
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
                      dbo.Transport.Transport_Week,transport.load_time
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


/**********************************************************************************************************************/
ALTER VIEW [dbo].[paid_v]
AS
SELECT     dbo.Customer_order.Order_id, dbo.Customer_order.Order_nr, dbo.Customer_order.paid_date, dbo.Customer.Customer_nr, dbo.Customer.First_name, 
                      dbo.Customer.Last_name, dbo.Customer_order.Postal_code, dbo.Customer_order.Post_office, dbo.Construction_type.Name AS Construction_name, 
                      dbo.Customer_order.Sent, dbo.Get_customer_cost(dbo.Customer_order.Order_id) AS customer_cost, dbo.get_order_comment(Customer_order.order_id) as Comment, 
                      dbo.Customer_order.Do_Assembly
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id
WHERE     dbo.Customer_order.Sent IS NULL

/**********************************************************************************************************************/
