alter table [Protrans2].[dbo].[Customer_order] add order_ready_wall datetime;
alter table [Protrans2].[dbo].[Customer_order] add order_ready_tross datetime;
alter table [Protrans2].[dbo].[Customer_order] add order_ready_pack datetime;
alter table [Protrans2].[dbo].[Customer_order] add packed_by_tross nvarchar(100);
alter table [Protrans2].[dbo].[Customer_order] add packed_by_pack nvarchar(100);

/*********************** Oppdatere slik at alle ordre som har order_ready får samme dato for alle order_ready-felter****************
update [Protrans2].[dbo].[Customer_order] set order_ready_wall=order_ready,order_ready_tross=order_ready,order_ready_pack=order_ready where order_ready is not null;