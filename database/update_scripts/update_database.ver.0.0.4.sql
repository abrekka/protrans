

CREATE TABLE Transport_temp (
	[Transport_id] [int] NOT NULL PRIMARY KEY,
	[Transport_Year] [int] NOT NULL ,
	[Transport_Week] [int] NOT NULL ,
	[Loading_date] [datetime] NULL 
) ON [PRIMARY]
Go

INSERT INTO Transport_temp(Transport_id,Transport_year,Transport_week,Loading_date) (SELECT Transport_id,year,week,Loading_date from transport);

CREATE TABLE Customer_order_temp (
	[Order_id] [int] NOT NULL PRIMARY KEY,
	[Order_nr] [nvarchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL ,
	[Delivery_address] [nvarchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL ,
	[Postal_code] [nvarchar] (4) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL ,
	[Post_office] [nvarchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL ,
	[Comment] [nvarchar] (255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL ,
	[Customer_id] [int] NOT NULL ,
	[Construction_type_id] [int] NOT NULL ,
	[Transport_id] [int] NULL ,
	Do_Assembly int NULL
) ON [PRIMARY]
GO
insert into customer_order_temp(Order_id,Order_nr,Delivery_address,Postal_code,Post_office,Comment,Customer_id,Construction_type_id,Transport_id,Do_Assembly) (select Order_id,Order_nr,Delivery_address,Postal_code,Post_office,Comment,Customer_id,Construction_type_id,Transport_id,Do_Assembly from customer_order);


create table Assembly_temp(
	Assembly_id int primary key not null,
	Assembly_team_id int not null references Assembly_team(Assembly_team_id),
	Order_id int not null references Customer_order_temp(Order_id),
	Assembly_year int not null,
	Assembly_week int not null,
	Invoiced int null,
	Comment nvarchar(255)
)
GO


insert into assembly_temp(Assembly_id,Assembly_team_id,Order_id,Assembly_year,Assembly_week,Invoiced,Comment) (select Assembly_id,Assembly_team_id,Order_id,Assembly_year,Assembly_week,Invoiced,Comment from assembly);
GO

DROP TABLE Assembly;
DROP TABLE Customer_order;
DROP TABLE Transport;
GO
CREATE TABLE Transport (
	[Transport_id] [int] IDENTITY (1, 1) NOT NULL PRIMARY KEY,
	[Transport_Year] [int] NOT NULL ,
	[Transport_Week] [int] NOT NULL ,
	[Loading_date] [datetime] NULL ,
	Transport_name nvarchar(50) NOT NULL
) ON [PRIMARY]
GO

set identity_insert transport on;
GO

INSERT INTO Transport(Transport_id,Transport_year,Transport_week,Loading_date) (SELECT Transport_id,transport_year,transport_week,Loading_date from transport_temp);
GO
set identity_insert transport off;
GO
CREATE TABLE Customer_order (
	[Order_id] [int] IDENTITY (1, 1) NOT NULL PRIMARY KEY,
	[Order_nr] [nvarchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL ,
	[Delivery_address] [nvarchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL ,
	[Postal_code] [nvarchar] (4) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL ,
	[Post_office] [nvarchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL ,
	[Comment] [nvarchar] (255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL ,
	[Customer_id] [int] NOT NULL REFERENCES Customer(Customer_id),
	[Construction_type_id] [int] NOT NULL REFERENCES Construction_type(Construction_type_id),
	[Transport_id] [int] NULL REFERENCES Transport(Transport_id),
	Do_Assembly int NULL,
	Order_date Datetime NOT NULL
) ON [PRIMARY]
GO

set identity_insert customer_order on;
GO
insert into customer_order(Order_id,Order_nr,Delivery_address,Postal_code,Post_office,Comment,Customer_id,Construction_type_id,Transport_id,Do_Assembly) (select Order_id,Order_nr,Delivery_address,Postal_code,Post_office,Comment,Customer_id,Construction_type_id,Transport_id,Do_Assembly from customer_order_temp);
GO
set identity_insert customer_order off;
GO
create table Assembly(
	Assembly_id int identity(1,1) primary key not null,
	Assembly_team_id int not null references Assembly_team(Assembly_team_id),
	Order_id int not null references Customer_order(Order_id),
	Assembly_year int not null,
	Assembly_week int not null,
	Invoiced int null,
	Comment nvarchar(255)
)
GO

set identity_insert assembly on;
GO
insert into assembly(Assembly_id,Assembly_team_id,Order_id,Assembly_year,Assembly_week,Invoiced,Comment) (select Assembly_id,Assembly_team_id,Order_id,Assembly_year,Assembly_week,Invoiced,Comment from assembly_temp);
GO
set identity_insert assembly off;
GO
DROP TABLE Assembly_temp;
DROP TABLE Customer_order_temp;
DROP TABLE Transport_temp;
GO
INSERT INTO Transport(Transport_Year,Transport_Week,Transport_name) VALUES(9999,0,'Historisk');
GO

ALTER TABLE Construction_type ADD Description nvarchar(255) null;
GO

CREATE TABLE Article_type(
  Article_type_id int identity(1,1) primary key not null,
  Article_type_name nvarchar(50) not null unique,
  Description nvarchar(255) null
);

CREATE TABLE Attribute(
  Attribute_id int identity(1,1) primary key not null,
  Name nvarchar(100) not null unique,
  Description nvarchar(255)
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
  Attribute_value nvarchar(50) not null
);

CREATE TABLE Construction_type_article(
  Construction_type_article_id int identity(1,1) primary key not null,
  Construction_type_id int references Construction_type(Construction_type_id) not null,
  Article_type_id int references Article_Type(Article_type_id) not null
  
);

CREATE TABLE Construction_type_article_attribute(
  Construction_type_article_attribute_id int identity(1,1) primary key not null,
  Construction_type_article_id int references Construction_type_article(Construction_type_article_id) not null,
  Article_type_attribute_id int references Article_type_attribute(Article_type_attribute_id) not null,
  Construction_type_article_value nvarchar(50) not null
);