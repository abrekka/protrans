ALTER TABLE dbo.sales_data_snapshot add SegmentNo varchar(150);


/**************************************************************************************************************************************************

ALTER PROCEDURE [dbo].[create_sales_v_snapshot] 
AS
BEGIN
      DECLARE @StartDateInt datetime
      DECLARE @EndDateInt datetime

      select @StartDateInt = DATEADD(wk,DATEDIFF(wk,0,dateadd(dd,-1,GETDATE())),0)
      select @EndDateInt = dateadd(hh,23,DATEADD(wk,DATEDIFF(wk,0,dateadd(dd,-1,GETDATE())),6))
    	SET NOCOUNT ON;

	insert into application_log(log_date,log_type,log_msg) values(getdate(),'DEBUG','create_sales_v_snapshot startdate:'+cast(@StartDateInt as nvarchar)+' enddate:'+cast(@EndDateInt as nvarchar));

    -- Insert statements for procedure here
	--SELECT , 
	insert into sales_data_snapshot(sale_id,order_nr,probability,product_area_nr,saledate,registered,customer_nr,customer_name,delivery_address,postal_code,post_office,own_production_cost,transport_cost,assembly_cost,yes_lines,contribution_margin,county_name,salesman,order_date,segmentno) 
	(select sale_id,order_nr,probability,product_area_nr,saledate,registered,customer_nr,customer_name,delivery_address,postal_code,post_office,own_production_cost,transport_cost,assembly_cost,yes_lines,contribution_margin,county_name,salesman,order_date,segmentno
	  from sales_v
	  where saledate between @StartDateInt and @EndDateInt)
END



