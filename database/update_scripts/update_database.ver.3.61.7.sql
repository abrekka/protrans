alter table [Protrans2].[dbo].[Colli] add created DATETIME2 CONSTRAINT colli_Created DEFAULT (SYSDATETIME());

alter table [Protrans2].[dbo].[Colli] add created_from nvarchar(255);
