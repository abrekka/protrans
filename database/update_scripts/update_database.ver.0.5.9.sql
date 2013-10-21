create table supplier(
  supplier_id int identity (1,1) not null primary key,
  supplier_name nvarchar(255) not null unique,
  supplier_description nvarchar(255)
);

alter table order_cost drop column supplier_name;
alter table order_cost add supplier_id int references supplier(supplier_id);

insert into application_param(param_name,param_value) values('front_attributt_navn','Lager');
insert into application_param(param_name,param_value) values('front_attributt_verdi','Nei');

/******************************************************************************************
ALTER VIEW [dbo].[vegg_production_v]
AS
SELECT     dbo.Order_line.Order_line_id,dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, dbo.Order_line.attribute_info, 
                      dbo.Order_line.Number_of_items, dbo.Transport.Loading_date, dbo.Order_line.produced, 
					CAST(dbo.Transport.Transport_Week AS nvarchar(2)) + '- ' + dbo.Transport.Transport_name AS transport_details, 
					dbo.Customer_order.Comment, isnull(dbo.Transport.Transport_Year,9999) as transport_year, 
                      dbo.Transport.Transport_Week
FROM         dbo.Order_line INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.Order_line_attribute ON dbo.Order_line.Order_line_id = dbo.Order_line_attribute.Order_line_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
WHERE     (dbo.Customer_order.Sent IS NULL) AND 
	(dbo.Transport.Transport_name <> 'Historisk' or customer_order.transport_id is null) AND 
	--(dbo.is_order_line_default(dbo.Order_line.Order_line_id,dbo.Order_line.is_default) = 0) AND 
	dbo.order_line_attribute.order_line_attribute_name='Lager' and
	lower(dbo.order_line_attribute.order_line_attribute_value)='nei' and
	(dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, 
        dbo.Order_line.Article_type_id) = 'Vegg')


/****************************************************************************************************
ALTER FUNCTION [dbo].[is_order_line_ready] 
(
	@orderLineId int,
	@isDefault int,
	@produced datetime,
	@articleName nvarchar(100)
)
RETURNS int
AS
BEGIN
	DECLARE @ready int
	declare @notDefault int
	--DECLARE attribute_cursor CURSOR FOR
	--	SELECT construction_type_article_attribute_id 
	--		FROM order_line_attribute
	--		WHERE order_line_id=@orderLineId


	if(lower(rtrim(ltrim(@articleName))) = 'front' or lower(rtrim(ltrim(@articleName)))='vegg')
	begin
	  if(@produced is not null)
	    select @ready=1
         else
	  begin
            select @notDefault = count(*) from order_line_attribute
		where order_line_attribute.order_line_id = @orderLineId
			and lower(order_line_attribute.order_line_attribute_name) = 'lager'
			and lower(order_line_attribute.order_line_attribute_value) = 'nei'
	
		if(@notDefault <> 0)
			select @ready=0
		else
			select @ready=1
	  end
	end
	else
	  select @ready=1

	
	RETURN @ready

END

/**********************************************************************************************
ALTER FUNCTION [dbo].[is_order_ready_for_package] 
(
	@orderid int
)
RETURNS int
AS
BEGIN
	DECLARE @ready int
	declare @numberOf int
	declare @numberOfOrderLines int

	select @numberOf = count(*) 
		from order_line where order_line.order_id=@orderid 
						and order_line.article_path in('Vegg','Front')
	
	select @numberOfOrderLines = count(*)
		from order_line where order_line.order_id=@orderid 
						and order_line.article_path in('Vegg','Front')
						and dbo.is_order_line_ready(order_line_id,is_default,produced,dbo.order_line_article_name(order_line_id,construction_type_article_id,article_type_id))=1

	if(@numberOf = @numberOfOrderLines)
      select @ready=1
	else
	  select @ready=0
	RETURN @ready

END


