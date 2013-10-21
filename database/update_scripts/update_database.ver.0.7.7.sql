ALTER VIEW [dbo].[assembly_overdue_v]
AS
SELECT     MIN(Assembly_year) AS assembly_year, MIN(Assembly_week) AS assembly_week
FROM         dbo.[Assembly]
WHERE     (CAST(Assembly_year AS nvarchar(10)) + CAST(Assembly_week AS nvarchar(10)) < CAST(DATEPART(yyyy, GETDATE()) AS nvarchar(10)) 
                      + CAST(DATEPART(ww, GETDATE()) AS nvarchar(10))) AND (assemblied_date IS NULL)
