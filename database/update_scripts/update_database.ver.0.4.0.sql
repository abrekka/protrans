alter table deviation add customer_name nvarchar(255);

/********************* has_order_line_top_article *******************************/
USE [ProTrans]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[has_order_line_top_article] 
(
	-- Add the parameters for the function here
	@orderLineId int,
	@constructionTypeArticleId int,
	@articleTypeId int
)
RETURNS int
AS
BEGIN
	DECLARE @hasTopLevel int

	if(@constructionTypeArticleId is not null)
	begin
	  select @hasTopLevel = article_type.top_level
		from construction_type_article,article_type
		where construction_type_article.construction_type_article_id = @constructionTypeArticleId
			and construction_type_article.article_type_id=article_type.article_type_id
			
	end
	else if(@articleTypeId is not null)
	begin
		select @hasTopLevel = article_type.top_level
			from article_type
			where article_type_id=@articleTypeId
	end
	else
		select @hasTopLevel = 0

	RETURN @hasTopLevel

END

/****************************************************/






/********************* is_order_line_ready *******************************/
USE [ProTrans]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[is_order_line_ready] 
(
	@orderLineId int,
	@isDefault int,
	@produced datetime
)
RETURNS int
AS
BEGIN
	DECLARE @ready int
	declare @notDefault int
	DECLARE attribute_cursor CURSOR FOR
		SELECT construction_type_article_attribute_id 
			FROM order_line_attribute
			WHERE order_line_id=@orderLineId


	if(@produced is not null)
		select @ready=1
    else if(@isDefault is not null)
        select @ready=@isDefault
	else
	begin
      select @notDefault = count(*) from order_line_attribute
		where order_line_attribute.order_line_id = @orderLineId
				and not exists(select 1 from construction_type_article_attribute
							where construction_type_article_attribute.construction_type_article_attribute_id = order_line_attribute.construction_type_article_attribute_id
								and construction_type_article_attribute.construction_type_article_value = order_line_attribute.order_line_attribute_value)

		if(@notDefault <> 0)
			select @ready=0
		else
			select @ready=1
	end

	
	RETURN @ready

END
/****************************************************/

/********************* is_order_ready_for_package *******************************/
USE [ProTrans]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[is_order_ready_for_package] 
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
						and dbo.is_order_line_ready(order_line_id,is_default,produced)=1

	if(@numberOf = @numberOfOrderLines)
      select @ready=1
	else
	  select @ready=0
	RETURN @ready

END
/****************************************************/


/********************* order_line_article_name *******************************/
USE [ProTrans]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[order_line_article_name] 
(
	@orderLineId int,
	@constructionTypeArticleId int,
	@articleTypeId int
)
RETURNS nvarchar(100)
AS
BEGIN
	DECLARE @articleName nvarchar(100)

	select @articleName='Garasjetype'

	if(@constructionTypeArticleId is not null)
		select @articleName=article_type.article_type_name
			from construction_type_article,article_type
			where construction_type_article.construction_type_article_id = @constructionTypeArticleId
				and construction_type_article.article_type_id = article_type.article_type_id
	else if (@articleTypeId is not null)
		select @articleName=article_type.article_type_name
			from article_type
			where article_type.article_type_id=@articleTypeId

	
	RETURN @articleName

END

/****************************************************/

/********************* order_line_has_article *******************************/
USE [ProTrans]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[order_line_has_article] 
(
	@orderLineId int,
	@hasArticle int,
	@articleName nvarchar
)
RETURNS int
AS
BEGIN
	DECLARE @has int
	declare @attributeName nvarchar
	declare @nos int

	select @attributeName = 'Har '+@articleName
	select @has=1

	if(@hasArticle is not null)
		select @has=@hasArticle
	else
	begin
		select @nos=count(*)
			from order_line_attribute
			where order_line_id = @orderLineId
				and order_line_attribute_name = @attributeName
				and order_line_attribute_value ='Nei'
		if(@nos<>0)
			select @has=0;
	end


	RETURN @has

END

/****************************************************/

/********************* is_order_done_package *******************************/
USE [ProTrans]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[is_order_done_package] 
(
	-- Add the parameters for the function here
	@orderId int,
	@colliesDone int
)
RETURNS int
AS
BEGIN
	DECLARE @donePackage int
	declare @numberNotDone int

	select @donePackage=1

	if(@colliesDone is not null)
		select @donePackage=@colliesDone
	else
	begin
		select @numberNotDone = count(*)
			from order_line
			where order_line.order_id=@orderId
				and dbo.has_order_line_top_article(order_line.order_line_id,order_line.construction_type_article_id,order_line.article_type_id)=1
				and order_line.colli_id is null
				and dbo.order_line_has_article(order_line_id,has_article,dbo.order_line_article_name(order_line_id,construction_type_article_id,article_type_id))=1
	end

	if(@numberNotDone <> 0)
		select @donePackage = 0
	

	RETURN @donePackage

END
/****************************************************/


/********************* main_package_v *******************************/
USE [ProTrans]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[main_package_v]
AS
SELECT     dbo.Customer_order.Order_id, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name as customer_details,customer_order.order_nr,customer_order.postal_code+' '+customer_order.post_office as address,Construction_type.Name as construction_type_name,customer_order.info
                      , CAST(dbo.Transport.Transport_Week AS nvarchar(2)) 
                      + '- ' + dbo.Transport.Transport_name AS transport_details, dbo.is_order_ready_for_package(dbo.Customer_order.Order_id) AS package_ready, 
                      dbo.is_order_done_package(dbo.Customer_order.Order_id, dbo.Customer_order.Collies_done) AS done_package, dbo.Customer_order.Comment,transport.transport_year,transport.transport_week
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
WHERE     (dbo.Customer_order.Transport_id IS NULL) AND (dbo.Customer_order.Sent IS NULL) OR
                      (dbo.Transport.Transport_name <> 'Historisk') AND (dbo.Customer_order.Sent IS NULL)

GO
/****************************************************/
