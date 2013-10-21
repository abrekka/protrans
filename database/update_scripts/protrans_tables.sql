

CREATE TABLE Application_user (
	[User_id] [int] IDENTITY (1, 1) NOT NULL PRIMARY KEY,
	[User_name] [nvarchar] (10)  NOT NULL UNIQUE,
	[First_name] [nvarchar] (50) NOT NULL ,
	[Last_name] [nvarchar] (50) NOT NULL ,
	[Password] [nvarchar] (50) NOT NULL ,
	group_user nvarchar(3) not null
) ON [PRIMARY]
GO

CREATE TABLE Construction_type (
	[Construction_type_id] [int] IDENTITY (1, 1) NOT NULL PRIMARY KEY,
	[Name] [nvarchar] (50) NOT NULL UNIQUE,
	Description nvarchar(255),
	Is_master int
) ON [PRIMARY]
GO

CREATE TABLE Customer (
	[Customer_id] [int] IDENTITY (1, 1) NOT NULL PRIMARY KEY,
	[Customer_nr] [int] NOT NULL UNIQUE,
	[First_name] [nvarchar] (50) NOT NULL ,
	[Last_name] [nvarchar] (50) NOT NULL 
) ON [PRIMARY]
GO



CREATE TABLE Special_concern (
	[Special_concern_id] [int] IDENTITY (1, 1) NOT NULL ,
	[Description] [nvarchar] (50) NOT NULL ,
	[Special_transport] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE Transport (
	[Transport_id] [int] IDENTITY (1, 1) NOT NULL PRIMARY KEY,
	[Transport_Year] [int] NOT NULL ,
	[Transport_Week] [int] NOT NULL ,
	[Loading_date] [datetime] NULL ,
	Transport_name nvarchar(50) not null
) ON [PRIMARY]
GO



CREATE TABLE Transport_company (
	[Transport_company_id] [int] IDENTITY (1, 1) NOT NULL PRIMARY KEY,
	[Company_name] [nvarchar] (50) NOT NULL 
) ON [PRIMARY]
GO



CREATE TABLE User_type (
	[User_type_id] [int] IDENTITY (1, 1) NOT NULL PRIMARY KEY,
	[Description] [nvarchar] (50) NOT NULL UNIQUE,
	Startup_window nvarchar(50),
	User_level int
) ON [PRIMARY]
GO

CREATE TABLE User_role (
	[Role_id] [int] IDENTITY (1, 1) NOT NULL PRIMARY KEY,
	[User_type_id] [int] NOT NULL REFERENCES User_type(User_type_id),
	[User_id] [int] NOT NULL REFERENCES Application_User(User_id)
) ON [PRIMARY]
GO

create table Assembly_team(
	Assembly_team_id int identity(1,1) not null primary key,
	Assembly_team_name nvarchar(155) not null unique
);



CREATE TABLE Customer_order (
	[Order_id] [int] IDENTITY (1, 1) NOT NULL PRIMARY KEY,
	[Order_nr] [nvarchar] (50) NOT NULL UNIQUE,
	[Delivery_address] [nvarchar] (50) NOT NULL ,
	[Postal_code] [nvarchar] (4) NOT NULL ,
	[Post_office] [nvarchar] (50) NOT NULL ,
	[Comment] [nvarchar] (255) NULL ,
	[Customer_id] [int] NOT NULL REFERENCES Customer(Customer_id),
	[Construction_type_id] [int] NOT NULL REFERENCES Construction_type(Construction_type_id),
	[Transport_id] [int] NULL REFERENCES Transport(Transport_id),
	Do_Assembly int NULL,
	Order_date Datetime NOT NULL,
	Invoiced int,
	Invoice_date datetime,
	Change_date datetime,
	Sent datetime,
	Info nvarchar(100) null,
	Status nvarchar(500),
	Collies_done int,
	Order_ready datetime,
	Order_complete datetime,
	Package_started datetime
) ON [PRIMARY]
GO


create table Assembly(
	Assembly_id int identity(1,1) primary key not null,
	Assembly_team_id int not null references Assembly_team(Assembly_team_id),
	Assembly_year int not null,
	Assembly_week int not null,
	Comment nvarchar(255),
	Order_id int not null references Customer_order(Order_id),
	Planned nvarchar(255) null,
	First_planned nvarchar(255) null,
	Assemblied nvarchar(3) null,
	Invoiced nvarchar(3) null
);




CREATE TABLE Article_type(
  Article_type_id int identity(1,1) primary key not null,
  Article_type_name nvarchar(50) not null unique,
  Description nvarchar(255) null,
  Top_level int
);

CREATE TABLE Attribute(
  Attribute_id int identity(1,1) primary key not null,
  Name nvarchar(100) not null unique,
  Description nvarchar(255),
  Yes_no int
);

CREATE TABLE Article_Type_Attribute(
  Article_type_attribute_id int identity(1,1) primary key not null,
  Article_type_id int not null references Article_type(Article_type_id),
  Attribute_id int not null references Attribute(Attribute_id),
  constraint article_attribute_uk unique(Article_type_id,Attribute_id)
);	

CREATE TABLE Construction_type_attribute(
  Construction_type_attribute_id int identity(1,1) primary key not null,
  Construction_type_id int not null references Construction_type(Construction_type_id),
  Attribute_id int not null references Attribute(Attribute_id),
  Attribute_value nvarchar(50) not null,
  Dialog_order int
);

CREATE TABLE Construction_type_article(
  Construction_type_article_id int identity(1,1) primary key not null,
  Construction_type_id int references Construction_type(Construction_type_id),
  Article_type_id int references Article_Type(Article_type_id) not null,
  Construction_type_article_ref int references Construction_type_article(Construction_type_article_id),
  Number_of_items numeric,
  Dialog_order int
  
);

CREATE TABLE Construction_type_article_attribute(
  Construction_type_article_attribute_id int identity(1,1) primary key not null,
  Construction_type_article_id int references Construction_type_article(Construction_type_article_id) not null,
  Article_type_attribute_id int references Article_type_attribute(Article_type_attribute_id) not null,
  Construction_type_article_value nvarchar(50) not null,
  Dialog_order int
);

CREATE TABLE Colli(
  Colli_id int identity(1,1) primary key not null,
  Order_id int references Customer_order(Order_id) not null,
  Colli_name nvarchar(255) not null,
  Transport_id int references Transport(Transport_id) null,
  Sent datetime null
);

CREATE TABLE Order_line(
  Order_line_id int identity(1,1) primary key not null,
  Order_id int references Customer_order(Order_id) null,
  Construction_type_article_id int references Construction_type_article(Construction_type_article_id),
  Article_type_id int references Article_type(Article_type_id),
  Order_line_ref int references Order_line(Order_line_id),
  Number_of_items numeric,
  produced datetime,
  Dialog_order int,
  Article_path nvarchar(500),
  Colli_id int references Colli(Colli_id) null,
  Has_article int,
  Attribute_info nvarchar(1000),
  Is_default int
);

CREATE TABLE Order_line_attribute(
  Order_line_attribute_id int identity(1,1) primary key not null,
  Order_line_id int references Order_line(Order_line_id) not null,
  Construction_type_attribute_id int references Construction_type_attribute(Construction_type_attribute_id),
  Construction_type_article_attribute_id int references Construction_type_article_attribute(Construction_type_article_attribute_id),
  Article_type_attribute_id int references Article_type_attribute(Article_type_attribute_id),
  Order_line_Attribute_value nvarchar(255) not null,
  Dialog_order int,
  Order_line_attribute_name nvarchar(255)
);

CREATE TABLE Article_type_article_type(
  Article_type_article_type_id int identity(1,1) primary key not null,
  Article_type_id int references Article_type(Article_type_id) not null,
  Article_type_id_ref int references Article_type(Article_type_id) not null
);

CREATE TABLE Cost_type(
  Cost_type_id int identity(1,1) primary key not null,
  Cost_type_name nvarchar(255) not null unique,
  Description nvarchar(255)
);

CREATE TABLE Cost_unit(
  Cost_unit_id int identity(1,1) primary key not null,
  Cost_unit_name nvarchar(255) not null unique,
  Description nvarchar(255)
);

CREATE TABLE Order_cost(
  Order_cost_id int identity(1,1) primary key not null,
  Order_id int not null references Customer_order(Order_id),
  Cost_type_id int not null references Cost_type(Cost_type_id),
  Cost_unit_id int references Cost_unit(Cost_unit_id),
  Cost_Amount numeric not null,
  Incl_vat int
);

CREATE TABLE Attribute_choice(
  Attribute_choice_id int identity(1,1) primary key not null,
  Attribute_id int not null references Attribute(Attribute_id),
  Choice_value nvarchar(255) not null,
  Comment nvarchar(255)
);

create table application_param(
  param_id int identity(1,1) not null primary key,
  param_name nvarchar(50) not null,
  param_value nvarchar(100) not null
);

create table job_function(
  job_function_id int identity(1,1) not null primary key,
  job_function_name nvarchar(100) not null,
  job_function_description nvarchar(255) null,
  user_id int references application_user(user_id) null
  
);

create table deviation_status(
  deviation_status_id int identity(1,1) not null primary key,
  deviation_status_name nvarchar(100) not null,
  deviation_status_description nvarchar(255) null
);

create table function_category(
  function_category_id int identity(1,1) not null primary key,
  function_category_name nvarchar(100) not null,
  function_category_description nvarchar(255) null,
  job_function_id int references job_function(job_function_id) not null
);

create table deviation(
  deviation_id int identity(1,1) not null primary key,
  registration_date datetime not null,
  user_name nvarchar(100) not null,
  description nvarchar(1000) null,
  customer_nr int null,
  order_nr int null,
  date_closed datetime null,
  free_text nvarchar(1000) null,
  product_name nvarchar(255) null,
  preventive_action nvarchar(3) null,
  own_function_id int references job_function(job_function_id) null,
  deviation_function_id int references job_function(job_function_id) null,
  deviation_status_id int references deviation_status(deviation_status_id) null,
  function_category_id int references function_category(function_category_id) null,
  internal_cost_id int references order_cost(order_cost_id) null,
  external_cost_id int references order_cost(order_cost_id) null,
  customer_name nvarchar(255)
);

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






INSERT INTO User_type(Description,Startup_window,User_level) VALUES('Administrator','no.ugland.utransprod.gui.ProTransMain',1);
INSERT INTO User_type(Description,Startup_window,User_level) VALUES('Gavlproduksjon','no.ugland.utransprod.gui.GavlProductionWindow',2);
INSERT INTO User_type(Description,Startup_window,User_level) VALUES('Takstolproduksjon','no.ugland.utransprod.gui.TakstolProductionWindow',3);
INSERT INTO User_type(Description,Startup_window,User_level) VALUES('Takstolpakking','no.ugland.utransprod.gui.TakstolPackageWindow',4);
INSERT INTO User_type(Description,Startup_window,User_level) VALUES('Garasjepakking','no.ugland.utransprod.gui.MainPackageWindow',5);

INSERT INTO Application_user(User_name,First_name,Last_name,Password) VALUES('admin','admin','admin','admin');
INSERT INTO Application_user(User_name,First_name,Last_name,Password) VALUES('gavl','gavl','gavl','gavl');
INSERT INTO Application_user(User_name,First_name,Last_name,Password) VALUES('takstol','takstol','takstol','takstol');
INSERT INTO Application_user(User_name,First_name,Last_name,Password) VALUES('takstol_pakking','takstol_pakking','takstol_pakking','takstol_pakking');
INSERT INTO Application_user(User_name,First_name,Last_name,Password) VALUES('garasjepakking','garasjepakking','garasjepakking','garasjepakking');

INSERT INTO User_role(User_type_id,User_id) VALUES(1,1);
INSERT INTO User_role(User_type_id,User_id) (select User_type.User_type_id,Application_user.User_id from user_type,application_user where user_type.description='Gavlproduksjon' and application_user.user_name='gavl');
INSERT INTO User_role(User_type_id,User_id) (select User_type.User_type_id,Application_user.User_id from user_type,application_user where user_type.description='Takstolproduksjon' and application_user.user_name='takstol');
INSERT INTO User_role(User_type_id,User_id) (select User_type.User_type_id,Application_user.User_id from user_type,application_user where user_type.description='Takstolpakking' and application_user.user_name='takstol_pakking');
INSERT INTO User_role(User_type_id,User_id) (select User_type.User_type_id,Application_user.User_id from user_type,application_user where user_type.description='Garasjepakking' and application_user.user_name='garasjepakking');

INSERT INTO Transport(Transport_Year,Transport_Week,Transport_name) VALUES(9999,0,'Historisk');

insert into application_param values('gavl_artikkel','Gavl');
insert into application_param values('excel_path','excel');
insert into application_param values('takstol_artikkel','Takstol');
insert into application_param values('stein_artikkel','Takstein');
insert into application_param values('gulvspon_attributt','Gulvspon');
insert into application_param values('gg_attributt','Sendes fra GG');
insert into application_param values('garasjetype_artikkel','Garasjetype');
insert into application_param values('gulvspon_artikkel','Gulvspon');

insert into application_param values('kolli_1','Garasjepakke;Garasjetype');
insert into application_param values('kolli_2','Takstol;Takstol');
insert into application_param values('kolli_3','Takstein;Takstein');
insert into application_param values('kolli_4','Gulvspon;Gulvspon');

--INSERT INTO User_type(Description,Startup_window,User_level) VALUES('Transport','no.ugland.utransprod.gui.TransportWindow',5);

--INSERT INTO Application_user(User_name,First_name,Last_name,Password) VALUES('transport','transport','transport','transport');

--INSERT INTO User_role(User_type_id,User_id) (select User_type.User_type_id,Application_user.User_id from user_type,application_user where user_type.description='Transport' and application_user.user_name='transport');