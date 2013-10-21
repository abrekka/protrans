CREATE VIEW [dbo].[assembly_overdue_v]
AS
SELECT     MAX(Assembly_year) AS assembly_year, MAX(Assembly_week) AS assembly_week
FROM         dbo.[Assembly]
WHERE     (CAST(Assembly_year AS nvarchar(10)) + CAST(Assembly_week AS nvarchar(10)) < CAST(DATEPART(yyyy, GETDATE()) AS nvarchar(10)) 
                      + CAST(DATEPART(ww, GETDATE()) AS nvarchar(10))) AND (assemblied_date IS NULL)

/************************************************************************************************************/

alter table post_shipment add comment nvarchar(1000);