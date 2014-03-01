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
  
  