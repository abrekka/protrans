delete  FROM [Protrans2].[dbo].[Assembly] where order_id is null and deviation_id is null;
/***************************************************************************************************
alter table [Protrans2].[dbo].[Assembly] add constraint uk_order_id unique(order_id,deviation_id);