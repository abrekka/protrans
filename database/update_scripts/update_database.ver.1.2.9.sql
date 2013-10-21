ALTER VIEW [dbo].[assembly_overdue_v]
AS
SELECT     distinct Assembly_year AS assembly_year, 
		Assembly_week AS assembly_week
FROM         dbo.[Assembly]
WHERE     CAST(Assembly_year AS nvarchar(10)) + right('00' + CAST(Assembly_week as varchar(2)), 2) 
			< CAST(DATEPART(yyyy, GETDATE()) AS nvarchar(10)) 
                      + right('00' + CAST(DATEPART(ww, GETDATE()) AS nvarchar(10)), 2) AND 
(assemblied_date IS NULL and 
isnull(assembly.inactive,0) =0)
and not exists(select 1 
				from customer_order 
				where customer_order.order_id=assembly.order_id and 
						(customer_order.do_assembly=0 or
						customer_order.do_assembly is null))
and not exists(select 1 
				from deviation 
				where deviation.deviation_id=assembly.deviation_id and 
						(deviation.do_assembly=0 or
						deviation.do_assembly is null))
