/************************* Sletter BYGGELEMENT som produktområde   ***********************************************
update [Protrans2].[dbo].[Application_user] set product_area_id=null where product_area_id=2;

delete
  FROM [Protrans2].[dbo].[Construction_type_attribute] 
  where exists(select 1 from [Protrans2].[dbo].[Construction_type] con 
                      where con.construction_type_id=[Protrans2].[dbo].[Construction_type_attribute] .Construction_type_id and con.product_area_id=2);
                      
delete
  FROM [Protrans2].[dbo].[Construction_type_article_attribute] where exists(select 1
  FROM [Protrans2].[dbo].[Construction_type_article] where [Protrans2].[dbo].[Construction_type_article_attribute].Construction_type_article_id=[Protrans2].[dbo].[Construction_type_article].Construction_type_article_id and exists(select 1 from [Protrans2].[dbo].[Construction_type] conn 
  where conn.Construction_type_id=[Protrans2].[dbo].[Construction_type_article].Construction_type_id and conn.product_area_id=2));
  
update [Protrans2].[dbo].[Construction_type_article] set Construction_type_article_ref=null where Construction_type_article_ref in(select Construction_type_article_id
  FROM [Protrans2].[dbo].[Construction_type_article] where exists(select 1 from [Protrans2].[dbo].[Construction_type] conn 
  where conn.Construction_type_id=[Protrans2].[dbo].[Construction_type_article].Construction_type_id and conn.product_area_id=2));
  
  delete
    FROM [Protrans2].[dbo].[Construction_type_article] where exists(select 1 from [Protrans2].[dbo].[Construction_type] conn 
  where conn.Construction_type_id=[Protrans2].[dbo].[Construction_type_article].Construction_type_id and conn.product_area_id=2);
  
delete   FROM [Protrans2].[dbo].[Construction_type] where product_area_id=2;

delete
  FROM [Protrans2].[dbo].[product_area] where product_area_nr=400;
  
  
/**********************Sletter Takstol intern som produktområde****************************************************************************************************************
delete
  FROM [Protrans2].[dbo].[product_area] where product_area_nr=1;
  
/*************************************Setter default kolli for Gavl til Takstol *****************************************************************************  
update [Protrans2].[dbo].[application_param] set param_value='Takstol;Gavl' where param_value='Gavl;Takstol';

/***************************************************************************************************************************************
ALTER VIEW [dbo].[own_production_v]
AS
SELECT dbo.Customer_order.Order_id
,dbo.Customer_order.packed_by
,dbo.Transport.Transport_Year
,dbo.Transport.Transport_Week
,dbo.Customer.Customer_nr
,dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_name
--,dbo.get_customer_cost_for_type(dbo.Customer_order.Order_id, N'Egenproduksjon') AS garage_value
,own_production_cost_v.sum_cost_amount as garage_value
,DATEPART(yyyy, isnull(dbo.Customer_order.order_ready,dbo.Customer_order.order_complete)) AS order_ready_year
,dbo.GetISOWeekNumberFromDate(isnull(dbo.Customer_order.order_ready,dbo.Customer_order.order_complete)) AS order_ready_week
,DATEPART(dw, isnull(dbo.Customer_order.order_ready,dbo.Customer_order.order_complete)) AS order_ready_day
,dbo.Customer_order.packlist_ready
,dbo.Customer_order.Invoice_date
,dbo.product_area.product_area
,dbo.Customer_order.Sent
,dbo.Customer_order.Order_nr
,dbo.product_area_group.product_area_group_name
,dbo.Customer_order.production_week
FROM dbo.Customer_order INNER JOIN
dbo.own_production_cost_v on dbo.Customer_order.order_id = dbo.own_production_cost_v.order_id INNER JOIN
dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id;
GO


