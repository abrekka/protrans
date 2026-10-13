CREATE TABLE [Protrans2].[dbo].Production_time (
	[Production_time_id] [int] IDENTITY (1, 1) NOT NULL PRIMARY KEY,
	[Order_nr] [nvarchar] (50) NOT NULL ,
	[Productionname] [nvarchar] (50) NOT NULL ,
	[Username] [nvarchar] (50) NOT NULL ,
	[Created] [datetime]  NOT NULL default getdate(),
	[Updated] [datetime]  NOT NULL default getdate(),
	[Started] [datetime]  NULL,
	[Stopped] [datetime] ,
	[Overtime] [int]
)