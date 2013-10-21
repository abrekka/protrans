ALTER VIEW [dbo].[sum_avvik_produksjon_v]
AS
SELECT     sum(deviation_count) as deviation_count,
		product_area, 
		job_function_name, 
		registration_year, 
		registration_week, 
		sum(internal_cost) as internal_cost
FROM         dbo.sum_avvik_v
WHERE     (job_function_name = N'Produksjon')
group by product_area, 
		job_function_name, 
		registration_year, 
		registration_week
		
/***************************************************************************************/
ALTER VIEW [dbo].[sum_avvik_drift_prosjektering_v]
AS
SELECT     sum(deviation_count) as deviation_count, 
		product_area,
		job_function_name, 
		registration_year, 
		registration_week, 
		sum(internal_cost) as internal_cost
FROM         dbo.sum_avvik_v
WHERE     (job_function_name = N'Drift / Prosjektering')
group by product_area,
		job_function_name, 
		registration_year, 
		registration_week
		
/***************************************************************************************/
ALTER VIEW [dbo].[sum_avvik_montering_v]
AS
SELECT     sum(deviation_count) as deviation_count,
			product_area, 
			job_function_name, 
			registration_year, 
			registration_week, 
			sum(internal_cost) as internal_cost
FROM         dbo.sum_avvik_v
WHERE     (job_function_name = N'Montering')
group by product_area, 
			job_function_name, 
			registration_year, 
			registration_week
/***************************************************************************************/
ALTER VIEW [dbo].[sum_avvik_okonomi_v]
AS
SELECT     sum(deviation_count) as deviation_count,
			product_area, 
			job_function_name, 
			registration_year, 
			registration_week, 
			sum(internal_cost) as internal_cost
FROM         dbo.sum_avvik_v
WHERE     (job_function_name = N'Økonomi')
group by product_area, 
			job_function_name, 
			registration_year, 
			registration_week
			
/***************************************************************************************/
ALTER VIEW [dbo].[sum_avvik_salg_v]
AS
SELECT     sum(deviation_count) as deviation_count, 
			product_area,
			job_function_name, 
		registration_year, 
		registration_week, 
		sum(internal_cost) as internal_cost
FROM         dbo.sum_avvik_v
WHERE     (job_function_name = N'Salg')
group by product_area,
			job_function_name, 
		registration_year, 
		registration_week
		
/***************************************************************************************/
ALTER VIEW [dbo].[sum_avvik_transport_v]
AS
SELECT     sum(deviation_count) as deviation_count, 
			product_area,
			job_function_name, 
			registration_year, 
			registration_week, 
			sum(internal_cost) as internal_cost
FROM         dbo.sum_avvik_v
WHERE     (job_function_name = N'Transport')
group by product_area,
			job_function_name, 
			registration_year, 
			registration_week