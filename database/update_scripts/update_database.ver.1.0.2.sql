CREATE VIEW [dbo].[post_shipment_count_v]
AS
SELECT     COUNT(dbo.Post_shipment.post_shipment_id) AS post_shipment_count, DATEPART(yyyy, dbo.Post_shipment.sent) AS sent_year, DATEPART(ww, 
                      dbo.Post_shipment.sent) AS sent_week, 
isnull(dbo.job_function.job_function_name,'Annet') as job_function_name
FROM         dbo.job_function RIGHT OUTER JOIN
                      dbo.deviation ON dbo.job_function.job_function_id = dbo.deviation.deviation_function_id RIGHT OUTER JOIN
                      dbo.Post_shipment ON dbo.deviation.deviation_id = dbo.Post_shipment.deviation_id
WHERE     (dbo.Post_shipment.sent IS NOT NULL)
GROUP BY DATEPART(yyyy, dbo.Post_shipment.sent), DATEPART(ww, dbo.Post_shipment.sent), dbo.job_function.job_function_name
