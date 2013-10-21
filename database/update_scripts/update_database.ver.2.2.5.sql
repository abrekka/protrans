ALTER VIEW [dbo].[count_agreement_v]
AS
SELECT     COUNT(Order_id) AS order_count, 
			--isnull(villa,0) as villa,
			product_area.product_area,
			DATEPART(yyyy, agreement_date) AS agreement_year, 
			DATEPART(ww, agreement_date) AS agreement_week
FROM         dbo.Customer_order,product_area,construction_type
where agreement_date is not null and
		customer_order.product_area_id=product_area.product_area_id and customer_order.construction_type_id=construction_type.construction_type_id and lower(construction_type.name) not like '%tilleggsordre%'
GROUP BY --isnull(villa,0),
product_area.product_area,
DATEPART(yyyy, agreement_date), DATEPART(ww, agreement_date)
/*********************************************************************************************************************************