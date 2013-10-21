create table application_log(
  log_id int identity(1,1) primary key,
  log_date datetime not null,
  log_type nvarchar(100),
  log_msg nvarchar (1000)
);
/************************************************************************************************
ALTER PROCEDURE [dbo].[create_sales_v_snapshot] 
AS
BEGIN
  DECLARE @StartDateInt int
  DECLARE @EndDateInt int

  select @StartDateInt = datediff(ss,convert(datetime,'1970-01-01 01:00:00',20),DATEADD(wk,DATEDIFF(wk,0,dateadd(dd,-1,GETDATE())),0))
  select @EndDateInt = datediff(ss,convert(datetime,'1970-01-01 01:00:00',20),dateadd(hh,23,DATEADD(wk,DATEDIFF(wk,0,dateadd(dd,-1,GETDATE())),6)))
--select @StartDateInt = datediff(ss,convert(datetime,'1970-01-01 01:00:00',20),DATEADD(wk,DATEDIFF(wk,0,convert(datetime,'2008-12-10 01:00:00',20)),0))
  --select @EndDateInt = datediff(ss,convert(datetime,'1970-01-01 01:00:00',20),DATEADD(wk,DATEDIFF(wk,0,convert(datetime,'2008-12-10 01:00:00',20)),6))
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	insert into application_log(log_date,log_type,log_msg) values(getdate(),'DEBUG','create_sales_v_snapshot startdate:'+cast(@StartDateInt as nvarchar)+' enddate:'+cast(@EndDateInt as nvarchar));

    -- Insert statements for procedure here
	--SELECT , 
	insert into sales_data_snapshot(sale_id,order_nr,probability,group_idx,saledate,registered,customer_nr,customer_name,delivery_address,postal_code,post_office,own_production_cost,transport_cost,assembly_cost,yes_lines,contribution_margin,county_name,salesman) 
	(select sale_id,order_nr,probability,group_idx,saledate,registered,customer_nr,customer_name,delivery_address,postal_code,post_office,own_production_cost,transport_cost,assembly_cost,yes_lines,contribution_margin,county_name,salesman
	  from sales_v
	  where saledate between @StartDateInt and @EndDateInt)
END
